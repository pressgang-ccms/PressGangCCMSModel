/*
  Copyright 2011-2014 Red Hat, Inc

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.model.base;

import javax.persistence.EntityManager;
import javax.persistence.Transient;

import org.jboss.pressgang.ccms.model.PropertyTag;

/**
 * This class provides consistent access to the details of a property tag
 */
public abstract class ToPropertyTag<T extends AuditedEntity> extends AuditedEntity {
    protected PropertyTag propertyTag;
    protected String value;

    @Transient
    public boolean isValid(final EntityManager entityManager, final Number revision) {
        if (propertyTag == null) return false;

        if (value == null) return propertyTag.isPropertyTagCanBeNull();

        if (!testUnique(entityManager, revision)) return false;

        return value.matches(propertyTag.getPropertyTagRegex());
    }

    protected abstract boolean testUnique(final EntityManager entityManager, final Number revision);

    public abstract PropertyTag getPropertyTag();

    public abstract void setPropertyTag(final PropertyTag propertyTag);

    public abstract String getValue();

    public abstract void setValue(final String value);
}
