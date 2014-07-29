/*
  Copyright 2011-2014 Red Hat

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "RelationshipTag", uniqueConstraints = @UniqueConstraint(columnNames = {"RelationshipTagName"}))
public class RelationshipTag extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1882693752297919114L;
    public static final String SELECT_ALL_QUERY = "select relationshipTag from RelationshipTag relationshipTag";

    private Integer relationshipTagId;
    private String relationshipTagName;
    private String relationshipTagDescription;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "RelationshipTagId", unique = true, nullable = false)
    public Integer getRelationshipTagId() {
        return relationshipTagId;
    }

    public void setRelationshipTagId(final Integer relationshipTagId) {
        this.relationshipTagId = relationshipTagId;
    }

    @Column(name = "RelationshipTagName", nullable = false, length = 255)
    @NotNull
    @NotBlank
    @Size(max = 255)
    public String getRelationshipTagName() {
        return relationshipTagName;
    }

    public void setRelationshipTagName(final String relationshipTagName) {
        this.relationshipTagName = relationshipTagName;
    }

    @Column(name = "RelationshipTagDescription", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getRelationshipTagDescription() {
        return relationshipTagDescription;
    }

    public void setRelationshipTagDescription(final String relationshipTagDescription) {
        this.relationshipTagDescription = relationshipTagDescription;
    }

    @Override
    @Transient
    public Integer getId() {
        return relationshipTagId;
    }
}
