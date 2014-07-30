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

package org.jboss.pressgang.ccms.model.contentspec;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.AuditQueryCreator;
import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.model.base.ToPropertyTag;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "ContentSpecToPropertyTag")
public class ContentSpecToPropertyTag extends ToPropertyTag<ContentSpecToPropertyTag> implements Serializable {
    private static final long serialVersionUID = 7567494908179498295L;
    public static String SELECT_ALL_QUERY = "SELECT contentSpecToPropertyTag FROM ContentSpecToPropertyTag AS contentSpecToPropertyTag";
    public static String SELECT_SIZE_QUERY = "SELECT COUNT(contentSpecToPropertyTag) FROM ContentSpecToPropertyTag AS " +
            "contentSpecToPropertyTag";

    private Integer contentSpecToPropertyTagId = null;
    private ContentSpec contentSpec = null;

    @Override
    @Transient
    public Integer getId() {
        return contentSpecToPropertyTagId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ContentSpecToPropertyTagID", unique = true, nullable = false)
    public Integer getContentSpecToPropertyTagId() {
        return contentSpecToPropertyTagId;
    }

    public void setContentSpecToPropertyTagId(Integer contentSpecToPropertyTagId) {
        this.contentSpecToPropertyTagId = contentSpecToPropertyTagId;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "ContentSpecID", nullable = false)
    @NotNull
    public ContentSpec getContentSpec() {
        return contentSpec;
    }

    public void setContentSpec(ContentSpec contentSpec) {
        this.contentSpec = contentSpec;
    }

    @Override
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "PropertyTagID", nullable = false)
    @NotNull
    public PropertyTag getPropertyTag() {
        return propertyTag;
    }

    @Override
    public void setPropertyTag(final PropertyTag propertyTag) {
        this.propertyTag = propertyTag;
    }

    @Override
    @Column(name = "Value", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    protected boolean testUnique(final EntityManager entityManager, final Number revision) {
        if (propertyTag.getPropertyTagIsUnique()) {
            /*
             * Since having to iterate over thousands of entities is slow, use a HQL query to find the count for us.
             */
            final Long count;
            if (revision == null) {
                final String query = ContentSpecToPropertyTag.SELECT_SIZE_QUERY + " WHERE contentSpecToPropertyTag.propertyTag" +
                        ".propertyTagId = :propertyTagId AND contentSpecToPropertyTag.value = :value";
                final Query entityQuery = entityManager.createQuery(query);
                entityQuery.setParameter("value", getValue());
                entityQuery.setParameter("propertyTagId", getPropertyTag().getId());
                count = (Long) entityQuery.getSingleResult();
            } else {
                final AuditReader reader = AuditReaderFactory.get(entityManager);
                final AuditQueryCreator queryCreator = reader.createQuery();
                final AuditQuery query = queryCreator.forEntitiesAtRevision(ContentSpecToPropertyTag.class, revision).addProjection(
                        AuditEntity.id().count("contentSpecToPropertyTagId")).add(
                        AuditEntity.relatedId("propertyTag").eq(getPropertyTag().getId())).add(
                        AuditEntity.property("value").eq(getValue()));
                query.setCacheable(true);
                count = (Long) query.getSingleResult();
            }

            if (count > 1) return false;
        }

        return true;
    }

    @PrePersist
    @PreUpdate
    protected void preSave() {
        // Set the content specs last modified date if one of it's nodes change
        if (contentSpec != null) {
            contentSpec.setLastModified();
        }
    }
}
