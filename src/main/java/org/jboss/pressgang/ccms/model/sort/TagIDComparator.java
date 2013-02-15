package org.jboss.pressgang.ccms.model.sort;

import java.io.Serializable;
import java.util.Comparator;

import org.jboss.pressgang.ccms.model.Tag;


public class TagIDComparator implements Comparator<Tag>, Serializable {
    private static final long serialVersionUID = -5358493028871191000L;

    @Override
    public int compare(final Tag o1, final Tag o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;

        if (o1.getTagId() == null && o2.getTagId() == null) return 0;
        if (o1.getTagId() == null) return -1;
        if (o2.getTagId() == null) return 1;

        return o1.getTagId().compareTo(o2.getTagId());
    }

}