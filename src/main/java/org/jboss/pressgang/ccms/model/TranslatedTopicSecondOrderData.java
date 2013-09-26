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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TranslatedTopicSecondOrderData")
public class TranslatedTopicSecondOrderData extends AuditedEntity implements Serializable {
    private static final long serialVersionUID = -5370068689171163463L;

    private Integer translatedTopicSecondOrderDataId;
    private TranslatedTopicData translatedTopicData;
    private String translatedAdditionalXml;

    public TranslatedTopicSecondOrderData() {
    }

    public TranslatedTopicSecondOrderData(final Integer translatedTopicSecondOrderDataId) {
        this.translatedTopicSecondOrderDataId = translatedTopicSecondOrderDataId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TranslatedTopicSecondOrderDataID")
    public Integer getTranslatedTopicSecondOrderDataId() {
        return translatedTopicSecondOrderDataId;
    }

    public void setTranslatedTopicSecondOrderDataId(final Integer translatedTopicSecondOrderDataId) {
        this.translatedTopicSecondOrderDataId = translatedTopicSecondOrderDataId;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "TranslatedTopicDataID", nullable = false)
    @NotNull
    public TranslatedTopicData getTranslatedTopicData() {
        return translatedTopicData;
    }

    public void setTranslatedTopicData(TranslatedTopicData translatedTopicData) {
        this.translatedTopicData = translatedTopicData;
    }

    @Column(name = "AdditionalXML", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getTranslatedAdditionalXml() {
        return translatedAdditionalXml;
    }

    public void setTranslatedAdditionalXml(final String translatedAdditionalXml) {
        this.translatedAdditionalXml = translatedAdditionalXml;
    }

    @Transient
    public Integer getId() {
        return translatedTopicSecondOrderDataId;
    }
}
