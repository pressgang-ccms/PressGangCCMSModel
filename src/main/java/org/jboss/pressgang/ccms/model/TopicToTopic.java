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

@Audited
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TopicToTopic", uniqueConstraints = @UniqueConstraint(columnNames = {"MainTopicID", "RelatedTopicID"}))
public class TopicToTopic extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -589601408520832737L;

    private Integer topicToTopicId;
    private Topic mainTopic;
    private Topic relatedTopic;
    private RelationshipTag relationshipTag;

    public TopicToTopic() {
    }

    public TopicToTopic(final Topic mainTopic, final Topic relatedTopic, final RelationshipTag relationshipTag) {
        this.mainTopic = mainTopic;
        this.relatedTopic = relatedTopic;
        this.relationshipTag = relationshipTag;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TopicToTopicID", unique = true, nullable = false)
    public Integer getTopicToTopicId() {
        return topicToTopicId;
    }

    public void setTopicToTopicId(final Integer topicToTopicId) {
        this.topicToTopicId = topicToTopicId;
    }

    @ManyToOne
    @JoinColumn(name = "MainTopicID", nullable = false)
    @NotNull
    public Topic getMainTopic() {
        return mainTopic;
    }

    public void setMainTopic(final Topic mainTopic) {
        this.mainTopic = mainTopic;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RelatedTopicID", nullable = false)
    @NotNull
    public Topic getRelatedTopic() {
        return relatedTopic;
    }

    public void setRelatedTopic(final Topic relatedTopic) {
        this.relatedTopic = relatedTopic;
    }

    @ManyToOne
    @JoinColumn(name = "RelationshipTagID", nullable = false)
    @NotNull
    public RelationshipTag getRelationshipTag() {
        return relationshipTag;
    }

    public void setRelationshipTag(final RelationshipTag relationshipTag) {
        this.relationshipTag = relationshipTag;
    }

    @Override
    @Transient
    public Integer getId() {
        return topicToTopicId;
    }

}
