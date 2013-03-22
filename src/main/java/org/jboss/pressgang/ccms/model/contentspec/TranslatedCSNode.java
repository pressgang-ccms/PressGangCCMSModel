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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
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
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TranslatedCSNode")
public class TranslatedCSNode extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5185674451816385008L;

    private Integer translatedCSNodeId = null;
    private TranslatedContentSpec translatedContentSpec = null;
    private Integer contentSpecNodeId = null;
    private Integer contentSpecNodeRevision = null;
    private String originalString = null;
    private Set<TranslatedCSNodeString> translatedCSNodeStrings = new HashSet<TranslatedCSNodeString>(0);

    private CSNode enversCSNode;

    @Transient
    public Integer getId() {
        return translatedCSNodeId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TranslatedCSNodeID", unique = true, nullable = false)
    public Integer getTranslatedCSNodeId() {
        return translatedCSNodeId;
    }

    public void setTranslatedCSNodeId(final Integer translatedCSNodeId) {
        this.translatedCSNodeId = translatedCSNodeId;
    }

    @Column(name = "CSNodeID", nullable = false)
    @NotNull
    public Integer getCSNodeId() {
        return contentSpecNodeId;
    }

    public void setCSNodeId(final Integer contentSpecNodeId) {
        this.contentSpecNodeId = contentSpecNodeId;
    }

    @Column(name = "CSNodeRevision", nullable = false)
    @NotNull
    public Integer getCSNodeRevision() {
        return contentSpecNodeRevision;
    }

    public void setCSNodeRevision(final Integer contentSpecNodeRevision) {
        this.contentSpecNodeRevision = contentSpecNodeRevision;
    }

    @Column(name = "OriginalString", columnDefinition = "TEXT")
    @Length(max = 65535)
    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(final String originalString) {
        this.originalString = originalString;
    }

    @JoinColumn(name = "TranslatedContentSpecID")
    @ManyToOne(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    public TranslatedContentSpec getTranslatedContentSpec() {
        return translatedContentSpec;
    }

    public void setTranslatedContentSpec(TranslatedContentSpec translatedContentSpec) {
        this.translatedContentSpec = translatedContentSpec;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "translatedCSNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<TranslatedCSNodeString> getTranslatedCSNodeStrings() {
        return translatedCSNodeStrings;
    }

    public void setTranslatedCSNodeStrings(final Set<TranslatedCSNodeString> translatedCSNodeStrings) {
        this.translatedCSNodeStrings = translatedCSNodeStrings;
    }

    @Transient
    public CSNode getEnversCSNode(final EntityManager entityManager) {
        if (enversCSNode == null) {
            /* Find the envers topic */
            final AuditReader reader = AuditReaderFactory.get(entityManager);
            final AuditQuery query = reader.createQuery().forEntitiesAtRevision(CSNode.class, contentSpecNodeRevision).add(
                    AuditEntity.id().eq(contentSpecNodeId));
            enversCSNode = (CSNode) query.getSingleResult();
        }
        return enversCSNode;
    }

    @Transient
    public void setEnversCSNode(final CSNode enversCSNode) {
        this.enversCSNode = enversCSNode;
    }

    @Transient
    public List<TranslatedCSNodeString> getCSTranslatedNodeStringsArray() {
        return new ArrayList<TranslatedCSNodeString>(getTranslatedCSNodeStrings());
    }

    public void addTranslatedString(final TranslatedCSNodeString translatedString) {
        translatedString.setTranslatedCSNode(this);
        getTranslatedCSNodeStrings().add(translatedString);
    }

    public void removeTranslatedString(final TranslatedCSNodeString translatedString) {
        translatedString.setTranslatedCSNode(null);
        getTranslatedCSNodeStrings().remove(translatedString);
    }
}