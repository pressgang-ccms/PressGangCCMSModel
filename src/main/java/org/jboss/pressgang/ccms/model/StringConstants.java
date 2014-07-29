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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

/**
 * StringConstants generated by hbm2java
 */
@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "StringConstants")
public class StringConstants extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -6403239052018430987L;
    public static final String SELECT_ALL_QUERY = "SELECT stringConstants FROM StringConstants stringConstants";

    private Integer stringConstantsId;
    private String constantName;
    private String constantValue;

    public StringConstants() {
    }

    public StringConstants(final String constantName) {
        this.constantName = constantName;
    }

    public StringConstants(final String constantName, final String constantValue) {
        this.constantName = constantName;
        this.constantValue = constantValue;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "StringConstantsID", unique = true, nullable = false)
    public Integer getStringConstantsId() {
        return stringConstantsId;
    }

    public void setStringConstantsId(Integer stringConstantsId) {
        this.stringConstantsId = stringConstantsId;
    }

    @Column(name = "ConstantName", nullable = false, length = 45)
    @NotNull(message = "{stringconstant.name.notBlank}")
    @NotBlank(message = "{stringconstant.name.notBlank}")
    @Size(max = 45)
    public String getConstantName() {
        return constantName;
    }

    public void setConstantName(final String constantName) {
        this.constantName = constantName;
    }

    @Column(name = "ConstantValue", columnDefinition = "MEDIUMTEXT")
    @Size(max = 16777215)
    public String getConstantValue() {
        return constantValue;
    }

    public void setConstantValue(final String constantValue) {
        this.constantValue = constantValue;
    }

    @Override
    @Transient
    public Integer getId() {
        return stringConstantsId;
    }

}
