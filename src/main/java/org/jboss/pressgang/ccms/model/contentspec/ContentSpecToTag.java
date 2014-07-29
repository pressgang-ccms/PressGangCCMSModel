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

package org.jboss.pressgang.ccms.model.contentspec;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "ContentSpecToTag", uniqueConstraints = @UniqueConstraint(columnNames = {"ContentSpecID", "TagID"}))
public class ContentSpecToTag extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -7516063608506037594L;

    private Integer contentSpecToTagId;
    private ContentSpec contentSpec;
    private Tag tag;
    private Boolean bookTag;

    public ContentSpecToTag() {
    }

    public ContentSpecToTag(final ContentSpec contentSpec, final Tag tag, final boolean isBookTag) {
        this.contentSpec = contentSpec;
        this.tag = tag;
        this.bookTag = isBookTag;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ContentSpecToTagID", unique = true, nullable = false)
    public Integer getContentSpecToTagId() {
        return contentSpecToTagId;
    }

    public void setContentSpecToTagId(final Integer contentSpecToTagId) {
        this.contentSpecToTagId = contentSpecToTagId;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "ContentSpecID", nullable = false)
    @NotNull
    public ContentSpec getContentSpec() {
        return contentSpec;
    }

    public void setContentSpec(final ContentSpec contentSpec) {
        this.contentSpec = contentSpec;
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

    @Column(name = "BookTag", columnDefinition = "BIT", length = 1, nullable = false)
    @NotNull
    public Boolean getBookTag() {
        return bookTag;
    }

    public void setBookTag(Boolean bookTag) {
        this.bookTag = bookTag;
    }

    @Override
    @Transient
    public Integer getId() {
        return contentSpecToTagId;
    }

    @PrePersist
    @PreUpdate
    protected void preSave() {
        // Set the content specs last modified date if one of it's nodes change
        if (contentSpec != null) {
            contentSpec.setLastModified();
        }
    }
}
