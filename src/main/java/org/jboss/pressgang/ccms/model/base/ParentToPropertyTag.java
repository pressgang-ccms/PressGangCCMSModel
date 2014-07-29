/*
  Copyright 2011-2014 Red Hat

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

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.interfaces.HasProperties;
import org.jboss.pressgang.ccms.model.sort.ParentToPropertyTagIDComparator;

/**
 * This class provides consistent access to property tags
 */
public abstract class ParentToPropertyTag<T extends AuditedEntity, U extends ToPropertyTag<U>> extends AuditedEntity implements
        HasProperties<U> {
    @Transient
    public String getProperties() {
        final List<U> tags = new ArrayList<U>(getPropertyTags());

        final StringBuilder retValue = new StringBuilder();

        Collections.sort(tags, new ParentToPropertyTagIDComparator());

        for (final ToPropertyTag<?> tagToPropertyTag : tags) {
            final PropertyTag propertyTag = tagToPropertyTag.getPropertyTag();

            if (retValue.length() > 0) retValue.append("\n");

            retValue.append(propertyTag.getPropertyTagName() + ": " + tagToPropertyTag.getValue());
        }

        return retValue.toString();
    }

    @Transient
    public List<U> getSortedToPropertyTags() {
        final List<U> sortedList = new ArrayList<U>(getPropertyTags());
        Collections.sort(sortedList, new ParentToPropertyTagIDComparator());
        return sortedList;
    }

    public boolean hasProperty(final PropertyTag propertyTag) {
        return hasProperty(propertyTag.getPropertyTagId());
    }

    public boolean hasProperty(final Integer propertyTag) {
        for (final ToPropertyTag<?> toPropertyTag : getPropertyTags()) {
            final PropertyTag myPropertyTag = toPropertyTag.getPropertyTag();
            if (myPropertyTag.getPropertyTagId().equals(propertyTag)) return true;
        }

        return false;
    }

    @Transient
    public U getProperty(final Integer propertyTagId) {
        for (final U toPropertyTag : getPropertyTags()) {
            final PropertyTag propertyTag = toPropertyTag.getPropertyTag();
            if (propertyTag.getPropertyTagId().equals(propertyTagId)) return toPropertyTag;
        }

        return null;
    }

    @Transient
    public List<U> getProperties(final Integer propertyTagId) {
        final List<U> properties = new ArrayList<U>();
        for (final U toPropertyTag : getPropertyTags()) {
            final PropertyTag propertyTag = toPropertyTag.getPropertyTag();
            if (propertyTag.getPropertyTagId().equals(propertyTagId)) {
                properties.add(toPropertyTag);
            }
        }

        return properties;
    }

    @Override
    @Transient
    public List<U> getPropertyTagsList() {
        return new ArrayList<U>(getPropertyTags());
    }
}
