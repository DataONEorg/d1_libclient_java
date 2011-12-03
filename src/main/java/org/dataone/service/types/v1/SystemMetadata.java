
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** 
 * 
 System metadata (often referred to as :term:`sysmeta`) is the information used
 by DataONE to track and manage objects across the distributed Coordinating and
 Member Nodes of the network. System metadata documents contain low level
 information (e.g. size, type, owner, access control rules) about managed objects
 such as science data, science metadata, and resource map objects and the
 relationships between objects (e.g. obsoletes and obsoletedBy).

 The information is maintained dynamically by Coordinating Nodes and is mutable
 in that it reflects the current state of an object in the system. Initial
 properties of system metadata are generated by clients and Member Nodes. After
 object synchronization, the Coordinating Nodes hold authoritative copies of
 system metadata. Mirror copies of system metadata are maintained at each of the
 Coordinating nodes.

 System metadata are considered operational information needed to run DataONE,
 and can be read by all Coordinating Nodes and Member Nodes in the course of
 service provision. In order to reduce issues with third-party tracking of data
 status information, users can read system metadata for an object if they have
 the access rights to read the corresponding object which a system metadata
 record describes.

 System Metadata elements are partitioned into two classes: metadata elements
 that must be provided by client software to the DataONE system, and elements
 that are generated by DataONE itself in the course of managing objects.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="SystemMetadata">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:long" name="serialVersion" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Identifier" name="identifier"/>
 *     &lt;xs:element type="ns:ObjectFormatIdentifier" name="formatId"/>
 *     &lt;xs:element type="xs:long" name="size"/>
 *     &lt;xs:element type="ns:Checksum" name="checksum"/>
 *     &lt;xs:element type="ns:Subject" name="submitter"/>
 *     &lt;xs:element type="ns:Subject" name="rightsHolder"/>
 *     &lt;xs:element type="ns:AccessPolicy" name="accessPolicy" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:ReplicationPolicy" name="replicationPolicy" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Identifier" name="obsoletes" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Identifier" name="obsoletedBy" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:boolean" name="archived" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="dateUploaded"/>
 *     &lt;xs:element type="xs:dateTime" name="dateSysMetadataModified"/>
 *     &lt;xs:element type="ns:NodeReference" name="originMemberNode" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:NodeReference" name="authoritativeMemberNode" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Replica" name="replica" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SystemMetadata implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private BigInteger serialVersion;
    private Identifier identifier;
    private ObjectFormatIdentifier formatId;
    private BigInteger size;
    private Checksum checksum;
    private Subject submitter;
    private Subject rightsHolder;
    private AccessPolicy accessPolicy;
    private ReplicationPolicy replicationPolicy;
    private Identifier obsoletes;
    private Identifier obsoletedBy;
    private Boolean archived;
    private Date dateUploaded;
    private Date dateSysMetadataModified;
    private NodeReference originMemberNode;
    private NodeReference authoritativeMemberNode;
    private List<Replica> replicaList = new ArrayList<Replica>();

    /** 
     * Get the 'serialVersion' element value. 
              		A serial number maintained by the coordinating node it indicate when
              		changes have occurred to SystemMetadata to avoid update conflicts. Clients
              		should ensure that they have the most recent version of a SystemMetadata
              		document before attempting to update, otherwise an error will be thrown to
              		prevent conflicts.
              		
     * 
     * @return value
     */
    public BigInteger getSerialVersion() {
        return serialVersion;
    }

    /** 
     * Set the 'serialVersion' element value. 
              		A serial number maintained by the coordinating node it indicate when
              		changes have occurred to SystemMetadata to avoid update conflicts. Clients
              		should ensure that they have the most recent version of a SystemMetadata
              		document before attempting to update, otherwise an error will be thrown to
              		prevent conflicts.
              		
     * 
     * @param serialVersion
     */
    public void setSerialVersion(BigInteger serialVersion) {
        this.serialVersion = serialVersion;
    }

    /** 
     * Get the 'identifier' element value. 
              		The identifier of the object that is described by this system metadata document.

    					The unique Unicode string that is used to canonically name and identify the
    					object in DataONE. Each object in DataONE is immutable, and therefore all
    					objects must have a unique Identifier. If two objects are related to one
    					another (such as one object is a more recent version of another object), each
    					of these two objects will have unique identifiers. The relationship among the
    					objects is specified in other metadata fields (see Obsoletes and ObsoletedBy),
    					but this does not preclude the inclusion of version information in the
    					identifier string. However, DataONE treats all Identifiers as opaque and will
    					not try to infer versioning semantics based on the content of the Identifiers
    					-- rather, this information is found in the Obsoletes and ObsoletedBy fields.

    					Note that identifiers are used in a number of REST API calls as parts of the
    					URL path. As such, all special characters such as "/", " ", "+", "\", "%"
    					must be properly encoded, e.g. "%2F", "%20", "%2B", "%5C", "%25"
    					respectively when used in REST method calls. See RFC3896_ for more details.
    					For example, the getObject() call for an object with identifier::

      				http://some.location.name/mydata.cgi?id=2088

    					would be::

      				http://mn1.server.name/mn/http:%2F%2Fsome.location.name%2Fmydata.cgi%3Fid%3D2088
              
    				.. _RFC3896: http://www.ietf.org/rfc/rfc3896.txt
              		
     * 
     * @return value
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /** 
     * Set the 'identifier' element value. 
              		The identifier of the object that is described by this system metadata document.

    					The unique Unicode string that is used to canonically name and identify the
    					object in DataONE. Each object in DataONE is immutable, and therefore all
    					objects must have a unique Identifier. If two objects are related to one
    					another (such as one object is a more recent version of another object), each
    					of these two objects will have unique identifiers. The relationship among the
    					objects is specified in other metadata fields (see Obsoletes and ObsoletedBy),
    					but this does not preclude the inclusion of version information in the
    					identifier string. However, DataONE treats all Identifiers as opaque and will
    					not try to infer versioning semantics based on the content of the Identifiers
    					-- rather, this information is found in the Obsoletes and ObsoletedBy fields.

    					Note that identifiers are used in a number of REST API calls as parts of the
    					URL path. As such, all special characters such as "/", " ", "+", "\", "%"
    					must be properly encoded, e.g. "%2F", "%20", "%2B", "%5C", "%25"
    					respectively when used in REST method calls. See RFC3896_ for more details.
    					For example, the getObject() call for an object with identifier::

      				http://some.location.name/mydata.cgi?id=2088

    					would be::

      				http://mn1.server.name/mn/http:%2F%2Fsome.location.name%2Fmydata.cgi%3Fid%3D2088
              
    				.. _RFC3896: http://www.ietf.org/rfc/rfc3896.txt
              		
     * 
     * @param identifier
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /** 
     * Get the 'formatId' element value. 
              		Designation of the standard or format that should be used to interpret the
    					contents of the object, drawn from controlled list of formats that are provided by the DataONE
    					ObjectFormat service. DataONE maintains a list of formats in use and their
    					canonical FormatIdentifiers. The format identifier for an object should
    					imply its mime type for data objects and metadata type and serialization
    					format for metadata objects. Examples include the namespace of the EML 2.1
    					metadata specification, the DOCTYPE of the Biological Data Profile, the mime
    					type of text/csv files, and the canonincal name of the NetCDF specification.
              		
     * 
     * @return value
     */
    public ObjectFormatIdentifier getFormatId() {
        return formatId;
    }

    /** 
     * Set the 'formatId' element value. 
              		Designation of the standard or format that should be used to interpret the
    					contents of the object, drawn from controlled list of formats that are provided by the DataONE
    					ObjectFormat service. DataONE maintains a list of formats in use and their
    					canonical FormatIdentifiers. The format identifier for an object should
    					imply its mime type for data objects and metadata type and serialization
    					format for metadata objects. Examples include the namespace of the EML 2.1
    					metadata specification, the DOCTYPE of the Biological Data Profile, the mime
    					type of text/csv files, and the canonincal name of the NetCDF specification.
              		
     * 
     * @param formatId
     */
    public void setFormatId(ObjectFormatIdentifier formatId) {
        this.formatId = formatId;
    }

    /** 
     * Get the 'size' element value. 
              		The size of the object in bytes.
              		
     * 
     * @return value
     */
    public BigInteger getSize() {
        return size;
    }

    /** 
     * Set the 'size' element value. 
              		The size of the object in bytes.
              		
     * 
     * @param size
     */
    public void setSize(BigInteger size) {
        this.size = size;
    }

    /** 
     * Get the 'checksum' element value. 
              		A calculated hash value used to validate object integrity over time and
    					after network transfers. The value is calculated using a standard hashing
    					algorithm that is accepted by DataONE and that is indicated in
    					the included ChecksumAlgorithm attribute.
              		
     * 
     * @return value
     */
    public Checksum getChecksum() {
        return checksum;
    }

    /** 
     * Set the 'checksum' element value. 
              		A calculated hash value used to validate object integrity over time and
    					after network transfers. The value is calculated using a standard hashing
    					algorithm that is accepted by DataONE and that is indicated in
    					the included ChecksumAlgorithm attribute.
              		
     * 
     * @param checksum
     */
    public void setChecksum(Checksum checksum) {
        this.checksum = checksum;
    }

    /** 
     * Get the 'submitter' element value. 
              		Subject who submitted the associated abject to the DataONE Member Node. The
    					Submitter is by default the RightsHolder if a RightsHolder has not been specified.
              		
     * 
     * @return value
     */
    public Subject getSubmitter() {
        return submitter;
    }

    /** 
     * Set the 'submitter' element value. 
              		Subject who submitted the associated abject to the DataONE Member Node. The
    					Submitter is by default the RightsHolder if a RightsHolder has not been specified.
              		
     * 
     * @param submitter
     */
    public void setSubmitter(Subject submitter) {
        this.submitter = submitter;
    }

    /** 
     * Get the 'rightsHolder' element value. 
              		Subject that has ultimate authority for object and is authorized to make all
    					decisions regarding the disposition and accessibility of the object. The
    					rightsHolder has all rights to access the object, update the object, and grant
    					permissions for the object, even if additional access control rules are not
    					specified for the object.
              		
     * 
     * @return value
     */
    public Subject getRightsHolder() {
        return rightsHolder;
    }

    /** 
     * Set the 'rightsHolder' element value. 
              		Subject that has ultimate authority for object and is authorized to make all
    					decisions regarding the disposition and accessibility of the object. The
    					rightsHolder has all rights to access the object, update the object, and grant
    					permissions for the object, even if additional access control rules are not
    					specified for the object.
              		
     * 
     * @param rightsHolder
     */
    public void setRightsHolder(Subject rightsHolder) {
        this.rightsHolder = rightsHolder;
    }

    /** 
     * Get the 'accessPolicy' element value. 
              		The accessPolicy determines which Subjects are allowed to make changes to
              		an object in addition to the RightsHolder and AuthoritativeMemberNode.
              		The accessPolicy is set for an object during a create() or update() call, or
              		when SystemMetadata is updated on the Coordinating Node via various mechanisms.
    					This policy replaces any existing policies that might exist for the object.
    					Member Nodes that house an object are obligated to enforce the accessPolicy for
    					that object.
              		
     * 
     * @return value
     */
    public AccessPolicy getAccessPolicy() {
        return accessPolicy;
    }

    /** 
     * Set the 'accessPolicy' element value. 
              		The accessPolicy determines which Subjects are allowed to make changes to
              		an object in addition to the RightsHolder and AuthoritativeMemberNode.
              		The accessPolicy is set for an object during a create() or update() call, or
              		when SystemMetadata is updated on the Coordinating Node via various mechanisms.
    					This policy replaces any existing policies that might exist for the object.
    					Member Nodes that house an object are obligated to enforce the accessPolicy for
    					that object.
              		
     * 
     * @param accessPolicy
     */
    public void setAccessPolicy(AccessPolicy accessPolicy) {
        this.accessPolicy = accessPolicy;
    }

    /** 
     * Get the 'replicationPolicy' element value. 
              		A controlled list of policy choices that determine how many replicas
    					should be maintained for a given object and any preferences or
    					requirements as to which Member Nodes should be allowed to house the
    					replicas that are created. The policy determines whether replication is allowed, the
    					number of replicas desired, the list of preferred nodes to hodl the replicas, and a 
    					list of blocked nodes on which replicas must not exist.
              		
     * 
     * @return value
     */
    public ReplicationPolicy getReplicationPolicy() {
        return replicationPolicy;
    }

    /** 
     * Set the 'replicationPolicy' element value. 
              		A controlled list of policy choices that determine how many replicas
    					should be maintained for a given object and any preferences or
    					requirements as to which Member Nodes should be allowed to house the
    					replicas that are created. The policy determines whether replication is allowed, the
    					number of replicas desired, the list of preferred nodes to hodl the replicas, and a 
    					list of blocked nodes on which replicas must not exist.
              		
     * 
     * @param replicationPolicy
     */
    public void setReplicationPolicy(ReplicationPolicy replicationPolicy) {
        this.replicationPolicy = replicationPolicy;
    }

    /** 
     * Get the 'obsoletes' element value. 
              		The Identifier of an object that is a prior version of the object
    					described in this system metadata record and that is obsoleted by this object. When an 
    					object is obsoleted, it is removed from all DataONE search indices but is
    					still accessible from the :func:`CNRead.get` service.
              		
     * 
     * @return value
     */
    public Identifier getObsoletes() {
        return obsoletes;
    }

    /** 
     * Set the 'obsoletes' element value. 
              		The Identifier of an object that is a prior version of the object
    					described in this system metadata record and that is obsoleted by this object. When an 
    					object is obsoleted, it is removed from all DataONE search indices but is
    					still accessible from the :func:`CNRead.get` service.
              		
     * 
     * @param obsoletes
     */
    public void setObsoletes(Identifier obsoletes) {
        this.obsoletes = obsoletes;
    }

    /** 
     * Get the 'obsoletedBy' element value. 
              		The Identifier of an object that is a subsequent version of the object
    					described in this system metadata record and that therefore obsoletes this 
    					object. When an object is obsoleted, it is removed from all DataONE search indices but is
    					still accessible from the :func:`CNRead.get` service.
              		
     * 
     * @return value
     */
    public Identifier getObsoletedBy() {
        return obsoletedBy;
    }

    /** 
     * Set the 'obsoletedBy' element value. 
              		The Identifier of an object that is a subsequent version of the object
    					described in this system metadata record and that therefore obsoletes this 
    					object. When an object is obsoleted, it is removed from all DataONE search indices but is
    					still accessible from the :func:`CNRead.get` service.
              		
     * 
     * @param obsoletedBy
     */
    public void setObsoletedBy(Identifier obsoletedBy) {
        this.obsoletedBy = obsoletedBy;
    }

    /** 
     * Get the 'archived' element value. A boolean flag, set to 'true' if the object has been classified
              		as archived. An archived object does not show up in search indexes in DataONE, but
              		is still accessible via the CNRead and MNRead services if associated access polices
              		allow. The field is optional, and if absent, then objects are implied to not be archived,
              		which is the same as setting archived to false.
              		
     * 
     * @return value
     */
    public Boolean getArchived() {
        return archived;
    }

    /** 
     * Set the 'archived' element value. A boolean flag, set to 'true' if the object has been classified
              		as archived. An archived object does not show up in search indexes in DataONE, but
              		is still accessible via the CNRead and MNRead services if associated access polices
              		allow. The field is optional, and if absent, then objects are implied to not be archived,
              		which is the same as setting archived to false.
              		
     * 
     * @param archived
     */
    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    /** 
     * Get the 'dateUploaded' element value. 
              		Date and time (UTC) that the object was uploaded into the DataONE system,
    					which is typically the time that the object is first created on a Member
    					Node using the 'create()' operation. Note this is independent of the 
    					publication or release date of the object.
              		
     * 
     * @return value
     */
    public Date getDateUploaded() {
        return dateUploaded;
    }

    /** 
     * Set the 'dateUploaded' element value. 
              		Date and time (UTC) that the object was uploaded into the DataONE system,
    					which is typically the time that the object is first created on a Member
    					Node using the 'create()' operation. Note this is independent of the 
    					publication or release date of the object.
              		
     * 
     * @param dateUploaded
     */
    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    /** 
     * Get the 'dateSysMetadataModified' element value. 
              		Date and time (UTC) that this system metadata record was last modified in the 
    					DataONE system. This is the same timestamp as DateUploaded until the system
    					metadata is further modified.
              		
     * 
     * @return value
     */
    public Date getDateSysMetadataModified() {
        return dateSysMetadataModified;
    }

    /** 
     * Set the 'dateSysMetadataModified' element value. 
              		Date and time (UTC) that this system metadata record was last modified in the 
    					DataONE system. This is the same timestamp as DateUploaded until the system
    					metadata is further modified.
              		
     * 
     * @param dateSysMetadataModified
     */
    public void setDateSysMetadataModified(Date dateSysMetadataModified) {
        this.dateSysMetadataModified = dateSysMetadataModified;
    }

    /** 
     * Get the 'originMemberNode' element value. 
              		A reference to the Member Node that originally uploaded the associated
    					object. This value should never change, even if the Member Node ceases to exist.
              		
     * 
     * @return value
     */
    public NodeReference getOriginMemberNode() {
        return originMemberNode;
    }

    /** 
     * Set the 'originMemberNode' element value. 
              		A reference to the Member Node that originally uploaded the associated
    					object. This value should never change, even if the Member Node ceases to exist.
              		
     * 
     * @param originMemberNode
     */
    public void setOriginMemberNode(NodeReference originMemberNode) {
        this.originMemberNode = originMemberNode;
    }

    /** 
     * Get the 'authoritativeMemberNode' element value. 
              		A reference to the Member Node that acts as the authoritative source for
    					an object in the system. The AuthoritativeMemberNode will often also be the 
    					OriginMemberNode, unless there has been a need to transfer authority for
    					an object to a new node, such as when a Member Node becomes defunct.
    					Replication should occur from the AuthoritativeMemberNode.  The Authoritative Member Node
    					has all the rights of the RightsHolder to maintain and curate the object, inlcuding making any
    					changes necessary.
              		
     * 
     * @return value
     */
    public NodeReference getAuthoritativeMemberNode() {
        return authoritativeMemberNode;
    }

    /** 
     * Set the 'authoritativeMemberNode' element value. 
              		A reference to the Member Node that acts as the authoritative source for
    					an object in the system. The AuthoritativeMemberNode will often also be the 
    					OriginMemberNode, unless there has been a need to transfer authority for
    					an object to a new node, such as when a Member Node becomes defunct.
    					Replication should occur from the AuthoritativeMemberNode.  The Authoritative Member Node
    					has all the rights of the RightsHolder to maintain and curate the object, inlcuding making any
    					changes necessary.
              		
     * 
     * @param authoritativeMemberNode
     */
    public void setAuthoritativeMemberNode(NodeReference authoritativeMemberNode) {
        this.authoritativeMemberNode = authoritativeMemberNode;
    }

    /** 
     * Get the list of 'replica' element items. 
              		A container field used to repeatedly provide several metadata fields about 
    					each replica that exists in the system, or is being replicated. Note that
    					a Replica field exists even for the Authoritative/Origin Member Nodes so
    					that the status of those objects can be tracked.
              		
     * 
     * @return list
     */
    public List<Replica> getReplicaList() {
        return replicaList;
    }

    /** 
     * Set the list of 'replica' element items. 
              		A container field used to repeatedly provide several metadata fields about 
    					each replica that exists in the system, or is being replicated. Note that
    					a Replica field exists even for the Authoritative/Origin Member Nodes so
    					that the status of those objects can be tracked.
              		
     * 
     * @param list
     */
    public void setReplicaList(List<Replica> list) {
        replicaList = list;
    }

    /** 
     * Get the number of 'replica' element items.
     * @return count
     */
    public int sizeReplicaList() {
        return replicaList.size();
    }

    /** 
     * Add a 'replica' element item.
     * @param item
     */
    public void addReplica(Replica item) {
        replicaList.add(item);
    }

    /** 
     * Get 'replica' element item by position.
     * @return item
     * @param index
     */
    public Replica getReplica(int index) {
        return replicaList.get(index);
    }

    /** 
     * Remove all 'replica' element items.
     */
    public void clearReplicaList() {
        replicaList.clear();
    }
}
