package org.jboss.pressgang.ccms.model.interfaces;

import java.util.List;

import org.jboss.pressgang.ccms.model.Tag;

public interface HasTags {

    void addTag(final Tag tag);

    void removeTag(final Tag tag);

    List<Tag> getTags();
}
