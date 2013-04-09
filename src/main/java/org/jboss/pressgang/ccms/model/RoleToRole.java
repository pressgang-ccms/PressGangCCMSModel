package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import org.hibernate.validator.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

/**
 * RoleToRole generated by hbm2java
 */
@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "RoleToRole", uniqueConstraints = @UniqueConstraint(columnNames = {"PrimaryRole", "SecondaryRole", "RelationshipType"}))
public class RoleToRole extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -4323051325365483977L;

    private Integer roleToRoleId;
    private RoleToRoleRelationship roleToRoleRelationship;
    private Role primaryRole;
    private Role secondaryRole;

    public RoleToRole() {
    }

    public RoleToRole(final RoleToRoleRelationship roleToRoleRelationship, final Role primaryRole, final Role secondaryRole) {
        this.roleToRoleRelationship = roleToRoleRelationship;
        this.primaryRole = primaryRole;
        this.secondaryRole = secondaryRole;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "RoleToRoleID", unique = true, nullable = false)
    public Integer getRoleToRoleId() {
        return roleToRoleId;
    }

    public void setRoleToRoleId(Integer roleToRoleId) {
        this.roleToRoleId = roleToRoleId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RelationshipType", nullable = false)
    @NotNull
    public RoleToRoleRelationship getRoleToRoleRelationship() {
        return roleToRoleRelationship;
    }

    public void setRoleToRoleRelationship(final RoleToRoleRelationship roleToRoleRelationship) {
        this.roleToRoleRelationship = roleToRoleRelationship;
    }

    @ManyToOne
    @JoinColumn(name = "PrimaryRole", nullable = false)
    @NotNull
    public Role getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(final Role primaryRole) {
        this.primaryRole = primaryRole;
    }

    @ManyToOne
    @JoinColumn(name = "SecondaryRole", nullable = false)
    @NotNull
    public Role getSecondaryRole() {
        return secondaryRole;
    }

    public void setSecondaryRole(final Role secondaryRole) {
        this.secondaryRole = secondaryRole;
    }

    @PreRemove
    private void preRemove() {
        primaryRole.getParentRoleToRole().remove(this);
        secondaryRole.getChildrenRoleToRole().remove(this);
    }

    @Override
    @Transient
    public Integer getId() {
        return roleToRoleId;
    }

}
