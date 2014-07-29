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

import org.jboss.pressgang.ccms.model.TopicToTag;


public class TopicToTagTagIDSort implements Comparator<TopicToTag>, Serializable {
    private static final long serialVersionUID = -3177443734039753335L;

    @Override
    public int compare(final TopicToTag o1, final TopicToTag o2) {
        final Integer thisTagID = o1.getTag() != null ? o1.getTag().getTagId() : null;
        final Integer otherTagID = o2.getTag() != null ? o2.getTag().getTagId() : null;

        if (thisTagID == null && otherTagID == null) return 0;

        if (thisTagID == null) return -1;

        if (otherTagID == null) return 1;

        return thisTagID - otherTagID;
    }

}