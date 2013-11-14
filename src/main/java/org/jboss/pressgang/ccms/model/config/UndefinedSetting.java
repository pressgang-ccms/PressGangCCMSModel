package org.jboss.pressgang.ccms.model.config;

public class UndefinedSetting {
    private String key;
    private String value;

    public UndefinedSetting(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
