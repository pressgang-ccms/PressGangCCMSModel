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

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.NotBlank;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.exceptions.CustomConstraintViolationException;
import org.jboss.pressgang.ccms.utils.common.HashUtilities;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "LanguageFile", uniqueConstraints = @UniqueConstraint(columnNames = {"FileID", "LocaleID"}))
public class LanguageFile extends AuditedEntity implements Serializable {
    private static final long serialVersionUID = 6439345505628825355L;

    private Integer languageFileId = null;
    private Locale locale;
    private String originalFileName = null;
    private byte[] fileData;
    private File file = null;
    private char[] fileContentHash;

    @PrePersist
    @PreUpdate
    private void updateImageData() throws CustomConstraintViolationException {
        if (fileData != null) {
            fileContentHash = HashUtilities.generateSHA256(fileData).toCharArray();
        }
    }

    @Transient
    @Override
    public Integer getId() {
        return languageFileId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "LanguageFileID", unique = true, nullable = false)
    public Integer getLanguageFileId() {
        return languageFileId;
    }

    public void setLanguageFileId(Integer languageFileId) {
        this.languageFileId = languageFileId;
    }

    @Column(name = "FileData", columnDefinition = "longblob")
    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    @Column(name = "OriginalFileName", length = 255)
    @Size(max = 255)
    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(final String originalFileName) {
        this.originalFileName = originalFileName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FileID", nullable = false)
    @NotNull
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "LocaleID")
    @NotNull(message = "{languagefile.locale.notBlank}")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Column(name = "FileContentHash", columnDefinition = "CHAR(64)")
    @Size(max = 64, min = 64)
    public char[] getFileContentHash() {
        return fileContentHash;
    }

    public void setFileContentHash(final char[] fileContentHash) {
        this.fileContentHash = fileContentHash;
    }
}
