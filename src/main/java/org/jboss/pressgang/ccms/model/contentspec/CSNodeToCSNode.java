/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.model.contentspec;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceException;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.exceptions.CustomConstraintViolationException;
import org.jboss.pressgang.ccms.utils.constants.CommonConstants;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "CSNodeToCSNode")
public class CSNodeToCSNode extends AuditedEntity implements Serializable {
    private static final long serialVersionUID = 1323433852480196579L;

    private Integer csNodeToCSNodeId = null;
    private CSNode mainNode = null;
    private CSNode relatedNode = null;
    private Integer relationshipType = null;
    private Integer relationshipSort = null;
    private Integer relationshipMode = null;

    @Override
    @Transient
    public Integer getId() {
        return csNodeToCSNodeId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CSNodeToCSNodeID", unique = true, nullable = false)
    public Integer getCSNodeToCSNodeId() {
        return csNodeToCSNodeId;
    }

    public void setCSNodeToCSNodeId(Integer csNodeToCSNodeId) {
        this.csNodeToCSNodeId = csNodeToCSNodeId;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "MainNodeID", nullable = false)
    @NotNull
    public CSNode getMainNode() {
        return mainNode;
    }

    public void setMainNode(CSNode mainNode) {
        this.mainNode = mainNode;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "RelatedNodeID", nullable = false)
    @NotNull
    public CSNode getRelatedNode() {
        return relatedNode;
    }

    public void setRelatedNode(CSNode relatedNode) {
        this.relatedNode = relatedNode;
    }

    @Column(name = "RelationshipType", nullable = false, columnDefinition = "TINYINT")
    @NotNull
    public Integer getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(Integer relationshipType) {
        this.relationshipType = relationshipType;
    }

    @Column(name = "Sort")
    public Integer getRelationshipSort() {
        return relationshipSort;
    }

    public void setRelationshipSort(Integer relationshipSort) {
        this.relationshipSort = relationshipSort;
    }

    @Column(name = "RelationshipMode", nullable = false, columnDefinition = "TINYINT")
    @NotNull
    public Integer getRelationshipMode() {
        return relationshipMode;
    }

    public void setRelationshipMode(Integer relationshipMode) {
        this.relationshipMode = relationshipMode;
    }

    @PreUpdate
    @PrePersist
    protected void preSave() {
        validateNodeToNode();

        // Set the content specs last modified date if one of it's nodes change
        if (mainNode != null) {
            if (mainNode.getContentSpec() != null) {
                mainNode.getContentSpec().setLastModified();
            }
        } else if (relatedNode != null) {
            if (relatedNode.getContentSpec() != null) {
                relatedNode.getContentSpec().setLastModified();
            }
        }
    }

    @Transient
    protected void validateNodeToNode() {
        if (getMainNode().getCSNodeType() == CommonConstants.CS_NODE_META_DATA || getRelatedNode().getCSNodeType() == CommonConstants
                .CS_NODE_META_DATA || getMainNode().getCSNodeType() == CommonConstants.CS_NODE_COMMENT || getRelatedNode().getCSNodeType
                () == CommonConstants.CS_NODE_COMMENT) {
            throw new CustomConstraintViolationException("Node to Node relationships can't be performed on Comments or Meta Data.");
        }
    }
}
