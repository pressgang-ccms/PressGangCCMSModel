/*
  Copyright 2011-2014 Red Hat

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

import org.jboss.pressgang.ccms.model.Tag;


public class TagNameComparator implements Comparator<Tag>, Serializable {
    private static final long serialVersionUID = 4124020196496965587L;

    @Override
    public int compare(final Tag o1, final Tag o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;

        if (o1.getTagName() == null && o2.getTagName() == null) return 0;
        if (o1.getTagName() == null) return -1;
        if (o2.getTagName() == null) return 1;

        return o1.getTagName().compareTo(o2.getTagName());
    }

}