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
