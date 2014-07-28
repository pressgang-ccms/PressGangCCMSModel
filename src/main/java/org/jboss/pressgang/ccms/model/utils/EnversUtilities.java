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

package org.jboss.pressgang.ccms.model.utils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

public class EnversUtilities {

    /**
     * When returning a collection of entity revisions, the lastModified property is set automatically (in the getRevision
     * method). For entities returned from a database query, the last modified date needs to be found manually.
     *
     * @return either the date saved in the lastModified property, or the latest revision date if lastModified is null
     */
    public static <T extends AuditedEntity> Date getFixedLastModifiedDate(final EntityManager entityManager, final T entity) {
        return entity.getLastModifiedDate() != null ? entity.getLastModifiedDate() : getLatestRevisionDate(entityManager, entity);
    }

    /**
     * @return Returns the latest Envers revision number
     */
    public static <T extends AuditedEntity> Date getLatestRevisionDate(final EntityManager entityManager, final T entity) {
        final AuditReader reader = AuditReaderFactory.get(entityManager);
        return reader.getRevisionDate(getLatestRevision(entityManager, entity));
    }

    /**
     * @return Returns a collection of revisions
     */
    public static <T extends AuditedEntity> Map<Number, T> getRevisionEntities(final EntityManager entityManager, final T entity) {
        return getRevisionEntities(entityManager, (Class<T>) entity.getClass(), entity.getId());
    }

    /**
     * @return Returns a collection of revisions
     */
    public static <T extends AuditedEntity> Map<Number, T> getRevisionEntities(final EntityManager entityManager,
            final Class<T> entityClass, final Integer id) {
        final AuditReader reader = AuditReaderFactory.get(entityManager);
        final List<Number> revisions = reader.getRevisions(entityClass, id);
        Collections.sort(revisions, Collections.reverseOrder());

        /* Use a LinkedHashMap to preserver the order */
        final Map<Number, T> retValue = new LinkedHashMap<Number, T>();
        for (final Number revision : revisions)
            retValue.put(revision, getRevision(reader, entityClass, id, revision, false));

        return retValue;
    }

    /**
     * @return Returns the list of revision numbers for this entity, as maintained by Envers
     */
    public static <T extends AuditedEntity> List<Number> getRevisions(final EntityManager entityManager, final T entity) {
        return getRevisions(entityManager, entity.getClass(), entity.getId());
    }

    /**
     * @return Returns the list of revision numbers for this entity, as maintained by Envers
     */
    public static <T extends AuditedEntity> List<Number> getRevisions(final EntityManager entityManager, final Class<T> entityClass,
            final Number id) {
        final AuditReader reader = AuditReaderFactory.get(entityManager);
        final List<Number> retValue = reader.getRevisions(entityClass, id);
        Collections.sort(retValue, Collections.reverseOrder());
        return retValue;
    }

    /**
     * @param entityManager
     * @param revision
     * @return
     */
    public static <T extends AuditedEntity> T getRevision(final EntityManager entityManager, final T entity, final Number revision) {
        final AuditReader reader = AuditReaderFactory.get(entityManager);
        return getRevision(reader, (Class<T>) entity.getClass(), entity.getId(), revision, true);
    }

    /**
     * @param entityManager
     * @param revision
     * @return
     */
    public static <T extends AuditedEntity> T getRevision(final EntityManager entityManager, final Class<T> entityClass, final Integer id,
            final Number revision) {
        final AuditReader reader = AuditReaderFactory.get(entityManager);
        return getRevision(reader, entityClass, id, revision, true);
    }

    @SuppressWarnings("unchecked")
    private static <T extends AuditedEntity> T getRevision(final AuditReader reader, final Class<T> entityClass, final Integer id,
            final Number revision, boolean revisionCheck) {
        final T revEntity = (T) reader.find(entityClass, id, revision);
        if (revEntity == null) return null;

        final Date revisionLastModified = reader.getRevisionDate(revision);
        revEntity.setLastModifiedDate(revisionLastModified);

        revEntity.setRevision(revisionCheck ? getClosestRevision(reader, entityClass, id, revision) : revision);

        return revEntity;
    }

    public static <T extends AuditedEntity> Number getLatestRevision(final EntityManager entityManager, final T entity) {
        return getLatestRevision(entityManager, entity.getClass(), entity.getId());
    }

    public static <T extends AuditedEntity> Number getLatestRevision(final EntityManager entityManager, final Class<T> entityClass,
            final Integer id) {
        final AuditReader reader = AuditReaderFactory.get(entityManager);
        final List<Number> retValue = reader.getRevisions(entityClass, id);
        Collections.sort(retValue, Collections.reverseOrder());
        return retValue.size() != 0 ? retValue.get(0) : -1;
    }

    public static <T extends AuditedEntity> Number getClosestRevision(final EntityManager entityManager, final T entity,
            final Number revision) {
        return getClosestRevision(entityManager, entity.getClass(), entity.getId(), revision);
    }

    public static <T extends AuditedEntity> Number getClosestRevision(final EntityManager entityManager, final Class<T> entityClass,
            final Integer id, final Number revision) {
        final AuditReader reader = AuditReaderFactory.get(entityManager);
        return getClosestRevision(reader, entityClass, id, revision);
    }

    public static <T extends AuditedEntity> Number getClosestRevision(final AuditReader reader, final Class<T> entityClass,
            final Integer id, final Number revision) {
        // Find the closest revision that is less than or equal to the revision specified.
        final Number closestRevision = (Number) reader.createQuery().forRevisionsOfEntity(entityClass, false, true).addProjection(
                AuditEntity.revisionNumber().max()).add(AuditEntity.id().eq(id)).add(
                AuditEntity.revisionNumber().le(revision)).getSingleResult();
        return closestRevision;
    }
}
