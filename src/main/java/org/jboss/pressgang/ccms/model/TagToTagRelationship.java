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

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@Audited
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TagToTagRelationship")
public class TagToTagRelationship extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = 54337112345485162L;

    private int tagToTagRelationshipType;
    private String tagToTagRelationshipDescription;
    private Set<TagToTag> tagToTags = new HashSet<TagToTag>(0);

    public TagToTagRelationship() {
    }

    public TagToTagRelationship(final int tagToTagRelationshipType) {
        this.tagToTagRelationshipType = tagToTagRelationshipType;
    }

    public TagToTagRelationship(final int tagToTagRelationshipType, final String tagToTagRelationshipDescription, Set<TagToTag> tagToTags) {
        this.tagToTagRelationshipType = tagToTagRelationshipType;
        this.tagToTagRelationshipDescription = tagToTagRelationshipDescription;
        this.tagToTags = tagToTags;
    }

    @Id
    @Column(name = "TagToTagRelationshipType", unique = true, nullable = false)
    public int getTagToTagRelationshipType() {
        return tagToTagRelationshipType;
    }

    public void setTagToTagRelationshipType(final int tagToTagRelationshipType) {
        this.tagToTagRelationshipType = tagToTagRelationshipType;
    }

    @Column(name = "TagToTagRelationshipDescription", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getTagToTagRelationshipDescription() {
        return tagToTagRelationshipDescription;
    }

    public void setTagToTagRelationshipDescription(final String tagToTagRelationshipDescription) {
        this.tagToTagRelationshipDescription = tagToTagRelationshipDescription;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tagToTagRelationship")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<TagToTag> getTagToTags() {
        return tagToTags;
    }

    public void setTagToTags(final Set<TagToTag> tagToTags) {
        this.tagToTags = tagToTags;
    }

    @Override
    @Transient
    public Integer getId() {
        return tagToTagRelationshipType;
    }

}
