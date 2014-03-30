package org.jboss.pressgang.ccms.model.contentspec;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "ContentSpecInfoNode")
public class CSInfoNode extends AuditedEntity implements Serializable {
    private static final long serialVersionUID = -4374423970605211574L;
    private Integer csNodeInfoId;
    private Integer topicId;
    private Integer topicRevision;
    private String condition;
    private CSNode csNode;

    private Topic topic;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContentSpecInfoNodeID", nullable = false, unique = true)
    public Integer getCSNodeInfoId() {
        return csNodeInfoId;
    }

    public void setCSNodeInfoId(Integer csNodeInfoId) {
        this.csNodeInfoId = csNodeInfoId;
    }

    @Transient
    @Override
    public Integer getId() {
        return csNodeInfoId;
    }

    @Column(name = "TopicID", nullable = false)
    @NotNull
    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    @Column(name = "TopicRevision", nullable = true)
    public Integer getTopicRevision() {
        return topicRevision;
    }

    public void setTopicRevision(Integer topicRevision) {
        this.topicRevision = topicRevision;
    }

    @Column(name = "InfoCondition", nullable = true)
    @Size(max = 255)
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @OneToOne
    @JoinColumn(name = "ContentSpecNodeID")
    public CSNode getCSNode() {
        return csNode;
    }

    public void setCSNode(CSNode csNode) {
        this.csNode = csNode;
    }

    @Transient
    public String getInheritedCondition() {
        if (getCondition() == null || getCondition().trim().isEmpty()) {
            if (getCSNode() != null) {
                return getCSNode().getInheritedCondition();
            } else {
                return null;
            }
        } else {
            return getCondition();
        }
    }

    @Transient
    public Topic getTopic(final EntityManager entityManager) {
        if (topicId == null)
            return null;

        if (topic == null) {
            if (topicRevision == null) {
                // Find the latest topic
                topic = entityManager.find(Topic.class, getTopicId());
            } else {
                // Find the envers topic
                topic = EnversUtilities.getRevision(entityManager, Topic.class, getTopicId(), getTopicRevision());
            }
        }
        return topic;
    }

    @PrePersist
    @PreUpdate
    protected void preSave() {
        // Set the content specs last modified date if one of it's nodes change
        if (csNode != null && csNode.getContentSpec() != null) {
            csNode.getContentSpec().setLastModified();
        }
    }
}
