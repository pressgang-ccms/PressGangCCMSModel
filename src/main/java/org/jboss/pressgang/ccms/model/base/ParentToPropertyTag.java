package org.jboss.pressgang.ccms.model.base;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.sort.ParentToPropertyTagIDComparator;

/**
 * This class provides consistent access to property tags
 */
public abstract class ParentToPropertyTag<T extends AuditedEntity, U extends ToPropertyTag<U>> extends AuditedEntity {
    protected abstract Set<U> getPropertyTags();

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
}
