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
package org.bondolo.tiles.hex;

import org.bondolo.tiles.grid.GridTileMap;
import java.awt.geom.Point2D;

/**
 *  A Cartesian grid map composed of hexagonal tiles.
 *
 * @param <T> type of the hex tiles in the map.
 */
public class HexTileMap<T extends HexTile> extends GridTileMap<T, HexTileCoord, HexTileDimension> {

    /**
     * Construct a new map.
     *
     * @param tiles The tiles for this map.
     */
    public HexTileMap(T tiles[][]) {
        super(tiles);
    }

    @Override
    public HexTileCoord pointToCoord(Point2D point, HexTileDimension dim) {
        int sectionX = (int) (point.getX() / dim.getA());
        int offsetX =  (int) (point.getX() % dim.getA());
        int sectionY = (int) (point.getY() / (dim.getH() + dim.getSide()));
        int offsetY = (int) (point.getY() % (dim.getH() + dim.getSide()));

        double m = dim.getH() / dim.getR();
        int coord_x;
        int coord_y;

        if (0 == (sectionY % 2)) {
            // even row
            if (offsetY < (dim.getH() - offsetX * m)) {
                // left side
                coord_x = sectionX - 1;
                coord_y = sectionY - 1;
            } else if (offsetY < (-dim.getH() + offsetX * m)) {
                // right side
                coord_x = sectionX;
                coord_y = sectionY - 1;
            } else {
                // middle
                coord_x = sectionX;
                coord_y = sectionY;
            }
        } else {
            // odd row
            if (offsetX >= dim.getR()) {
                // right
                if (offsetY < (2 * dim.getH() - offsetX * m)) {
                    // top
                    coord_x = sectionX;
                    coord_y = sectionY - 1;
                } else {
                    // bottom
                    coord_x = sectionX;
                    coord_y = sectionY;
                }
            } else {
                // left
                if (offsetY < (offsetX * m)) {
                    // top
                    coord_x = sectionX;
                    coord_y = sectionY - 1;
                } else {
                    // bottom
                    coord_x = sectionX - 1;
                    coord_y = sectionY;
                }
            }
        }

        if ((coord_x < 0) || (coord_x >= getXSize()) ||
                (coord_y < 0) || (coord_y >= getYSize())) {
            // not on the map
            return null;
        } else {
            // turn it into a tile coordinate.
            return new HexTileCoord(coord_x, coord_y);
        }
    }

    @Override
    public Point2D coordToPoint(HexTileCoord coord, HexTileDimension dim) {
        int coord_x = coord.getX();
        int coord_y = coord.getY();

        double pixel_x = coord_x * 2 * dim.getR() + (coord_y & 1) * dim.getR();
        double pixel_y = coord_y * (dim.getH() + dim.getSide());

        return new Point2D.Double(pixel_x, pixel_y);
    }
}