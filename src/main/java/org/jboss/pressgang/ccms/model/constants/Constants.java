package org.jboss.pressgang.ccms.model.constants;

public class Constants {

    /**
     * The default size to use for batch fetching.
     */
    public static final int DEFAULT_BATCH_SIZE = 15;

    /**
     * The number of min hashes to generate a topic signature with.
     */
    public static final int NUM_MIN_HASHES = 200;

    /**
     * Given the number of min hashes above, this is the number of rows
     * that will approximately match documents with a 60% similarity
     * with the formula Math.pow(1/b, 1/r) (See http://infolab.stanford.edu/~ullman/mmds/ch3.pdf
     * for more details).
     */
    public static final int LSH_SIXTY_PERCENT_ROWS = 7;

    /**
     * Given the number of min hashes above, this is the number of rows
     * that will approximately match documents with a 90% similarity
     * with the formula Math.pow(1/b, 1/r) (See http://infolab.stanford.edu/~ullman/mmds/ch3.pdf
     * for more details).
     */
    public static final int LSH_NINETY_PERCENT_ROWS = 22;
    /**
     * This is the minimum similarity that can be detected between two documents.
     */
    public static final float MIN_DOCUMENT_SIMILARITY = 0.6f;
    /**
     * This is the maximum similarity that can be detected between two documents.
     */
    public static final float MAX_DOCUMENT_SIMILARITY = 0.9f;



    private Constants() {
    }
}
