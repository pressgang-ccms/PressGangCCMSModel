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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "PropertyTagCategory", uniqueConstraints = @UniqueConstraint(columnNames = {"PropertyTagCategoryName"}))
public class PropertyTagCategory extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -8850771550313264840L;
    public static final String SELECT_ALL_QUERY = "select propertyTagCategory from PropertyTagCategory propertyTagCategory";

    private Integer propertyTagCategoryId;
    private String propertyTagCategoryName;
    private String propertyTagCategoryDescription;
    private Set<PropertyTagToPropertyTagCategory> propertyTagToPropertyTagCategories = new HashSet<PropertyTagToPropertyTagCategory>(0);

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "PropertyTagCategoryID", unique = true, nullable = false)
    public Integer getPropertyTagCategoryId() {
        return propertyTagCategoryId;
    }

    public void setPropertyTagCategoryId(final Integer propertyTagCategoryId) {
        this.propertyTagCategoryId = propertyTagCategoryId;
    }

    @Column(name = "PropertyTagCategoryName", nullable = false, length = 255)
    @NotNull(message = "{propertytagcategory.name.notBlank}")
    @NotBlank(message = "{propertytagcategory.name.notBlank}")
    @Size(max = 255)
    public String getPropertyTagCategoryName() {
        return propertyTagCategoryName;
    }

    public void setPropertyTagCategoryName(final String propertyTagCategoryName) {
        this.propertyTagCategoryName = propertyTagCategoryName;
    }

    @Column(name = "PropertyTagCategoryDescription", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getPropertyTagCategoryDescription() {
        return propertyTagCategoryDescription;
    }

    public void setPropertyTagCategoryDescription(final String propertyTagCategoryDescription) {
        this.propertyTagCategoryDescription = propertyTagCategoryDescription;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "propertyTagCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<PropertyTagToPropertyTagCategory> getPropertyTagToPropertyTagCategories() {
        return propertyTagToPropertyTagCategories;
    }

    public void setPropertyTagToPropertyTagCategories(final Set<PropertyTagToPropertyTagCategory> propertyTagToPropertyTagCategories) {
        this.propertyTagToPropertyTagCategories = propertyTagToPropertyTagCategories;
    }

    @PreRemove
    private void preRemove() {
        propertyTagToPropertyTagCategories.clear();
    }

    public boolean hasPropertyTag(final PropertyTag propertyTag) {
        for (final PropertyTagToPropertyTagCategory propertyTagToPropertyTagCategory : propertyTagToPropertyTagCategories)
            if (propertyTagToPropertyTagCategory.getPropertyTag().equals(propertyTag)) return true;
        return false;
    }

    public boolean removePropertyTag(final PropertyTag propertyTag) {
        for (final PropertyTagToPropertyTagCategory propertyTagToPropertyTagCategory : propertyTagToPropertyTagCategories) {
            if (propertyTagToPropertyTagCategory.getPropertyTag().equals(propertyTag)) {
                propertyTagToPropertyTagCategories.remove(propertyTagToPropertyTagCategory);
                propertyTag.getPropertyTagToPropertyTagCategories().remove(propertyTagToPropertyTagCategory);
                return true;
            }
        }

        return false;
    }

    public boolean addPropertyTag(final PropertyTag propertyTag) {
        boolean found = false;
        for (final PropertyTagToPropertyTagCategory propertyTagToPropertyTagCategory : propertyTagToPropertyTagCategories) {
            if (propertyTagToPropertyTagCategory.getPropertyTag().equals(propertyTag)) {
                found = true;
                break;
            }
        }

        if (!found) {
            final PropertyTagToPropertyTagCategory propertyTagToPropertyTagCategory = new PropertyTagToPropertyTagCategory();
            propertyTagToPropertyTagCategory.setPropertyTag(propertyTag);
            propertyTagToPropertyTagCategory.setPropertyTagCategory(this);
            propertyTagToPropertyTagCategories.add(propertyTagToPropertyTagCategory);
            propertyTag.getPropertyTagToPropertyTagCategories().add(propertyTagToPropertyTagCategory);
        }

        return !found;
    }

    @Override
    @Transient
    public Integer getId() {
        return propertyTagCategoryId;
    }

    @Transient
    public List<PropertyTagToPropertyTagCategory> getPropertyTagToPropertyTagCategoriesList() {
        final List<PropertyTagToPropertyTagCategory> propertyTagToPropertyTagCategories = new ArrayList<PropertyTagToPropertyTagCategory>();

        propertyTagToPropertyTagCategories.addAll(this.propertyTagToPropertyTagCategories);

        return propertyTagToPropertyTagCategories;
    }
}