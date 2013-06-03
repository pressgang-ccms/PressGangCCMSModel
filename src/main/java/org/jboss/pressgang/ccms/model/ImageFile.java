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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;
import org.jboss.pressgang.ccms.model.exceptions.CustomConstraintViolationException;
import org.jboss.pressgang.ccms.utils.common.FileUtilities;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "ImageFile")
public class ImageFile extends AuditedEntity implements java.io.Serializable {
    public static final String SELECT_ALL_QUERY = "select imageFile from ImageFile imageFile";
    private static final String SVG_MIME_TYPE = "image/svg+xml";
    private static final String JPG_MIME_TYPE = "image/jpeg";
    private static final String GIF_MIME_TYPE = "image/gif";
    private static final String PNG_MIME_TYPE = "image/png";

    private static final long serialVersionUID = -3885332582642450795L;
    private Integer imageFileId;

    private String description;
    private Set<LanguageImage> languageImages = new HashSet<LanguageImage>(0);

    public ImageFile() {
    }

    public ImageFile(final Integer imageFileId) {
        this.imageFileId = imageFileId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ImageFileID", unique = true, nullable = false)
    public Integer getImageFileId() {
        return imageFileId;
    }

    public void setImageFileId(final Integer imageFileId) {
        this.imageFileId = imageFileId;
    }

    @Column(name = "Description", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Transient
    public String getDocbookFileName() {
        if (languageImages != null && languageImages.size() != 0 && imageFileId != null) {
            for (final LanguageImage firstChild : languageImages) {
                if (firstChild.getOriginalFileName() != null) {
                    final int extensionIndex = firstChild.getOriginalFileName().lastIndexOf(".");
                    if (extensionIndex != -1) return imageFileId + firstChild.getOriginalFileName().substring(extensionIndex);
                }
            }
        }

        return "";
    }

    @Transient
    public String getExtension() {
        if (languageImages.size() != 0) {
            for (final LanguageImage firstChild : languageImages) {
                if (firstChild.getOriginalFileName() != null) {
                    return FileUtilities.getFileExtension(firstChild.getOriginalFileName());
                }
            }
        }

        return null;
    }

    @Transient
    public String getMimeType() {
        final String extension = getExtension();

        if (extension != null) {
            if (extension.equalsIgnoreCase("JPG")) return JPG_MIME_TYPE;
            if (extension.equalsIgnoreCase("GIF")) return GIF_MIME_TYPE;
            if (extension.equalsIgnoreCase("PNG")) return PNG_MIME_TYPE;
            if (extension.equalsIgnoreCase("SVG")) return SVG_MIME_TYPE;
        }

        return "application/octet-stream";
    }

    @Override
    @Transient
    public Integer getId() {
        return imageFileId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "imageFile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<LanguageImage> getLanguageImages() {
        return languageImages;
    }

    @Transient
    public List<LanguageImage> getLanguageImagesArray() {
        final List<LanguageImage> retValue = new ArrayList<LanguageImage>(languageImages);
        Collections.sort(retValue, new Comparator<LanguageImage>() {
            @Override
            public int compare(final LanguageImage o1, final LanguageImage o2) {
                if (o1.getLocale() == null && o2.getLocale() == null) return 0;
                if (o1.getLocale() == null) return -1;
                if (o2.getLocale() == null) return 1;
                return o1.getLocale().compareTo(o2.getLocale());
            }
        });

        return retValue;
    }

    public void setLanguageImages(Set<LanguageImage> languageImages) {
        this.languageImages = languageImages;
    }

    /**
     * An image file can be represented by multiple translated images. These images can all be from originally differently names
     * files, and can have different dimensions. However, all translated images need to be of the same file file. The
     * getDocbookFileName() method needs to be able to append a fixed extension to the file name when the image is included in a
     * Docbook XML file.
     * <p/>
     * This method will throw an exception if there are inconsistent file extensions on any of the original file names assigned
     * to the translated images.
     *
     * @throws CustomConstraintViolationException
     *
     */
    public void validate() throws CustomConstraintViolationException {
        final String extension = getExtension();
        if (extension != null) {
            for (final LanguageImage langImg : languageImages) {
                if (langImg.getOriginalFileName() != null && !langImg.getOriginalFileName().isEmpty()) {
                    final String thisExtension = FileUtilities.getFileExtension(langImg.getOriginalFileName());
                    if (!thisExtension.toLowerCase().equals(extension.toLowerCase())) throw new CustomConstraintViolationException(
                            "All LanguageImages contained by an ImageFile need to have the same file extension");
                }
            }
        }
    }

    public void addLanguageImage(final LanguageImage languageImage) {
        languageImages.add(languageImage);
        languageImage.setImageFile(this);
    }

    public void removeLanguageImage(final LanguageImage languageImage) {
        languageImages.remove(languageImage);
        languageImage.setImageFile(null);
    }
}
