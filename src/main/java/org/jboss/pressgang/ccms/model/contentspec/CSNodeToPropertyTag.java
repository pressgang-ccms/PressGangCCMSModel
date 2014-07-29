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
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.AuditQueryCreator;
import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.base.ToPropertyTag;

@Audited
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "CSNodeToPropertyTag")
public class CSNodeToPropertyTag extends ToPropertyTag<CSNodeToPropertyTag> implements java.io.Serializable {
    private static final long serialVersionUID = 2778607056935737000L;
    public static String SELECT_ALL_QUERY = "SELECT csNodeToPropertyTag FROM CSNodeToPropertyTag AS csNodeToPropertyTag";
    public static String SELECT_SIZE_QUERY = "SELECT COUNT(csNodeToPropertyTag) FROM CSNodeToPropertyTag AS csNodeToPropertyTag";

    private Integer csNodeToPropertyTagId;
    private CSNode csNode;

    public CSNodeToPropertyTag() {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CSNodeToPropertyTagID", unique = true, nullable = false)
    public Integer getCSNodeToPropertyTagID() {
        return csNodeToPropertyTagId;
    }

    public void setCSNodeToPropertyTagID(final Integer csNodeToPropertyTagID) {
        this.csNodeToPropertyTagId = csNodeToPropertyTagID;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "CSNodeID", nullable = false)
    @NotNull
    public CSNode getCSNode() {
        return csNode;
    }

    public void setCSNode(final CSNode csNode) {
        this.csNode = csNode;
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
    @Transient
    public Integer getId() {
        return csNodeToPropertyTagId;
    }

    @Override
    protected boolean testUnique(final EntityManager entityManager, final Number revision) {
        if (propertyTag.getPropertyTagIsUnique()) {
            /*
             * Since having to iterate over thousands of entities is slow, use a HQL query to find the count for us.
             */
            final Long count;
            if (revision == null) {
                final String query = CSNodeToPropertyTag.SELECT_SIZE_QUERY + " WHERE csNodeToPropertyTag.propertyTag = :propertyTagId AND" +
                        " csNodeToPropertyTag.value = :value";
                final Query entityQuery = entityManager.createQuery(query);
                entityQuery.setParameter("value", getValue());
                entityQuery.setParameter("propertyTagId", getPropertyTag().getId());
                count = (Long) entityQuery.getSingleResult();
            } else {
                final AuditReader reader = AuditReaderFactory.get(entityManager);
                final AuditQueryCreator queryCreator = reader.createQuery();
                final AuditQuery query = queryCreator.forEntitiesAtRevision(CSNodeToPropertyTag.class, revision).addProjection(
                        AuditEntity.id().count("csNodeToPropertyTagId")).add(
                        AuditEntity.relatedId("propertyTag").eq(getPropertyTag().getId())).add(
                        AuditEntity.property("value").eq(getValue()));
                query.setCacheable(true);
                count = (Long) query.getSingleResult();
            }

            if (count > 1) return false;
        }

        return true;
    }
}
