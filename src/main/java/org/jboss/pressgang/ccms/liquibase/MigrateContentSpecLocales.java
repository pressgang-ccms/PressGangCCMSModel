package org.jboss.pressgang.ccms.liquibase;

import java.sql.SQLException;
import java.sql.Statement;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import org.jboss.pressgang.ccms.liquibase.base.AbstractMigrateLocales;

public class MigrateContentSpecLocales extends AbstractMigrateLocales implements CustomTaskChange {
    @Override
    protected void _execute(final JdbcConnection conn, final Statement stmt) throws SQLException, DatabaseException, CustomChangeException {
        migrateTableWithoutIndexes(conn, stmt, "ContentSpec", "ContentSpecID", "Locale");
        migrateTableWithoutIndexes(conn, stmt, "ContentSpec_AUD", "ContentSpecID", "Locale");
    }

    @Override
    public String getConfirmationMessage() {
        return "Successfully migrated the Content Spec locales.";
    }
}
