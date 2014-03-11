/*
 * Copyright (c) 2011 Mike Duigou
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
package org.bondolo.tiles.hex;

import org.bondolo.tiles.grid.GridTile;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 *  A single tile in a hex tile map.
 *
 */
public class HexTile extends GridTile<HexTileCoord, HexTileDimension> {

    /**
     * Construct a hex tile at the specified coordinates.
     *
     * @param coord the location of the tile.
     */
    public HexTile(HexTileCoord coord) {
        this(coord, null);
    }

    public HexTile(HexTileCoord coord, String label) {
        super(coord, label);
    }

    @Override
    public void draw(final Graphics2D g, final Point2D origin, final HexTileDimension dim,boolean highlight) {
        final GeneralPath p = new GeneralPath(GeneralPath.WIND_NON_ZERO, 16);
        final double x = origin.getX();
        final double y = origin.getY();

        p.moveTo(x, y + dim.getH());
        p.lineTo(x + dim.getR(), y);
        p.lineTo(x + dim.getA(), y + dim.getH());
        p.lineTo(x + dim.getA(), y + dim.getH() + dim.getSide());
        p.lineTo(x + dim.getR(), y + dim.getB());
        p.lineTo(x, y + dim.getH() + dim.getSide());
        p.closePath();

        if (highlight) {
            g.setColor(Color.LIGHT_GRAY);
            g.fill(p);
        }

        g.setColor(Color.black);
        g.draw(p);

        super.draw(g, origin, dim, highlight);
    }
}