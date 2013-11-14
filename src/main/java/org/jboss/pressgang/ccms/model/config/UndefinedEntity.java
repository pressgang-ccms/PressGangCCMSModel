package org.jboss.pressgang.ccms.model.config;

public class UndefinedEntity {
    private String key;
    private Integer value;

    public UndefinedEntity(final String key, final Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
