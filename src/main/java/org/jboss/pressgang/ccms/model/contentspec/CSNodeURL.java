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

package org.jboss.pressgang.ccms.model.contentspec;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "ContentSpecNodeURL")
public class CSNodeURL extends AuditedEntity implements Serializable {
    private static final long serialVersionUID = 145623170043810612L;

    private Integer csNodeUrlId;
    private String url;
    private CSNode csNode;
    private ContentSpec contentSpec;

    @Override
    @Transient
    public Integer getId() {
        return csNodeUrlId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CSNodeURLID", unique = true, nullable = false)
    public Integer getCSNodeUrlId() {
        return csNodeUrlId;
    }

    public void setCSNodeUrlId(final Integer csNodeUrlId) {
        this.csNodeUrlId = csNodeUrlId;
    }

    @Column(name = "URL", nullable = false, length = 1024)
    @Size(max = 1024)
    @NotNull
    @NotEmpty
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "ContentSpecNodeID")
    public CSNode getCSNode() {
        return csNode;
    }

    public void setCSNode(final CSNode csNode) {
        this.csNode = csNode;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "ContentSpecID")
    public ContentSpec getContentSpec() {
        return contentSpec;
    }

    public void setContentSpec(ContentSpec contentSpec) {
        this.contentSpec = contentSpec;
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
