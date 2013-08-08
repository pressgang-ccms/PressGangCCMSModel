package org.jboss.pressgang.ccms.model.interfaces;

public interface HasTwoWayRelationships<T> {

    void addRelationshipTo(T entity);

    void addRelationshipFrom(T entity);

    void removeRelationshipTo(T entity);

    void removeRelationshipFrom(T entity);
}
