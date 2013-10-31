package org.jboss.pressgang.ccms.model.utils;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.collection.LambdaCollections.with;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;


import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.TopicToTag;
import org.jboss.pressgang.ccms.model.TopicToTopic;
import org.jboss.pressgang.ccms.model.sort.TagToCategorySortingComparator;
import org.jboss.pressgang.ccms.utils.common.XMLUtilities;
import org.jboss.pressgang.ccms.utils.structures.StringToNodeCollection;
import org.w3c.dom.Document;

public class TopicUtilities {
    /**
     * Returns the minimum hash of the sentences in an XML file.
     * @param xml The xml to analyse
     * @return The minimum hash
     */
    public static Integer getMinHash(final String xml) {
        if (xml == null) {
            return null;
        }

        try {
            final Document doc = XMLUtilities.convertStringToDocument(xml);
            if (doc != null) {
                final List<StringToNodeCollection> strings = XMLUtilities.getTranslatableStringsV2(doc, false);
                if (strings != null) {
                    Integer minHash = null;
                    for (final StringToNodeCollection string : strings) {
                        final int hash = string.getTranslationString().hashCode();
                        if (minHash == null || hash < minHash) {
                            minHash = hash;
                        }
                    }
                    return minHash;
                }
            }
        }
        catch (final Exception ex) {

        }

        // if we get to here the topic does not have valid xml or has no translatable strings.
        final String[] sentences = xml.split("\\.");
        Integer minHash = null;
        for (final String string : sentences) {
            final int hash = string.hashCode();
            if (minHash == null || hash < minHash) {
                minHash = hash;
            }
        }
        return minHash;
    }

    /**
     * Validate and Fix a topics relationships to ensure that the topics related topics are still matched by the Related Topics
     * themselves.
     *
     * @param topic The topic to validate and fix the relationships for.
     */
    public static void validateAndFixRelationships(final Topic topic) {
        /* remove relationships to this topic in the parent collection */
        final ArrayList<TopicToTopic> removeList = new ArrayList<TopicToTopic>();
        for (final TopicToTopic topicToTopic : topic.getParentTopicToTopics())
            if (topicToTopic.getRelatedTopic().getTopicId().equals(topic.getTopicId())) removeList.add(topicToTopic);

        for (final TopicToTopic topicToTopic : removeList)
            topic.getParentTopicToTopics().remove(topicToTopic);

        /* remove relationships to this topic in the child collection */
        final ArrayList<TopicToTopic> removeChildList = new ArrayList<TopicToTopic>();
        for (final TopicToTopic topicToTopic : topic.getChildTopicToTopics())
            if (topicToTopic.getMainTopic().getTopicId().equals(topic.getTopicId())) removeChildList.add(topicToTopic);

        for (final TopicToTopic topicToTopic : removeChildList)
            topic.getChildTopicToTopics().remove(topicToTopic);
    }

    /**
     * Validate and Fix a topics tags so that mutually exclusive tags are enforced and also remove any tags that may have been
     * duplicated.
     *
     * @param topic The topic to fix the tags for.
     */
    public static void validateAndFixTags(final Topic topic) {
        /*
         * validate the tags that are applied to this topic. generally the gui should enforce these rules, with the exception of
         * the bulk tag apply function
         */

        // Create a collection of Categories mapped to TagToCategories, sorted by the Category sorting order
        final TreeMap<Category, ArrayList<TagToCategory>> tagDB = new TreeMap<Category, ArrayList<TagToCategory>>(
                Collections.reverseOrder());

        for (final TopicToTag topicToTag : topic.getTopicToTags()) {
            final Tag tag = topicToTag.getTag();
            for (final TagToCategory tagToCategory : tag.getTagToCategories()) {
                final Category category = tagToCategory.getCategory();

                if (!tagDB.containsKey(category)) tagDB.put(category, new ArrayList<TagToCategory>());

                tagDB.get(category).add(tagToCategory);
            }
        }

        // now remove conflicting tags
        for (final Category category : tagDB.keySet()) {
            /* sort by the tags position in the category */
            Collections.sort(tagDB.get(category), new TagToCategorySortingComparator(false));

            /*
             * because of the way we have ordered the tagDB collections, and the ArrayLists it contains, this process will
             * remove those tags that belong to lower priority categories, and lower priority tags in those categories
             */

            final ArrayList<TagToCategory> tagToCategories = tagDB.get(category);

            // remove tags in the same mutually exclusive categories
            if (category.isMutuallyExclusive() && tagToCategories.size() > 1) {
                while (tagToCategories.size() > 1) {
                    final TagToCategory tagToCategory = tagToCategories.get(1);
                    /* get the lower priority tag */
                    final Tag removeTag = tagToCategory.getTag();
                    /* remove it from the tagDB collection */
                    tagToCategories.remove(tagToCategory);

                    /* and remove it from the tag collection */
                    final ArrayList<TopicToTag> removeTopicToTagList = new ArrayList<TopicToTag>();
                    for (final TopicToTag topicToTag : topic.getTopicToTags()) {
                        if (topicToTag.getTag().equals(removeTag)) removeTopicToTagList.add(topicToTag);
                    }

                    for (final TopicToTag removeTopicToTag : removeTopicToTagList) {
                        topic.getTopicToTags().remove(removeTopicToTag);
                    }
                }
            }

            /* remove tags that are explicitly defined as mutually exclusive */
            for (final TagToCategory tagToCategory : tagToCategories) {
                final Tag tag = tagToCategory.getTag();
                for (final Tag exclusionTag : tag.getExcludedTags()) {
                    if (filter(having(on(TopicToTag.class).getTag(), equalTo(tagToCategory.getTag())),
                            topic.getTopicToTags()).size() != 0 && // make
                            /*
                             * sure that we have not removed this tag already
                             */
                            filter(having(on(TopicToTag.class).getTag(), equalTo(exclusionTag)),
                                    topic.getTopicToTags()).size() != 0 && // make
                            /*
                             * sure the exclusion tag exists
                             */
                            !exclusionTag.equals(tagToCategory.getTag())) // make
                    /*
                     * sure we are not trying to remove ourselves
                     */ {
                        with(topic.getTopicToTags()).remove(having(on(TopicToTag.class).getTag(), equalTo(exclusionTag)));
                    }
                }
            }
        }
    }

    private TopicUtilities() {
    }
}
