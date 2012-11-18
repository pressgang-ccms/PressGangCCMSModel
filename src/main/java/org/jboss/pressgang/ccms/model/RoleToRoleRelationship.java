package org.jboss.pressgang.ccms.model;

// Generated Aug 11, 2011 2:31:45 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import javax.validation.constraints.Size;

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
public class RoleToRoleRelationship extends AuditedEntity<RoleToRoleRelationship> implements java.io.Serializable {
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
        return this.roleToRoleRelationshipId;
    }

    public void setRoleToRoleRelationshipId(final Integer roleToRoleRelationshipId) {
        this.roleToRoleRelationshipId = roleToRoleRelationshipId;
    }

    // @Column(name = "Description", length = 65535)
    @Column(name = "Description", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getDescription() {
        return this.description;
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
