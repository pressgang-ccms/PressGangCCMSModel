package org.jboss.pressgang.ccms.model.contentspec;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceException;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.utils.constants.CommonConstants;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "CSNodeToCSNode")
public class CSNodeToCSNode extends AuditedEntity implements Serializable {
    private static final long serialVersionUID = 1323433852480196579L;

    private Integer csNodeToCSNodeId = null;
    private CSNode mainNode = null;
    private CSNode relatedNode = null;
    private Integer relationshipType = null;
    private Integer relationshipSort = null;

    @Override
    @Transient
    public Integer getId() {
        return csNodeToCSNodeId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CSNodeToCSNodeID", unique = true, nullable = false)
    public Integer getCSNodeToCSNodeId() {
        return csNodeToCSNodeId;
    }

    public void setCSNodeToCSNodeId(Integer csNodeToCSNodeId) {
        this.csNodeToCSNodeId = csNodeToCSNodeId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MainNodeID", nullable = false)
    @NotNull
    public CSNode getMainNode() {
        return mainNode;
    }

    public void setMainNode(CSNode mainNode) {
        this.mainNode = mainNode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RelatedNodeID", nullable = false)
    @NotNull
    public CSNode getRelatedNode() {
        return relatedNode;
    }

    public void setRelatedNode(CSNode relatedNode) {
        this.relatedNode = relatedNode;
    }

    @Column(name = "RelationshipType", nullable = false, columnDefinition = "TINYINT")
    public Integer getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(Integer relationshipType) {
        this.relationshipType = relationshipType;
    }

    @Column(name = "Sort")
    public Integer getRelationshipSort() {
        return relationshipSort;
    }

    public void setRelationshipSort(Integer relationshipSort) {
        this.relationshipSort = relationshipSort;
    }

    @PrePersist
    protected void prePersist() {
        validateNodeToNode();
    }

    @PreUpdate
    protected void preUpdate() {
        validateNodeToNode();
    }

    @Transient
    protected void validateNodeToNode() {
        if (getMainNode().getCSNodeType() == CommonConstants.CS_NODE_META_DATA || getRelatedNode().getCSNodeType() == CommonConstants
                .CS_NODE_META_DATA || getMainNode().getCSNodeType() == CommonConstants.CS_NODE_COMMENT || getRelatedNode().getCSNodeType
                () == CommonConstants.CS_NODE_COMMENT) {
            throw new PersistenceException("Node to Node relationships can't be performed on Comments or Meta Data.");
        }
    }
}