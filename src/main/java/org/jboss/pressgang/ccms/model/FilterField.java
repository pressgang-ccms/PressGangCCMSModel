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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

/**
 * FilterField generated by hbm2java
 */
@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "FilterField")
public class FilterField extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -4542168304657354480L;

    private Integer filterFieldId;
    private Filter filter;
    private String field;
    private String value;
    private String description;

    public FilterField() {
    }

    public FilterField(final Filter filter, final String field, final String value) {
        this.filter = filter;
        this.field = field;
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "FilterFieldID", unique = true, nullable = false)
    public Integer getFilterFieldId() {
        return filterFieldId;
    }

    public void setFilterFieldId(final Integer filterFieldId) {
        this.filterFieldId = filterFieldId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FilterID", nullable = false)
    @NotNull
    public Filter getFilter() {
        return filter;
    }

    public void setFilter(final Filter filter) {
        this.filter = filter;
    }

    @Column(name = "Field", nullable = false, length = 255)
    @NotNull
    @NotBlank
    @Size(max = 255)
    public String getField() {
        return field;
    }

    public void setField(final String field) {
        this.field = field;
    }

    @Column(name = "Value", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "filter.field.name.notBlank")
    @NotBlank(message = "filter.field.name.notBlank")
    @Size(max = 65535)
    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Column(name = "Description", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    @Transient
    public Integer getId() {
        return filterFieldId;
    }

}
