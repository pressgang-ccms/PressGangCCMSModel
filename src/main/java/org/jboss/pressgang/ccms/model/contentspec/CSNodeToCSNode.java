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
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.NotNull;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "CSNodeToCSNode")
public class CSNodeToCSNode extends AuditedEntity<CSNodeToCSNode> implements Serializable {
    private static final long serialVersionUID = 1323433852480196579L;

    private Integer csNodeToCSNodeId = null;
    private CSNode mainNode = null;
    private CSNode relatedNode = null;
    private Integer relationshipType = null;

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

    @Column(name = "RelationshipType", nullable = false)
    public Integer getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(Integer relationshipType) {
        this.relationshipType = relationshipType;
    }
}
