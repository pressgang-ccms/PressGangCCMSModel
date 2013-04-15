package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TranslatedTopicDataID", nullable = false)
    @NotNull
    public TranslatedTopicData getTranslatedTopicData() {
        return translatedTopicData;
    }

    public void setTranslatedTopicData(final TranslatedTopicData translatedTopicData) {
        this.translatedTopicData = translatedTopicData;
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
