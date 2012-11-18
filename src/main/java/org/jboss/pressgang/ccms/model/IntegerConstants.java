package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

/**
 * IntegerConstants generated by hbm2java
 */
@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "IntegerConstants")
public class IntegerConstants extends AuditedEntity<IntegerConstants> implements java.io.Serializable {
    public static final String SELECT_ALL_QUERY = "SELECT integerConstants FROM IntegerConstants integerConstants";
    private static final long serialVersionUID = -6660808431652587223L;

    private Integer integerConstantsId;
    private String constantName;
    private Integer constantValue;

    public IntegerConstants() {
    }

    public IntegerConstants(final String constantName) {
        this.constantName = constantName;
    }

    public IntegerConstants(final String constantName, final Integer constantValue) {
        this.constantName = constantName;
        this.constantValue = constantValue;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "IntegerConstantsID", unique = true, nullable = false)
    public Integer getIntegerConstantsId() {
        return this.integerConstantsId;
    }

    public void setIntegerConstantsId(final Integer integerConstantsId) {
        this.integerConstantsId = integerConstantsId;
    }

    @Column(name = "ConstantName", nullable = false, length = 45)
    @NotNull
    @Length(max = 45)
    public String getConstantName() {
        return this.constantName;
    }

    public void setConstantName(final String constantName) {
        this.constantName = constantName;
    }

    @Column(name = "ConstantValue")
    public Integer getConstantValue() {
        return this.constantValue;
    }

    public void setConstantValue(final Integer constantValue) {
        this.constantValue = constantValue;
    }

    @Override
    @Transient
    public Integer getId() {
        return this.integerConstantsId;
    }

}
