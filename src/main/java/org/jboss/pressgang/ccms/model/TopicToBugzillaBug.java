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
import javax.persistence.FetchType;
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
@Table(name = "TopicToBugzillaBug", uniqueConstraints = @UniqueConstraint(columnNames = {"TopicID", "BugzillaBugID"}))
public class TopicToBugzillaBug extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -4963859367368389435L;
    public static final String SELECT_ALL_QUERY = "select topicToBugzillaBug from TopicToBugzillaBug topicToBugzillaBug";

    private Integer topicToBugzillaBugId;
    private BugzillaBug bugzillaBug;
    private Topic topic;

    public TopicToBugzillaBug() {
    }

    public TopicToBugzillaBug(final BugzillaBug bugzillaBug, final Topic topic) {
        this.bugzillaBug = bugzillaBug;
        this.topic = topic;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TopicToBugzillaBugID", unique = true, nullable = false)
    public Integer getTopicToBugzillaBugId() {
        return topicToBugzillaBugId;
    }

    public void setTopicToBugzillaBugId(final Integer topicToBugzillaBugId) {
        this.topicToBugzillaBugId = topicToBugzillaBugId;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BugzillaBugID", nullable = false)
    @NotNull
    public BugzillaBug getBugzillaBug() {
        return bugzillaBug;
    }

    public void setBugzillaBug(final BugzillaBug bugzillaBug) {
        this.bugzillaBug = bugzillaBug;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TopicID", nullable = false)
    @NotNull
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(final Topic topic) {
        this.topic = topic;
    }

    @Override
    @Transient
    public Integer getId() {
        return topicToBugzillaBugId;
    }
}
