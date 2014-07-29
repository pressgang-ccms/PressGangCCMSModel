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
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TopicSecondOrderData")
public class TopicSecondOrderData implements java.io.Serializable {
    private static final long serialVersionUID = 3393132758855818345L;

    private Integer topicSecondOrderDataId;
    private String topicXMLErrors;

    public TopicSecondOrderData() {
    }

    public TopicSecondOrderData(final Integer topicSecondOrderDataId) {
        this.topicSecondOrderDataId = topicSecondOrderDataId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TopicSecondOrderDataID")
    public Integer getTopicSecondOrderDataId() {
        return topicSecondOrderDataId;
    }

    public void setTopicSecondOrderDataId(final Integer topicSecondOrderDataId) {
        this.topicSecondOrderDataId = topicSecondOrderDataId;
    }

    @Column(name = "TopicXMLErrors", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getTopicXMLErrors() {
        return topicXMLErrors;
    }

    public void setTopicXMLErrors(final String topicXMLErrors) {
        this.topicXMLErrors = topicXMLErrors;
    }

    @Transient
    public Integer getId() {
        return topicSecondOrderDataId;
    }
}
