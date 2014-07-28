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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "BugzillaBug")
public class BugzillaBug extends AuditedEntity implements java.io.Serializable {
    private static final long serialVersionUID = -2421128855519132960L;
    public static final String SELECT_ALL_QUERY = "select bugzillaBug from BugzillaBug bugzillaBug";

    private Integer bugzillaBugId;
    private Integer bugzillaBugBugzillaId;
    private String bugzillaBugSummary;
    private Boolean bugzillaBugOpen;
    private Set<TopicToBugzillaBug> topicToBugzillaBugs = new HashSet<TopicToBugzillaBug>(0);

    @Override
    @Transient
    public Integer getId() {
        return bugzillaBugId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "BugzillaBugID", unique = true, nullable = false)
    public Integer getBugzillaBugId() {
        return bugzillaBugId;
    }

    public void setBugzillaBugId(final Integer bugzillaBugId) {
        this.bugzillaBugId = bugzillaBugId;
    }

    @Column(name = "BugzillaBugSummary", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getBugzillaBugSummary() {
        return bugzillaBugSummary;
    }

    public void setBugzillaBugSummary(final String bugzillaBugSummary) {
        this.bugzillaBugSummary = bugzillaBugSummary;
    }

    @Column(name = "BugzillaBugBugzillaID", nullable = false)
    @NotNull
    public Integer getBugzillaBugBugzillaId() {
        return bugzillaBugBugzillaId;
    }

    public void setBugzillaBugBugzillaId(final Integer bugzillaBugBugzillaId) {
        this.bugzillaBugBugzillaId = bugzillaBugBugzillaId;
    }

    @Column(name = "BugzillaBugOpen", columnDefinition = "BIT", length = 1)
    public Boolean getBugzillaBugOpen() {
        return bugzillaBugOpen;
    }

    public void setBugzillaBugOpen(final Boolean bugzillaBugOpen) {
        this.bugzillaBugOpen = bugzillaBugOpen;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bugzillaBug", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<TopicToBugzillaBug> getTopicToBugzillaBugs() {
        return topicToBugzillaBugs;
    }

    public void setTopicToBugzillaBugs(final Set<TopicToBugzillaBug> topicToBugzillaBugs) {
        this.topicToBugzillaBugs = topicToBugzillaBugs;
    }

    @PreRemove
    private void preRemove() {
        for (final TopicToBugzillaBug mapping : topicToBugzillaBugs)
            mapping.getTopic().getTopicToBugzillaBugs().remove(mapping);
        topicToBugzillaBugs.clear();
    }
}
