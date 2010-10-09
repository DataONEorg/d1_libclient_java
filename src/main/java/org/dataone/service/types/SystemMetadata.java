
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://dataone.org/service/types/SystemMetadata/0.1" xmlns:ns1="http://dataone.org/service/types/common/0.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="SystemMetadata">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns1:Identifier" name="identifier" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns1:ObjectFormat" name="objectFormat"/>
 *     &lt;xs:element type="xs:long" name="size"/>
 *     &lt;xs:element type="ns1:Principal" name="submitter"/>
 *     &lt;xs:element type="ns1:Principal" name="rightsHolder"/>
 *     &lt;xs:element type="ns1:Identifier" name="obsoletes" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns1:Identifier" name="obsoletedBy" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns1:Identifier" name="derivedFrom" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns1:Identifier" name="describes" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns1:Identifier" name="describedBy" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns1:Checksum" name="checksum" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="embargoExpires" minOccurs="0"/>
 *     &lt;xs:element type="ns:AccessRule" name="accessRule" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:ReplicationPolicy" name="replicationPolicy" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="dateUploaded"/>
 *     &lt;xs:element type="xs:dateTime" name="dateSysMetadataModified"/>
 *     &lt;xs:element type="ns1:NodeReference" name="originMemberNode"/>
 *     &lt;xs:element type="ns1:NodeReference" name="authoritativeMemberNode"/>
 *     &lt;xs:element name="replica" minOccurs="0" maxOccurs="unbounded">
 *       &lt;!-- Reference to inner class Replica -->
 *     &lt;/xs:element>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SystemMetadata
{
    private Identifier identifier;
    private ObjectFormat objectFormat;
    private long size;
    private Principal submitter;
    private Principal rightsHolder;
    private List<Identifier> obsoleteList = new ArrayList<Identifier>();
    private List<Identifier> obsoletedByList = new ArrayList<Identifier>();
    private List<Identifier> derivedFromList = new ArrayList<Identifier>();
    private List<Identifier> describeList = new ArrayList<Identifier>();
    private List<Identifier> describedByList = new ArrayList<Identifier>();
    private Checksum checksum;
    private Date embargoExpires;
    private List<AccessRule> accessRuleList = new ArrayList<AccessRule>();
    private ReplicationPolicy replicationPolicy;
    private Date dateUploaded;
    private Date dateSysMetadataModified;
    private NodeReference originMemberNode;
    private NodeReference authoritativeMemberNode;
    private List<Replica> replicaList = new ArrayList<Replica>();

    /** 
     * Get the 'identifier' element value.
     * 
     * @return value
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /** 
     * Set the 'identifier' element value.
     * 
     * @param identifier
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /** 
     * Get the 'objectFormat' element value.
     * 
     * @return value
     */
    public ObjectFormat getObjectFormat() {
        return objectFormat;
    }

    /** 
     * Set the 'objectFormat' element value.
     * 
     * @param objectFormat
     */
    public void setObjectFormat(ObjectFormat objectFormat) {
        this.objectFormat = objectFormat;
    }

    /** 
     * Get the 'size' element value.
     * 
     * @return value
     */
    public long getSize() {
        return size;
    }

    /** 
     * Set the 'size' element value.
     * 
     * @param size
     */
    public void setSize(long size) {
        this.size = size;
    }

    /** 
     * Get the 'submitter' element value.
     * 
     * @return value
     */
    public Principal getSubmitter() {
        return submitter;
    }

    /** 
     * Set the 'submitter' element value.
     * 
     * @param submitter
     */
    public void setSubmitter(Principal submitter) {
        this.submitter = submitter;
    }

    /** 
     * Get the 'rightsHolder' element value.
     * 
     * @return value
     */
    public Principal getRightsHolder() {
        return rightsHolder;
    }

    /** 
     * Set the 'rightsHolder' element value.
     * 
     * @param rightsHolder
     */
    public void setRightsHolder(Principal rightsHolder) {
        this.rightsHolder = rightsHolder;
    }

    /** 
     * Get the list of 'obsoletes' element items.
     * 
     * @return list
     */
    public List<Identifier> getObsoleteList() {
        return obsoleteList;
    }

    /** 
     * Set the list of 'obsoletes' element items.
     * 
     * @param list
     */
    public void setObsoleteList(List<Identifier> list) {
        obsoleteList = list;
    }

    /** 
     * Get the number of 'obsoletes' element items.
     * @return count
     */
    public int sizeObsoleteList() {
        return obsoleteList.size();
    }

    /** 
     * Add a 'obsoletes' element item.
     * @param item
     */
    public void addObsolete(Identifier item) {
        obsoleteList.add(item);
    }

    /** 
     * Get 'obsoletes' element item by position.
     * @return item
     * @param index
     */
    public Identifier getObsolete(int index) {
        return obsoleteList.get(index);
    }

    /** 
     * Remove all 'obsoletes' element items.
     */
    public void clearObsoleteList() {
        obsoleteList.clear();
    }

    /** 
     * Get the list of 'obsoletedBy' element items.
     * 
     * @return list
     */
    public List<Identifier> getObsoletedByList() {
        return obsoletedByList;
    }

    /** 
     * Set the list of 'obsoletedBy' element items.
     * 
     * @param list
     */
    public void setObsoletedByList(List<Identifier> list) {
        obsoletedByList = list;
    }

    /** 
     * Get the number of 'obsoletedBy' element items.
     * @return count
     */
    public int sizeObsoletedByList() {
        return obsoletedByList.size();
    }

    /** 
     * Add a 'obsoletedBy' element item.
     * @param item
     */
    public void addObsoletedBy(Identifier item) {
        obsoletedByList.add(item);
    }

    /** 
     * Get 'obsoletedBy' element item by position.
     * @return item
     * @param index
     */
    public Identifier getObsoletedBy(int index) {
        return obsoletedByList.get(index);
    }

    /** 
     * Remove all 'obsoletedBy' element items.
     */
    public void clearObsoletedByList() {
        obsoletedByList.clear();
    }

    /** 
     * Get the list of 'derivedFrom' element items.
     * 
     * @return list
     */
    public List<Identifier> getDerivedFromList() {
        return derivedFromList;
    }

    /** 
     * Set the list of 'derivedFrom' element items.
     * 
     * @param list
     */
    public void setDerivedFromList(List<Identifier> list) {
        derivedFromList = list;
    }

    /** 
     * Get the number of 'derivedFrom' element items.
     * @return count
     */
    public int sizeDerivedFromList() {
        return derivedFromList.size();
    }

    /** 
     * Add a 'derivedFrom' element item.
     * @param item
     */
    public void addDerivedFrom(Identifier item) {
        derivedFromList.add(item);
    }

    /** 
     * Get 'derivedFrom' element item by position.
     * @return item
     * @param index
     */
    public Identifier getDerivedFrom(int index) {
        return derivedFromList.get(index);
    }

    /** 
     * Remove all 'derivedFrom' element items.
     */
    public void clearDerivedFromList() {
        derivedFromList.clear();
    }

    /** 
     * Get the list of 'describes' element items.
     * 
     * @return list
     */
    public List<Identifier> getDescribeList() {
        return describeList;
    }

    /** 
     * Set the list of 'describes' element items.
     * 
     * @param list
     */
    public void setDescribeList(List<Identifier> list) {
        describeList = list;
    }

    /** 
     * Get the number of 'describes' element items.
     * @return count
     */
    public int sizeDescribeList() {
        return describeList.size();
    }

    /** 
     * Add a 'describes' element item.
     * @param item
     */
    public void addDescribe(Identifier item) {
        describeList.add(item);
    }

    /** 
     * Get 'describes' element item by position.
     * @return item
     * @param index
     */
    public Identifier getDescribe(int index) {
        return describeList.get(index);
    }

    /** 
     * Remove all 'describes' element items.
     */
    public void clearDescribeList() {
        describeList.clear();
    }

    /** 
     * Get the list of 'describedBy' element items.
     * 
     * @return list
     */
    public List<Identifier> getDescribedByList() {
        return describedByList;
    }

    /** 
     * Set the list of 'describedBy' element items.
     * 
     * @param list
     */
    public void setDescribedByList(List<Identifier> list) {
        describedByList = list;
    }

    /** 
     * Get the number of 'describedBy' element items.
     * @return count
     */
    public int sizeDescribedByList() {
        return describedByList.size();
    }

    /** 
     * Add a 'describedBy' element item.
     * @param item
     */
    public void addDescribedBy(Identifier item) {
        describedByList.add(item);
    }

    /** 
     * Get 'describedBy' element item by position.
     * @return item
     * @param index
     */
    public Identifier getDescribedBy(int index) {
        return describedByList.get(index);
    }

    /** 
     * Remove all 'describedBy' element items.
     */
    public void clearDescribedByList() {
        describedByList.clear();
    }

    /** 
     * Get the 'checksum' element value.
     * 
     * @return value
     */
    public Checksum getChecksum() {
        return checksum;
    }

    /** 
     * Set the 'checksum' element value.
     * 
     * @param checksum
     */
    public void setChecksum(Checksum checksum) {
        this.checksum = checksum;
    }

    /** 
     * Get the 'embargoExpires' element value.
     * 
     * @return value
     */
    public Date getEmbargoExpires() {
        return embargoExpires;
    }

    /** 
     * Set the 'embargoExpires' element value.
     * 
     * @param embargoExpires
     */
    public void setEmbargoExpires(Date embargoExpires) {
        this.embargoExpires = embargoExpires;
    }

    /** 
     * Get the list of 'accessRule' element items.
     * 
     * @return list
     */
    public List<AccessRule> getAccessRuleList() {
        return accessRuleList;
    }

    /** 
     * Set the list of 'accessRule' element items.
     * 
     * @param list
     */
    public void setAccessRuleList(List<AccessRule> list) {
        accessRuleList = list;
    }

    /** 
     * Get the number of 'accessRule' element items.
     * @return count
     */
    public int sizeAccessRuleList() {
        return accessRuleList.size();
    }

    /** 
     * Add a 'accessRule' element item.
     * @param item
     */
    public void addAccessRule(AccessRule item) {
        accessRuleList.add(item);
    }

    /** 
     * Get 'accessRule' element item by position.
     * @return item
     * @param index
     */
    public AccessRule getAccessRule(int index) {
        return accessRuleList.get(index);
    }

    /** 
     * Remove all 'accessRule' element items.
     */
    public void clearAccessRuleList() {
        accessRuleList.clear();
    }

    /** 
     * Get the 'replicationPolicy' element value.
     * 
     * @return value
     */
    public ReplicationPolicy getReplicationPolicy() {
        return replicationPolicy;
    }

    /** 
     * Set the 'replicationPolicy' element value.
     * 
     * @param replicationPolicy
     */
    public void setReplicationPolicy(ReplicationPolicy replicationPolicy) {
        this.replicationPolicy = replicationPolicy;
    }

    /** 
     * Get the 'dateUploaded' element value.
     * 
     * @return value
     */
    public Date getDateUploaded() {
        return dateUploaded;
    }

    /** 
     * Set the 'dateUploaded' element value.
     * 
     * @param dateUploaded
     */
    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    /** 
     * Get the 'dateSysMetadataModified' element value.
     * 
     * @return value
     */
    public Date getDateSysMetadataModified() {
        return dateSysMetadataModified;
    }

    /** 
     * Set the 'dateSysMetadataModified' element value.
     * 
     * @param dateSysMetadataModified
     */
    public void setDateSysMetadataModified(Date dateSysMetadataModified) {
        this.dateSysMetadataModified = dateSysMetadataModified;
    }

    /** 
     * Get the 'originMemberNode' element value.
     * 
     * @return value
     */
    public NodeReference getOriginMemberNode() {
        return originMemberNode;
    }

    /** 
     * Set the 'originMemberNode' element value.
     * 
     * @param originMemberNode
     */
    public void setOriginMemberNode(NodeReference originMemberNode) {
        this.originMemberNode = originMemberNode;
    }

    /** 
     * Get the 'authoritativeMemberNode' element value.
     * 
     * @return value
     */
    public NodeReference getAuthoritativeMemberNode() {
        return authoritativeMemberNode;
    }

    /** 
     * Set the 'authoritativeMemberNode' element value.
     * 
     * @param authoritativeMemberNode
     */
    public void setAuthoritativeMemberNode(NodeReference authoritativeMemberNode) {
        this.authoritativeMemberNode = authoritativeMemberNode;
    }

    /** 
     * Get the list of 'replica' element items.
     * 
     * @return list
     */
    public List<Replica> getReplicaList() {
        return replicaList;
    }

    /** 
     * Set the list of 'replica' element items.
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
    public int sizeReplicas() {
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
    public void clearReplicas() {
        replicaList.clear();
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:element xmlns:ns="http://dataone.org/service/types/common/0.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="replica" minOccurs="0" maxOccurs="unbounded">
     *   &lt;xs:complexType>
     *     &lt;xs:sequence>
     *       &lt;xs:element type="ns:NodeReference" name="replicaMemberNode"/>
     *       &lt;xs:element name="replicationStatus">
     *         &lt;xs:simpleType>
     *           &lt;!-- Reference to inner class ReplicationStatus -->
     *         &lt;/xs:simpleType>
     *       &lt;/xs:element>
     *       &lt;xs:element type="xs:dateTime" name="replicaVerified"/>
     *     &lt;/xs:sequence>
     *   &lt;/xs:complexType>
     * &lt;/xs:element>
     * </pre>
     */
    public static class Replica
    {
        private NodeReference replicaMemberNode;
        private ReplicationStatus replicationStatus;
        private Date replicaVerified;

        /** 
         * Get the 'replicaMemberNode' element value.
         * 
         * @return value
         */
        public NodeReference getReplicaMemberNode() {
            return replicaMemberNode;
        }

        /** 
         * Set the 'replicaMemberNode' element value.
         * 
         * @param replicaMemberNode
         */
        public void setReplicaMemberNode(NodeReference replicaMemberNode) {
            this.replicaMemberNode = replicaMemberNode;
        }

        /** 
         * Get the 'replicationStatus' element value.
         * 
         * @return value
         */
        public ReplicationStatus getReplicationStatus() {
            return replicationStatus;
        }

        /** 
         * Set the 'replicationStatus' element value.
         * 
         * @param replicationStatus
         */
        public void setReplicationStatus(ReplicationStatus replicationStatus) {
            this.replicationStatus = replicationStatus;
        }

        /** 
         * Get the 'replicaVerified' element value.
         * 
         * @return value
         */
        public Date getReplicaVerified() {
            return replicaVerified;
        }

        /** 
         * Set the 'replicaVerified' element value.
         * 
         * @param replicaVerified
         */
        public void setReplicaVerified(Date replicaVerified) {
            this.replicaVerified = replicaVerified;
        }
        /** 
         * Schema fragment(s) for this class:
         * <pre>
         * &lt;xs:simpleType xmlns:xs="http://www.w3.org/2001/XMLSchema">
         *   &lt;xs:restriction base="xs:string">
         *     &lt;xs:enumeration value="queued"/>
         *     &lt;xs:enumeration value="requested"/>
         *     &lt;xs:enumeration value="completed"/>
         *     &lt;xs:enumeration value="invalidated"/>
         *   &lt;/xs:restriction>
         * &lt;/xs:simpleType>
         * </pre>
         */
        public static enum ReplicationStatus {
            QUEUED("queued"), REQUESTED("requested"), COMPLETED("completed"), INVALIDATED(
                    "invalidated");
            private final String value;

            private ReplicationStatus(String value) {
                this.value = value;
            }

            public String toString() {
                return value;
            }

            public static ReplicationStatus convert(String value) {
                for (ReplicationStatus inst : values()) {
                    if (inst.toString().equals(value)) {
                        return inst;
                    }
                }
                return null;
            }
        }
    }
}