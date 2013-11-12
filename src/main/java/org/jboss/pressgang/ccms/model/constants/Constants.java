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

    private Constants() {
    }
}
