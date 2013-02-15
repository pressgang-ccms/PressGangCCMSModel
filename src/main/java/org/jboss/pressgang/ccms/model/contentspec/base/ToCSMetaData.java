package org.jboss.pressgang.ccms.model.contentspec.base;

import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.contentspec.CSMetaData;

/**
 * This class provides consistent access to the details of a property tag
 */
public abstract class ToCSMetaData<T extends AuditedEntity> extends AuditedEntity {
    protected CSMetaData csMetaData;
    protected String value;

    public abstract CSMetaData getCSMetaData();

    public abstract void setCSMetaData(final CSMetaData csMetaData);

    public abstract String getValue();

    public abstract void setValue(final String value);
}
