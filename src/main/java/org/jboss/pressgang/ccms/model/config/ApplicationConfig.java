package org.jboss.pressgang.ccms.model.config;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private static final String KEY_PROCESS_DIR = "process.dir";
    private static final String KEY_READONLY = "readonly";
    /**
     * We will add new items to the JMS notification topics every 10 seconds by default.
     */
    private static final int KEY_JMS_UPDATE_FREQUENCY_DEFAULT = 10;
    private static final String KEY_JMS_UPDATE_FREQUENCY = "jms.UpdateFrequency";

    private static final String KEY_ZANATA_PREFIX = "zanata";
    private static final String KEY_ZANATA_PREFIX_WITH_DOT = "zanata.";

    private static final List<String> RESERVED_KEYS = Arrays.asList(
            KEY_BUGZILLA_TEIID,
            KEY_BUGZILLA_URL,
            KEY_DEFAULT_LOCALE,
            KEY_DOCBOOK_TEMPLATE_IDS,
            KEY_DOCBUILDER_URL,
            KEY_LOCALES,
            KEY_SEO_CATEGORY_IDS,
            KEY_UI_URL,
            KEY_PROCESS_DIR,
            KEY_READONLY,
            KEY_JMS_UPDATE_FREQUENCY
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

    public int getJmsUpdateFrequency() {
        return getConfiguration().getInt(KEY_JMS_UPDATE_FREQUENCY, KEY_JMS_UPDATE_FREQUENCY_DEFAULT);
    }

    public void setJmsUpdateFrequency(final int jmsUpdateFrequency) {
        getConfiguration().setProperty(KEY_JMS_UPDATE_FREQUENCY, jmsUpdateFrequency);
    }

    public boolean getReadOnly() {
        return getConfiguration().getBoolean(KEY_READONLY, false);
    }

    public void setReadOnly(final boolean readOnly) {
        getConfiguration().setProperty(KEY_READONLY, readOnly);
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

    public void setUIUrl(final String uiUrl) {
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

    public String getProcessDirectory() {
        return getConfiguration().getString(KEY_PROCESS_DIR);
    }

    public void addUndefinedSetting(final String key, final String value) throws ConfigurationException {
        if (RESERVED_KEYS.contains(key)) {
            throw new ConfigurationException("\"" + key + "\" is already defined.");
        } else if (key.startsWith(KEY_ZANATA_PREFIX_WITH_DOT)) {
            throw new ConfigurationException("\"" + key + "\" is in a predefined namespace.");
        } else {
            getConfiguration().setProperty(key, value);
        }
    }

    public List<UndefinedSetting> getUndefinedSettings() {
        final List<UndefinedSetting> undefinedProperties = new ArrayList<UndefinedSetting>();
        for (final String key : getKeys()) {
            if (!RESERVED_KEYS.contains(key) && !key.startsWith(KEY_ZANATA_PREFIX_WITH_DOT)) {
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

    public Map<String, ZanataServerConfig> getZanataServers() {
        final Map<String, ZanataServerConfig> configs = new HashMap<String, ZanataServerConfig>();
        final Iterator<String> keyIter = getConfiguration().getKeys(KEY_ZANATA_PREFIX);

        // Break up into id/keys first
        final Map<String, Set<String>> idKeyMap = new HashMap<String, Set<String>>();
        while (keyIter.hasNext()) {
            final String key = keyIter.next();
            String[] split = key.replace(KEY_ZANATA_PREFIX_WITH_DOT, "").split("\\.", 2);
            final String id = split[0];

            if (!idKeyMap.containsKey(id)) {
                idKeyMap.put(id, new HashSet<String>());
            }

            // Add the key
            final String serverKey = split[1];
            idKeyMap.get(id).add(serverKey);
        }

        // Populate the config map now that we know all the values
        for (final Map.Entry<String, Set<String>> entry : idKeyMap.entrySet()) {
            final String id = entry.getKey();
            final ZanataServerConfig zanataServerConfig = new ZanataServerConfig(id);

            final Set<String> keys = entry.getValue();
            for (final String key : keys) {
                final String fullKeyName = KEY_ZANATA_PREFIX_WITH_DOT + id + "." + key;

                if (key.equals("name")) {
                    zanataServerConfig.setName(getConfiguration().getString(fullKeyName));
                } else if (key.equals("url")) {
                    zanataServerConfig.setUrl(getConfiguration().getString(fullKeyName));
                }
                else if (key.equals("project")) {
                    zanataServerConfig.setProject(getConfiguration().getString(fullKeyName));
                }
                else if (key.equals("project.version")) {
                    zanataServerConfig.setProjectVersion(getConfiguration().getString(fullKeyName));
                }
            }

            if (isNullOrEmpty(zanataServerConfig.getName())) {
                zanataServerConfig.setName(id);
            }

            configs.put(id, zanataServerConfig);
        }

        return configs;
    }

    public void addZanataServerSetting(final String id, final String key, final String value) {
        getConfiguration().setProperty(KEY_ZANATA_PREFIX_WITH_DOT + id + "." + key, value);
    }

    public void removeZanataServerSetting(final String id, final String key) {
        getConfiguration().clearProperty(KEY_ZANATA_PREFIX_WITH_DOT + id + "." + key);
    }

    public void removeZanataServer(final String id) {
        final Iterator<String> keyIter = getConfiguration().getKeys(KEY_ZANATA_PREFIX_WITH_DOT + id);
        while (keyIter.hasNext()) {
            removeProperty(keyIter.next());
        }
    }

    public void addZanataServer(final ZanataServerConfig zanataServerConfig) {
        final String baseKeyName = KEY_ZANATA_PREFIX_WITH_DOT + zanataServerConfig + ".";
        if (zanataServerConfig.getName() != null) {
            getConfiguration().setProperty(baseKeyName + "name", zanataServerConfig.getName());
        }
        if (zanataServerConfig.getUrl() != null) {
            getConfiguration().setProperty(baseKeyName + "url", zanataServerConfig.getUrl());
        }
        if (zanataServerConfig.getProject() != null) {
            getConfiguration().setProperty(baseKeyName + "project", zanataServerConfig.getProject());
        }
        if (zanataServerConfig.getProjectVersion() != null) {
            getConfiguration().setProperty(baseKeyName + "project.version", zanataServerConfig.getProjectVersion());
        }
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

        boolean foundOneServer = false;
        final Iterator<String> keyIter = getConfiguration().getKeys(KEY_ZANATA_PREFIX);
        while (keyIter.hasNext()) {
            final String key = keyIter.next();
            final String[] split = key.replace(KEY_ZANATA_PREFIX_WITH_DOT, "").split("\\.", 2);

            // Make sure we have the minimum amount
            if (split.length < 2) {
                LOG.error("Invalid zanata server key (" + key + "). A zanata server key should be in the format \"" +
                        KEY_ZANATA_PREFIX_WITH_DOT + "<ID>.<KEY>=<VALUE>\". eg: \"" + KEY_ZANATA_PREFIX_WITH_DOT + "local.name=Local\"");
                valid = false;
                continue;
            } else {
                // Make sure we have a value
                final String value = getConfiguration().getString(key);
                if (isNullOrEmpty(value)) {
                    LOG.error("\"" + key + "\" has no value.");
                    valid = false;
                    continue;
                }

                foundOneServer = true;
            }
        }

        if (!foundOneServer) {
            LOG.error("No Zanata servers configured. At least one server must be configured. eg: " + KEY_ZANATA_PREFIX_WITH_DOT + "local" +
                    ".url=http://localhost/zanata/");
            valid = false;
        }

        return valid;
    }
}