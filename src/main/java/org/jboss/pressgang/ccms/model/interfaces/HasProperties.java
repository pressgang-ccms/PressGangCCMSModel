package org.jboss.pressgang.ccms.model.interfaces;

import java.util.List;
import java.util.Set;

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.base.ToPropertyTag;

public interface HasProperties<T extends ToPropertyTag<T>> {

    void addPropertyTag(PropertyTag propertyTag, String value);

    void addPropertyTag(T propertyTag);

    void removePropertyTag(PropertyTag propertyTag, String value);

    void removePropertyTag(T propertyTag);

    Set<T> getPropertyTags();

    List<T> getPropertyTagsList();
}
