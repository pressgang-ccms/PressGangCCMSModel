package org.jboss.pressgang.ccms.model.sort;

import java.io.Serializable;
import java.util.Comparator;

import org.jboss.pressgang.ccms.model.TagToCategory;


public class TagToCategorySortingComparator implements Comparator<TagToCategory>, Serializable {
    private static final long serialVersionUID = -8971118568273772780L;
    private int lessThan;
    private int greaterThan;

    public TagToCategorySortingComparator(boolean accending) {
        lessThan = accending ? -1 : 1;
        greaterThan = accending ? 1 : -1;
    }

    public TagToCategorySortingComparator() {
        lessThan = -1;
        greaterThan = 1;
    }

    /**
     * Sorting order is preferentially used to sort TagToCategory's, or the name of the Tag that
     * the TagToCategory's point to are used if both TagToCategory's sorting orders are null.
     */
    @Override
    public int compare(final TagToCategory o1, final TagToCategory o2) {
        if (o1 == null && o2 == null) return 0;

        if (o1 == null) return lessThan;

        if (o2 == null) return greaterThan;

        if (o1.getSorting() == null && o2.getSorting() == null) return compareSecondLevel(o1, o2);

        if (o1.getSorting() == null) return greaterThan;
        if (o2.getSorting() == null) return lessThan;

        if (o1.getSorting().equals(o2.getSorting())) return compareSecondLevel(o1, o2);

        return o1.getSorting().compareTo(o2.getSorting()) * greaterThan;
    }

    protected int compareSecondLevel(final TagToCategory o1, final TagToCategory o2) {
        if (o2 == null) return greaterThan;

        if (o1.getTag() == null && o2.getTag() == null) return 0;
        if (o1.getTag() == null) return greaterThan;
        if (o2.getTag() == null) return lessThan;

        if (o1.getTag().getTagName() == null && o2.getTag().getTagName() == null) return 0;
        if (o1.getTag().getTagName() == null) return lessThan;
        if (o2.getTag().getTagName() == null) return greaterThan;

        return o1.getTag().getTagName().compareTo(o2.getTag().getTagName()) * greaterThan;
    }
}
