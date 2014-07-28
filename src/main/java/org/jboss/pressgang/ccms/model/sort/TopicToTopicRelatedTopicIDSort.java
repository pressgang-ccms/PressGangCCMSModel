/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.model.sort;

import java.io.Serializable;
import java.util.Comparator;

import org.jboss.pressgang.ccms.model.TopicToTopic;


public class TopicToTopicRelatedTopicIDSort implements Comparator<TopicToTopic>, Serializable {
    private static final long serialVersionUID = 9132030612056851243L;

    @Override
    public int compare(final TopicToTopic o1, final TopicToTopic o2) {
        final Integer thisID = o1.getRelatedTopic() != null ? o1.getRelatedTopic().getTopicId() : null;
        final Integer otherID = o2.getRelatedTopic() != null ? o2.getRelatedTopic().getTopicId() : null;

        if (thisID == null && otherID == null) return 0;

        if (thisID == null) return -1;

        if (otherID == null) return 1;

        return thisID - otherID;
    }

}
