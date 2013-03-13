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
import org.hibernate.validator.NotNull;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "CSTranslatedNode")
public class CSTranslatedNode extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5185674451816385008L;

    private Integer contentSpecTranslatedNodeId = null;
    private TranslatedContentSpec translatedContentSpec = null;
    private Integer contentSpecNodeId = null;
    private Integer contentSpecNodeRevision = null;
    private Set<CSTranslatedNodeString> csTranslatedNodeStrings = new HashSet<CSTranslatedNodeString>(0);

    private CSNode enversCSNode;

    @Transient
    public Integer getId() {
        return contentSpecTranslatedNodeId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CSTranslatedNodeID", unique = true, nullable = false)
    public Integer getCSTranslatedNodeId() {
        return contentSpecTranslatedNodeId;
    }

    public void setCSTranslatedNodeId(final Integer translatedNodeId) {
        contentSpecTranslatedNodeId = translatedNodeId;
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

    @JoinColumn(name = "TranslatedContentSpecID")
    @ManyToOne(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    public TranslatedContentSpec getTranslatedContentSpec() {
        return translatedContentSpec;
    }

    public void setTranslatedContentSpec(TranslatedContentSpec translatedContentSpec) {
        this.translatedContentSpec = translatedContentSpec;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "CSTranslatedNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<CSTranslatedNodeString> getCSTranslatedNodeStrings() {
        return csTranslatedNodeStrings;
    }

    public void setCSTranslatedNodeStrings(final Set<CSTranslatedNodeString> csTranslatedNodeStrings) {
        this.csTranslatedNodeStrings = csTranslatedNodeStrings;
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
    public List<CSTranslatedNodeString> getCSTranslatedNodeStringsArray() {
        return new ArrayList<CSTranslatedNodeString>(getCSTranslatedNodeStrings());
    }

    public void addTranslatedString(final CSTranslatedNodeString translatedString) {
        translatedString.setCSTranslatedNode(this);
        getCSTranslatedNodeStrings().add(translatedString);
    }

    public void removeTranslatedString(final CSTranslatedNodeString translatedString) {
        translatedString.setCSTranslatedNode(null);
        getCSTranslatedNodeStrings().remove(translatedString);
    }
}