package org.jboss.pressgang.ccms.model.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntitiesConfig extends AbstractConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(EntitiesConfig.class);
    public static final String FILENAME = "entities.properties";
    private static final String KEY_TAG_ABSTRACT = "tag.abstract";
    private static final String KEY_TAG_AUTHOR_GROUP = "tag.authorGroup";
    private static final String KEY_TAG_CONTENT_SPEC = "tag.contentspec";
    private static final String KEY_TAG_LEGAL_NOTICE = "tag.legalNotice";
    private static final String KEY_TAG_REVIEW = "tag.review";
    private static final String KEY_TAG_REVISION_HISTORY = "tag.revisionHistory";
    private static final String KEY_PROPERTY_TAG_FIXED_URL = "propertyTag.fixedUrl";
    private static final String KEY_PROPERTY_TAG_ORIGINAL_FILE_NAME = "propertyTag.originalFileName";
    private static final String KEY_PROPERTY_TAG_TAG_STYLE = "propertyTag.tagStyle";
    private static final String KEY_CONSTANT_ROCBOOK_DTD = "constant.blob.rocbookDTD";
    private static final String KEY_CONSTANT_XML_FORMATTING_ELEMENTS = "constant.string.xml.formatting";
    private static final String KEY_CONSTANT_DOCBOOK_ELEMENTS = "constant.string.docbook.elements";
    private static final String KEY_CONSTANT_TOPIC_TEMPLATE = "constant.string.template.topic";
    private static final String KEY_CONSTANT_CONTENT_SPEC_TEMPLATE = "constant.string.template.contentspec";
    private static final String KEY_CATEGORY_WRITER = "category.writer";
    private static final String KEY_CATEGORY_TYPE = "category.type";
    private static final String KEY_USER_UNKNOWN = "user.unknown";

    private static final List<String> RESERVED_KEYS = Arrays.asList(
            KEY_CATEGORY_TYPE,
            KEY_CATEGORY_WRITER,
            KEY_CONSTANT_CONTENT_SPEC_TEMPLATE,
            KEY_CONSTANT_DOCBOOK_ELEMENTS,
            KEY_CONSTANT_ROCBOOK_DTD,
            KEY_CONSTANT_TOPIC_TEMPLATE,
            KEY_CONSTANT_XML_FORMATTING_ELEMENTS,
            KEY_PROPERTY_TAG_FIXED_URL,
            KEY_PROPERTY_TAG_ORIGINAL_FILE_NAME,
            KEY_PROPERTY_TAG_TAG_STYLE,
            KEY_TAG_ABSTRACT,
            KEY_TAG_AUTHOR_GROUP,
            KEY_TAG_CONTENT_SPEC,
            KEY_TAG_LEGAL_NOTICE,
            KEY_TAG_REVIEW,
            KEY_TAG_REVISION_HISTORY,
            KEY_USER_UNKNOWN
    );

    private static EntitiesConfig INSTANCE = new EntitiesConfig();

    public static EntitiesConfig getInstance() {
        return INSTANCE;
    }

    private EntitiesConfig() {
    }

    public Integer getAbstractTagId() {
        return getConfiguration().getInteger(KEY_TAG_ABSTRACT, null);
    }

    public Integer getAuthorGroupTagId() {
        return getConfiguration().getInteger(KEY_TAG_AUTHOR_GROUP, null);
    }

    public Integer getContentSpecTagId() {
        return getConfiguration().getInteger(KEY_TAG_CONTENT_SPEC, null);
    }

    public Integer getLegalNoticeTagId() {
        return getConfiguration().getInteger(KEY_TAG_LEGAL_NOTICE, null);
    }

    public Integer getReviewTagId() {
        return getConfiguration().getInteger(KEY_TAG_REVIEW, null);
    }

    public Integer getRevisionHistoryTagId() {
        return getConfiguration().getInteger(KEY_TAG_REVISION_HISTORY, null);
    }

    public Integer getFixedUrlPropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_FIXED_URL, null);
    }

    public Integer getOriginalFileNamePropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_ORIGINAL_FILE_NAME, null);
    }

    public Integer getTagStylePropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_TAG_STYLE, null);
    }

    public Integer getRocBookDTDBlobConstantId() {
        return getConfiguration().getInteger(KEY_CONSTANT_ROCBOOK_DTD, null);
    }

    public Integer getXMLFormattingElementsStringConstantId() {
        return getConfiguration().getInteger(KEY_CONSTANT_XML_FORMATTING_ELEMENTS, null);
    }

    public Integer getDocBookElementsStringConstantId() {
        return getConfiguration().getInteger(KEY_CONSTANT_DOCBOOK_ELEMENTS, null);
    }

    public Integer getTopicTemplateStringConstantId() {
        return getConfiguration().getInteger(KEY_CONSTANT_TOPIC_TEMPLATE, null);
    }

    public Integer getContentSpecTemplateStringConstantId() {
        return getConfiguration().getInteger(KEY_CONSTANT_CONTENT_SPEC_TEMPLATE, null);
    }

    public Integer getTypeCategoryId() {
        return getConfiguration().getInteger(KEY_CATEGORY_TYPE, null);
    }

    public Integer getWriterCategoryId() {
        return getConfiguration().getInteger(KEY_CATEGORY_WRITER, null);
    }

    public Integer getUnknownUserId() {
        return getConfiguration().getInteger(KEY_USER_UNKNOWN, null);
    }

    public void addUndefinedProperty(final String key, final Integer value) throws ConfigurationException {
        if (RESERVED_KEYS.contains(key)) {
            throw new ConfigurationException("\"" + key + "\" is already defined.");
        } else {
            getConfiguration().setProperty(key, value);
        }
    }

    public List<UndefinedEntity> getUndefinedProperties() {
        final List<UndefinedEntity> undefinedProperties = new ArrayList<UndefinedEntity>();
        for (final String key : getKeys()) {
            if (!RESERVED_KEYS.contains(key)) {
                undefinedProperties.add(new UndefinedEntity(key, getConfiguration().getInteger(key, null)));
            }
        }
        return undefinedProperties;
    }

    public boolean validate() {
        boolean valid = true;

        if (getAbstractTagId() == null) {
            LOG.error("The Abstract Tag ID isn't configured (eg. {}=1)", KEY_TAG_ABSTRACT);
            valid = false;
        }

        if (getAuthorGroupTagId() == null) {
            LOG.error("The Author Group Tag ID isn't configured (eg. {}=1)", KEY_TAG_AUTHOR_GROUP);
            valid = false;
        }

        if (getContentSpecTagId() == null) {
            LOG.error("The Content Spec Tag ID isn't configured (eg. {}=1)", KEY_TAG_CONTENT_SPEC);
            valid = false;
        }

        if (getLegalNoticeTagId() == null) {
            LOG.error("The Legal Notice Tag ID isn't configured (eg. {}=1)", KEY_TAG_LEGAL_NOTICE);
            valid = false;
        }

        if (getRevisionHistoryTagId() == null) {
            LOG.error("The Revision History Tag ID isn't configured (eg. {}=1)", KEY_TAG_REVISION_HISTORY);
            valid = false;
        }

        if (getFixedUrlPropertyTagId() == null) {
            LOG.error("The Fixed URL Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_FIXED_URL);
            valid = false;
        }

        if (getOriginalFileNamePropertyTagId() == null) {
            LOG.error("The Original File Name Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_ORIGINAL_FILE_NAME);
            valid = false;
        }

        if (getFixedUrlPropertyTagId() == null) {
            LOG.error("The Tag Style Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_TAG_STYLE);
            valid = false;
        }

        if (getRocBookDTDBlobConstantId() == null) {
            LOG.error("The RocBook DTD Blob Constant ID isn't configured (eg. {}=1)", KEY_CONSTANT_ROCBOOK_DTD);
            valid = false;
        }

        if (getXMLFormattingElementsStringConstantId() == null) {
            LOG.error("The XML Formatting Elements String Constant ID isn't configured (eg. {}=1)", KEY_CONSTANT_XML_FORMATTING_ELEMENTS);
            valid = false;
        }

        if (getDocBookElementsStringConstantId() == null) {
            LOG.error("The XML Formatting Elements String Constant ID isn't configured (eg. {}=1)", KEY_CONSTANT_DOCBOOK_ELEMENTS);
            valid = false;
        }

        if (getTopicTemplateStringConstantId() == null) {
            LOG.error("The Topic Template String Constant ID isn't configured (eg. {}=1)", KEY_CONSTANT_TOPIC_TEMPLATE);
            valid = false;
        }

        if (getContentSpecTemplateStringConstantId() == null) {
            LOG.error("The Content Spec Template String Constant ID isn't configured (eg. {}=1)", KEY_CONSTANT_CONTENT_SPEC_TEMPLATE);
            valid = false;
        }

        if (getTypeCategoryId() == null) {
            LOG.error("The Type Category ID isn't configured (eg. {}=1)", KEY_CATEGORY_TYPE);
            valid = false;
        }

        if (getWriterCategoryId() == null) {
            LOG.error("The Writer Category ID isn't configured (eg. {}=1)", KEY_CATEGORY_WRITER);
            valid = false;
        }

        if (getUnknownUserId() == null) {
            LOG.error("The Unknown User ID isn't configured (eg. {}=1)", KEY_USER_UNKNOWN);
            valid = false;
        }

        return valid;
    }
}
