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
package org.bondolo.tiles.tri;

import org.bondolo.tiles.grid.GridTile;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Color.black;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import static java.awt.geom.Path2D.WIND_NON_ZERO;
import java.awt.geom.Point2D;

/**
 *  A single tile in a triangle tile map.
 */
public class TriTile extends GridTile<TriTileCoord, TriTileDimension> {

    /**
     * Construct a triangle tile at the specified coordinates.
     *
     * @param coords the location of the tile.
     */
    public TriTile(TriTileCoord coords) {
        super(coords, null);
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec Draws the outline of the tile in black and optionally fills it with light gray if highlighted
     */
    @Override
    public void draw(final Graphics2D g, Point2D origin, TriTileDimension dim, boolean highlight) {
        var p = new Path2D.Double(WIND_NON_ZERO, 10);
        double x = origin.getX();
        double y = origin.getY();
        double s = dim.getSide();

        if((0 != (coord.getX() % 2) ^ (0 != (coord.getY() % 2)))) {
            // Down pointing
            p.moveTo(x, y);
            p.lineTo(x + s, y);
            p.lineTo(x + (s / 2.0), y + dim.getHeight());
        } else {
            // Up pointing
            p.moveTo(x + (s / 2.0), y);
            p.lineTo(x + s, y + dim.getHeight());
            p.lineTo(x, y + dim.getHeight());
        }

        p.closePath();

        if (highlight) {
            g.setColor(LIGHT_GRAY);
            g.fill(p);
        }

        g.setColor(black);
        g.draw(p);

        super.draw(g, origin, dim, highlight);
    }
}
