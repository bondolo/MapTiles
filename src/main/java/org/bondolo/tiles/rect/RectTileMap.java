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

import org.bondolo.tiles.grid.GridTileMap;
import java.awt.geom.Point2D;

/**
 *  A cartesian grid map composed of square tiles.
 */
public class RectTileMap extends GridTileMap<RectTile, RectTileCoord, RectTileDimension> {
    
    /**
     * Construct a new map.
     *
     * @param tiles
     */
    public RectTileMap(RectTile tiles[][]) {
        super(tiles);
    }

    @Override
    public RectTileCoord pointToCoord(Point2D point, RectTileDimension dim) {
        final int coord_x = (int) (point.getX() / dim.getSide());
        final int coord_y = (int) (point.getY() / dim.getSide());

        if ((coord_x < 0) || (coord_x >= getXSize()) ||
                (coord_y < 0) || (coord_y >= getYSize())) {
            return null;
        } else {
            return new RectTileCoord(coord_x, coord_y);
        }
    }

    @Override
    public Point2D coordToPoint(RectTileCoord coord, RectTileDimension dim) {
        final int coord_x = coord.getX();
        final int coord_y = coord.getY();

        final double pixel_x = coord_x * dim.getWidth();
        final double pixel_y = coord_y * dim.getHeight();

        return new Point2D.Double(pixel_x, pixel_y);
    }
}