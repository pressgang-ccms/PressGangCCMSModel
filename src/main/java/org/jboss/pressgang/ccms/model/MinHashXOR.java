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

package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "MinHashXOR", uniqueConstraints = @UniqueConstraint(columnNames = {"MinHashXOR"}))
public class MinHashXOR extends AuditedEntity implements java.io.Serializable {
    public static final String SELECT_ALL_QUERY = "SELECT xor FROM MinHashXOR as xor";

    private Integer minHashXORId;
    private Integer minHashXORFuncId;
    private Integer minHashXOR;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MinHashXORID", unique = true, nullable = false)
    public Integer getMinHashXORId() {
        return minHashXORId;
    }

    public void setMinHashXORId(final Integer minHashXORId) {
        this.minHashXORId = minHashXORId;
    }

    @Column(name = "MinHashXOR", unique = true, nullable = false)
    @NotNull
    public Integer getMinHashXOR() {
        return minHashXOR;
    }

    public void setMinHashXOR(final Integer minHashXOR) {
        this.minHashXOR = minHashXOR;
    }

    @Column(name = "MinHashXORFuncID", unique = true, nullable = false)
    @NotNull
    public Integer getMinHashXORFuncId() {
        return minHashXORFuncId;
    }

    public void setMinHashXORFuncId(final Integer minHashXORFuncId) {
        this.minHashXORFuncId = minHashXORFuncId;
    }

    @Override
    @Transient
    public Integer getId() {
        return minHashXORId;
    }
}
