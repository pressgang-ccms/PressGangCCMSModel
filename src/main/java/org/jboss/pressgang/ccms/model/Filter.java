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
import org.jboss.pressgang.ccms.utils.constants.CommonFilterConstants;

/**
 * Filter generated by hbm2java
 */
@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "Filter", uniqueConstraints = @UniqueConstraint(columnNames = {"FilterName"}))
public class Filter extends AuditedEntity implements java.io.Serializable {
    public static final String SELECT_ALL_QUERY = "select filter from Filter filter";
    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 1378015715100292871L;

    private Integer filterId;
    private String filterName;
    private String filterDescription;
    private Integer filterClassType;
    private Set<FilterTag> filterTags = new HashSet<FilterTag>(0);
    private Set<FilterCategory> filterCategories = new HashSet<FilterCategory>(0);
    private Set<FilterField> filterFields = new HashSet<FilterField>(0);
    private Set<FilterOption> filterOptions = new HashSet<FilterOption>(0);
    private Set<FilterLocale> filterLocales = new HashSet<FilterLocale>(0);

    public Filter() {
    }

    public Filter(final String filterName) {
        this.filterName = filterName;
    }

    public Filter(final String filterName, final String filterDescription, final Set<FilterTag> filterTags,
            final Set<FilterCategory> filterCategories) {
        this.filterName = filterName;
        this.filterDescription = filterDescription;
        this.filterTags = filterTags;
        this.filterCategories = filterCategories;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "FilterID", unique = true, nullable = false)
    public Integer getFilterId() {
        return filterId;
    }

    public void setFilterId(Integer filterId) {
        this.filterId = filterId;
    }

    @Column(name = "FilterName", nullable = false, length = 255)
    @NotNull(message = "{filter.name.notBlank}")
    @NotBlank(message = "{filter.name.notBlank}")
    @Size(max = 255)
    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(final String filterName) {
        this.filterName = filterName;
    }

    @Column(name = "FilterDescription", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getFilterDescription() {
        return filterDescription;
    }

    public void setFilterDescription(final String filterDescription) {
        this.filterDescription = filterDescription;
    }

    @Column(name = "FilterClassType", columnDefinition = "TINYINT")
    @NotNull
    public Integer getFilterClassType() {
        return filterClassType;
    }

    public void setFilterClassType(Integer filterClassType) {
        this.filterClassType = filterClassType;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filter", orphanRemoval = true, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<FilterTag> getFilterTags() {
        return filterTags;
    }

    public void setFilterTags(final Set<FilterTag> filterTags) {
        this.filterTags = filterTags;
    }

    @Transient
    public List<FilterTag> getFilterTagsList() {
        final List<FilterTag> retValue = new ArrayList<FilterTag>();
        for (final FilterTag filterTag : filterTags) {
            retValue.add(filterTag);
        }

        return retValue;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filter", orphanRemoval = true, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<FilterCategory> getFilterCategories() {
        return filterCategories;
    }

    public void setFilterCategories(final Set<FilterCategory> filterCategories) {
        this.filterCategories = filterCategories;
    }

    @Transient
    public List<FilterCategory> getFilterCategoriesList() {
        final List<FilterCategory> retValue = new ArrayList<FilterCategory>();
        for (final FilterCategory filterCategory : filterCategories) {
            retValue.add(filterCategory);
        }

        return retValue;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filter", orphanRemoval = true, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<FilterField> getFilterFields() {
        return filterFields;
    }

    public void setFilterFields(final Set<FilterField> filterFields) {
        this.filterFields = filterFields;
    }

    @Transient
    public List<FilterField> getFilterFieldsList() {
        final List<FilterField> retValue = new ArrayList<FilterField>();
        for (final FilterField filterField : filterFields) {
            retValue.add(filterField);
        }

        return retValue;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filter", orphanRemoval = true, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<FilterOption> getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(final Set<FilterOption> filterOptions) {
        this.filterOptions = filterOptions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filter", orphanRemoval = true, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<FilterLocale> getFilterLocales() {
        return filterLocales;
    }

    public void setFilterLocales(final Set<FilterLocale> filterLocales) {
        this.filterLocales = filterLocales;
    }

    @Transient
    public List<FilterLocale> getFilterLocalesList() {
        final List<FilterLocale> retValue = new ArrayList<FilterLocale>();
        for (final FilterLocale filterLocale : filterLocales) {
            retValue.add(filterLocale);
        }

        return retValue;
    }

    /**
     * Loops through the FilterTags held by this Filter, and returns the state of the tag if it exists, and -1 if it does not.
     *
     * @param tagID The id of the tag to get the state of
     * @return -1 if the tag was not found, otherwise the state of the tag
     */
    public List<Integer> hasTag(final Integer tagID) {
        final List<Integer> retValue = new ArrayList<Integer>();

        for (final FilterTag tag : getFilterTags()) {
            if (tag.getTag().getTagId().equals(tagID)) retValue.add(tag.getTagState());
        }

        return retValue;
    }

    public ArrayList<Integer> hasCategory(final Integer categoryId, final Integer projectId) {
        ArrayList<Integer> states = new ArrayList<Integer>();

        for (final FilterCategory cat : getFilterCategories()) {
            if (cat.getCategory().getCategoryId().equals(categoryId)
                    // Check if the project id matches. If the project is null then its the common project.
                    && ((cat.getProject() == null && projectId == null) || (cat.getProject() != null && cat.getProject().getId().equals(
                    projectId)))) states.add(cat.getCategoryState());
        }

        return states;
    }

    public ArrayList<Integer> hasLocale(final String localeName) {
        ArrayList<Integer> states = new ArrayList<Integer>();

        for (final FilterLocale locale : getFilterLocales()) {
            if (locale.getLocaleName().equals(localeName)) states.add(locale.getLocaleState());
        }

        return states;
    }

    /**
     * Not all FilterTags assigned to a filter have an associated FilterCategory. If a FilterTags belongs to a category that
     * does not have an associated FilterCategory, the default boolean logic is used.
     * <p/>
     * This function returns all the categories that the tags in this filter belong to. These are then matched to any associated
     * FilterCategories to find out how the tags are matched (i.e. are we matching all the tags ("And" logic), or one or more of
     * the tags matches ("Or" logic)), using the default logic ("Or") if no associated FilterCategory exists.
     *
     * @return An ArrayList containing the Category IDs that the tags in this filter belong to
     */
    @Transient
    public ArrayList<Category> getFilterTagCategories() {
        final ArrayList<Category> categories = new ArrayList<Category>();

        for (final FilterTag filterTag : filterTags) {
            final int filterTagState = filterTag.getTagState();

            if (filterTagState == CommonFilterConstants.MATCH_TAG_STATE || filterTagState == CommonFilterConstants.NOT_MATCH_TAG_STATE) {
                final Tag tag = filterTag.getTag();
                final Set<TagToCategory> tagToCategories = tag.getTagToCategories();

                if (tagToCategories.size() != 0) {
                    for (final TagToCategory category : tagToCategories) {
                        if (!categories.contains(category.getCategory())) {
                            categories.add(category.getCategory());
                        }
                    }
                } else {
                    if (!categories.contains(null)) categories.add(null);
                }
            }
        }

        return categories;
    }

    @Transient
    public ArrayList<Project> getFilterTagProjects() {
        final ArrayList<Project> projects = new ArrayList<Project>();

        for (final FilterTag filterTag : filterTags) {
            final Tag tag = filterTag.getTag();
            final Set<TagToProject> tagToProjects = tag.getTagToProjects();

            if (tagToProjects.size() != 0) {
                for (final TagToProject tagToProject : tagToProjects) {
                    final Project project = tagToProject.getProject();

                    if (!projects.contains(project)) projects.add(project);
                }
            } else {
                if (!projects.contains(null)) projects.add(null);
            }
        }

        return projects;
    }

    @Override
    @Transient
    public Integer getId() {
        return filterId;
    }

    @Transient
    public void addFilterTag(FilterTag filterTag) {
        getFilterTags().add(filterTag);
        filterTag.setFilter(this);
    }

    @Transient
    public void removeFilterTag(FilterTag filterTag) {
        getFilterTags().remove(filterTag);
        filterTag.setFilter(null);
    }

    @Transient
    public void addFilterLocale(FilterLocale filterLocale) {
        getFilterLocales().add(filterLocale);
        filterLocale.setFilter(this);
    }

    @Transient
    public void removeFilterLocale(FilterLocale filterLocale) {
        getFilterLocales().remove(filterLocale);
        filterLocale.setFilter(null);
    }

    @Transient
    public void addFilterCategory(FilterCategory filterCategory) {
        getFilterCategories().add(filterCategory);
        filterCategory.setFilter(this);
    }

    @Transient
    public void removeFilterCategory(FilterCategory filterCategory) {
        getFilterCategories().remove(filterCategory);
        filterCategory.setFilter(null);
    }

    @Transient
    public void addFilterField(FilterField filterField) {
        getFilterFields().add(filterField);
        filterField.setFilter(this);
    }

    @Transient
    public void removeFilterField(FilterField filterField) {
        getFilterFields().remove(filterField);
        filterField.setFilter(null);
    }
}
