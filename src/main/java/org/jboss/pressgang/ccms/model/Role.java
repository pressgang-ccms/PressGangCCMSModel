package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;
import org.jboss.pressgang.ccms.model.validator.NotBlank;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "Role", uniqueConstraints = @UniqueConstraint(columnNames = {"RoleName"}))
public class Role extends AuditedEntity implements java.io.Serializable {
    public static final String SELECT_ALL_QUERY = "select role from Role role";
    private static final long serialVersionUID = 894929331710959265L;

    private Integer roleId;
    private String roleName;
    private String description;
    private Set<UserRole> userRoles = new HashSet<UserRole>(0);
    private Set<RoleToRole> childrenRoleToRole = new HashSet<RoleToRole>(0);
    private Set<RoleToRole> parentRoleToRole = new HashSet<RoleToRole>(0);

    public Role() {
    }

    public Role(final String roleName) {
        this.roleName = roleName;
    }

    public Role(final String roleName, final String description, final Set<UserRole> userRoles) {
        this.roleName = roleName;
        this.description = description;
        this.userRoles = userRoles;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "RoleID", unique = true, nullable = false)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Column(name = "RoleName", nullable = false, length = 255)
    @NotNull(message = "{role.name.notBlank}")
    @NotBlank(message = "{role.name.notBlank}")
    @Length(max = 255)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "Description", columnDefinition = "TEXT")
    @Length(max = 65535)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secondaryRole",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<RoleToRole> getChildrenRoleToRole() {
        return childrenRoleToRole;
    }

    public void setChildrenRoleToRole(final Set<RoleToRole> childrenRoleToRole) {
        this.childrenRoleToRole = childrenRoleToRole;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "primaryRole",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<RoleToRole> getParentRoleToRole() {
        return parentRoleToRole;
    }

    public void setParentRoleToRole(final Set<RoleToRole> parentRoleToRole) {
        this.parentRoleToRole = parentRoleToRole;
    }

    public boolean hasUser(final User user) {
        return hasUser(user.getUserId());
    }

    public boolean hasUser(final Integer user) {
        for (final UserRole userRole : userRoles) {
            if (userRole.getUser().getUserId().equals(user)) return true;
        }

        return false;
    }

    public void addUser(final User user) {
        if (!hasUser(user)) {
            final UserRole userRole = new UserRole(user, this);
            getUserRoles().add(userRole);
            user.getUserRoles().add(userRole);
        }
    }

    public void removeUser(final User user) {
        removeUser(user.getUserId());
    }

    public void removeUser(final Integer userId) {
        for (final UserRole userRole : userRoles) {
            if (userRole.getUser().getUserId().equals(userId)) {
                getUserRoles().remove(userRole);
                userRole.getUser().getUserRoles().remove(userRole);
                break;
            }
        }
    }

    @Transient
    public String getUsersCommaSeperatedList() {
        String retValue = "";
        for (final UserRole role : userRoles) {
            if (retValue.length() != 0) retValue += ", ";
            retValue += role.getUser().getUserName();
        }
        return retValue;
    }

    @PreRemove
    private void preRemove() {
        userRoles.clear();
        childrenRoleToRole.clear();
        parentRoleToRole.clear();
    }

    @Transient
    public List<User> getUsers() {
        final List<User> retValue = new ArrayList<User>();

        for (final UserRole userRole : userRoles) {
            retValue.add(userRole.getUser());
        }

        return retValue;
    }

    @Transient
    public List<Role> getParentRoles() {
        final List<Role> retValue = new ArrayList<Role>();

        for (final RoleToRole userRole : parentRoleToRole) {
            retValue.add(userRole.getSecondaryRole());
        }

        return retValue;
    }

    @Transient
    public List<Role> getChildRoles() {
        final List<Role> retValue = new ArrayList<Role>();

        for (final RoleToRole userRole : parentRoleToRole) {
            retValue.add(userRole.getPrimaryRole());
        }

        return retValue;
    }

    public RoleToRole getParentRole(final Integer role, final Integer relationshipID) {
        for (final RoleToRole roleToRole : parentRoleToRole) {
            if (roleToRole.getSecondaryRole().getRoleId().equals(
                    role) && roleToRole.getRoleToRoleRelationship().getRoleToRoleRelationshipId().equals(relationshipID)) return roleToRole;
        }

        return null;
    }

    public boolean hasParentRole(final Integer role, final Integer relationshipID) {
        return getParentRole(role, relationshipID) != null;
    }

    public RoleToRole getChildRole(final Integer role, final Integer relationshipID) {
        for (final RoleToRole roleToRole : getChildrenRoleToRole()) {
            if (roleToRole.getPrimaryRole().getRoleId().equals(
                    role) && roleToRole.getRoleToRoleRelationship().getRoleToRoleRelationshipId().equals(relationshipID)) return roleToRole;
        }

        return null;
    }

    public boolean hasChildRole(final Integer role, final Integer relationshipID) {
        return getChildRole(role, relationshipID) != null;
    }

    public void addParentRole(final Role role, final RoleToRoleRelationship roleToRoleRelationship) {
        if (!hasParentRole(role.getRoleId(), roleToRoleRelationship.getRoleToRoleRelationshipId())) {
            final RoleToRole roleToRole = new RoleToRole(roleToRoleRelationship, this, role);
            getParentRoleToRole().add(roleToRole);
            role.getChildrenRoleToRole().add(roleToRole);
        }
    }

    public void addChildRole(final Role role, final RoleToRoleRelationship roleToRoleRelationship) {
        if (!hasChildRole(role.getRoleId(), roleToRoleRelationship.getRoleToRoleRelationshipId())) {
            final RoleToRole roleToRole = new RoleToRole(roleToRoleRelationship, role, this);
            getChildrenRoleToRole().add(roleToRole);
            role.getParentRoleToRole().add(roleToRole);
        }
    }

    public void removeParentRole(final Role role, final RoleToRoleRelationship roleToRoleRelationship) {
        final RoleToRole roleToRole = getParentRole(role.getRoleId(), roleToRoleRelationship.getRoleToRoleRelationshipId());
        if (roleToRole != null) {
            getParentRoleToRole().remove(roleToRole);
            role.getChildrenRoleToRole().remove(roleToRole);
        }
    }

    public void removeChildRole(final Role role, final RoleToRoleRelationship roleToRoleRelationship) {
        final RoleToRole roleToRole = getChildRole(role.getRoleId(), roleToRoleRelationship.getRoleToRoleRelationshipId());
        if (roleToRole != null) {
            getChildrenRoleToRole().remove(roleToRole);
            role.getParentRoleToRole().remove(roleToRole);
        }
    }

    @Override
    @Transient
    public Integer getId() {
        return roleId;
    }

}
