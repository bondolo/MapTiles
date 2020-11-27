/*
 * Copyright © 2011, 2020 Mike Duigou
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.bondolo.tiles.grid;

import org.bondolo.tiles.*;
import static java.awt.Font.BOLD;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * A single tile in a Cartesian grid map.
 *
 * @param <C> Coordinate class for this class.
 * @param <D> Dimension class for this class.
 */
public abstract class GridTile<C extends GridTileCoord, D extends GridTileDimension> implements Tile<C>, TileView<D> {

    /**
     * Coordinates of this tile.
     */
    protected final C coord;

    /**
     * Label for this tile.
     */
    protected final String label;

    /**
     * Construct a new grid tile.
     *
     * @param coord The coordinates of the tile
     * @param label The label for the tile or null if unlabeled
     * @throws NullPointerException if the coordinates of the tile.
     */
    protected GridTile(C coord, String label) {
        this.coord = Objects.requireNonNull(coord, "Null coordinates");
        this.label = label;
    }

    @Override
    public boolean equals(Object target) {
        if(target.getClass() == this.getClass()) {
            @SuppressWarnings("unchecked")
            var likeMe = (GridTile<C,D>) target;

            return coord.equals(likeMe.coord);
        }

        return super.equals(target);
    }

    @Override
    public int hashCode() {
        return coord.hashCode();
    }

    @Override
    public String toString() {
        return null != getLabel()
                ? getLabel()
                : coord.toString();
    }

    @Override
    public C getCoord() {
        return coord;
    }

    /**
     * Returns the tile label.
     *
     * @return the tile label or null if unlabeled.
     */
    public String getLabel() {
        return label;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec Draws the tile coordinates string centered in the tile.
     */
    @Override
    public void draw(Graphics2D g2, Point2D origin, D dim, boolean highlight) {
        var drawing = toString();
        // XXX This is too much work to be doing for each tile
        var font = g2.getFont().deriveFont((float)(dim.side / 3.0));
        if(highlight) {
            font = font.deriveFont(BOLD);
        }
        g2.setFont(font);

        var fm = g2.getFontMetrics();
        double drawx = origin.getX() + (dim.getWidth() - fm.stringWidth(drawing)) / 2.0;
        double drawy = origin.getY() + (dim.getHeight() + fm.getAscent()) / 2.0;

        g2.drawString( drawing, (float) drawx, (float) drawy);
    }
}
