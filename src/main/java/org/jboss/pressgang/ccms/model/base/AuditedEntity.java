/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.model.base;

import javax.persistence.Transient;
import java.util.Date;

/**
 * This base class is used to provide consistent access to previous versions of an audited entity
 */
public abstract class AuditedEntity {
    private Date lastModified;
    private Number revision;

    /**
     * @return The ID of the database entity
     */
    public abstract Integer getId();

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

    public void setRevision(Number revision) {
        this.revision = revision;
    }
}
