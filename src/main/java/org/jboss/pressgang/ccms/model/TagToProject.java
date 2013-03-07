package org.jboss.pressgang.ccms.model;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.NotNull;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Audited
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TagToProject", uniqueConstraints = @UniqueConstraint(columnNames = {"ProjectID", "TagID"}))
public class TagToProject extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = 8977075767446465613L;

    private Integer tagToProjectId;
    private Project project;
    private Tag tag;

    public TagToProject() {
    }

    public TagToProject(final Project project, final Tag tag) {
        this.project = project;
        this.tag = tag;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TagToProjectID", unique = true, nullable = false)
    public Integer getTagToProjectId() {
        return tagToProjectId;
    }

    public void setTagToProjectId(final Integer tagToProjectId) {
        this.tagToProjectId = tagToProjectId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectID", nullable = false)
    @NotNull
    public Project getProject() {
        return project;
    }

    public void setProject(final Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TagID", nullable = false)
    @NotNull
    public Tag getTag() {
        return tag;
    }

    public void setTag(final Tag tag) {
        this.tag = tag;
    }

    @Override
    @Transient
    public Integer getId() {
        return tagToProjectId;
    }

}
