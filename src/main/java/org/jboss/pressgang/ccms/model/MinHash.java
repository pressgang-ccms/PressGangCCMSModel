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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "MinHash", uniqueConstraints = @UniqueConstraint(columnNames = {"TopicID", "MinHashFuncID"}))
public class MinHash extends AuditedEntity implements java.io.Serializable {
    public static final String SELECT_ALL_QUERY = "SELECT minhash FROM MinHash as minhash";

    private Integer minHashId;
    private Integer minHashFuncID;
    private Topic topic;
    private Integer minHash;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MinHashID", unique = true, nullable = false)
    public Integer getMinHashId() {
        return minHashId;
    }

    public void setMinHashId(final Integer minHashId) {
        this.minHashId = minHashId;
    }

    @Column(name = "MinHashFuncID", nullable = false)
    @NotNull
    public Integer getMinHashFuncID() {
        return minHashFuncID;
    }

    public void setMinHashFuncID(final Integer minHashFuncID) {
        this.minHashFuncID = minHashFuncID;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "TopicID", nullable = false)
    @NotNull
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(final Topic topic) {
        this.topic = topic;
    }

    @Column(name = "MinHash", unique = false, nullable = false)
    @NotNull
    public Integer getMinHash() {
        return minHash;
    }

    public void setMinHash(final Integer minHash) {
        this.minHash = minHash;
    }

    @Override
    @Transient
    public Integer getId() {
        return minHashId;
    }
}
