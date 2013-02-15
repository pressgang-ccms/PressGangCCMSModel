package org.jboss.pressgang.ccms.model.sort;

import java.io.Serializable;
import java.util.Comparator;

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.base.ToPropertyTag;


public class ParentToPropertyTagIDComparator implements Comparator<ToPropertyTag<?>>, Serializable {
    private static final long serialVersionUID = 7246141485687738023L;

    @Override
    public int compare(final ToPropertyTag<?> o1, final ToPropertyTag<?> o2) {
        final PropertyTag o1Tag = o1.getPropertyTag() != null ? o1.getPropertyTag() : null;
        final PropertyTag o2Tag = o2.getPropertyTag() != null ? o2.getPropertyTag() : null;

        if (o1Tag == null && o2Tag == null) return 0;

        if (o1Tag == null) return -1;

        if (o2Tag == null) return 1;

        return o1Tag.getPropertyTagId().compareTo(o2Tag.getPropertyTagId());
    }

}
