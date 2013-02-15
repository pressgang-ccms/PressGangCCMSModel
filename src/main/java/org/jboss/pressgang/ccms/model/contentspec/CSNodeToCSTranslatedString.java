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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.NotNull;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "CSNodeToCSTranslatedString", uniqueConstraints = @UniqueConstraint(columnNames = {"CSNodeID", "CSTranslatedStringID"}))
public class CSNodeToCSTranslatedString extends AuditedEntity<CSNodeToCSTranslatedString> implements java.io.Serializable {
    private static final long serialVersionUID = -7516063608506037594L;

    private Integer topicToTagId;
    private CSNode csNode;
    private CSTranslatedString csTranslatedString;

    public CSNodeToCSTranslatedString() {
    }

    public CSNodeToCSTranslatedString(final CSNode csMetaData, final CSTranslatedString csTranslatedString) {
        csNode = csMetaData;
        this.csTranslatedString = csTranslatedString;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CSNodeToCSTranslatedStringID", unique = true, nullable = false)
    public Integer getTopicToTagId() {
        return topicToTagId;
    }

    public void setTopicToTagId(final Integer topicToTagId) {
        this.topicToTagId = topicToTagId;
    }

    @ManyToOne
    @JoinColumn(name = "CSNodeID", nullable = false)
    @NotNull
    public CSNode getCSNode() {
        return csNode;
    }

    public void setCSNode(final CSNode csNode) {
        this.csNode = csNode;
    }

    @ManyToOne
    @JoinColumn(name = "CSTranslatedStringID", nullable = false)
    @NotNull
    public CSTranslatedString getCSTranslatedString() {
        return csTranslatedString;
    }

    public void setCSTranslatedString(final CSTranslatedString csTranslatedString) {
        this.csTranslatedString = csTranslatedString;
    }

    @Override
    @Transient
    public Integer getId() {
        return topicToTagId;
    }

}