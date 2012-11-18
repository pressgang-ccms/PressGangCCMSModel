package org.jboss.pressgang.ccms.model;

// Generated Oct 21, 2011 10:43:30 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import org.jboss.pressgang.ccms.model.base.AuditedEntity;

/**
 * FilterOption generated by hbm2java
 */
@Audited
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "FilterOption", uniqueConstraints = @UniqueConstraint(columnNames = { "FilterOptionName", "FilterID" }))
public class FilterOption extends AuditedEntity<FilterOption> implements java.io.Serializable {
    private static final long serialVersionUID = 4103450242726344458L;

    private Integer filterOptionId;
    private Filter filter;
    private String filterOptionName;
    private String filterOptionValue;

    public FilterOption() {
    }

    public FilterOption(final Filter filter, final String filterOptionName, final String filterOptionValue) {
        this.filter = filter;
        this.filterOptionName = filterOptionName;
        this.filterOptionValue = filterOptionValue;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "FilterOptionID", unique = true, nullable = false)
    public Integer getFilterOptionId() {
        return this.filterOptionId;
    }

    public void setFilterOptionId(Integer filterOptionId) {
        this.filterOptionId = filterOptionId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FilterID", nullable = false)
    @NotNull
    public Filter getFilter() {
        return this.filter;
    }

    public void setFilter(final Filter filter) {
        this.filter = filter;
    }

    @Column(name = "FilterOptionName", nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    public String getFilterOptionName() {
        return this.filterOptionName;
    }

    public void setFilterOptionName(final String filterOptionName) {
        this.filterOptionName = filterOptionName;
    }

    @Column(name = "FilterOptionValue", nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    public String getFilterOptionValue() {
        return this.filterOptionValue;
    }

    public void setFilterOptionValue(final String filterOptionValue) {
        this.filterOptionValue = filterOptionValue;
    }

    @Override
    @Transient
    public Integer getId() {
        return this.filterOptionId;
    }

}
