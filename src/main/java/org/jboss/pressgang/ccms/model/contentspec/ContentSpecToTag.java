package org.jboss.pressgang.ccms.model.contentspec;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.hibernate.validator.NotNull;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "ContentSpecToTag", uniqueConstraints = @UniqueConstraint(columnNames = { "ContentSpecID", "TagID" }))
public class ContentSpecToTag extends AuditedEntity<ContentSpecToTag> implements java.io.Serializable {
    private static final long serialVersionUID = -7516063608506037594L;

    private Integer contentSpecToTagId;
    private ContentSpec contentSpec;
    private Tag tag;

    public ContentSpecToTag() {
    }

    public ContentSpecToTag(final ContentSpec contentSpec, final Tag tag) {
        this.contentSpec = contentSpec;
        this.tag = tag;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ContentSpecToTagID", unique = true, nullable = false)
    public Integer getContentSpecToTagId() {
        return this.contentSpecToTagId;
    }

    public void setContentSpecToTagId(final Integer contentSpecToTagId) {
        this.contentSpecToTagId = contentSpecToTagId;
    }

    @ManyToOne
    @JoinColumn(name = "ContentSpecID", nullable = false)
    @NotNull
    public ContentSpec getContentSpec() {
        return this.contentSpec;
    }

    public void setContentSpec(final ContentSpec contentSpec) {
        this.contentSpec = contentSpec;
    }

    @ManyToOne
    @JoinColumn(name = "TagID", nullable = false)
    @NotNull
    public Tag getTag() {
        return this.tag;
    }

    public void setTag(final Tag tag) {
        this.tag = tag;
    }

    @Override
    @Transient
    public Integer getId() {
        return this.contentSpecToTagId;
    }

}
