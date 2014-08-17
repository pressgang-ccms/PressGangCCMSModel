package org.jboss.pressgang.ccms.liquibase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.jboss.pressgang.ccms.model.config.ZanataServerConfig;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;

@SuppressWarnings("deprecation")
public class MigrateTranslationServers implements CustomTaskChange {
    private List<String> movedServers = new ArrayList<String>();

    protected void populateTable(final Map<String, ZanataServerConfig> zanataServerConfigs, final PreparedStatement stmt) throws
            SQLException {
        // Insert the moved translation servers using batched statements
        int count = 1;
        for (final Map.Entry<String, ZanataServerConfig> entry : zanataServerConfigs.entrySet()) {
            final ZanataServerConfig config = entry.getValue();

            stmt.setInt(1, count);
            stmt.setString(2, "Zanata - " + config.getName());
            stmt.setString(3, config.getUrl());
            // Add row to the batch.
            stmt.addBatch();
            count++;
        }

        // Execute the updates and commit the changes
        stmt.executeBatch();
    }

    @Override
    public void execute(final Database database) throws CustomChangeException {
        final JdbcConnection conn = (JdbcConnection) database.getConnection();
        final ApplicationConfig appConfig = ApplicationConfig.getInstance();

        PreparedStatement stmt = null;
        try {
            appConfig.loadDefault();
            final Map<String, ZanataServerConfig> zanataServerConfigs = appConfig.getZanataServers();
            movedServers.addAll(zanataServerConfigs.keySet());

            // Create the insert prepared statement
            stmt = conn.prepareStatement("INSERT INTO TranslationServer (TranslationServerID, Name, URL, " +
                    "Username, ApiKey) VALUES (?, ?, ?, \"\", \"\")");
            populateTable(zanataServerConfigs, stmt);
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
        return "Moved " + CollectionUtilities.toSeperatedString(movedServers, ", ") + " to the database.";
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
