package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

/**
 * FilterOption generated by hbm2java
 */
@Audited
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "FilterLocale", uniqueConstraints = @UniqueConstraint(columnNames = { "LocaleName", "FilterID" }))
public class FilterLocale extends AuditedEntity<FilterLocale> implements java.io.Serializable {
    private static final long serialVersionUID = -7054018830978278379L;

    private Integer filterLocaleId;
    private Filter filter;
    private String localeName;
    private int localeState;

    public FilterLocale() {
    }

    public FilterLocale(final Filter filter, final String filterLocaleName, final int filterLocaleState) {
        this.filter = filter;
        this.localeName = filterLocaleName;
        this.localeState = filterLocaleState;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "FilterLocaleID", unique = true, nullable = false)
    public Integer getFilterLocaleId() {
        return this.filterLocaleId;
    }

    public void setFilterLocaleId(Integer filterLocaleId) {
        this.filterLocaleId = filterLocaleId;
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

    @Column(name = "LocaleName", nullable = false, length = 255)
    @NotNull
    @Length(max = 255)
    public String getLocaleName() {
        return this.localeName;
    }

    public void setLocaleName(final String localeName) {
        this.localeName = localeName;
    }

    @Column(name = "LocaleState", nullable = false)
    public int getLocaleState() {
        return this.localeState;
    }

    public void setLocaleState(final int localeState) {
        this.localeState = localeState;
    }

    @Override
    @Transient
    public Integer getId() {
        return this.filterLocaleId;
    }

}
