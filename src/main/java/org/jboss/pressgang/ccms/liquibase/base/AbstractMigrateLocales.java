package org.jboss.pressgang.ccms.liquibase.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

public abstract class AbstractMigrateLocales implements CustomTaskChange {
    protected static final Integer BATCH_SIZE = 100;

    protected Map<String, Integer> buildLocaleMap(final Statement stmt) throws SQLException, DatabaseException {
        final HashMap<String, Integer> localeMap = new HashMap<String, Integer>();

        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT LocaleID, Value FROM Locale");
            while (rs.next()) {
                localeMap.put(rs.getString("Value"), rs.getInt("LocaleID"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return localeMap;
    }

    protected void migrateTableWithIndexes(final JdbcConnection conn, final Statement stmt, final String tableName,
            final String localeColumnName) throws SQLException, DatabaseException, CustomChangeException {

        PreparedStatement preparedStmt = null;
        try {
            // Get the mapping
            final Map<String, Integer> localeMap = buildLocaleMap(stmt);

            // Do the updates
            preparedStmt = conn.prepareStatement("UPDATE " + tableName + " SET LocaleID = ? WHERE " + localeColumnName + " = ?");

            for (Map.Entry<String, Integer> entry : localeMap.entrySet()) {
                preparedStmt.setInt(1, entry.getValue());
                preparedStmt.setString(2, entry.getKey());

                // Add the query to the batch
                preparedStmt.addBatch();
            }

            // Workaround some locales
            migrateBrokenLocales(preparedStmt, localeMap);

            preparedStmt.executeBatch();
        } finally {
            if (preparedStmt != null) {
                preparedStmt.close();
            }
        }
    }

    /**
     * Migrates locales from a very early version of PressGang that was using the wrong locale value.
     *
     * @param preparedStmt
     * @param localeMap
     * @throws SQLException
     */
    private void migrateBrokenLocales(final PreparedStatement preparedStmt, final Map<String, Integer> localeMap) throws SQLException {
        final Integer enUSLocaleID = localeMap.get("en-US");
        if (enUSLocaleID != null && enUSLocaleID > 0) {
            migrateBrokenLocale(preparedStmt, localeMap, "es_US", enUSLocaleID);
            migrateBrokenLocale(preparedStmt, localeMap, "en_AU", enUSLocaleID);
            migrateBrokenLocale(preparedStmt, localeMap, "ar", enUSLocaleID);
            migrateBrokenLocale(preparedStmt, localeMap, "as", enUSLocaleID);
        }
    }

    private void migrateBrokenLocale(final PreparedStatement preparedStmt, final Map<String, Integer> localeMap, final String locale,
            final Integer enUSLocaleID) throws SQLException {
        if (!localeMap.containsKey(locale)) {
            preparedStmt.setInt(1, enUSLocaleID);
            preparedStmt.setString(2, locale);

            // Add the query to the batch
            preparedStmt.addBatch();
        }
    }

    protected void migrateTableWithoutIndexes(final JdbcConnection conn, final Statement stmt, final String tableName,
            final String idColumnName, final String localeColumnName) throws SQLException, DatabaseException, CustomChangeException {
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            // Get the mapping
            final Map<String, Integer> localeMap = buildLocaleMap(stmt);

            // Find the data to copy
            final Map<Integer, Integer> specToLocaleMap = new HashMap<Integer, Integer>();
            rs = stmt.executeQuery("SELECT " + idColumnName + ", " + localeColumnName + " FROM " + tableName);
            while (rs.next()) {
                final String locale = rs.getString(localeColumnName);
                if (locale != null && localeMap.containsKey(locale)) {
                    specToLocaleMap.put(rs.getInt(idColumnName), localeMap.get(locale));
                } else if ("es_US".equals(locale) || "en_AU".equals(locale) || "ar".equals(locale) || "as".equals(locale)) {
                    // Fix rogue es_US locales
                    specToLocaleMap.put(rs.getInt(idColumnName), localeMap.get("en-US"));
                } else if (locale != null) {
                    throw new CustomChangeException("No locale found for " + locale);
                }
            }
            rs.close();

            // Do the updates
            preparedStmt = conn.prepareStatement("UPDATE " + tableName + " SET LocaleID = ? WHERE " + idColumnName + " = ?");

            int count = 0;
            for (Map.Entry<Integer, Integer> entry : specToLocaleMap.entrySet()) {
                preparedStmt.setInt(1, entry.getValue());
                preparedStmt.setInt(2, entry.getKey());

                // Add the query to the batch
                preparedStmt.addBatch();

                // Check if we should flush the batches
                if (count % BATCH_SIZE == 0) {
                    preparedStmt.executeBatch();
                }
                count++;
            }

            preparedStmt.executeBatch();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preparedStmt != null) {
                preparedStmt.close();
            }
        }
    }

    @Override
    public final void execute(Database database) throws CustomChangeException {
        final JdbcConnection conn = (JdbcConnection) database.getConnection();

        Statement stmt = null;
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            _execute(conn, stmt);
        } catch (DatabaseException e) {
            throw new CustomChangeException(e);
        } catch (SQLException e) {
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

    protected abstract void _execute(final JdbcConnection conn, final Statement stmt) throws SQLException, DatabaseException,
            CustomChangeException;

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
