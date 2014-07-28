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

package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TranslatedTopicString")
public class TranslatedTopicString extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5185674451816385008L;

    private Integer translatedTopicStringID;
    private TranslatedTopicData translatedTopicData;
    private String originalString;
    private String translatedString;
    private Boolean fuzzyTranslation = false;

    public TranslatedTopicString() {
    }

    @Transient
    public Integer getId() {
        return translatedTopicStringID;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TranslatedTopicStringID", unique = true, nullable = false)
    public Integer getTranslatedTopicStringID() {
        return translatedTopicStringID;
    }

    public void setTranslatedTopicStringID(final Integer translatedTopicStringID) {
        this.translatedTopicStringID = translatedTopicStringID;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "TranslatedTopicDataID", nullable = false)
    @NotNull
    public TranslatedTopicData getTranslatedTopicData() {
        return translatedTopicData;
    }

    public void setTranslatedTopicData(final TranslatedTopicData translatedTopicData) {
        this.translatedTopicData = translatedTopicData;
    }

    @Column(name = "OriginalString", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(final String originalString) {
        this.originalString = originalString;
    }

    @Column(name = "TranslatedString", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getTranslatedString() {
        return translatedString;
    }

    public void setTranslatedString(final String translatedString) {
        this.translatedString = translatedString;
    }

    @Column(name = "FuzzyTranslation", nullable = false, columnDefinition = "BIT", length = 1)
    @NotNull
    public Boolean getFuzzyTranslation() {
        return fuzzyTranslation;
    }

    public void setFuzzyTranslation(final Boolean fuzzyTranslation) {
        this.fuzzyTranslation = fuzzyTranslation;
    }
}
