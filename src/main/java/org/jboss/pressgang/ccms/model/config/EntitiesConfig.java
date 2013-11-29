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
    private static final String KEY_TAG_FROZEN = "tag.frozen";
    private static final String KEY_TAG_INTERNAL = "tag.internalOnly";
    private static final String KEY_TAG_LEGAL_NOTICE = "tag.legalNotice";
    private static final String KEY_TAG_OBSOLETE = "tag.obsolete";
    private static final String KEY_TAG_REVIEW = "tag.review";
    private static final String KEY_TAG_REVISION_HISTORY = "tag.revisionHistory";
    private static final String KEY_TAG_TASK = "tag.task";

    private static final String KEY_PROPERTY_TAG_ADDED_BY = "propertyTag.addedBy";
    private static final String KEY_PROPERTY_TAG_BUG_LINKS_LAST_VALIDATED = "propertyTag.bugLinksLastValidated";
    private static final String KEY_PROPERTY_TAG_CSP_ID = "propertyTag.cspId";
    private static final String KEY_PROPERTY_TAG_EMAIL = "propertyTag.email";
    private static final String KEY_PROPERTY_TAG_FIRST_NAME = "propertyTag.firstName";
    private static final String KEY_PROPERTY_TAG_FIXED_URL = "propertyTag.fixedUrl";
    private static final String KEY_PROPERTY_TAG_ORGANIZATION = "propertyTag.organization";
    private static final String KEY_PROPERTY_TAG_ORG_DIVISION = "propertyTag.orgDivision";
    private static final String KEY_PROPERTY_TAG_ORIGINAL_FILE_NAME = "propertyTag.originalFileName";
    private static final String KEY_PROPERTY_TAG_PRESSGANG_WEBSITE = "propertyTag.pressgangWebsite";
    private static final String KEY_PROPERTY_TAG_READ_ONLY = "propertyTag.readOnly";
    private static final String KEY_PROPERTY_TAG_SURNAME = "propertyTag.surname";
    private static final String KEY_PROPERTY_TAG_TAG_STYLE = "propertyTag.tagStyle";

    private static final String KEY_BLOB_CONSTANT_ROCBOOK_DTD = "constant.blob.rocbookDTD";
    private static final String KEY_BLOB_CONSTANT_FAIL_PENGUIN = "constant.blob.failPenguin";

    private static final String KEY_STRING_CONSTANT_XML_FORMATTING_ELEMENTS = "constant.string.xml.formatting";
    private static final String KEY_STRING_CONSTANT_DOCBOOK_ELEMENTS = "constant.string.docbook.elements";
    private static final String KEY_STRING_CONSTANT_TOPIC_TEMPLATE = "constant.string.template.topic";
    private static final String KEY_STRING_CONSTANT_CONTENT_SPEC_TEMPLATE = "constant.string.template.contentspec";
    private static final String KEY_STRING_CONSTANT_ARTICLE_BUILD_TEMPLATE = "constant.string.template.build.article";
    private static final String KEY_STRING_CONSTANT_ARTICLE_INFO_BUILD_TEMPLATE = "constant.string.template.build.articleInfo";
    private static final String KEY_STRING_CONSTANT_AUTHOR_GROUP_BUILD_TEMPLATE = "constant.string.template.build.authorGroup";
    private static final String KEY_STRING_CONSTANT_BOOK_BUILD_TEMPLATE = "constant.string.template.build.book";
    private static final String KEY_STRING_CONSTANT_BOOK_INFO_BUILD_TEMPLATE = "constant.string.template.build.bookInfo";
    private static final String KEY_STRING_CONSTANT_POM_BUILD_TEMPLATE = "constant.string.template.build.pom";
    private static final String KEY_STRING_CONSTANT_PREFACE_BUILD_TEMPLATE = "constant.string.template.build.preface";
    private static final String KEY_STRING_CONSTANT_PUBLICAN_CFG_BUILD_TEMPLATE = "constant.string.template.build.publicanCfg";
    private static final String KEY_STRING_CONSTANT_REV_HISTORY_BUILD_TEMPLATE = "constant.string.template.build.revHistory";
    private static final String KEY_STRING_CONSTANT_EMPTY_TOPIC_BUILD_TEMPLATE = "constant.string.template.build.emptyTopic";
    private static final String KEY_STRING_CONSTANT_INVALID_INJECTION_BUILD_TEMPLATE = "constant.string.template.build.invalidInjection";
    private static final String KEY_STRING_CONSTANT_INVALID_TOPIC_BUILD_TEMPLATE = "constant.string.template.build.invalidTopic";

    private static final String KEY_CATEGORY_WRITER = "category.writer";
    private static final String KEY_CATEGORY_TYPE = "category.type";

    private static final String KEY_USER_UNKNOWN = "user.unknown";

    private static final List<String> RESERVED_KEYS = Arrays.asList(
            KEY_BLOB_CONSTANT_FAIL_PENGUIN,
            KEY_BLOB_CONSTANT_ROCBOOK_DTD,
            KEY_CATEGORY_TYPE,
            KEY_CATEGORY_WRITER,
            KEY_PROPERTY_TAG_ADDED_BY,
            KEY_PROPERTY_TAG_BUG_LINKS_LAST_VALIDATED,
            KEY_PROPERTY_TAG_CSP_ID,
            KEY_PROPERTY_TAG_EMAIL,
            KEY_PROPERTY_TAG_FIRST_NAME,
            KEY_PROPERTY_TAG_FIXED_URL,
            KEY_PROPERTY_TAG_ORGANIZATION,
            KEY_PROPERTY_TAG_ORG_DIVISION,
            KEY_PROPERTY_TAG_ORIGINAL_FILE_NAME,
            KEY_PROPERTY_TAG_PRESSGANG_WEBSITE,
            KEY_PROPERTY_TAG_READ_ONLY,
            KEY_PROPERTY_TAG_SURNAME,
            KEY_PROPERTY_TAG_TAG_STYLE,
            KEY_STRING_CONSTANT_CONTENT_SPEC_TEMPLATE,
            KEY_STRING_CONSTANT_DOCBOOK_ELEMENTS,
            KEY_STRING_CONSTANT_TOPIC_TEMPLATE,
            KEY_STRING_CONSTANT_XML_FORMATTING_ELEMENTS,
            KEY_STRING_CONSTANT_ARTICLE_BUILD_TEMPLATE,
            KEY_STRING_CONSTANT_ARTICLE_INFO_BUILD_TEMPLATE,
            KEY_STRING_CONSTANT_AUTHOR_GROUP_BUILD_TEMPLATE,
            KEY_STRING_CONSTANT_BOOK_BUILD_TEMPLATE,
            KEY_STRING_CONSTANT_BOOK_INFO_BUILD_TEMPLATE,
            KEY_STRING_CONSTANT_POM_BUILD_TEMPLATE,
            KEY_STRING_CONSTANT_PREFACE_BUILD_TEMPLATE,
            KEY_STRING_CONSTANT_PUBLICAN_CFG_BUILD_TEMPLATE,
            KEY_STRING_CONSTANT_REV_HISTORY_BUILD_TEMPLATE,
            KEY_STRING_CONSTANT_EMPTY_TOPIC_BUILD_TEMPLATE,
            KEY_STRING_CONSTANT_INVALID_INJECTION_BUILD_TEMPLATE,
            KEY_STRING_CONSTANT_INVALID_TOPIC_BUILD_TEMPLATE,
            KEY_TAG_ABSTRACT,
            KEY_TAG_AUTHOR_GROUP,
            KEY_TAG_CONTENT_SPEC,
            KEY_TAG_FROZEN,
            KEY_TAG_INTERNAL,
            KEY_TAG_LEGAL_NOTICE, KEY_TAG_OBSOLETE,
            KEY_TAG_REVIEW,
            KEY_TAG_REVISION_HISTORY,
            KEY_TAG_TASK,
            KEY_USER_UNKNOWN
    );

    private static EntitiesConfig INSTANCE = new EntitiesConfig();

    public static EntitiesConfig getInstance() {
        return INSTANCE;
    }

    private EntitiesConfig() {
    }

    /*
     * TAGS
     */

    public Integer getAbstractTagId() {
        return getConfiguration().getInteger(KEY_TAG_ABSTRACT, null);
    }

    public Integer getAuthorGroupTagId() {
        return getConfiguration().getInteger(KEY_TAG_AUTHOR_GROUP, null);
    }

    public Integer getContentSpecTagId() {
        return getConfiguration().getInteger(KEY_TAG_CONTENT_SPEC, null);
    }

    public Integer getFrozenTagId() {
        return getConfiguration().getInteger(KEY_TAG_FROZEN, null);
    }

    public Integer getLegalNoticeTagId() {
        return getConfiguration().getInteger(KEY_TAG_LEGAL_NOTICE, null);
    }

    public Integer getInternalOnlyTagId() {
        return getConfiguration().getInteger(KEY_TAG_INTERNAL, null);
    }

    public Integer getObsoleteTagId() {
        return getConfiguration().getInteger(KEY_TAG_OBSOLETE, null);
    }

    public Integer getReviewTagId() {
        return getConfiguration().getInteger(KEY_TAG_REVIEW, null);
    }

    public Integer getRevisionHistoryTagId() {
        return getConfiguration().getInteger(KEY_TAG_REVISION_HISTORY, null);
    }

    public Integer getTaskTagId() {
        return getConfiguration().getInteger(KEY_TAG_TASK, null);
    }

    /*
     * PROPERTY TAGS
     */

    public Integer getAddedByPropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_ADDED_BY, null);
    }

    public Integer getBugLinksLastValidatedPropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_BUG_LINKS_LAST_VALIDATED, null);
    }

    public Integer getCSPIDPropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_CSP_ID, null);
    }

    public Integer getEmailPropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_EMAIL, null);
    }

    public Integer getFirstNamePropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_FIRST_NAME, null);
    }

    public Integer getFixedUrlPropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_FIXED_URL, null);
    }

    public Integer getOrganizationPropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_ORGANIZATION, null);
    }

    public Integer getOrganizationDivisionPropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_ORG_DIVISION, null);
    }

    public Integer getOriginalFileNamePropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_ORIGINAL_FILE_NAME, null);
    }

    public Integer getPressGangWebsitePropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_PRESSGANG_WEBSITE, null);
    }

    public Integer getReadOnlyPropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_READ_ONLY, null);
    }

    public Integer getSurnamePropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_SURNAME, null);
    }

    public Integer getTagStylePropertyTagId() {
        return getConfiguration().getInteger(KEY_PROPERTY_TAG_TAG_STYLE, null);
    }

    /*
     * BLOB CONSTANTS
     */

    public Integer getFailPenguinBlobConstantId() {
        return getConfiguration().getInteger(KEY_BLOB_CONSTANT_FAIL_PENGUIN, null);
    }

    public Integer getRocBookDTDBlobConstantId() {
        return getConfiguration().getInteger(KEY_BLOB_CONSTANT_ROCBOOK_DTD, null);
    }

    /*
     * STRING CONSTANTS
     */

    public Integer getXMLFormattingElementsStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_XML_FORMATTING_ELEMENTS, null);
    }

    public Integer getDocBookElementsStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_DOCBOOK_ELEMENTS, null);
    }

    public Integer getTopicTemplateStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_TOPIC_TEMPLATE, null);
    }

    public Integer getContentSpecTemplateStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_CONTENT_SPEC_TEMPLATE, null);
    }

    public Integer getArticleStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_ARTICLE_BUILD_TEMPLATE, null);
    }

    public Integer getArticleInfoStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_ARTICLE_INFO_BUILD_TEMPLATE, null);
    }

    public Integer getAuthorGroupStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_AUTHOR_GROUP_BUILD_TEMPLATE, null);
    }

    public Integer getBookStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_BOOK_BUILD_TEMPLATE, null);
    }

    public Integer getBookInfoStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_BOOK_INFO_BUILD_TEMPLATE, null);
    }

    public Integer getPOMStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_POM_BUILD_TEMPLATE, null);
    }

    public Integer getPrefaceStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_PREFACE_BUILD_TEMPLATE, null);
    }

    public Integer getPublicanCfgStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_PUBLICAN_CFG_BUILD_TEMPLATE, null);
    }

    public Integer getRevisionHistoryStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_REV_HISTORY_BUILD_TEMPLATE, null);
    }

    public Integer getEmptyTopicStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_EMPTY_TOPIC_BUILD_TEMPLATE, null);
    }

    public Integer getInvalidInjectionStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_INVALID_INJECTION_BUILD_TEMPLATE, null);
    }

    public Integer getInvalidTopicStringConstantId() {
        return getConfiguration().getInteger(KEY_STRING_CONSTANT_INVALID_TOPIC_BUILD_TEMPLATE, null);
    }

    /*
     * CATEGORIES
     */

    public Integer getTypeCategoryId() {
        return getConfiguration().getInteger(KEY_CATEGORY_TYPE, null);
    }

    public Integer getWriterCategoryId() {
        return getConfiguration().getInteger(KEY_CATEGORY_WRITER, null);
    }

    /*
     * USERS
     */

    public Integer getUnknownUserId() {
        return getConfiguration().getInteger(KEY_USER_UNKNOWN, null);
    }

    public void addUndefinedEntity(final String key, final Integer value) throws ConfigurationException {
        if (RESERVED_KEYS.contains(key)) {
            throw new ConfigurationException("\"" + key + "\" is already defined.");
        } else {
            getConfiguration().setProperty(key, value);
        }
    }

    public List<UndefinedEntity> getUndefinedEntities() {
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

        // Tags
        if (!validateTags()) {
            valid = false;
        }

        // Property Tags
        if (!validatePropertyTags()) {
            valid = false;
        }

        // Blob Constants
        if (!validateBlobConstants()) {
            valid = false;
        }

        // String Constants
        if (!validateStringConstants()) {
            valid = false;
        }

        // Categories
        if (!validateCategories()) {
            valid = false;
        }

        // Users
        if (getUnknownUserId() == null) {
            LOG.error("The Unknown User ID isn't configured (eg. {}=1)", KEY_USER_UNKNOWN);
            valid = false;
        }

        return valid;
    }

    protected boolean validateTags() {
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

        if (getFrozenTagId() == null) {
            LOG.error("The Frozen Tag ID isn't configured (eg. {}=1)", KEY_TAG_FROZEN);
            valid = false;
        }

        if (getInternalOnlyTagId() == null) {
            LOG.error("The Internal Only Tag ID isn't configured (eg. {}=1)", KEY_TAG_INTERNAL);
            valid = false;
        }

        if (getLegalNoticeTagId() == null) {
            LOG.error("The Legal Notice Tag ID isn't configured (eg. {}=1)", KEY_TAG_LEGAL_NOTICE);
            valid = false;
        }

        if (getObsoleteTagId() == null) {
            LOG.error("The Obsolete Tag ID isn't configured (eg. {}=1)", KEY_TAG_OBSOLETE);
            valid = false;
        }

//        if (getReviewTagId() == null) {
//            LOG.error("The Review Tag ID isn't configured (eg. {}=1)", KEY_TAG_REVIEW);
//            valid = false;
//        }

        if (getRevisionHistoryTagId() == null) {
            LOG.error("The Revision History Tag ID isn't configured (eg. {}=1)", KEY_TAG_REVISION_HISTORY);
            valid = false;
        }

        if (getTaskTagId() == null) {
            LOG.error("The Task Tag ID isn't configured (eg. {}=1)", KEY_TAG_TASK);
            valid = false;
        }

        return valid;
    }

    protected boolean validatePropertyTags() {
        boolean valid = true;

        if (getAddedByPropertyTagId() == null) {
            LOG.error("The Added By Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_ADDED_BY);
            valid = false;
        }

        if (getBugLinksLastValidatedPropertyTagId() == null) {
            LOG.error("The Bug Links Last Validated Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_BUG_LINKS_LAST_VALIDATED);
            valid = false;
        }

        if (getCSPIDPropertyTagId() == null) {
            LOG.error("The Content Spec ID Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_CSP_ID);
            valid = false;
        }

        if (getEmailPropertyTagId() == null) {
            LOG.error("The Email Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_EMAIL);
            valid = false;
        }

        if (getFirstNamePropertyTagId() == null) {
            LOG.error("The First Name Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_FIRST_NAME);
            valid = false;
        }

        if (getFixedUrlPropertyTagId() == null) {
            LOG.error("The Fixed URL Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_FIXED_URL);
            valid = false;
        }

        if (getOrganizationPropertyTagId() == null) {
            LOG.error("The Organization Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_ORGANIZATION);
            valid = false;
        }

        if (getOrganizationDivisionPropertyTagId() == null) {
            LOG.error("The Organization Division Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_ORG_DIVISION);
            valid = false;
        }

        if (getOriginalFileNamePropertyTagId() == null) {
            LOG.error("The Original File Name Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_ORIGINAL_FILE_NAME);
            valid = false;
        }

        if (getPressGangWebsitePropertyTagId() == null) {
            LOG.error("The PressGang Website Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_PRESSGANG_WEBSITE);
            valid = false;
        }

        if (getReadOnlyPropertyTagId() == null) {
            LOG.error("The Read Only Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_READ_ONLY);
            valid = false;
        }

        if (getSurnamePropertyTagId() == null) {
            LOG.error("The Surname Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_CSP_ID);
            valid = false;
        }

        if (getTagStylePropertyTagId() == null) {
            LOG.error("The Tag Style Property Tag ID isn't configured (eg. {}=1)", KEY_PROPERTY_TAG_TAG_STYLE);
            valid = false;
        }

        return valid;
    }

    protected boolean validateBlobConstants() {
        boolean valid = true;

        if (getFailPenguinBlobConstantId() == null) {
            LOG.error("The Fail Penguin Blob Constant ID isn't configured (eg. {}=1)", KEY_BLOB_CONSTANT_FAIL_PENGUIN);
            valid = false;
        }

        if (getRocBookDTDBlobConstantId() == null) {
            LOG.error("The RocBook DTD Blob Constant ID isn't configured (eg. {}=1)", KEY_BLOB_CONSTANT_ROCBOOK_DTD);
            valid = false;
        }

        return valid;
    }

    protected boolean validateStringConstants() {
        boolean valid = true;

        if (getXMLFormattingElementsStringConstantId() == null) {
            LOG.error("The XML Formatting Elements String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_XML_FORMATTING_ELEMENTS);
            valid = false;
        }

        if (getDocBookElementsStringConstantId() == null) {
            LOG.error("The XML Formatting Elements String Constant ID isn't configured (eg. {}=1)", KEY_STRING_CONSTANT_DOCBOOK_ELEMENTS);
            valid = false;
        }

        if (getTopicTemplateStringConstantId() == null) {
            LOG.error("The Topic Template String Constant ID isn't configured (eg. {}=1)", KEY_STRING_CONSTANT_TOPIC_TEMPLATE);
            valid = false;
        }

        if (getContentSpecTemplateStringConstantId() == null) {
            LOG.error("The Content Spec Template String Constant ID isn't configured (eg. {}=1)", KEY_STRING_CONSTANT_CONTENT_SPEC_TEMPLATE);
            valid = false;
        }

        if (getArticleStringConstantId() == null) {
            LOG.error("The Article.xml Build Template String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_ARTICLE_BUILD_TEMPLATE);
            valid = false;
        }

        if (getArticleInfoStringConstantId() == null) {
            LOG.error("The Article_Info.xml Build Template String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_ARTICLE_INFO_BUILD_TEMPLATE);
            valid = false;
        }

        if (getAuthorGroupStringConstantId() == null) {
            LOG.error("The Author_Group.xml Build Template String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_AUTHOR_GROUP_BUILD_TEMPLATE);
            valid = false;
        }

        if (getBookStringConstantId() == null) {
            LOG.error("The Book.xml Build Template String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_BOOK_BUILD_TEMPLATE);
            valid = false;
        }

        if (getBookInfoStringConstantId() == null) {
            LOG.error("The Book_Info.xml Build Template String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_BOOK_INFO_BUILD_TEMPLATE);
            valid = false;
        }

        if (getPOMStringConstantId() == null) {
            LOG.error("The pom.xml Build Template String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_POM_BUILD_TEMPLATE);
            valid = false;
        }

        if (getPrefaceStringConstantId() == null) {
            LOG.error("The Preface.xml Build Template String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_PREFACE_BUILD_TEMPLATE);
            valid = false;
        }

        if (getPublicanCfgStringConstantId() == null) {
            LOG.error("The publican.cfg Build Template String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_PUBLICAN_CFG_BUILD_TEMPLATE);
            valid = false;
        }

        if (getRevisionHistoryStringConstantId() == null) {
            LOG.error("The Revision_History.xml Build Template String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_REV_HISTORY_BUILD_TEMPLATE);
            valid = false;
        }

        if (getEmptyTopicStringConstantId() == null) {
            LOG.error("The Empty Topic Build Template String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_EMPTY_TOPIC_BUILD_TEMPLATE);
            valid = false;
        }

        if (getInvalidInjectionStringConstantId() == null) {
            LOG.error("The Invalid Injection Build Template String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_INVALID_INJECTION_BUILD_TEMPLATE);
            valid = false;
        }

        if (getInvalidTopicStringConstantId() == null) {
            LOG.error("The Invalid Topic Build Template String Constant ID isn't configured (eg. {}=1)",
                    KEY_STRING_CONSTANT_INVALID_TOPIC_BUILD_TEMPLATE);
            valid = false;
        }

        return valid;
    }

    protected boolean validateCategories() {
        boolean valid = true;

        if (getTypeCategoryId() == null) {
            LOG.error("The Type Category ID isn't configured (eg. {}=1)", KEY_CATEGORY_TYPE);
            valid = false;
        }

        if (getWriterCategoryId() == null) {
            LOG.error("The Writer Category ID isn't configured (eg. {}=1)", KEY_CATEGORY_WRITER);
            valid = false;
        }

        return valid;
    }
}
