package org.jboss.pressgang.ccms.model;

// Generated Aug 12, 2011 11:10:16 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import javax.validation.constraints.NotNull;

import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TopicToBugzillaBug", uniqueConstraints = @UniqueConstraint(columnNames = { "TopicID", "BugzillaBugID" }))
public class TopicToBugzillaBug extends AuditedEntity<TopicToBugzillaBug> implements java.io.Serializable {
    private static final long serialVersionUID = -4963859367368389435L;
    public static final String SELECT_ALL_QUERY = "select topicToBugzillaBug from TopicToBugzillaBug topicToBugzillaBug";

    private Integer topicToBugzillaBugId;
    private BugzillaBug bugzillaBug;
    private Topic topic;

    public TopicToBugzillaBug() {
    }

    public TopicToBugzillaBug(final BugzillaBug bugzillaBug, final Topic topic) {
        this.bugzillaBug = bugzillaBug;
        this.topic = topic;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TopicToBugzillaBugID", unique = true, nullable = false)
    public Integer getTopicToBugzillaBugId() {
        return this.topicToBugzillaBugId;
    }

    public void setTopicToBugzillaBugId(final Integer topicToBugzillaBugId) {
        this.topicToBugzillaBugId = topicToBugzillaBugId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BugzillaBugID", nullable = false)
    @NotNull
    public BugzillaBug getBugzillaBug() {
        return this.bugzillaBug;
    }

    public void setBugzillaBug(final BugzillaBug bugzillaBug) {
        this.bugzillaBug = bugzillaBug;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TopicID", nullable = false)
    @NotNull
    public Topic getTopic() {
        return this.topic;
    }

    public void setTopic(final Topic topic) {
        this.topic = topic;
    }

    @Override
    @Transient
    public Integer getId() {
        return this.topicToBugzillaBugId;
    }

}
