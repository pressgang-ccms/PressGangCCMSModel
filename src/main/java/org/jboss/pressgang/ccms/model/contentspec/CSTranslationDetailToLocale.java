package org.jboss.pressgang.ccms.model.contentspec;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jboss.pressgang.ccms.model.Locale;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "CSTranslationDetailToLocale")
public class CSTranslationDetailToLocale extends AuditedEntity implements Serializable {
    private static final long serialVersionUID = 6186609227659906206L;

    private Integer id;
    private CSTranslationDetail translationDetail;
    private Locale locale;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TranslationDetailToLocaleID", unique = true, nullable = false)
    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "TranslationDetailID", nullable = false)
    @NotNull
    public CSTranslationDetail getTranslationDetail() {
        return translationDetail;
    }

    public void setTranslationDetail(CSTranslationDetail translationDetail) {
        this.translationDetail = translationDetail;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "LocaleID", nullable = false)
    @NotNull
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
