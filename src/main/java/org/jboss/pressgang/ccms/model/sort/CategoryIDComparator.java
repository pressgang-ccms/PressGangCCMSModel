package org.jboss.pressgang.ccms.model.sort;

import java.io.Serializable;
import java.util.Comparator;

import org.jboss.pressgang.ccms.model.Category;


public class CategoryIDComparator implements Comparator<Category>, Serializable {
    private static final long serialVersionUID = 2687487534194832840L;

    @Override
    public int compare(final Category o1, final Category o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;

        if (o1.getCategoryId() == null && o2.getCategoryId() == null) return 0;
        if (o1.getCategoryId() == null) return -1;
        if (o2.getCategoryId() == null) return 1;

        return o1.getCategoryId().compareTo(o2.getCategoryId());
    }

}