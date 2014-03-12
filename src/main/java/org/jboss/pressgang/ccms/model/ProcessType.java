package org.jboss.pressgang.ccms.model;

import java.io.Serializable;

public enum ProcessType implements Serializable {
    GENERIC("Generic"), TRANSLATION_SYNC("Translation Sync"), TRANSLATION_PUSH("Translation Push");

    private final String name;

    ProcessType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
