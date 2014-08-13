package org.jboss.pressgang.ccms.liquibase;

import java.sql.SQLException;
import java.sql.Statement;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import org.jboss.pressgang.ccms.liquibase.base.AbstractMigrateLocales;

public class MigrateTopicLocales extends AbstractMigrateLocales implements CustomTaskChange {
    @Override
    protected void _execute(final JdbcConnection conn, final Statement stmt) throws SQLException, DatabaseException, CustomChangeException {
        migrateTableWithoutIndexes(conn, stmt, "Topic", "TopicID", "TopicLocale");
        migrateTableWithoutIndexes(conn, stmt, "Topic_AUD", "TopicID", "TopicLocale");
    }

    @Override
    public String getConfirmationMessage() {
        return "Successfully migrated the Topic locales.";
    }
}
