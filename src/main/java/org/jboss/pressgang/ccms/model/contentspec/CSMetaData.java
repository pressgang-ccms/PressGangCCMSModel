package org.jboss.pressgang.ccms.model.contentspec;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "ContentSpecMetaData")
public class CSMetaData extends AuditedEntity implements Serializable {
    private static final long serialVersionUID = 5183201301752071357L;
    public static final String SELECT_ALL_QUERY = "select csMetaData FROM CSMetaData AS csMetaData";

    private Integer csMetaDataId = null;
    private String csMetaDataTitle = null;
    private String csMetaDataDescription = null;
    private Set<ContentSpecToCSMetaData> contentSpecToCSMetaData = new HashSet<ContentSpecToCSMetaData>(0);

    @Override
    @Transient
    public Integer getId() {
        return csMetaDataId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ContentSpecMetaDataID", unique = true, nullable = false)
    public Integer getCSMetaDataId() {
        return csMetaDataId;
    }

    public void setCSMetaDataId(Integer csMetaDataId) {
        this.csMetaDataId = csMetaDataId;
    }

    @Column(name = "MetaDataTitle", nullable = false, length = 255)
    public String getCSMetaDataTitle() {
        return csMetaDataTitle;
    }

    public void setCSMetaDataTitle(String csMetaDataTitle) {
        this.csMetaDataTitle = csMetaDataTitle;
    }

    @Column(name = "MetaDataDescription", columnDefinition = "TEXT")
    public String getCSMetaDataDescription() {
        return csMetaDataDescription;
    }

    public void setCSMetaDataDescription(String csMetaDataDescription) {
        this.csMetaDataDescription = csMetaDataDescription;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "CSMetaData", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<ContentSpecToCSMetaData> getContentSpecToCSMetaData() {
        return contentSpecToCSMetaData;
    }

    public void setContentSpecToCSMetaData(Set<ContentSpecToCSMetaData> contentSpecToCSMetaData) {
        this.contentSpecToCSMetaData = contentSpecToCSMetaData;
    }

    @Transient
    public List<ContentSpec> getContentSpecsList() {
        final List<ContentSpec> contentSpecs = new ArrayList<ContentSpec>();

        for (final ContentSpecToCSMetaData mapping : contentSpecToCSMetaData) {
            contentSpecs.add(mapping.getContentSpec());
        }

        return contentSpecs;
    }
}
