/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

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
    /**
     * Math.pow(1/MIN_HASH_BANDS,1/MIN_HASH_ROWS) should approximate how similar two documents should
     * be when searching the database for duplicates. Or, to minimize false negatives, it should be
     * smaller than the threshold. Here we have fixed the bands and rows to find topics with roughly
     * 50% similarity, which is smaller than the minimum similarity of 60% that can be requested.
     */
    public static final int MIN_HASH_BANDS = 40;
    public static final int MIN_HASH_ROWS = 5;



    private Constants() {
    }
}
