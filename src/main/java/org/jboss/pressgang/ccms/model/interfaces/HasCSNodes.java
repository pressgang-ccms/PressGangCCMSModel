package org.jboss.pressgang.ccms.model.interfaces;

import java.util.List;
import java.util.Set;

import org.jboss.pressgang.ccms.model.contentspec.CSNode;

public interface HasCSNodes {

    void addChild(CSNode child);

    void removeChild(CSNode child);

    List<CSNode> getChildrenList();

    Set<CSNode> getChildren();
}
