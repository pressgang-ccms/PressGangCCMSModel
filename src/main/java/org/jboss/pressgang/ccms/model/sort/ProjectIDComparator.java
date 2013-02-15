package org.jboss.pressgang.ccms.model.sort;

import java.io.Serializable;
import java.util.Comparator;

import org.jboss.pressgang.ccms.model.Project;


public class ProjectIDComparator implements Comparator<Project>, Serializable {
    private static final long serialVersionUID = 4905039547440839408L;

    @Override
    public int compare(final Project o1, final Project o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;

        if (o1.getProjectId() == null && o2.getProjectId() == null) return 0;
        if (o1.getProjectId() == null) return -1;
        if (o2.getProjectId() == null) return 1;

        return o1.getProjectId().compareTo(o2.getProjectId());
    }

}