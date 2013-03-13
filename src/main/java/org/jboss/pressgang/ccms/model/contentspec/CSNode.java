package org.jboss.pressgang.ccms.model.contentspec;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceException;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;
import org.jboss.pressgang.ccms.utils.constants.CommonConstants;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "ContentSpecNode", uniqueConstraints = {@UniqueConstraint(columnNames = {"ContentSpecID", "NextNodeID"}), @UniqueConstraint(
        columnNames = {"ContentSpecID", "PreviousNodeID"})})
public class CSNode extends AuditedEntity implements Serializable {

    private static final long serialVersionUID = -5074781793940947664L;
    public static final String SELECT_ALL_QUERY = "select csNode FROM CSNode AS csNode";

    private Integer csNodeId = null;
    private String csNodeTitle = null;
    private String csNodeTargetId = null;
    private String additionalText = null;
    private Integer csNodeType = null;
    private ContentSpec contentSpec = null;
    private CSNode parent = null;
    private CSNode next = null;
    private CSNode previous = null;
    private Integer entityId = null;
    private Integer entityRevision = null;
    private String condition = null;
    private Set<CSNode> children = new HashSet<CSNode>(0);
    private Set<CSNodeToCSNode> relatedFromNodes = new HashSet<CSNodeToCSNode>(0);
    private Set<CSNodeToCSNode> relatedToNodes = new HashSet<CSNodeToCSNode>(0);
    private Set<CSNodeToPropertyTag> csNodeToPropertyTags = new HashSet<CSNodeToPropertyTag>(0);

    private Topic topic;

    public CSNode() {
    }

    @Override
    @Transient
    public Integer getId() {
        return csNodeId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ContentSpecNodeID", unique = true, nullable = false)
    public Integer getCSNodeId() {
        return csNodeId;
    }

    public void setCSNodeId(Integer csNodeId) {
        this.csNodeId = csNodeId;
    }

    @Column(name = "NodeTitle", length = 255)
    @NotNull
    public String getCSNodeTitle() {
        return csNodeTitle;
    }

    public void setCSNodeTitle(String csNodeTitle) {
        this.csNodeTitle = csNodeTitle;
    }

    @Column(name = "NodeTargetID", length = 255, nullable = true)
    public String getCSNodeTargetId() {
        return csNodeTargetId;
    }

    public void setCSNodeTargetId(String csNodeTargetId) {
        this.csNodeTargetId = csNodeTargetId;
    }

    @Column(name = "AdditionalText", columnDefinition = "TEXT", nullable = true)
    @Size(max = 65535)
    public String getAdditionalText() {
        return additionalText;
    }

    public void setAdditionalText(String csNodeAlternativeTitle) {
        this.additionalText = csNodeAlternativeTitle;
    }

    @Column(name = "NodeType", nullable = false)
    public Integer getCSNodeType() {
        return csNodeType;
    }

    public void setCSNodeType(Integer csNodeType) {
        this.csNodeType = csNodeType;
    }

    @JoinColumn(name = "ContentSpecID")
    @ManyToOne(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    public ContentSpec getContentSpec() {
        return contentSpec;
    }

    public void setContentSpec(ContentSpec contentSpec) {
        this.contentSpec = contentSpec;
    }

    @JoinColumn(name = "ParentID")
    @ManyToOne(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    public CSNode getParent() {
        return parent;
    }

    public void setParent(CSNode parent) {
        this.parent = parent;
    }

    @JoinColumn(name = "NextNodeID")
    @ManyToOne(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    public CSNode getNext() {
        return next;
    }

    public void setNext(CSNode next) {
        this.next = next;
    }

    @JoinColumn(name = "PreviousNodeID")
    @ManyToOne(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    public CSNode getPrevious() {
        return previous;
    }

    public void setPrevious(CSNode previous) {
        this.previous = previous;
    }

    @Column(name = "EntityID")
    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    @Column(name = "EntityRevision")
    public Integer getEntityRevision() {
        return entityRevision;
    }

    public void setEntityRevision(Integer entityRevision) {
        this.entityRevision = entityRevision;
    }

    @Column(name = "NodeCondition")
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<CSNode> getChildren() {
        return children;
    }

    public void setChildren(Set<CSNode> children) {
        this.children = children;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainNode", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<CSNodeToCSNode> getRelatedToNodes() {
        return relatedToNodes;
    }

    public void setRelatedFromNodes(Set<CSNodeToCSNode> relatedFromNodes) {
        this.relatedFromNodes = relatedFromNodes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "relatedNode", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<CSNodeToCSNode> getRelatedFromNodes() {
        return relatedFromNodes;
    }

    public void setRelatedToNodes(Set<CSNodeToCSNode> relatedToNodes) {
        this.relatedToNodes = relatedToNodes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "CSNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<CSNodeToPropertyTag> getCSNodeToPropertyTags() {
        return csNodeToPropertyTags;
    }

    public void setCSNodeToPropertyTags(Set<CSNodeToPropertyTag> csNodeToPropertyTags) {
        this.csNodeToPropertyTags = csNodeToPropertyTags;
    }

    @Transient
    public List<CSNode> getChildrenList() {
        return new ArrayList<CSNode>(getChildren());
    }

    @Transient
    public List<CSNodeToCSNode> getRelatedFromNodesList() {
        return new ArrayList<CSNodeToCSNode>(relatedFromNodes);
    }

    @Transient
    public List<CSNodeToCSNode> getRelatedToNodesList() {
        return new ArrayList<CSNodeToCSNode>(relatedToNodes);
    }

    @Transient
    public void removeChild(final CSNode child) {
        final List<CSNode> removeNodes = new ArrayList<CSNode>();

        for (final CSNode childNode : children) {
            if (childNode.equals(child)) {
                removeNodes.add(childNode);
            }
        }

        for (final CSNode removeNode : removeNodes) {
            children.remove(removeNode);
            removeNode.setParent(null);
        }
    }

    @Transient
    public void addChild(final CSNode child) {
        children.add(child);
        child.setParent(this);
    }

    @Transient
    public void removeRelatedTo(final CSNode node, Integer relationshipTypeId) {
        final List<CSNodeToCSNode> removeNodes = new ArrayList<CSNodeToCSNode>();

        for (final CSNodeToCSNode nodeToNode : relatedToNodes) {
            if (nodeToNode.getRelatedNode().equals(node) && nodeToNode.getRelationshipType().equals(relationshipTypeId)) {
                removeNodes.add(nodeToNode);
            }
        }

        for (final CSNodeToCSNode removeNode : removeNodes) {
            relatedToNodes.remove(removeNode);
            removeNode.getRelatedNode().getRelatedFromNodes().remove(removeNode);
        }
    }

    @Transient
    public void addRelatedTo(final CSNode relatedNode, Integer relationshipTypeId) {
        final CSNodeToCSNode nodeToNode = new CSNodeToCSNode();
        nodeToNode.setMainNode(this);
        nodeToNode.setRelatedNode(relatedNode);
        nodeToNode.setRelationshipType(relationshipTypeId);

        getRelatedToNodes().add(nodeToNode);
        relatedNode.getRelatedFromNodes().add(nodeToNode);
    }

    @Transient
    public void addRelatedTo(final CSNodeToCSNode relatedNode) {
        getRelatedToNodes().add(relatedNode);
        relatedNode.getRelatedNode().getRelatedFromNodes().add(relatedNode);
    }

    @Transient
    public void removeRelatedTo(final CSNodeToCSNode relatedNode) {
        getRelatedToNodes().remove(relatedNode);
        relatedNode.getRelatedNode().getRelatedFromNodes().remove(relatedNode);
    }

    @Transient
    public void removeRelatedFrom(final CSNode node, Integer relationshipTypeId) {
        final List<CSNodeToCSNode> removeNodes = new ArrayList<CSNodeToCSNode>();

        for (final CSNodeToCSNode nodeFromNode : relatedFromNodes) {
            if (nodeFromNode.getRelatedNode().equals(node) && nodeFromNode.getRelationshipType().equals(relationshipTypeId)) {
                removeNodes.add(nodeFromNode);
            }
        }

        for (final CSNodeToCSNode removeNode : removeNodes) {
            relatedFromNodes.remove(removeNode);
            removeNode.getRelatedNode().getRelatedToNodes().remove(removeNode);
        }
    }

    @Transient
    public void addRelatedFrom(final CSNode relatedNode, Integer relationshipTypeId) {
        final CSNodeToCSNode nodeFromNode = new CSNodeToCSNode();
        nodeFromNode.setMainNode(this);
        nodeFromNode.setRelatedNode(relatedNode);
        nodeFromNode.setRelationshipType(relationshipTypeId);

        getRelatedFromNodes().add(nodeFromNode);
        relatedNode.getRelatedToNodes().add(nodeFromNode);
    }

    @Transient
    public void addRelatedFrom(final CSNodeToCSNode relatedNode) {
        getRelatedFromNodes().add(relatedNode);
        relatedNode.getRelatedNode().getRelatedToNodes().add(relatedNode);
    }

    @Transient
    public void removeRelatedFrom(final CSNodeToCSNode relatedNode) {
        getRelatedFromNodes().remove(relatedNode);
        relatedNode.getRelatedNode().getRelatedToNodes().remove(relatedNode);
    }

    @Transient
    public List<CSNodeToPropertyTag> getCSNodeToPropertyTagsList() {
        return new ArrayList<CSNodeToPropertyTag>(csNodeToPropertyTags);
    }

    public void addPropertyTag(final PropertyTag propertyTag, final String value) {
        final CSNodeToPropertyTag mapping = new CSNodeToPropertyTag();
        mapping.setCSNode(this);
        mapping.setPropertyTag(propertyTag);
        mapping.setValue(value);

        csNodeToPropertyTags.add(mapping);
        propertyTag.getCSNodeToPropertyTags().add(mapping);
    }

    public void removePropertyTag(final PropertyTag propertyTag, final String value) {
        final List<CSNodeToPropertyTag> removeList = new ArrayList<CSNodeToPropertyTag>();

        for (final CSNodeToPropertyTag mapping : csNodeToPropertyTags) {
            final PropertyTag myPropertyTag = mapping.getPropertyTag();
            if (myPropertyTag.equals(propertyTag) && mapping.getValue().equals(value)) {
                removeList.add(mapping);
            }
        }

        for (final CSNodeToPropertyTag mapping : removeList) {
            csNodeToPropertyTags.remove(mapping);
            mapping.getPropertyTag().getCSNodeToPropertyTags().remove(mapping);
        }
    }

    @Transient
    public Topic getTopic(final EntityManager entityManager) {
        if (getCSNodeType() != CommonConstants.CS_NODE_TOPIC) return null;
        if (entityId == null) return null;

        if (topic == null) {
            if (entityRevision == null) {
                // Find the latest topic
                topic = entityManager.find(Topic.class, entityId);
            } else {
                // Find the envers topic
                final AuditReader reader = AuditReaderFactory.get(entityManager);
                final AuditQuery query = reader.createQuery().forEntitiesAtRevision(Topic.class, entityRevision).add(
                        AuditEntity.id().eq(entityId));
                topic = (Topic) query.getSingleResult();
            }
        }
        return topic;
    }

    @SuppressWarnings("unchecked")
    @Transient
    public List<TranslatedCSNode> getTranslatedNodes(final EntityManager entityManager, final Number revision) {
        /*
         * We have to do a query here as a @OneToMany won't work with hibernate envers since the TranslatedCSNode entity is
         * audited and we need the latest results. This is because the translated node will never exist for its matching
         * audited node.
         */
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<TranslatedCSNode> query = criteriaBuilder.createQuery(TranslatedCSNode.class);
        final Root<TranslatedCSNode> root = query.from(TranslatedCSNode.class);
        query.select(root);

        final Predicate csNodeIdMatches = criteriaBuilder.equal(root.get("CSNodeId"), csNodeId);
        final Predicate csNodeRevisionMatches = criteriaBuilder.lessThanOrEqualTo(root.get("CSNodeRevision").as(Integer.class),
                (Integer) revision);

        if (revision == null) {
            query.where(csNodeIdMatches);
        } else {
            query.where(criteriaBuilder.and(csNodeIdMatches, csNodeRevisionMatches));
        }

        return entityManager.createQuery(query).getResultList();
    }

    @PrePersist
    protected void prePersist() {
        validateNode();
    }

    @PreUpdate
    protected void preUpdate() {
        validateNode();
    }

    @Transient
    protected void validateNode() {
        if (getCSNodeType() == CommonConstants.CS_NODE_META_DATA && getParent() != null) {
            throw new PersistenceException("Meta Data nodes are only allowed at the root level.");
        }

        if (getCSNodeType() == CommonConstants.CS_NODE_META_DATA && !getChildren().isEmpty()) {
            throw new PersistenceException("Meta Data nodes cannot have children nodes.");
        } else if (getCSNodeType() == CommonConstants.CS_NODE_TOPIC && !getChildren().isEmpty()) {
            throw new PersistenceException("Topic nodes cannot have children nodes.");
        }
    }
}
