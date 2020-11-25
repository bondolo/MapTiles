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
package org.bondolo.tiles.rect;

import org.bondolo.tiles.grid.GridTile;
import java.awt.Color;
import static java.awt.Color.LIGHT_GRAY;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import static java.awt.geom.Path2D.WIND_NON_ZERO;
import java.awt.geom.Point2D;

/**
 *  A single tile in a square tile map.
 */
public class RectTile extends GridTile<RectTileCoord, RectTileDimension> {

    /**
     * Construct a square tile at the specified coordinates.
     *
     * @param coords the location of the tile.
     */
    public RectTile(RectTileCoord coords) {
        super(coords, null);
    }

    @Override
    public void draw(final Graphics2D g, final Point2D origin, final RectTileDimension dim, boolean highlight) {
        final GeneralPath p = new GeneralPath(WIND_NON_ZERO, 10);

        final double x = origin.getX();
        final double y = origin.getY();
        final double s = dim.getSide();

        p.moveTo(x, y);
        p.lineTo(x + s, y);
        p.lineTo(x + s, y + s);
        p.lineTo(x, y + s);
        p.lineTo(x, y);
        p.closePath();

        Color currentColor = g.getColor();

        if (highlight) {
            g.setColor(LIGHT_GRAY);
            g.fill(p);
        }

        g.setColor(currentColor);
        g.draw(p);

        super.draw(g, origin, dim, highlight);
    }
}
