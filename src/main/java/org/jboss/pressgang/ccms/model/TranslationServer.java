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

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.pressgang.ccms.model.base.PressGangEntity;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TranslationServer")
public class TranslationServer implements Serializable, PressGangEntity {
    public static final String SELECT_ALL_QUERY = "SELECT translationServer FROM TranslationServer as translationServer";
    private static final long serialVersionUID = 5973566271841696214L;

    private Integer id;
    private String name;
    private String url;
    private String username;
    private String apikey;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TranslationServerID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "Name", length = 255, nullable = false)
    @NotNull(message = "{translationserver.name.notBlank}")
    @NotEmpty(message = "{translationserver.name.notBlank}")
    @Size(max = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "URL", length = 1024)
    @NotNull(message = "{translationserver.url.notBlank}")
    @NotEmpty(message = "{translationserver.url.notBlank}")
    @Size(max = 1024)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "Username", length = 255)
    @NotNull(message = "{translationserver.username.notBlank}")
    @NotEmpty(message = "{translationserver.username.notBlank}")
    @Size(max = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "ApiKey", length = 255)
    @NotNull(message = "{translationserver.key.notBlank}")
    @NotEmpty(message = "{translationserver.key.notBlank}")
    @Size(max = 255)
    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
}
