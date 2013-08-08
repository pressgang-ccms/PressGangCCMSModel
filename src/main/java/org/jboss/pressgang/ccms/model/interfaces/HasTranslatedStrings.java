package org.jboss.pressgang.ccms.model.interfaces;

public interface HasTranslatedStrings<T> {

    void addTranslatedString(T entity);

    void removeTranslatedString(T entity);
}
