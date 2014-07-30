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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TranslatedContentSpec", uniqueConstraints = @UniqueConstraint(columnNames = {"ContentSpecID", "ContentSpecRevision"}))
public class TranslatedContentSpec extends AuditedEntity implements Serializable {
    private static final long serialVersionUID = 115815564559221625L;

    private Integer translatedContentSpecId = null;
    private Integer contentSpecId = null;
    private Integer contentSpecRevision = null;
    private Set<TranslatedCSNode> translatedCSNodes = new HashSet<TranslatedCSNode>(0);
    private ContentSpec enversContentSpec = null;

    @Override
    @Transient
    public Integer getId() {
        return translatedContentSpecId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TranslatedContentSpecID", unique = true, nullable = false)
    public Integer getTranslatedContentSpecId() {
        return translatedContentSpecId;
    }

    public void setTranslatedContentSpecId(Integer translatedContentSpecId) {
        this.translatedContentSpecId = translatedContentSpecId;
    }

    @Column(name = "ContentSpecID", nullable = false)
    @NotNull
    public Integer getContentSpecId() {
        return contentSpecId;
    }

    public void setContentSpecId(Integer contentSpecId) {
        this.contentSpecId = contentSpecId;
    }

    @Column(name = "ContentSpecRevision", nullable = false)
    @NotNull
    public Integer getContentSpecRevision() {
        return contentSpecRevision;
    }

    public void setContentSpecRevision(Integer contentSpecRevision) {
        this.contentSpecRevision = contentSpecRevision;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "translatedContentSpec", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<TranslatedCSNode> getTranslatedCSNodes() {
        return translatedCSNodes;
    }

    public void setTranslatedCSNodes(Set<TranslatedCSNode> translatedCSNodes) {
        this.translatedCSNodes = translatedCSNodes;
    }

    @Transient
    public ContentSpec getEnversContentSpec(final EntityManager entityManager) {
        if (enversContentSpec == null) {
            /* Find the envers contentSpec */
            final AuditReader reader = AuditReaderFactory.get(entityManager);
            final AuditQuery query = reader.createQuery().forEntitiesAtRevision(ContentSpec.class, contentSpecRevision).add(
                    AuditEntity.id().eq(contentSpecId));
            enversContentSpec = (ContentSpec) query.getSingleResult();
        }
        return enversContentSpec;
    }

    @Transient
    public void setEnversContentSpec(final ContentSpec enversContentSpec) {
        this.enversContentSpec = enversContentSpec;
    }

    public void addTranslatedNode(final TranslatedCSNode translatedNode) {
        translatedNode.setTranslatedContentSpec(this);
        getTranslatedCSNodes().add(translatedNode);
    }

    public void removeTranslatedNode(final TranslatedCSNode translatedNode) {
        translatedNode.setTranslatedContentSpec(null);
        getTranslatedCSNodes().remove(translatedNode);
    }
}
