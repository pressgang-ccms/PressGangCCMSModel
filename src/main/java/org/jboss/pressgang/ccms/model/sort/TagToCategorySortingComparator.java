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
