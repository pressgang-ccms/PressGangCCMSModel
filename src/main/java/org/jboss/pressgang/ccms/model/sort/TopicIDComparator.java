package org.jboss.pressgang.ccms.model.sort;

import java.io.Serializable;
import java.util.Comparator;

import org.jboss.pressgang.ccms.model.Topic;


public class TopicIDComparator implements Comparator<Topic>, Serializable {
    private static final long serialVersionUID = -38602664852947772L;

    @Override
    public int compare(final Topic o1, final Topic o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;

        if (o1.getTopicId() == null && o2.getTopicId() == null) return 0;
        if (o1.getTopicId() == null) return -1;
        if (o2.getTopicId() == null) return 1;

        return o1.getTopicId().compareTo(o2.getTopicId());
    }

}