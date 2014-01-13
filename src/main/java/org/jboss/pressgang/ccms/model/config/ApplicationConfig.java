package org.jboss.pressgang.ccms.model.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationConfig extends AbstractConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);
    public static final String FILENAME = "application.properties";
    private static final String KEY_DEFAULT_LOCALE = "locale.default";
    private static final String KEY_LOCALES = "locales";
    private static final String KEY_UI_URL = "ui.url";
    private static final String KEY_DOCBOOK_TEMPLATE_IDS = "docbook.template.ids";
    private static final String KEY_SEO_CATEGORY_IDS = "seo.category.ids";
    private static final String KEY_DOCBUILDER_URL = "docbuilder.url";
    private static final String KEY_BUGZILLA_TEIID = "bugzilla.teiid";
    private static final String KEY_BUGZILLA_URL = "bugzilla.url";
    private static final String KEY_ZANATA_URL = "zanata.url";
    private static final String KEY_ZANATA_PROJECT = "zanata.project";
    private static final String KEY_ZANATA_PROJECT_VERSION = "zanata.project.version";

    private static final List<String> RESERVED_KEYS = Arrays.asList(
            KEY_BUGZILLA_TEIID,
            KEY_BUGZILLA_URL,
            KEY_DEFAULT_LOCALE,
            KEY_DOCBOOK_TEMPLATE_IDS,
            KEY_DOCBUILDER_URL,
            KEY_LOCALES,
            KEY_SEO_CATEGORY_IDS,
            KEY_UI_URL,
            KEY_ZANATA_URL,
            KEY_ZANATA_PROJECT,
            KEY_ZANATA_PROJECT_VERSION
    );

    private static ApplicationConfig INSTANCE = new ApplicationConfig();

    public static ApplicationConfig getInstance() {
        return INSTANCE;
    }

    private ApplicationConfig() {
    }

    @Override
    public void load(final File file) throws ConfigurationException {
        LOG.info("Loading the PressGang Application Configuration");
        super.load(file);
    }

    public String getDefaultLocale() {
        return getConfiguration().getString(KEY_DEFAULT_LOCALE, "en-US");
    }

    public void setDefaultLocale(final String defaultLocale) {
        getConfiguration().setProperty(KEY_DEFAULT_LOCALE, defaultLocale);
    }

    public List<String> getLocales() {
        final List<Object> list = getConfiguration().getList(KEY_LOCALES);
        final List<String> retValue = new ArrayList<String>();

        for (final Object o : list) {
            retValue.add(o.toString());
        }

        Collections.sort(retValue);

        return retValue;
    }

    public void setLocales(final List<String> locales) {
        getConfiguration().setProperty(KEY_LOCALES, locales);
    }

    public String getUIUrl() {
        return getConfiguration().getString(KEY_UI_URL);
    }

    public void setUIUrl(final String uiUrl){
        getConfiguration().setProperty(KEY_UI_URL, uiUrl);
    }

    public List<Integer> getDocBookTemplateStringConstantIds() {
        final List<Object> list = getConfiguration().getList(KEY_DOCBOOK_TEMPLATE_IDS);
        final List<Integer> retValue = new ArrayList<Integer>();

        for (final Object o : list) {
            if (o instanceof Integer) {
                retValue.add((Integer) o);
            } else {
                retValue.add(Integer.parseInt(o.toString()));
            }
        }

        return retValue;
    }

    public void setDocBookTemplateStringConstantIds(final List<Integer> docbookTemplateIds) {
        getConfiguration().setProperty(KEY_DOCBOOK_TEMPLATE_IDS, docbookTemplateIds);
    }

    public List<Integer> getSEOCategoryIds() {
        final List<Object> list = getConfiguration().getList(KEY_SEO_CATEGORY_IDS);
        final List<Integer> retValue = new ArrayList<Integer>();

        for (final Object o : list) {
            if (o instanceof Integer) {
                retValue.add((Integer) o);
            } else {
                retValue.add(Integer.parseInt(o.toString()));
            }
        }

        return retValue;
    }

    public void setSEOCategoryIds(final List<Integer> seoCategoryIds) {
        getConfiguration().setProperty(KEY_SEO_CATEGORY_IDS, seoCategoryIds);
    }

    public String getDocBuilderUrl() {
        return getConfiguration().getString(KEY_DOCBUILDER_URL);
    }

    public void setDocBuilderUrl(final String docBuilderUrl) {
        getConfiguration().setProperty(KEY_DOCBUILDER_URL, docBuilderUrl);
    }

    public Boolean getBugzillaTeiid() {
        return getConfiguration().getBoolean(KEY_BUGZILLA_TEIID, false);
    }

    public void setBugzillaTeiid(final Boolean useTeiid) {
        getConfiguration().setProperty(KEY_BUGZILLA_TEIID, useTeiid);
    }

    public String getBugzillaUrl() {
        return getConfiguration().getString(KEY_BUGZILLA_URL);
    }

    public void addUndefinedSetting(final String key, final String value) throws ConfigurationException {
        if (RESERVED_KEYS.contains(key)) {
            throw new ConfigurationException("\"" + key + "\" is already defined.");
        } else {
            getConfiguration().setProperty(key, value);
        }
    }

    public String getZanataUrl() {
        return getConfiguration().getString(KEY_ZANATA_URL);
    }

    public void setZanataUrl(final String zanataUrl){
        getConfiguration().setProperty(KEY_ZANATA_URL, zanataUrl);
    }

    public String getZanataProject() {
        return getConfiguration().getString(KEY_ZANATA_PROJECT);
    }

    public void setZanataProject(final String zanataProject){
        getConfiguration().setProperty(KEY_ZANATA_PROJECT, zanataProject);
    }

    public String getZanataProjectVersion() {
        return getConfiguration().getString(KEY_ZANATA_PROJECT_VERSION);
    }

    public void setZanataProjectVersion(final String zanataProjectVersion){
        getConfiguration().setProperty(KEY_ZANATA_PROJECT_VERSION, zanataProjectVersion);
    }

    public List<UndefinedSetting> getUndefinedSettings() {
        final List<UndefinedSetting> undefinedProperties = new ArrayList<UndefinedSetting>();
        for (final String key : getKeys()) {
            if (!RESERVED_KEYS.contains(key)) {
                final Object value = getConfiguration().getProperty(key);
                if (value instanceof List) {
                    final String joinedValue = CollectionUtilities.toSeperatedString((List<Object>) value,
                            getConfiguration().getListDelimiter() + "");
                    undefinedProperties.add(new UndefinedSetting(key, joinedValue));
                } else {
                    undefinedProperties.add(new UndefinedSetting(key, value.toString()));
                }
            }
        }
        return undefinedProperties;
    }

    public boolean validate() {
        boolean valid = true;

        if (!validateZanataSettings()) {
            valid = false;
        }

        return valid;
    }

    protected boolean validateZanataSettings() {
        boolean valid = true;

        if (getZanataUrl() == null) {
            LOG.error("The Zanata Server URL isn't configured (eg. {}=http://localhost/)", KEY_ZANATA_URL);
            valid = false;
        }

        if (getZanataProject() == null) {
            LOG.error("The Zanata Project isn't configured (eg. {}=My Project)", KEY_ZANATA_PROJECT);
            valid = false;
        }

        if (getZanataUrl() == null) {
            LOG.error("The Zanata Project Version isn't configured (eg. {}=1)", KEY_ZANATA_PROJECT_VERSION);
            valid = false;
        }

        return valid;
    }
}
