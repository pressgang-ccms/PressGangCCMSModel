package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

/**
 * TagToCategory generated by hbm2java
 */
@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TagToCategory", uniqueConstraints = @UniqueConstraint(columnNames = {"TagID", "CategoryID"}))
public class TagToCategory extends AuditedEntity implements java.io.Serializable {
    public static final String SELECT_ALL_QUERY = "select tagToCategory from TagToCategory tagToCategory";
    private static final long serialVersionUID = 1037132589833037549L;

    private Integer tagToCategoryId;
    private Tag tag;
    private Category category;
    private Integer sorting;

    public TagToCategory() {
    }

    public TagToCategory(final Tag tag, final Category category) {
        this.tag = tag;
        this.category = category;
    }

    public TagToCategory(final Tag tag, final Category category, final Integer sorting) {
        this.tag = tag;
        this.category = category;
        this.sorting = sorting;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TagToCategoryID", unique = true, nullable = false)
    public Integer getTagToCategoryId() {
        return tagToCategoryId;
    }

    public void setTagToCategoryId(final Integer tagToCategoryId) {
        this.tagToCategoryId = tagToCategoryId;
    }

    @ManyToOne
    @JoinColumn(name = "TagID", nullable = false)
    @NotNull
    public Tag getTag() {
        return tag;
    }

    public void setTag(final Tag tag) {
        this.tag = tag;
    }

    @ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false)
    @NotNull
    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    @Column(name = "Sorting")
    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    @Override
    @Transient
    public Integer getId() {
        return tagToCategoryId;
    }
}
