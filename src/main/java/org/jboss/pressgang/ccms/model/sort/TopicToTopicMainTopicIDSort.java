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

import org.jboss.pressgang.ccms.model.TopicToTopic;


public class TopicToTopicMainTopicIDSort implements Comparator<TopicToTopic>, Serializable {
    private static final long serialVersionUID = 4076721909174007420L;

    @Override
    public int compare(final TopicToTopic o1, final TopicToTopic o2) {
        final Integer thisID = o1.getMainTopic() != null ? o1.getMainTopic().getTopicId() : null;
        final Integer otherID = o2.getMainTopic() != null ? o2.getMainTopic().getTopicId() : null;

        if (thisID == null && otherID == null) return 0;

        if (thisID == null) return -1;

        if (otherID == null) return 1;

        return thisID - otherID;
    }

}
