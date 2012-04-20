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
 * $Id$
 */

package org.dataone.ore;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dataone.configuration.Settings;
import org.dataone.service.types.v1.Identifier;
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
import org.dspace.foresite.jena.TripleJena;

public class ResourceMapFactory {
	
	// TODO: will this always resolve?
	private static final String D1_URI_PREFIX = Settings.getConfiguration().getString("D1Client.CN_URL") + "/v1/object/";

	private static final String RESOURCE_MAP_SERIALIZATION_FORMAT = "RDF/XML";

	private static Predicate DC_TERMS_IDENTIFIER = null;
	
	private static Predicate CITO_IS_DOCUMENTED_BY = null;
	
	private static Predicate CITO_DOCUMENTS = null;

	private static ResourceMapFactory instance = null;
	
	private void init() throws URISyntaxException {
		// use as much as we can from the included Vocab for dcterms:Agent
		DC_TERMS_IDENTIFIER = new Predicate();
		DC_TERMS_IDENTIFIER.setNamespace(Vocab.dcterms_Agent.ns().toString());
		DC_TERMS_IDENTIFIER.setPrefix(Vocab.dcterms_Agent.schema());
		DC_TERMS_IDENTIFIER.setName("identifier");
		DC_TERMS_IDENTIFIER.setURI(new URI(DC_TERMS_IDENTIFIER.getNamespace() + DC_TERMS_IDENTIFIER.getName()));
		
		// create the CITO:isDocumentedBy predicate
		CITO_IS_DOCUMENTED_BY = new Predicate();
		CITO_IS_DOCUMENTED_BY.setNamespace("http://purl.org/spar/cito/");
		CITO_IS_DOCUMENTED_BY.setPrefix("cito");
		CITO_IS_DOCUMENTED_BY.setName("isDocumentedBy");
		CITO_IS_DOCUMENTED_BY.setURI(new URI(CITO_IS_DOCUMENTED_BY.getNamespace() + CITO_IS_DOCUMENTED_BY.getName()));
		
		// create the CITO:documents predicate
		CITO_DOCUMENTS = new Predicate();
		CITO_DOCUMENTS.setNamespace(CITO_IS_DOCUMENTED_BY.getNamespace());
		CITO_DOCUMENTS.setPrefix(CITO_IS_DOCUMENTED_BY.getPrefix());
		CITO_DOCUMENTS.setName("documents");
		CITO_DOCUMENTS.setURI(new URI(CITO_DOCUMENTS.getNamespace() + CITO_DOCUMENTS.getName()));
	}
	
	private ResourceMapFactory() {
		try {
			init();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ResourceMapFactory getInstance() {
		if (instance == null) {
			instance = new ResourceMapFactory();
		}
		return instance;
	}
	
	public ResourceMap createResourceMap(
			Identifier resourceMapId, 
			Map<Identifier, List<Identifier>> idMap) 
		throws OREException, URISyntaxException {
		
		// create the resource map and the aggregation
		// FIXME: more appropriate aggregation id
		Aggregation aggregation = OREFactory.createAggregation(new URI("ore://d1.aggregation"));
		ResourceMap resourceMap = aggregation.createResourceMap(new URI(D1_URI_PREFIX + resourceMapId.getValue()));
		
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
			AggregatedResource metadataResource = aggregation.createAggregatedResource(new URI(D1_URI_PREFIX + metadataId.getValue()));
			Triple metadataIdentifier = new TripleJena();
			metadataIdentifier.initialise(metadataResource);
			metadataIdentifier.relate(DC_TERMS_IDENTIFIER, metadataId.getValue());
			resourceMap.addTriple(metadataIdentifier);
			aggregation.addAggregatedResource(metadataResource);
	
			// iterate through data items
			List<Identifier> dataIds = idMap.get(metadataId);
			for (Identifier dataId: dataIds) {
				AggregatedResource dataResource = aggregation.createAggregatedResource(new URI(D1_URI_PREFIX + dataId.getValue()));
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
	
	public Map<Identifier, Map<Identifier, List<Identifier>>> parseResourceMap(String resourceMapContents) 
	throws OREException, URISyntaxException, UnsupportedEncodingException, OREParserException {
		InputStream is = new ByteArrayInputStream(resourceMapContents.getBytes("UTF-8"));
		return parseResourceMap(is);
	}
	
	//public Map<Identifier, List<Identifier>> parseResourceMap(InputStream is) 
	public Map<Identifier, Map<Identifier, List<Identifier>>> parseResourceMap(InputStream is) 
		throws OREException, URISyntaxException, UnsupportedEncodingException, OREParserException {
		
	    Map<Identifier, List<Identifier>> idMap = new HashMap<Identifier, List<Identifier>>();
		
		OREParser parser = OREParserFactory.getInstance(RESOURCE_MAP_SERIALIZATION_FORMAT);
		ResourceMap resourceMap = parser.parse(is);
        
		// get the identifier of the whole package ResourceMap
		Identifier packageId = new Identifier();
        TripleSelector packageIdSelector = new TripleSelector(resourceMap.getURI(), DC_TERMS_IDENTIFIER.getURI(), null);
        List<Triple> packageIdTriples = resourceMap.listTriples(packageIdSelector);
        if (!packageIdTriples.isEmpty()) {
            String packageIdValue = packageIdTriples.get(0).getObjectLiteral();
            packageId.setValue(packageIdValue);
        }
		
        // Now process the aggregation
        Aggregation aggregation = resourceMap.getAggregation();
		List<AggregatedResource> resources = aggregation.getAggregatedResources();
		for (AggregatedResource entry: resources) {
			// metadata entries should have everything we need to reconstruct the model
			Identifier metadataId = new Identifier();
			List<Identifier> dataIds = new ArrayList<Identifier>();
			
			TripleSelector documentsSelector = new TripleSelector(null, CITO_DOCUMENTS.getURI(), null);
			List<Triple> documentsTriples = entry.listTriples(documentsSelector);
			if (documentsTriples.isEmpty()) {
				continue;
			}
			
			// get the identifier of the metadata
			TripleSelector identifierSelector = new TripleSelector(null, DC_TERMS_IDENTIFIER.getURI(), null);
			List<Triple> identifierTriples = entry.listTriples(identifierSelector);
			if (!identifierTriples.isEmpty()) {
				String metadataIdValue = identifierTriples.get(0).getObjectLiteral();
				metadataId.setValue(metadataIdValue);
			}
			
			// iterate through the data entries to find dcterms:identifier
			for (Triple triple: documentsTriples) {
				String dataIdValue = null;
				
				// get the dataId reference we are documenting
				URI dataResourceURI = triple.getObjectURI();
				// look up the data object resource triple to find the dcterms:identifier for it
				TripleSelector dataIdentifierSelector = new TripleSelector(dataResourceURI, DC_TERMS_IDENTIFIER.getURI(), null);
				List<Triple> dataIdentifierTriples = resourceMap.listAllTriples(dataIdentifierSelector);
				if (!dataIdentifierTriples.isEmpty()) {
					// get the value of the identifier
					dataIdValue = dataIdentifierTriples.get(0).getObjectLiteral();
				}
				
				// add it to our list
				Identifier dataId = new Identifier();
				dataId.setValue(dataIdValue);
				dataIds.add(dataId);
			}
			
			// add the metadata entry with the data ids
			idMap.put(metadataId , dataIds);
			
		}
		
		// Now group the packageId with the Map of metadata/data Ids and return it
		Map<Identifier, Map<Identifier, List<Identifier>>> packageMap = new HashMap<Identifier, Map<Identifier, List<Identifier>>>();
		packageMap.put(packageId, idMap);
		
		return packageMap;
		
	}
	
	public String serializeResourceMap(ResourceMap resourceMap) throws ORESerialiserException {
		// serialize
		ORESerialiser serializer = ORESerialiserFactory.getInstance(RESOURCE_MAP_SERIALIZATION_FORMAT);
		ResourceMapDocument doc = serializer.serialise(resourceMap);
		String serialisation = doc.toString();
		return serialisation;
	}

}
