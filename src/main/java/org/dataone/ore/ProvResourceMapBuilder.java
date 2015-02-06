/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For 
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 * 
 * $Id: ResourceMapFactory.java 13886 2014-05-28 20:26:50Z rnahf $
 */

package org.dataone.ore;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.configuration.Settings;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.util.EncodingUtilities;
import org.dspace.foresite.Agent;
import org.dspace.foresite.AggregatedResource;
import org.dspace.foresite.Aggregation;
import org.dspace.foresite.OREException;
import org.dspace.foresite.OREFactory;
import org.dspace.foresite.OREParser;
import org.dspace.foresite.OREParserException;
import org.dspace.foresite.OREParserFactory;
import org.dspace.foresite.ORESerialiser;
import org.dspace.foresite.ORESerialiserException;
import org.dspace.foresite.ORESerialiserFactory;
import org.dspace.foresite.Predicate;
import org.dspace.foresite.ResourceMap;
import org.dspace.foresite.ResourceMapDocument;
import org.dspace.foresite.Triple;
import org.dspace.foresite.TripleSelector;
import org.dspace.foresite.Vocab;
import org.dspace.foresite.jena.JenaOREFactory;
import org.dspace.foresite.jena.ORE;
import org.dspace.foresite.jena.ResourceMapJena;
import org.dspace.foresite.jena.TripleJena;

import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Selector;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
/**
 *  A Resource Map builder with methods for adding providence-related statements. 
 *  Uses ResourceMapFactory...
 *  
 */
//TODO:  This is a straight-up copy of the ResourceMapFactory in turnk as of July 12, 2014
// The new methods found suggest a builder pattern, whereas ResourceMapFactory is 
// only a serialization and deserialization class for our package relationships.
// Further design work needed to determine the best relationships between the two
// classes and their proper placement.  The first question is whether or not this
// belongs in libclient or to the application.

public class ProvResourceMapBuilder {
	
	// TODO: will this always resolve?
	private static final String D1_URI_PREFIX = Settings.getConfiguration()
			.getString("D1Client.CN_URL","howdy") + "/v1/resolve/";

	private static final String RESOURCE_MAP_SERIALIZATION_FORMAT = "RDF/XML";

	private static Predicate DC_TERMS_IDENTIFIER = null;
	
	private static Predicate CITO_IS_DOCUMENTED_BY = null;
	
	private static Predicate CITO_DOCUMENTS = null;
	
	private static Predicate PROV_WAS_DERIVED_FROM = null;
	
	private static Predicate PROV_WAS_GENERATED_BY = null;
	
	private static Predicate PROV_WAS_INFORMED_BY = null;
	
	private static Predicate PROV_USED = null;

	private static Predicate PROV_WAS_ASSOCIATED_WITH = null;
	
	private static Predicate PROV_QUALIFIED_ASSOCIATION = null;

	private static Predicate PROV_P_AGENT = null;

	private static Predicate PROV_HAD_PLAN = null;

	private static List<Predicate> predicates = null;

	private static ProvResourceMapBuilder instance = null;
	
	private static Model oreModel = null;
	
	private static Log log = LogFactory.getLog(ProvResourceMapBuilder.class);
	
	private static final String CITO_NAMESPACE_URI = "http://purl.org/spar/cito/";
	
	private static final String PROV_NAMESPACE_URI = "http://www.w3.org/ns/prov#";

	private void init() throws URISyntaxException {
		predicates = new ArrayList<Predicate>();
		
		// use as much as we can from the included Vocab for dcterms:Agent
		DC_TERMS_IDENTIFIER = new Predicate();
		DC_TERMS_IDENTIFIER.setNamespace(Vocab.dcterms_Agent.ns().toString());
		DC_TERMS_IDENTIFIER.setPrefix(Vocab.dcterms_Agent.schema());
		DC_TERMS_IDENTIFIER.setName("identifier");
		DC_TERMS_IDENTIFIER.setURI(new URI(DC_TERMS_IDENTIFIER.getNamespace() 
				+ DC_TERMS_IDENTIFIER.getName()));
		
		// create the CITO:isDocumentedBy predicate
		CITO_IS_DOCUMENTED_BY = new Predicate();
		CITO_IS_DOCUMENTED_BY.setNamespace(CITO_NAMESPACE_URI);
		CITO_IS_DOCUMENTED_BY.setPrefix("cito");
		CITO_IS_DOCUMENTED_BY.setName("isDocumentedBy");
		CITO_IS_DOCUMENTED_BY.setURI(new URI(CITO_NAMESPACE_URI 
				+ CITO_IS_DOCUMENTED_BY.getName()));
		
		// create the CITO:documents predicate
		CITO_DOCUMENTS = new Predicate();
		CITO_DOCUMENTS.setNamespace(CITO_IS_DOCUMENTED_BY.getNamespace());
		CITO_DOCUMENTS.setPrefix(CITO_IS_DOCUMENTED_BY.getPrefix());
		CITO_DOCUMENTS.setName("documents");
		CITO_DOCUMENTS.setURI(new URI(CITO_NAMESPACE_URI 
				+ CITO_DOCUMENTS.getName()));
		
		// create the PROV:wasDerivedFrom predicate
		PROV_WAS_DERIVED_FROM = new Predicate();
		PROV_WAS_DERIVED_FROM.setNamespace(PROV_NAMESPACE_URI);
		PROV_WAS_DERIVED_FROM.setPrefix("prov");
		PROV_WAS_DERIVED_FROM.setName("wasDerivedFrom");
		PROV_WAS_DERIVED_FROM.setURI(new URI(PROV_NAMESPACE_URI 
						+ PROV_WAS_DERIVED_FROM.getName()));
		
		// create the PROV:wasGeneratedBy predicate
		PROV_WAS_GENERATED_BY = new Predicate();
		PROV_WAS_GENERATED_BY.setNamespace(PROV_NAMESPACE_URI);
		PROV_WAS_GENERATED_BY.setPrefix(PROV_WAS_DERIVED_FROM.getPrefix());
		PROV_WAS_GENERATED_BY.setName("wasGeneratedBy");
		PROV_WAS_GENERATED_BY.setURI(new URI(PROV_NAMESPACE_URI 
						+ PROV_WAS_GENERATED_BY.getName()));
		
		// create the PROV:wasInformedBy predicate
		PROV_WAS_INFORMED_BY = new Predicate();
		PROV_WAS_INFORMED_BY.setNamespace(PROV_NAMESPACE_URI);
		PROV_WAS_INFORMED_BY.setPrefix(PROV_WAS_DERIVED_FROM.getPrefix());
		PROV_WAS_INFORMED_BY.setName("wasInformedBy");
		PROV_WAS_INFORMED_BY.setURI(new URI(PROV_NAMESPACE_URI 
						+ PROV_WAS_INFORMED_BY.getName()));
		
		// create the PROV:used predicate
		PROV_USED = new Predicate();
		PROV_USED.setNamespace(PROV_NAMESPACE_URI);
		PROV_USED.setPrefix(PROV_WAS_DERIVED_FROM.getPrefix());
		PROV_USED.setName("used");
		PROV_USED.setURI(new URI(PROV_NAMESPACE_URI + PROV_USED.getName()));
		
		// create the PROV:wasAssociatedWith predicate
		PROV_WAS_ASSOCIATED_WITH = new Predicate();
		PROV_WAS_ASSOCIATED_WITH.setNamespace(PROV_NAMESPACE_URI);
		PROV_WAS_ASSOCIATED_WITH.setPrefix(PROV_USED.getPrefix());
		PROV_WAS_ASSOCIATED_WITH.setName("wasAssociatedWith");
		PROV_WAS_ASSOCIATED_WITH.setURI(new URI(PROV_NAMESPACE_URI + 
				PROV_WAS_ASSOCIATED_WITH.getName()));
		
		// create the PROV:qualifiedAssociation predicate
		PROV_QUALIFIED_ASSOCIATION = new Predicate();
		PROV_QUALIFIED_ASSOCIATION.setNamespace(PROV_NAMESPACE_URI);
		PROV_QUALIFIED_ASSOCIATION.setPrefix(PROV_WAS_ASSOCIATED_WITH.getPrefix());
		PROV_QUALIFIED_ASSOCIATION.setName("qualifiedAssociation");
		PROV_QUALIFIED_ASSOCIATION.setURI(new URI(PROV_NAMESPACE_URI + 
				PROV_QUALIFIED_ASSOCIATION.getName()));
		
		// create the PROV:agent predicate
		PROV_P_AGENT = new Predicate();
		PROV_P_AGENT.setNamespace(PROV_NAMESPACE_URI);
		PROV_P_AGENT.setPrefix(PROV_QUALIFIED_ASSOCIATION.getPrefix());
		PROV_P_AGENT.setName("agent");
		PROV_P_AGENT.setURI(new URI(PROV_NAMESPACE_URI + 
				PROV_P_AGENT.getName()));
		
		// create the PROV:hadPlan predicate
		PROV_HAD_PLAN = new Predicate();
		PROV_HAD_PLAN.setNamespace(PROV_NAMESPACE_URI);
		PROV_HAD_PLAN.setPrefix(PROV_P_AGENT.getPrefix());
		PROV_HAD_PLAN.setName("hadPlan");
		PROV_HAD_PLAN.setURI(new URI(PROV_NAMESPACE_URI + 
				PROV_HAD_PLAN.getName()));
		
		// include predicates from each namespace we want to support
		predicates.add(CITO_DOCUMENTS);
		predicates.add(PROV_WAS_DERIVED_FROM);
		predicates.add(PROV_WAS_GENERATED_BY);
		predicates.add(PROV_WAS_INFORMED_BY);
		predicates.add(PROV_USED);
		predicates.add(PROV_WAS_ASSOCIATED_WITH);
		predicates.add(PROV_QUALIFIED_ASSOCIATION);
		predicates.add(PROV_P_AGENT);
		predicates.add(PROV_HAD_PLAN);
	}
	
	private ProvResourceMapBuilder() {
		try {
			init();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Returns the singleton instance for this class.
	 * @return
	 */
	public static ProvResourceMapBuilder getInstance() {
		if (instance == null) {
			instance = new ProvResourceMapBuilder();
		}
		return instance;
	}
	
	/**
	 * creates a ResourceMap from the DataPackage representation.
	 * @param resourceMapId
	 * @param idMap
	 * @return
	 * @throws OREException
	 * @throws URISyntaxException
	 */
	public ResourceMap createResourceMap(
			Identifier resourceMapId, 
			Map<Identifier, List<Identifier>> idMap) 
		throws OREException, URISyntaxException {
				
		// create the resource map and the aggregation
		// NOTE: use distinct, but related URI for the aggregation
		Aggregation aggregation = OREFactory.createAggregation(new URI(D1_URI_PREFIX 
				+ EncodingUtilities.encodeUrlPathSegment(resourceMapId.getValue()) 
				+ "#aggregation"));
		ResourceMap resourceMap = aggregation.createResourceMap(new URI(D1_URI_PREFIX 
				+ EncodingUtilities.encodeUrlPathSegment(resourceMapId.getValue())));
		
		Agent creator = OREFactory.createAgent();
		creator.addName("Java libclient");
		resourceMap.addCreator(creator);
		// add the resource map identifier
		Triple resourceMapIdentifier = new TripleJena();
		resourceMapIdentifier.initialise(resourceMap);
		resourceMapIdentifier.relate(DC_TERMS_IDENTIFIER, resourceMapId.getValue());
		resourceMap.addTriple(resourceMapIdentifier);
		
		//aggregation.addCreator(creator);
		aggregation.addTitle("DataONE Aggregation");
		
		// iterate through the metadata items
		for (Identifier metadataId: idMap.keySet()) {
		
			// add the science metadata
			AggregatedResource metadataResource = aggregation.createAggregatedResource(new URI(D1_URI_PREFIX 
					+ EncodingUtilities.encodeUrlPathSegment(metadataId.getValue())));
			Triple metadataIdentifier = new TripleJena();
			metadataIdentifier.initialise(metadataResource);
			metadataIdentifier.relate(DC_TERMS_IDENTIFIER, metadataId.getValue());
			resourceMap.addTriple(metadataIdentifier);
			aggregation.addAggregatedResource(metadataResource);
	
			// iterate through data items
			List<Identifier> dataIds = idMap.get(metadataId);
			for (Identifier dataId: dataIds) {
				AggregatedResource dataResource = aggregation.createAggregatedResource(new URI(D1_URI_PREFIX 
						+ EncodingUtilities.encodeUrlPathSegment(dataId.getValue())));
				// dcterms:identifier
				Triple identifier = new TripleJena();
				identifier.initialise(dataResource);
				identifier.relate(DC_TERMS_IDENTIFIER, dataId.getValue());
				resourceMap.addTriple(identifier);
				// cito:isDocumentedBy
				Triple isDocumentedBy = new TripleJena();
				isDocumentedBy.initialise(dataResource);
				isDocumentedBy.relate(CITO_IS_DOCUMENTED_BY, metadataResource);
				resourceMap.addTriple(isDocumentedBy);
				// cito:documents (on metadata resource)
				Triple documents = new TripleJena();
				documents.initialise(metadataResource);
				documents.relate(CITO_DOCUMENTS, dataResource);
				resourceMap.addTriple(documents);
				
				aggregation.addAggregatedResource(dataResource);
			}
		}
		
		return resourceMap;
		
	}

	/**
	 * Adds a wasDerivedFrom triple to the specified Resource Map
	 * @param resourceMap
	 * @param primaryDataId
	 * @param derivedDataId
	 * @return
	 * @throws OREException
	 * @throws URISyntaxException
	 */
	public void addWasDerivedFrom(ResourceMap resourceMap, Identifier primaryDataId, Identifier derivedDataId)
	throws OREException, URISyntaxException{
		
		Triple triple = OREFactory.createTriple(
								new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(derivedDataId.getValue())), 
								PROV_WAS_DERIVED_FROM, 
								new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(primaryDataId.getValue())));
		resourceMap.addTriple(triple);				
	}
	
	
	
	/**
	 * Add multiple wasDerivedFrom triples to the specified Resource Map
	 * @param resourceMap
	 * @param idMap
	 * @return
	 * @throws OREException
	 * @throws URISyntaxException
	 */
	public void addWasDerivedFrom(ResourceMap resourceMap, Map<Identifier, List<Identifier>> idMap)
	throws OREException, URISyntaxException{
		
		//Iterate over each derived data ID
		for(Identifier derivedDataId: idMap.keySet()){
			//Get the list of primary data IDs
			List<Identifier> primaryDataIds = idMap.get(derivedDataId);
				for(Identifier primaryDataId: primaryDataIds){
				Triple triple = OREFactory.createTriple(
										new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(derivedDataId.getValue())), 
										PROV_WAS_DERIVED_FROM, 
										new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(primaryDataId.getValue())));
				resourceMap.addTriple(triple);
			}
		}		
	}
	
	
	/**
	 * Adds a wasGeneratedBy triple to the specified Resource Map
	 * @param resourceMap
	 * @param subjectId
	 * @param objectId
	 * @return
	 * @throws OREException
	 * @throws URISyntaxException
	 */
	public void addWasGeneratedBy(ResourceMap resourceMap, Identifier subjectId, Identifier objectId)
	throws OREException, URISyntaxException{
		
		Triple triple = OREFactory.createTriple(
								new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(subjectId.getValue())), 
								PROV_WAS_GENERATED_BY, 
								new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(objectId.getValue())));
		resourceMap.addTriple(triple);				
	}

	/**
	 * Add multiple addWasGeneratedBy triples to the specified Resource Map
	 * @param resourceMap
	 * @param idMap
	 * @return
	 * @throws OREException
	 * @throws URISyntaxException
	 */
	public void addWasGeneratedBy(ResourceMap resourceMap, Map<Identifier, List<Identifier>> idMap)
	throws OREException, URISyntaxException{
		
		//Iterate over each subject ID
		for(Identifier subjectId: idMap.keySet()){
			//Get the list of primary data IDs
			List<Identifier> objectIds = idMap.get(subjectId);
				for(Identifier objectId: objectIds){
				Triple triple = OREFactory.createTriple(
										new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(subjectId.getValue())), 
										PROV_WAS_GENERATED_BY, 
										new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(objectId.getValue())));
				resourceMap.addTriple(triple);
			}
		}		
	}
	
	
	/**
	 * Adds a addWasInformedBy triple to the specified Resource Map
	 * @param resourceMap
	 * @param subjectId
	 * @param objectId
	 * @return
	 * @throws OREException
	 * @throws URISyntaxException
	 */
	public void addWasInformedBy(ResourceMap resourceMap, Identifier subjectId, Identifier objectId)
	throws OREException, URISyntaxException{
		
		Triple triple = OREFactory.createTriple(
								new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(subjectId.getValue())), 
								PROV_WAS_INFORMED_BY, 
								new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(objectId.getValue())));
		resourceMap.addTriple(triple);				
	}

	/**
	 * Add multiple addWasInformedBy triples to the specified Resource Map
	 * @param resourceMap
	 * @param idMap
	 * @return
	 * @throws OREException
	 * @throws URISyntaxException
	 */
	public void addWasInformedBy(ResourceMap resourceMap, Map<Identifier, List<Identifier>> idMap)
	throws OREException, URISyntaxException{
		
		//Iterate over each subject ID
		for(Identifier subjectId: idMap.keySet()){
			//Get the list of primary data IDs
			List<Identifier> objectIds = idMap.get(subjectId);
				for(Identifier objectId: objectIds){
				Triple triple = OREFactory.createTriple(
										new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(subjectId.getValue())), 
										PROV_WAS_INFORMED_BY, 
										new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(objectId.getValue())));
				resourceMap.addTriple(triple);
			}
		}		
	}
	
	/**
	 * Adds a addUsed triple to the specified Resource Map
	 * @param resourceMap
	 * @param subjectId
	 * @param objectId
	 * @return
	 * @throws OREException
	 * @throws URISyntaxException
	 */
	public void addUsed(ResourceMap resourceMap, Identifier subjectId, Identifier objectId)
	throws OREException, URISyntaxException{
		
		Triple triple = OREFactory.createTriple(
								new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(subjectId.getValue())), 
								PROV_USED, 
								new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(objectId.getValue())));
		resourceMap.addTriple(triple);				
	}

	/**
	 * Add multiple addUsed triples to the specified Resource Map
	 * @param resourceMap
	 * @param idMap
	 * @return
	 * @throws OREException
	 * @throws URISyntaxException
	 */
	public void addUsed(ResourceMap resourceMap, Map<Identifier, List<Identifier>> idMap)
	throws OREException, URISyntaxException{
		
		//Iterate over each subject ID
		for(Identifier subjectId: idMap.keySet()){
			//Get the list of primary data IDs
			List<Identifier> objectIds = idMap.get(subjectId);
				for(Identifier objectId: objectIds){
				Triple triple = OREFactory.createTriple(
										new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(subjectId.getValue())), 
										PROV_USED, 
										new URI(D1_URI_PREFIX + EncodingUtilities.encodeUrlPathSegment(objectId.getValue())));
				resourceMap.addTriple(triple);
			}
		}		
	}
	
/*	/**
	 * Experimental.  Do not use!  Creates a ResourceMap that does not contain the inverse
	 * relationships for ore:aggregates, and cito:documents, requiring a reasoning
	 * model to reconstitute to the DataPackage representation. 
	 * @param resourceMapId
	 * @param idMap
	 * @return
	 * @throws OREException
	 * @throws URISyntaxException
	 */
/*	public ResourceMap createSparseResourceMap(
			Identifier resourceMapId, 
			Map<Identifier, List<Identifier>> idMap) 
		throws OREException, URISyntaxException {

	
		// create the resource map and the aggregation
		// NOTE: use distinct, but related URI for the aggregation
		Aggregation aggregation = OREFactory.createAggregation(new URI(D1_URI_PREFIX 
				+ EncodingUtilities.encodeUrlPathSegment(resourceMapId.getValue()) 
				+ "#aggregation"));
		ResourceMap resourceMap = aggregation.createResourceMap(new URI(D1_URI_PREFIX 
				+ EncodingUtilities.encodeUrlPathSegment(resourceMapId.getValue())));
		
		Agent creator = OREFactory.createAgent();
		creator.addName("Java libclient");
		resourceMap.addCreator(creator);
		// add the resource map identifier
		Triple resourceMapIdentifier = new TripleJena();
		resourceMapIdentifier.initialise(resourceMap);
		resourceMapIdentifier.relate(DC_TERMS_IDENTIFIER, resourceMapId.getValue());
		resourceMap.addTriple(resourceMapIdentifier);
		
		//aggregation.addCreator(creator);
		aggregation.addTitle("DataONE Aggregation");
		
		// iterate through the metadata items

		//		int i = 0;
		for (Identifier metadataId: idMap.keySet()) {
		
			// add the science metadata
			AggregatedResource metadataResource = aggregation.createAggregatedResource(new URI(D1_URI_PREFIX 
					+ EncodingUtilities.encodeUrlPathSegment(metadataId.getValue())));
			Triple metadataIdentifier = new TripleJena();
			metadataIdentifier.initialise(metadataResource);
			metadataIdentifier.relate(DC_TERMS_IDENTIFIER, metadataId.getValue());
			resourceMap.addTriple(metadataIdentifier);
			aggregation.addAggregatedResource(metadataResource);
	
			// iterate through data items
			List<Identifier> dataIds = idMap.get(metadataId);
			for (Identifier dataId: dataIds) {
				AggregatedResource dataResource = aggregation.createAggregatedResource(new URI(D1_URI_PREFIX 
						+ EncodingUtilities.encodeUrlPathSegment(dataId.getValue())));
				// dcterms:identifier
				Triple identifier = new TripleJena();
				identifier.initialise(dataResource);
				identifier.relate(DC_TERMS_IDENTIFIER, dataId.getValue());
				resourceMap.addTriple(identifier);

//				if ((i++ % 2) == 0) {
//					// cito:isDocumentedBy
//					Triple isDocumentedBy = new TripleJena();
//					isDocumentedBy.initialise(dataResource);
//					isDocumentedBy.relate(CITO_IS_DOCUMENTED_BY, metadataResource);
//					resourceMap.addTriple(isDocumentedBy);
//				} else {
				// cito:documents (on metadata resource)
					Triple documents = new TripleJena();
					documents.initialise(metadataResource);
					documents.relate(CITO_DOCUMENTS, dataResource);
					resourceMap.addTriple(documents);
//				}
				
				aggregation.addAggregatedResource(dataResource);
			}
		}
		
		return resourceMap;
		
	}
	*/
	
	
	/**
	 * Parses the string containing serialized form into the Map representation
     * used by the org.dataone.client.DataPackage class.
	 * @param resourceMapContents
	 * @return
	 * @throws OREException
	 * @throws URISyntaxException
	 * @throws UnsupportedEncodingException
	 * @throws OREParserException
	 */
	public Map<Identifier, Map<Identifier, List<Identifier>>> parseResourceMap(String resourceMapContents) 
	throws OREException, URISyntaxException, UnsupportedEncodingException, OREParserException {
		InputStream is = new ByteArrayInputStream(resourceMapContents.getBytes("UTF-8"));
		return parseResourceMap(is);
	}
	
    /**
     * Parses the input stream from the serialized form into the Map representation
     * used by the org.dataone.client.DataPackage class.
     * @param is
     * @return
     * @throws OREException - also thrown when identifier triple is missing from 
     *         any aggregated resource.
     * @throws URISyntaxException
     * @throws UnsupportedEncodingException
     * @throws OREParserException
     */
	public Map<Identifier, Map<Identifier, List<Identifier>>> parseResourceMap(InputStream is) 
	throws OREException, URISyntaxException, UnsupportedEncodingException, OREParserException 
	{
		// the inner map of the return object	
		Map<Identifier, List<Identifier>> idMap = new HashMap<Identifier, List<Identifier>>();
			
		OREParser parser = OREParserFactory.getInstance(RESOURCE_MAP_SERIALIZATION_FORMAT);
		ResourceMap resourceMap = parser.parse(is);
		if (resourceMap == null) {
			throw new OREException("Null resource map returned from OREParser (format: " + 
					RESOURCE_MAP_SERIALIZATION_FORMAT + ")");
		}
		
		TripleSelector idSelector = new TripleSelector(null, DC_TERMS_IDENTIFIER.getURI(), null);
		TripleSelector documentsSelector = new TripleSelector(null, CITO_DOCUMENTS.getURI(), null);
		TripleSelector isDocBySelector = new TripleSelector(null, CITO_IS_DOCUMENTED_BY.getURI(), null);
		
		
		
		// get the identifier of the whole package ResourceMap
		Identifier packageId = new Identifier();
		log.debug(resourceMap.getURI());
	
		
		// get the resource map identifier
		List<Triple> packageIdTriples = resourceMap.listTriples(idSelector);
		if (!packageIdTriples.isEmpty()) {
			packageId.setValue(packageIdTriples.get(0).getObjectLiteral());
		} else {
			throw new OREException("No Identifer statement was found for the " +
					"resourceMap resource ('" + resourceMap.getURI().toString() + "')");
		}
	
		// Process the aggregated resources to get the other relevant statements
		List<AggregatedResource> resources = resourceMap.getAggregation().getAggregatedResources();

		// assemble an identifier map from the aggregated resources first, to
		// make life easier later
		HashMap<String, Identifier> idHash = new HashMap<String,Identifier>();
		for (AggregatedResource ar: resources) {
			List<Triple> idTriples = ar.listTriples(idSelector);
			if (!idTriples.isEmpty()) {  // need an identifier to do anything
				Identifier arId = new Identifier();
				arId.setValue(idTriples.get(0).getObjectLiteral());
				idHash.put(ar.getURI().toString(), arId);
			} else {
				throw new OREException("Aggregated resource '" + ar.getURI().toString() + 
						"' in the resource map is missing the required Identifier statement");
			}
		}
		
		HashMap<String, Set<String>> metadataMap = new HashMap<String, Set<String>>();
		for (AggregatedResource ar: resources) {
			log.debug("Agg resource: " + ar.getURI());

			List<Triple> documentsTriples = ar.listTriples(documentsSelector);
			log.debug("--- documents count: " + documentsTriples.size());
			
			if (!documentsTriples.isEmpty()) {
				// get all of the objects this resource documents
				String metadataURI = ar.getURI().toString();
				log.debug("  ---metadataURI : "  + metadataURI);
				if (!metadataMap.containsKey(metadataURI))  {
					metadataMap.put(metadataURI, new HashSet<String>());
					log.debug("Creating new HashSet for: " + metadataURI + " : " + metadataMap.get(metadataURI).size());
				}
				for (Triple trip : documentsTriples) {
					String documentsObject = trip.getObjectURI().toString();
					log.debug("  ---documentsObject: " + documentsObject);
					metadataMap.get(metadataURI).add(documentsObject);
				}
			}
			
			
			List<Triple> docByTriples = ar.listTriples(isDocBySelector);
			log.debug("+++ isDocBy count: " + docByTriples.size());
			if (!docByTriples.isEmpty()) {
				// get all of the objects this resource is documented by
				String docBySubjectURI = ar.getURI().toString();
				log.debug("  +++docBySubjectURI: " + docBySubjectURI);
				for (Triple trip : docByTriples) {
					String metadataURI = trip.getObjectURI().toString();
					log.debug("  +++metadataURI: " + metadataURI);
					if (!metadataMap.containsKey(metadataURI)) {
						metadataMap.put(metadataURI,new HashSet<String>());
						log.debug("Creating new HashSet for: " + metadataURI + " : " + metadataMap.get(metadataURI).size());
					}
					metadataMap.get(metadataURI).add(docBySubjectURI);
				}
			}
		}
	
		for (String metadata : metadataMap.keySet()) {
			Identifier metadataID = idHash.get(metadata);
			List<Identifier> dataIDs = new ArrayList<Identifier>();
			log.debug("~~~~~ data count: " + metadata + ": " + metadataMap.get(metadata).size());
			for (String dataURI: metadataMap.get(metadata)) {
				Identifier pid = idHash.get(dataURI);
				dataIDs.add(pid);
			}
			idMap.put(metadataID, dataIDs);
		}

		// Now group the packageId with the Map of metadata/data Ids and return it
		Map<Identifier, Map<Identifier, List<Identifier>>> packageMap = 
				new HashMap<Identifier, Map<Identifier, List<Identifier>>>();
		packageMap.put(packageId, idMap);

		return packageMap;			
	}


	
	
	/**
	 * Serialize the ResourceMap
	 * @param resourceMap
	 * @return
	 * @throws ORESerialiserException
	 */
	public String serializeResourceMap(ResourceMap resourceMap) throws ORESerialiserException {
		// serialize
		ORESerialiser serializer = ORESerialiserFactory.getInstance(RESOURCE_MAP_SERIALIZATION_FORMAT);
		
		// set the NS prefixes we use in the predicates
		if (resourceMap instanceof ResourceMapJena) {
			Iterator<Predicate> tripleIter = predicates.iterator();
			while (tripleIter.hasNext()) {
				Predicate predicate = tripleIter.next();
				if (predicate != null && predicate.getPrefix() != null) {
					((ResourceMapJena) resourceMap).getModel().setNsPrefix(predicate.getPrefix(), predicate.getNamespace());
				}
			}
		}
		
		ResourceMapDocument doc = serializer.serialise(resourceMap);
		String serialisation = doc.toString();
		return serialisation;
	}
	
	
	/**
	 * Deserialize the inputStream into a ResourceMap using the standard OREParserFactory
	 * settings. (No inferences / reasoning)
	 * @param is
	 * @return ResourceMap
	 * @throws OREException
	 * @throws URISyntaxException
	 * @throws UnsupportedEncodingException
	 * @throws OREParserException
	 * @since v1.3
	 */
	public ResourceMap deserializeResourceMap(InputStream is)
	throws OREException, URISyntaxException, UnsupportedEncodingException, OREParserException 
	{		
		return deserializeResourceMap(is, false);
	}
	
	
	/**
	 * Deserialize the inputStream into a ResourceMap either literally (only asserted
	 * statements present), or including inferred statements.  The model configuration
	 * currently includes only the ORE model (http://www.openarchives.org/ore/terms)
	 * on top of the OWL DL ontology, so reasoning may be limited.
	 * @param is
	 * @param useReasoning - if true, the resourceMap will contain inferred statements
	 * @return ResourceMap
	 * @throws OREException
	 * @throws URISyntaxException
	 * @throws UnsupportedEncodingException
	 * @throws OREParserException
	 * @since v1.3
	 */
	// Note:  depending on the need, future development might employ a bridge pattern
	// by passing in a Model object instead of building it internally.  Not clear if
	// it's necessary now.
	public ResourceMap deserializeResourceMap(InputStream is, boolean useReasoning)
	throws OREException, URISyntaxException, UnsupportedEncodingException, OREParserException 
	{		
		ResourceMap resourceMap = null;
		if (useReasoning) {
			// need to load in the ORE model / schema for ORE inferences to work
			Model model = ModelFactory.createOntologyModel(
					OntModelSpec.OWL_DL_MEM_RULE_INF, 
					this.getOREModel()  /* passes in the ORE definitions */
					);
			model.read(is, null, RESOURCE_MAP_SERIALIZATION_FORMAT);
			Selector selector = new SimpleSelector(null, ORE.describes, (RDFNode) null);
			StmtIterator itr = model.listStatements(selector);
		
			if (itr.hasNext()) {
				Statement statement = itr.nextStatement();
				Resource resource = (Resource) statement.getSubject();
				resourceMap = JenaOREFactory.createResourceMap(model, new URI(resource.getURI()));
			}
		} else {
			// use the default (non-inferring model)
			OREParser parser = OREParserFactory.getInstance(RESOURCE_MAP_SERIALIZATION_FORMAT);
			resourceMap = parser.parse(is);
		}
		
		
		return resourceMap;
	}

	/**
	 * this method optimizes retrieving and caching the ORE model, used for 
	 * reasoning model
	 * @return
	 */
	protected Model getOREModel()
	{
		if (oreModel == null) {
			InputStream is = this.getClass().getResourceAsStream("www.openarchives.org-ore-terms.xml");
			oreModel = ModelFactory.createDefaultModel();
			if (is != null) {
				oreModel.read(is, null, RESOURCE_MAP_SERIALIZATION_FORMAT);
			} else {
				log.warn("Cannot find cached ORE terms document. Getting from namespace URI");
				oreModel.read("http://www.openarchives.org/ore/terms/");
			}
		}
		return oreModel;
	}
	
	
	/**
	 * Validate the ResourceMap against DataONE constraints (use of CN_Read.resolve,
	 * identifier triple defined, identifier matching URI, aggregation resource
	 * using {resourceMapID}#aggregation)
	 * @param resourceMap
	 * @return list of messages representing points of failure
	 * @throws OREException
	 * @throws UnsupportedEncodingException
	 * @since v1.3
	 */
	public List<String> validateResourceMap(ResourceMap resourceMap) 
	throws OREException, UnsupportedEncodingException 
	{
		List<String> messages = new LinkedList<String>();
		if (resourceMap == null) {
			messages.add("resource Map object was null");
			return messages;
		}
		
		// validate the resourceMap object's URI
		messages.addAll(validateD1Resource(resourceMap, resourceMap.getURI()));
	
		Aggregation aggregation = resourceMap.getAggregation();
		List<AggregatedResource> resources = aggregation.getAggregatedResources();
		for (AggregatedResource entry: resources) {
			log.info("aggregatedResource: " + entry.getURI());
			messages.addAll(validateD1Resource(resourceMap,entry.getURI()));     
		}

		if (!aggregation.getURI().toString().equals(resourceMap.getURI().toString() + "#aggregation"))
		{
			messages.add("the aggregation resource is not of the proper construct: " +
					"'{resourceMapURI}#aggregation'.  Got: " + aggregation.getURI().toString());
		}
		
		return messages;
	}
	
	
	private List<String> validateD1Resource(ResourceMap resourceMap, URI subjectURI) 
	throws UnsupportedEncodingException, OREException 
	{
		List<String> messages = new LinkedList<String>();
		String uriIdentifier = EncodingUtilities.decodeString(
				StringUtils.substringAfterLast(subjectURI.getRawPath(),"/"));
		
		String prefix = StringUtils.substringBeforeLast(subjectURI.toString(),"/");
		
		// 1. validate that using CN_READ.resolve endpoint
		if (!prefix.endsWith(".dataone.org/cn/v1/resolve")) {
			messages.add("A. uri does not use the DataONE CN_Read.resolve endpoint " +
					"(https://cn(.*).dataone.org/cn/v1/resolve). Got " + 
					prefix);
		} else if (!prefix.startsWith("https://cn")) {
			messages.add("A. uri does not use the DataONE CN_Read.resolve endpoint" +
					"(https://cn(.*).dataone.org/cn/v1/resolve/{pid}). Got " + 
					prefix);
		}
		TripleSelector idTripleSelector = 
        		new TripleSelector(subjectURI, DC_TERMS_IDENTIFIER.getURI(), null);
		
		List<Triple> triples = resourceMap.listAllTriples(idTripleSelector);
		
		// 2. validate that the identifier triple is stated
		if (triples.isEmpty()) {
			messages.add("B. Identifier triple is not asserted for " + subjectURI.toString());
		} else {

			String identifierValue = triples.get(0).getObjectLiteral();

			// 3. the identifier in the URI must match the identifier that's the object of the triple
			if (!uriIdentifier.equals(identifierValue)) {
				messages.add("C. Identifier object doesn't match the URI of the resource: " + subjectURI.toString() +
						" : " + uriIdentifier + " : " + identifierValue);
			}		
		}
		return messages;
	}
}