package org.jboss.pressgang.ccms.model.constants;

public class Constants {

    /**
     * The default size to use for batch fetching.
     */
    public static final int DEFAULT_BATCH_SIZE = 15;
    
    public static final Integer CS_BOOK = 0;
    public static final Integer CS_ARTICLE = 1;
    
    public static final Integer CS_NODE_TOPIC = 0;
    public static final Integer CS_NODE_SECTION = 1;
    public static final Integer CS_NODE_CHAPTER = 2;
    public static final Integer CS_NODE_APPENDIX = 3;
    public static final Integer CS_NODE_PART = 4;
    public static final Integer CS_NODE_PROCESS = 5;
    public static final Integer CS_NODE_COMMENT = 6;
    
    public static final Integer CS_RELATIONSHIP_PREREQUISITE = 0;
    public static final Integer CS_RELATIONSHIP_REFER_TO = 1;
    public static final Integer CS_RELATIONSHIP_LINK_LIST = 2;
    public static final Integer CS_RELATIONSHIP_NEXT = 3;
    public static final Integer CS_RELATIONSHIP_PREVIOUS = 4;
    
    public static final Integer DOCBOOK_45 = 0;
    public static final Integer DOCBOOK_50 = 1;
}
