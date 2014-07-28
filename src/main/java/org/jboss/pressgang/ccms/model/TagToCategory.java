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

/**
 * TagToCategory generated by hbm2java
 */
@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TagToCategory", uniqueConstraints = @UniqueConstraint(columnNames = {"TagID", "CategoryID"}))
public class TagToCategory extends AuditedEntity implements java.io.Serializable {
    public static final String SELECT_ALL_QUERY = "select tagToCategory from TagToCategory tagToCategory";
    private static final long serialVersionUID = 1037132589833037549L;

    private Integer tagToCategoryId;
    private Tag tag;
    private Category category;
    private Integer sorting;

    public TagToCategory() {
    }

    public TagToCategory(final Tag tag, final Category category) {
        this.tag = tag;
        this.category = category;
    }

    public TagToCategory(final Tag tag, final Category category, final Integer sorting) {
        this.tag = tag;
        this.category = category;
        this.sorting = sorting;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TagToCategoryID", unique = true, nullable = false)
    public Integer getTagToCategoryId() {
        return tagToCategoryId;
    }

    public void setTagToCategoryId(final Integer tagToCategoryId) {
        this.tagToCategoryId = tagToCategoryId;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "TagID", nullable = false)
    @NotNull
    public Tag getTag() {
        return tag;
    }

    public void setTag(final Tag tag) {
        this.tag = tag;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "CategoryID", nullable = false)
    @NotNull
    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    @Column(name = "Sorting")
    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    @Override
    @Transient
    public Integer getId() {
        return tagToCategoryId;
    }
}
