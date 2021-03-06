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

/**
 * TopicToTag generated by hbm2java
 */
@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TopicToTag", uniqueConstraints = @UniqueConstraint(columnNames = {"TopicID", "TagID"}))
public class TopicToTag extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -7516063608506037594L;

    private Integer topicToTagId;
    private Topic topic;
    private Tag tag;

    public TopicToTag() {
    }

    public TopicToTag(final Topic topic, final Tag tag) {
        this.topic = topic;
        this.tag = tag;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TopicToTagID", unique = true, nullable = false)
    public Integer getTopicToTagId() {
        return topicToTagId;
    }

    public void setTopicToTagId(final Integer topicToTagId) {
        this.topicToTagId = topicToTagId;
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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "TagID", nullable = false)
    @NotNull
    public Tag getTag() {
        return tag;
    }

    public void setTag(final Tag tag) {
        this.tag = tag;
    }

    @Override
    @Transient
    public Integer getId() {
        return topicToTagId;
    }

}
