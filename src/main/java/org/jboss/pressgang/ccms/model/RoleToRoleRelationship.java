/*
  Copyright 2011-2014 Red Hat, Inc

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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;

/**
 * RoleToRoleRelationship generated by hbm2java
 */
@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "RoleToRoleRelationship")
public class RoleToRoleRelationship extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -5070074888185753060L;

    private Integer roleToRoleRelationshipId;
    private String description;
    private Set<RoleToRole> roleToRoles = new HashSet<RoleToRole>(0);

    public RoleToRoleRelationship() {
    }

    public RoleToRoleRelationship(final String description, final Set<RoleToRole> roleToRoles) {
        this.description = description;
        this.roleToRoles = roleToRoles;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "RoleToRoleRelationshipID", unique = true, nullable = false)
    public Integer getRoleToRoleRelationshipId() {
        return roleToRoleRelationshipId;
    }

    public void setRoleToRoleRelationshipId(final Integer roleToRoleRelationshipId) {
        this.roleToRoleRelationshipId = roleToRoleRelationshipId;
    }

    @Column(name = "Description", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roleToRoleRelationship")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<RoleToRole> getRoleToRoles() {
        return this.roleToRoles;
    }

    public void setRoleToRoles(final Set<RoleToRole> roleToRoles) {
        this.roleToRoles = roleToRoles;
    }

    @Override
    @Transient
    public Integer getId() {
        return this.roleToRoleRelationshipId;
    }

}
