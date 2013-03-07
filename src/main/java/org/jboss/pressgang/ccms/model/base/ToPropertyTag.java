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
