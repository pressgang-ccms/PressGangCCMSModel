package org.jboss.pressgang.ccms.model.base;

import java.util.Date;
import javax.persistence.Transient;

/**
 * This base class is used to provide consistent access to previous versions of an audited entity
 */
public abstract class AuditedEntity<T extends AuditedEntity<T>> {
    private Date lastModified;
    private Number revision;

    /**
     * @return The ID of the database entity
     */
    abstract public Integer getId();

    /**
     * @return The last modified date for this entity
     */
    @Transient
    public Date getLastModifiedDate() {
        return lastModified;
    }

    /**
     * @param lastModified The last modified date for this entity
     */
    public void setLastModifiedDate(final Date lastModified) {
        this.lastModified = lastModified;
    }

    public Number getRevision() {
        return revision;
    }

    public void setRevision(final Number revision) {
        this.revision = revision;
    }
}
