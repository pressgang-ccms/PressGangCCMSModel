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

package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.sort.LocaleValueComparator;

/**
 * A TranslatedTopic represents a particular revision of a topic. This revision then holds the translated version of the
 * document for various locales in a collection of TranslatedTopicData entities.
 */
@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TranslatedTopic", uniqueConstraints = @UniqueConstraint(columnNames = {"TopicRevision", "TopicID", "TranslatedCSNodeID"}))
public class TranslatedTopic extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = 4190214754023153898L;
    public static final String SELECT_ALL_QUERY = "select translatedTopic from TranslatedTopic translatedTopic";

    private Integer translatedTopicId;
    private Integer topicId;
    private Integer topicRevision;
    private TranslatedCSNode translatedCSNode;
    private String translatedXMLCondition;
    private String customEntities;
    private Set<TranslatedTopicData> translatedTopicDatas = new HashSet<TranslatedTopicData>(0);
    private Topic enversTopic;

    @Transient
    public Integer getId() {
        return translatedTopicId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TranslatedTopicID", unique = true, nullable = false)
    public Integer getTranslatedTopicId() {
        return translatedTopicId;
    }

    public void setTranslatedTopicId(final Integer translatedTopicId) {
        this.translatedTopicId = translatedTopicId;
    }

    @Column(name = "TopicID", nullable = false)
    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(final Integer topicId) {
        this.topicId = topicId;
    }

    @Column(name = "TopicRevision", nullable = false)
    public Integer getTopicRevision() {
        return topicRevision;
    }

    public void setTopicRevision(final Integer topicRevision) {
        this.topicRevision = topicRevision;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TranslatedCSNodeID")
    public TranslatedCSNode getTranslatedCSNode() {
        return translatedCSNode;
    }

    public void setTranslatedCSNode(final TranslatedCSNode translatedCSNode) {
        this.translatedCSNode = translatedCSNode;
    }

    @Column(name = "TranslatedXMLCondition", nullable = true, length = 255)
    @Size(max = 255)
    public String getTranslatedXMLCondition() {
        return translatedXMLCondition;
    }

    public void setTranslatedXMLCondition(final String translatedXMLCondition) {
        this.translatedXMLCondition = translatedXMLCondition;
    }

    @Column(name = "CustomEntities", nullable = true, columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getCustomEntities() {
        return customEntities;
    }

    public void setCustomEntities(String customEntities) {
        this.customEntities = customEntities;
    }

    /**
     * @return The File ID used to identify this topic and revision in Zanata
     */
    @Transient
    public String getZanataId() {
        if (translatedCSNode == null) {
            return topicId + "-" + topicRevision;
        } else {
            return topicId + "-" + topicRevision + "-" + translatedCSNode.getId();
        }
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "translatedTopic", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<TranslatedTopicData> getTranslatedTopicDatas() {
        return translatedTopicDatas;
    }

    public void setTranslatedTopicDatas(final Set<TranslatedTopicData> translatedTopicDatas) {
        this.translatedTopicDatas = translatedTopicDatas;
    }

    @Transient
    public List<TranslatedTopicData> getTranslatedTopicDataList() {
        return new ArrayList<TranslatedTopicData>(translatedTopicDatas);
    }

    @Transient
    public List<Locale> getTranslatedTopicDataLocales() {
        List<Locale> locales = new ArrayList<Locale>();
        for (TranslatedTopicData translatedTopicData : translatedTopicDatas) {
            locales.add(translatedTopicData.getLocale());
        }

        /* Sort the locales into alphabetical order */
        Collections.sort(locales, new LocaleValueComparator());

        return locales;
    }

    @Transient
    public Topic getEnversTopic(final EntityManager entityManager) {
        if (enversTopic == null) {
            /* Find the envers topic */
            final AuditReader reader = AuditReaderFactory.get(entityManager);
            final AuditQuery query = reader.createQuery().forEntitiesAtRevision(Topic.class, topicRevision).add(
                    AuditEntity.id().eq(topicId));
            enversTopic = (Topic) query.getSingleResult();
        }
        return enversTopic;
    }

    @Transient
    public void setEnversTopic(final Topic enversTopic) {
        this.enversTopic = enversTopic;
    }

    @Transient
    public void addTranslatedTopicData(final TranslatedTopicData translatedTopicData) {
        if (!translatedTopicDatas.contains(translatedTopicData)) {
            translatedTopicData.setTranslatedTopic(this);
            translatedTopicDatas.add(translatedTopicData);
        }
    }

    @Transient
    public void removeTranslatedTopicData(final TranslatedTopicData translatedTopicData) {
        translatedTopicData.setTranslatedTopic(null);
        translatedTopicDatas.remove(translatedTopicData);
    }
}
