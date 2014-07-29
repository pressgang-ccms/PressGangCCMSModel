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
import org.hibernate.validator.constraints.NotBlank;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;


@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TranslatedCSNodeString")
public class TranslatedCSNodeString extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -2111116884944950287L;

    private Integer translatedCSNodeStringId;
    private TranslatedCSNode translatedCSNode;
    private String locale;
    private String translatedString;
    private Boolean fuzzyTranslation = false;

    @Transient
    public Integer getId() {
        return translatedCSNodeStringId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TranslatedCSNodeStringID", unique = true, nullable = false)
    public Integer getTranslatedCSNodeStringId() {
        return translatedCSNodeStringId;
    }

    public void setTranslatedCSNodeStringId(final Integer translatedCSNodeStringId) {
        this.translatedCSNodeStringId = translatedCSNodeStringId;
    }

    @Column(name = "Locale", nullable = false)
    @NotNull(message = "{contentspec.translatedstring.locale.notBlank}")
    @NotBlank(message = "{contentspec.translatedstring.locale.notBlank}")
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "TranslatedCSNodeID", nullable = false)
    @NotNull
    public TranslatedCSNode getTranslatedCSNode() {
        return translatedCSNode;
    }

    public void setTranslatedCSNode(final TranslatedCSNode translatedCSNode) {
        this.translatedCSNode = translatedCSNode;
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