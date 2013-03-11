package org.jboss.pressgang.ccms.model.contentspec;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "CSTranslatedNodeString")
public class CSTranslatedNodeString extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -2111116884944950287L;

    private Integer contentSpecTranslatedNodeStringId;
    private CSTranslatedNode translatedNode;
    private String locale;
    private String originalString;
    private String translatedString;
    private Boolean fuzzyTranslation = false;

    @Transient
    public Integer getId() {
        return contentSpecTranslatedNodeStringId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CSTranslatedNodeStringID", unique = true, nullable = false)
    public Integer getCSTranslatedNodeStringId() {
        return contentSpecTranslatedNodeStringId;
    }

    public void setCSTranslatedNodeStringId(final Integer translatedTopicStringId) {
        contentSpecTranslatedNodeStringId = translatedTopicStringId;
    }

    @Column(name = "Locale", nullable = false)
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @ManyToOne
    @JoinColumn(name = "CSTranslatedNodeID", nullable = false)
    @NotNull
    public CSTranslatedNode getCSTranslatedNode() {
        return translatedNode;
    }

    public void setCSTranslatedNode(final CSTranslatedNode translatedNode) {
        this.translatedNode = translatedNode;
    }

    @Column(name = "OriginalString", columnDefinition = "TEXT")
    @Length(max = 65535)
    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(final String originalString) {
        this.originalString = originalString;
    }

    @Column(name = "TranslatedString", columnDefinition = "TEXT")
    @Length(max = 65535)
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