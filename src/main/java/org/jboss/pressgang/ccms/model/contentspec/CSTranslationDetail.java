package org.jboss.pressgang.ccms.model.contentspec;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static org.hamcrest.Matchers.equalTo;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import org.jboss.pressgang.ccms.model.Locale;
import org.jboss.pressgang.ccms.model.TranslationServer;
import org.jboss.pressgang.ccms.model.base.PressGangEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "ContentSpecTranslationDetail")
public class CSTranslationDetail implements Serializable, PressGangEntity {
    private static final long serialVersionUID = 261765504308787347L;
    private Integer csTranslationDetailId;
    private boolean enabled;
    private TranslationServer translationServer;
    private String project;
    private String projectVersion;
    private Set<CSTranslationDetailToLocale> translationDetailsToLocales = new HashSet<CSTranslationDetailToLocale>(0);
    private ContentSpec contentSpec;

    @Transient
    @Override
    public Integer getId() {
        return csTranslationDetailId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TranslationDetailID", unique = true, nullable = false)
    public Integer getCSTranslationDetailId() {
        return csTranslationDetailId;
    }

    public void setCSTranslationDetailId(Integer csTranslationDetailId) {
        this.csTranslationDetailId = csTranslationDetailId;
    }

    @Column(name = "Enabled", nullable = false, columnDefinition = "BIT")
    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "TranslationServerID")
    @NotNull(message = "{translationdetail.translationserver.notNull}")
    public TranslationServer getTranslationServer() {
        return translationServer;
    }

    public void setTranslationServer(TranslationServer translationServer) {
        this.translationServer = translationServer;
    }

    @Column(name = "Project", nullable = false, length = 255)
    @NotBlank
    @Size(max = 255)
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Column(name = "ProjectVersion", nullable = false, length = 255)
    @NotBlank
    @Size(max = 255)
    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "ContentSpecID")
    @NotNull
    public ContentSpec getContentSpec() {
        return contentSpec;
    }

    public void setContentSpec(ContentSpec contentSpec) {
        this.contentSpec = contentSpec;
    }

    @OneToMany(mappedBy = "translationDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<CSTranslationDetailToLocale> getTranslationDetailsToLocales() {
        return translationDetailsToLocales;
    }

    public void setTranslationDetailsToLocales(Set<CSTranslationDetailToLocale> translationDetailsToLocales) {
        this.translationDetailsToLocales = translationDetailsToLocales;
    }

    @Transient
    public void addLocale(Locale locale) {
        final CSTranslationDetailToLocale mapping = new CSTranslationDetailToLocale();
        mapping.setTranslationDetail(this);
        mapping.setLocale(locale);

        translationDetailsToLocales.add(mapping);
    }

    @Transient
    public void removeLocale(final Locale locale) {
        // Filter down to the matching locales
        final List<CSTranslationDetailToLocale> mappingEntities = filter(having(on(CSTranslationDetailToLocale.class).getLocale(),
                equalTo(locale)), getTranslationDetailsToLocales());
        if (mappingEntities.size() != 0) {
            for (final CSTranslationDetailToLocale mapping : mappingEntities) {
                translationDetailsToLocales.remove(mapping);
            }
        }
    }

    @Transient
    public List<Locale> getLocales() {
        final List<Locale> retValue = new ArrayList<Locale>();

        for (final CSTranslationDetailToLocale locale : translationDetailsToLocales) {
            retValue.add(locale.getLocale());
        }

        return retValue;
    }
}
