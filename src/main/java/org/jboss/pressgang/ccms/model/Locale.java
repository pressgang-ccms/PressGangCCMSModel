package org.jboss.pressgang.ccms.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.base.PressGangEntity;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "Locale", uniqueConstraints = @UniqueConstraint(columnNames = {"Value"}))
public class Locale implements Serializable, PressGangEntity {
    public static final String SELECT_ALL_QUERY = "SELECT locale FROM Locale as locale";
    private static final long serialVersionUID = -5401627148294498239L;

    private Integer localeId;
    private String value;
    private String translationValue;
    private String buildValue;

    @Transient
    @Override
    public Integer getId() {
        return localeId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LocaleID", unique = true, nullable = false)
    public Integer getLocaleId() {
        return localeId;
    }

    public void setLocaleId(Integer localeId) {
        this.localeId = localeId;
    }

    @NaturalId
    @Column(name = "Value", nullable = false, length = 40, unique = true)
    @NotNull(message = "{locale.value.notBlank}")
    @NotEmpty(message = "{locale.value.notBlank}")
    @Size(max = 40)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "TranslationValue", nullable = false, length = 40)
    @Size(max = 40)
    public String getTranslationValue() {
        return translationValue;
    }

    public void setTranslationValue(String translationValue) {
        this.translationValue = translationValue;
    }

    @Column(name = "BuildValue", nullable = false, length = 40)
    @Size(max = 40)
    public String getBuildValue() {
        return buildValue;
    }

    public void setBuildValue(String buildValue) {
        this.buildValue = buildValue;
    }

    @PrePersist
    @PreUpdate
    protected void fixUpLocales() {
        if (translationValue == null) {
            translationValue = value;
        }

        if (buildValue == null) {
            buildValue = value;
        }
    }
}
