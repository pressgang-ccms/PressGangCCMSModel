package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TopicSourceUrl generated by hbm2java
 */
@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "TopicSourceURL")
public class TopicSourceUrl extends AuditedEntity implements java.io.Serializable {
    private static final Logger log = LoggerFactory.getLogger(TopicSourceUrl.class);
    private static final long serialVersionUID = 1923828486173137768L;

    private Integer topicSourceUrlId;
    private String sourceUrl;
    private String title;
    private String description;
    private Set<TopicToTopicSourceUrl> topicToTopicSourceUrls = new HashSet<TopicToTopicSourceUrl>(0);

    public TopicSourceUrl() {
    }

    public TopicSourceUrl(final String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public TopicSourceUrl(final String sourceUrl, final String description, final Set<TopicToTopicSourceUrl> topicToTopicSourceUrls) {
        this.sourceUrl = sourceUrl;
        this.description = description;
        this.topicToTopicSourceUrls = topicToTopicSourceUrls;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TopicSourceURLID", unique = true, nullable = false)
    public Integer getTopicSourceUrlId() {
        return topicSourceUrlId;
    }

    public void setTopicSourceUrlId(final Integer topicSourceUrlId) {
        this.topicSourceUrlId = topicSourceUrlId;
    }

    @Column(name = "SourceURL", nullable = false, length = 2048)
    @NotNull
    @Size(max = 2048)
    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(final String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Column(name = "Title", nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Column(name = "Description", columnDefinition = "TEXT")
    @Size(max = 65535)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "topicSourceUrl")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @BatchSize(size = Constants.DEFAULT_BATCH_SIZE)
    public Set<TopicToTopicSourceUrl> getTopicToTopicSourceUrls() {
        return topicToTopicSourceUrls;
    }

    public void setTopicToTopicSourceUrls(final Set<TopicToTopicSourceUrl> topicToTopicSourceUrls) {
        this.topicToTopicSourceUrls = topicToTopicSourceUrls;
    }

    /**
     * If the user has left the title field empty, try to download the page and get the title from the HTML.
     */
    @PrePersist
    @PreUpdate
    private void setTitle() {
        try {
            if (title == null || title.trim().length() == 0 && (sourceUrl != null && !sourceUrl.trim().isEmpty())) {
                /* Some common string replacements to make in the titles */
                final Map<String, String> replaceList = new HashMap<String, String>();
                replaceList.put("&nbsp;", " ");

                // create an instance of HtmlCleaner
                final HtmlCleaner cleaner = new HtmlCleaner();

                // clean the source url
                final TagNode node = cleaner.clean(new URL(getSourceUrl()));

                // find the first title node
                final TagNode title = node.findElementByName("title", true);

                if (title != null) {
                    // clean up the title
                    String titleText = title.getText().toString();

                    for (final String replace : replaceList.keySet())
                        titleText = titleText.replaceAll(replace, replaceList.get(replace));

                    titleText = titleText.trim();

                    // assign it to the entity
                    this.title = titleText;
                }
            }
        } catch (final IOException ex) {
            log.error("Probably a problem with HTMLCleaner", ex);
        }
    }

    @Override
    @Transient
    public Integer getId() {
        return topicSourceUrlId;
    }

}