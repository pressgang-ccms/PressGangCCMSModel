package org.jboss.pressgang.ccms.liquibase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import org.apache.commons.configuration.ConfigurationException;
import org.jboss.pressgang.ccms.model.config.ApplicationConfig;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;

public class MigrateLocales implements CustomTaskChange {
    private List<String> movedLocales = new ArrayList<String>();

    protected Map<String, String> buildLocaleMap() {
        final Map<String, String> LOCALE_MAP = new HashMap<String, String>();

        LOCALE_MAP.put("en-US", "en-US");
        LOCALE_MAP.put("ja", "ja-JP");
        LOCALE_MAP.put("es", "es-ES");
        LOCALE_MAP.put("zh-Hans", "zh-CN");
        LOCALE_MAP.put("zh-TW", "zh-TW");
        LOCALE_MAP.put("pt-BR", "pt-BR");
        LOCALE_MAP.put("de", "de-DE");
        LOCALE_MAP.put("fr", "fr-FR");
        LOCALE_MAP.put("ko", "ko-KR");
        LOCALE_MAP.put("it", "it-IT");
        LOCALE_MAP.put("ru", "ru-RU");

        return LOCALE_MAP;
    }

    protected void populateTable(final List<String> locales, final Map<String, String> buildLocaleMap, final PreparedStatement stmt) throws SQLException {
        // Insert the moved locales using batched statements
        for (int i = 0; i < locales.size(); i++) {
            final String locale = locales.get(i);
            final String buildLocale = buildLocaleMap.get(locale);

            stmt.setInt(1, i + 1);
            stmt.setString(2, locale);
            stmt.setString(3, locale);
            stmt.setString(4, buildLocale == null ? locale : buildLocale);
            // Add row to the batch.
            stmt.addBatch();
        }

        // Execute the updates and commit the changes
        stmt.executeBatch();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void execute(final Database database) throws CustomChangeException {
        final JdbcConnection conn = (JdbcConnection) database.getConnection();
        final ApplicationConfig appConfig = ApplicationConfig.getInstance();
        final Map<String, String> buildLocaleMap = buildLocaleMap();

        PreparedStatement stmt = null;
        try {
            appConfig.loadDefault();
            final List<String> currentLocales = appConfig.getLocales();
            movedLocales.addAll(currentLocales);

            // Create the insert prepared statement
            stmt = conn.prepareStatement("INSERT INTO Locale (LocaleID, Value, TranslationValue, BuildValue) VALUES (?, ?, ?, ?)");
            populateTable(currentLocales, buildLocaleMap, stmt);

            // Create the revisions insert statement
            stmt = conn.prepareStatement("INSERT INTO Locale_AUD (LocaleID, Value, TranslationValue, BuildValue, REV, REVTYPE, REVEND) " +
                    "VALUES (?, ?, ?, ?, 1, 0, NULL)");
            populateTable(currentLocales, buildLocaleMap, stmt);
        } catch (DatabaseException e) {
            throw new CustomChangeException(e);
        } catch (SQLException e) {
            throw new CustomChangeException(e);
        } catch (ConfigurationException e) {
            throw new CustomChangeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                // Ignore this one, already closed probably
            }
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Moved " + CollectionUtilities.toSeperatedString(movedLocales, ", ") + " to the database.";
    }

    @Override
    public void setUp() throws SetupException {
    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {
    }

    @Override
    public ValidationErrors validate(Database database) {
        return new ValidationErrors();
    }
}
