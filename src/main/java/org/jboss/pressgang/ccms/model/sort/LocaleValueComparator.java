package org.jboss.pressgang.ccms.model.sort;

import java.io.Serializable;
import java.util.Comparator;

import org.jboss.pressgang.ccms.model.Locale;


public class LocaleValueComparator implements Comparator<Locale>, Serializable {
    private static final long serialVersionUID = 2691874927193546398L;

    @Override
    public int compare(final Locale o1, final Locale o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;

        if (o1.getValue() == null && o2.getValue() == null) return 0;
        if (o1.getValue() == null) return -1;
        if (o2.getValue() == null) return 1;

        return o1.getValue().compareTo(o2.getValue());
    }

}