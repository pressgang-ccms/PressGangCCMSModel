package org.jboss.pressgang.ccms.model.utils;

import javax.persistence.EntityManager;
import java.util.Map;

import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.Filter;
import org.jboss.pressgang.ccms.model.FilterCategory;
import org.jboss.pressgang.ccms.model.FilterField;
import org.jboss.pressgang.ccms.model.FilterLocale;
import org.jboss.pressgang.ccms.model.FilterTag;
import org.jboss.pressgang.ccms.model.Project;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.filter.IFieldFilter;
import org.jboss.pressgang.ccms.utils.constants.CommonFilterConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityUtilities {
    private static final Logger log = LoggerFactory.getLogger(EntityUtilities.class);

    /**
     * This function takes the url parameters and uses them to populate a Filter object
     */
    public static Filter populateFilter(final EntityManager entityManager, final Map<String, String> paramMap, final String filterName,
            final String tagPrefix, final String groupTagPrefix, final String categoryInternalPrefix, final String categoryExternalPrefix,
            final String localePrefix, final IFieldFilter fieldFilter) {
        // attempt to get the filter id from the url
        Integer filterId = null;
        if (paramMap.containsKey(filterName)) {
            final String filterQueryParam = paramMap.get(filterName);

            try {
                filterId = Integer.parseInt(filterQueryParam);
            } catch (final Exception ex) {
                // filter value was not an integer
                filterId = null;

                log.debug("The filter ID URL query parameter was not an integer. Got " + filterQueryParam + ". Probably a malformed URL.",
                        ex);
            }
        }

        Filter filter = null;

        /* First attempt to populate the filter from a filterID variable */
        if (filterId != null) {
            filter = entityManager.find(Filter.class, filterId);
        }

        /* If that fails, use the other URL params */
        if (filter == null) {
            filter = new Filter();

            for (final String key : paramMap.keySet()) {
                final boolean tagVar = tagPrefix != null && key.startsWith(tagPrefix);
                final boolean groupTagVar = groupTagPrefix != null && key.startsWith(groupTagPrefix);
                final boolean catIntVar = categoryInternalPrefix != null && key.startsWith(categoryInternalPrefix);
                final boolean catExtVar = categoryExternalPrefix != null && key.startsWith(categoryExternalPrefix);
                final boolean localeVar = localePrefix != null && key.matches("^" + localePrefix + "\\d*$");
                final String state = paramMap.get(key);

                // add the filter category states
                if (catIntVar || catExtVar) {
                    /*
                     * get the category and project id data from the variable name
                     */
                    final String catProjDetails = catIntVar ? key.replaceFirst(categoryInternalPrefix, "") : key.replaceFirst(
                            categoryExternalPrefix, "");
                    // split the category and project id out of the data
                    final String[] catProjID = catProjDetails.split("-");

                    /*
                     * some validity checks. make sure we have one or two strings after the split.
                     */
                    if (catProjID.length != 1 && catProjID.length != 2) continue;

                    // try to get the category and project ids
                    Integer catID = null;
                    Integer projID = null;
                    try {
                        catID = Integer.parseInt(catProjID[0]);

                        /*
                         * if the array has just one element, we have only specified the category. in this case the project is
                         * the common project
                         */
                        if (catProjID.length == 2) projID = Integer.parseInt(catProjID[1]);
                    } catch (final Exception ex) {
                        log.debug("Was expecting an integer. Got " + catProjID[0] + ". Probably a malformed URL.", ex);
                        continue;
                    }

                    // at this point we have found a url variable that
                    // contains a catgeory and project id

                    final Category category = entityManager.find(Category.class, catID);
                    final Project project = projID != null ? entityManager.find(Project.class, projID) : null;

                    Integer dbState;

                    if (catIntVar) {
                        if (state.equals(CommonFilterConstants.AND_LOGIC)) dbState = CommonFilterConstants.CATEGORY_INTERNAL_AND_STATE;
                        else dbState = CommonFilterConstants.CATEGORY_INTERNAL_OR_STATE;
                    } else {
                        if (state.equals(CommonFilterConstants.AND_LOGIC)) dbState = CommonFilterConstants.CATEGORY_EXTERNAL_AND_STATE;
                        else dbState = CommonFilterConstants.CATEGORY_EXTERNAL_OR_STATE;
                    }

                    final FilterCategory filterCategory = new FilterCategory();
                    filterCategory.setFilter(filter);
                    filterCategory.setProject(project);
                    filterCategory.setCategory(category);
                    filterCategory.setCategoryState(dbState);

                    filter.getFilterCategories().add(filterCategory);
                }

                // add the filter tag states
                else if (tagVar) {
                    try {
                        final Integer tagId = Integer.parseInt(key.replaceFirst(tagPrefix, ""));
                        final Integer intState = Integer.parseInt(state);

                        // get the Tag object that the tag id represents
                        final Tag tag = entityManager.getReference(Tag.class, tagId);

                        if (tag != null) {
                            final FilterTag filterTag = new FilterTag();
                            filterTag.setTag(tag);
                            filterTag.setTagState(intState);
                            filterTag.setFilter(filter);
                            filter.getFilterTags().add(filterTag);
                        }
                    } catch (final Exception ex) {
                        log.debug("Probably an invalid tag query parameter. Parameter: " + key + " Value: " + state, ex);
                    }
                } else if (groupTagVar) {
                    final Integer tagId = Integer.parseInt(key.replaceFirst(groupTagPrefix, ""));
                    // final Integer intState = Integer.parseInt(state);

                    // get the Tag object that the tag id represents
                    final Tag tag = entityManager.getReference(Tag.class, tagId);

                    if (tag != null) {
                        final FilterTag filterTag = new FilterTag();
                        filterTag.setTag(tag);
                        filterTag.setTagState(CommonFilterConstants.GROUP_TAG_STATE);
                        filterTag.setFilter(filter);
                        filter.getFilterTags().add(filterTag);
                    }
                } else if (localeVar) {
                    try {
                        final String localeName = state.replaceAll("\\d", "");
                        final Integer intState = Integer.parseInt(state.replaceAll("[^\\d]", ""));

                        final FilterLocale filterLocale = new FilterLocale();
                        filterLocale.setLocaleName(localeName);
                        filterLocale.setLocaleState(intState);
                        filterLocale.setFilter(filter);
                        filter.getFilterLocales().add(filterLocale);
                    } catch (final Exception ex) {
                        log.debug("Probably an invalid locale query parameter. Parameter: " + key + " Value: " + state, ex);
                    }
                }

                // add the filter field states
                else {
                    if (fieldFilter.hasFieldName(key)) {
                        final FilterField filterField = new FilterField();
                        filterField.setFilter(filter);
                        filterField.setField(key);
                        filterField.setValue(state);
                        filterField.setDescription(fieldFilter.getFieldDesc(key));
                        filter.getFilterFields().add(filterField);
                    }
                }

            }

        }

        return filter;
    }
}
