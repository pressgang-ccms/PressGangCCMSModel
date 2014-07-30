/*
  Copyright 2011-2014 Red Hat, Inc

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
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

@Audited
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "PropertyTagToPropertyTagCategory",
        uniqueConstraints = @UniqueConstraint(columnNames = {"PropertyTagID", "PropertyTagCategoryID"}))
public class PropertyTagToPropertyTagCategory extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -4464431865504442832L;
    public static final String SELECT_ALL_QUERY = "select propertyTagToPropertyTagCategory from PropertyTagToPropertyTagCategory " +
            "propertyTagToPropertyTagCategory";

    private Integer propertyTagToPropertyTagCategoryId;
    private PropertyTag propertyTag;
    private PropertyTagCategory propertyTagCategory;
    private Integer sorting;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "PropertyTagToPropertyTagCategoryID", unique = true, nullable = false)
    public Integer getPropertyTagToPropertyTagCategoryId() {
        return propertyTagToPropertyTagCategoryId;
    }

    public void setPropertyTagToPropertyTagCategoryId(final Integer propertyTagToPropertyTagCategoryId) {
        this.propertyTagToPropertyTagCategoryId = propertyTagToPropertyTagCategoryId;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "PropertyTagID", nullable = false)
    @NotNull
    public PropertyTag getPropertyTag() {
        return propertyTag;
    }

    public void setPropertyTag(final PropertyTag propertyTag) {
        this.propertyTag = propertyTag;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "PropertyTagCategoryID", nullable = false)
    @NotNull
    public PropertyTagCategory getPropertyTagCategory() {
        return propertyTagCategory;
    }

    public void setPropertyTagCategory(final PropertyTagCategory propertyTagCategory) {
        this.propertyTagCategory = propertyTagCategory;
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
        return propertyTagToPropertyTagCategoryId;
    }
}
