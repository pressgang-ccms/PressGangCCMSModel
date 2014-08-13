/*
 * Copyright 2011-2014 Red Hat, Inc.
 *
 * This file is part of PressGang CCMS.
 *
 * PressGang CCMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PressGang CCMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with PressGang CCMS. If not, see <http://www.gnu.org/licenses/>.
 */

package org.jboss.pressgang.ccms.liquibase;

import java.sql.SQLException;
import java.sql.Statement;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import org.jboss.pressgang.ccms.liquibase.base.AbstractMigrateLocales;

public class MigrateTranslatedCSNodeStringLocales extends AbstractMigrateLocales implements CustomTaskChange {
    @Override
    protected void _execute(final JdbcConnection conn, final Statement stmt) throws SQLException, DatabaseException, CustomChangeException {
        migrateTableWithoutIndexes(conn, stmt, "TranslatedCSNodeString", "TranslatedCSNodeStringID", "Locale");
        migrateTableWithoutIndexes(conn, stmt, "TranslatedCSNodeString_AUD", "TranslatedCSNodeStringID", "Locale");
    }

    @Override
    public String getConfirmationMessage() {
        return "Successfully migrated the Translated Content Spec Node String locales.";
    }
}
