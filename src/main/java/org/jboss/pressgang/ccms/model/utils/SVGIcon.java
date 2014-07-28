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

package org.jboss.pressgang.ccms.model.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.w3c.dom.Document;

/**
 * A Swing Icon that draws an SVG image.
 *
 * @author <a href="mailto:cam%40mcc%2eid%2eau">Cameron McCormack</a>
 */
public class SVGIcon extends UserAgentAdapter implements Icon {
    /**
     * The BufferedImage generated from the SVG document.
     */
    private BufferedImage bufferedImage;

    /**
     * The width of the rendered image.
     */
    private int width;

    /**
     * The height of the rendered image.
     */
    private int height;

    private Color backgroundColour;

    /**
     * Create a new SVGIcon object.
     *
     * @param uri The URI to read the SVG document from.
     */
    public SVGIcon(String uri) throws TranscoderException {
        this(uri, 0, 0);
    }

    /**
     * Create a new SVGIcon object.
     *
     * @param uri The URI to read the SVG document from.
     * @param w   The width of the icon.
     * @param h   The height of the icon.
     */
    public SVGIcon(String uri, int w, int h) throws TranscoderException {
        generateBufferedImage(new TranscoderInput(uri), w, h);
    }

    /**
     * Create a new SVGIcon object.
     *
     * @param doc The SVG document.
     */
    public SVGIcon(Document doc) throws TranscoderException {
        this(doc, 0, 0);
    }

    public SVGIcon(final InputStream steam) throws TranscoderException {
        generateBufferedImage(new TranscoderInput(steam), 0, 0);
    }

    /**
     * Create a new SVGIcon object.
     *
     * @param doc The SVG document.
     * @param w   The width of the icon.
     * @param h   The height of the icon.
     */
    public SVGIcon(Document doc, int w, int h) throws TranscoderException {
        generateBufferedImage(new TranscoderInput(doc), w, h);
    }

    /**
     * Create a new SVGIcon object.
     *
     * @param doc The SVG document.
     * @param w   The width of the icon.
     * @param h   The height of the icon.
     */
    public SVGIcon(final InputStream steam, int w, int h) throws TranscoderException {
        generateBufferedImage(new TranscoderInput(steam), w, h);
    }

    /**
     * Generate the BufferedImage.
     */
    protected void generateBufferedImage(TranscoderInput in, int w, int h) throws TranscoderException {
        BufferedImageTranscoder t = new BufferedImageTranscoder();
        if (w != 0 && h != 0) {
            t.setDimensions(w, h);
        }
        t.transcode(in, null);
        bufferedImage = t.getBufferedImage();
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }

    /**
     * A transcoder that generates a BufferedImage.
     */
    protected class BufferedImageTranscoder extends ImageTranscoder {

        /**
         * The BufferedImage generated from the SVG document.
         */
        protected BufferedImage bufferedImage;

        /**
         * Creates a new ARGB image with the specified dimension.
         *
         * @param width  the image width in pixels
         * @param height the image height in pixels
         */
        @Override
        public BufferedImage createImage(int width, int height) {
            return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }

        /**
         * Writes the specified image to the specified output.
         *
         * @param img                 the image to write
         * @param output              the output where to store the image
         * @param TranscoderException if an error occured while storing the image
         */
        @Override
        public void writeImage(BufferedImage img, TranscoderOutput output) throws TranscoderException {
            bufferedImage = img;
        }

        /**
         * Returns the BufferedImage generated from the SVG document.
         */
        public BufferedImage getBufferedImage() {
            return bufferedImage;
        }

        /**
         * Set the dimensions to be used for the image.
         */
        public void setDimensions(int w, int h) {
            hints.put(KEY_WIDTH, new Float(w));
            hints.put(KEY_HEIGHT, new Float(h));
        }
    }

    // Icon //////////////////////////////////////////////////////////////////

    /**
     * Returns the icon's width.
     */
    @Override
    public int getIconWidth() {
        return width;
    }

    /**
     * Returns the icon's height.
     */
    @Override
    public int getIconHeight() {
        return height;
    }

    /**
     * Draw the icon at the specified location.
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (backgroundColour == null) g.drawImage(bufferedImage, x, y, null);
        else g.drawImage(bufferedImage, x, y, backgroundColour, null);
    }

    // UserAgent /////////////////////////////////////////////////////////////

    /**
     * Returns the default size of this user agent.
     */
    @Override
    public Dimension2D getViewportSize() {
        return new Dimension(width, height);
    }

    public Color getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(Color backgroundColour) {
        this.backgroundColour = backgroundColour;
    }
}
