package org.jboss.pressgang.ccms.model;

// Generated Apr 14, 2011 12:17:30 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import javax.validation.constraints.NotNull;

import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;

/**
 * FilterCategory generated by hbm2java
 */
@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "FilterCategory")
public class FilterCategory extends AuditedEntity<FilterCategory> implements java.io.Serializable {
    private static final long serialVersionUID = -9199839815820171298L;

    private Integer filterCategoryId;
    private Filter filter;
    private Project project;
    private Category category;
    private int categoryState;

    public FilterCategory() {
    }

    public FilterCategory(final Filter filter, final Category category, final int categoryState) {
        this.filter = filter;
        this.category = category;
        this.categoryState = categoryState;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "FilterCategoryID", unique = true, nullable = false)
    public Integer getFilterCategoryId() {
        return this.filterCategoryId;
    }

    public void setFilterCategoryId(Integer filterCategoryId) {
        this.filterCategoryId = filterCategoryId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FilterID", nullable = false)
    @NotNull
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Filter getFilter() {
        return this.filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID", nullable = false)
    @NotNull
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(name = "CategoryState", nullable = false)
    public int getCategoryState() {
        return this.categoryState;
    }

    public void setCategoryState(int categoryState) {
        this.categoryState = categoryState;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectID")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Project getProject() {
        return project;
    }

    public void setProject(final Project project) {
        this.project = project;
    }

    @Override
    @Transient
    public Integer getId() {
        return this.filterCategoryId;
    }

}
