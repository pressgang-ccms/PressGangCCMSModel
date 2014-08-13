package org.jboss.pressgang.ccms.liquibase;

import java.sql.SQLException;
import java.sql.Statement;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import org.jboss.pressgang.ccms.liquibase.base.AbstractMigrateLocales;

public class MigrateTranslatedTopicLocales extends AbstractMigrateLocales implements CustomTaskChange {
    @Override
    protected void _execute(final JdbcConnection conn, final Statement stmt) throws SQLException, DatabaseException, CustomChangeException {
        migrateTableWithIndexes(conn, stmt, "TranslatedTopicData", "TranslationLocale");
        migrateTableWithIndexes(conn, stmt, "TranslatedTopicData_AUD", "TranslationLocale");
    }

    @Override
    public String getConfirmationMessage() {
        return "Successfully migrated the Translated Topic Data locales.";
    }
}
