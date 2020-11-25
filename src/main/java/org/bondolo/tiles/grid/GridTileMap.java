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
import java.awt.geom.Point2D;

/**
 *  A map of tiles composed as a Cartesian grid of tiles.
 *
 * @param <T> Class of tiles in this map.
 * @param <C> Class of tile coordinates in this map.
 * @param <D> Class of tile dimensions in this map.
 */
public abstract class GridTileMap<T extends GridTile<C,D>, C extends GridTileCoord, D extends GridTileDimension> implements TileMap<T,C,D> {

    /**
     * Tiles for this map.
     */
    private final T tiles[][];

    public enum ORIGIN {
       TOP_LEFT,
       MIDDLE_LEFT,
       BOTTOM_LEFT,
       TOP_CENTER,
       MIDDLE_CENTER,
       BOTTOM_CENTER,
       TOP_RIGHT,
       MIDDLE_RIGHT,
       BOTTOM_RIGHT;
    }

    /**
     * Construct a new map of tiles.
     *
     * @param tiles The tiles for this map.
     */
    protected GridTileMap(final T tiles[][]) {
        assert tiles.length > 0;
        assert tiles[0].length > 0;
        for(T[] row : tiles) {
            assert row.length == tiles[0].length;
        }

        this.tiles = tiles.clone();
    }

    /**
     * Return the horizontal size of the tile map.
     *
     * @return the horizontal size of the tile map.
     */
    public int getXSize() {
        return tiles.length;
    }

    /**
     * Return the vertical size of the tile map.
     *
     * @return the vertical size of the tile map.
     */
    public int getYSize() {
        return tiles[0].length;
    }

    /**
     * Retrieve the tile associated with the specified location.
     *
     * @param x The horizontal index of the requested tile.
     * @param y The vertical index of the requested tile.
     * @return The tile.
     */
    public T getTile(final int x, final int y) {
        if((x < 0) || (x >= tiles.length)) {
            throw new IllegalArgumentException("invalid X coordinate");
        }

        if((y < 0) || (y >= tiles[x].length)) {
            throw new IllegalArgumentException("invalid Y coordinate");
        }

        return tiles[x][y];
    }

    @Override
    public T getTile(C forLoc) {
        int x = forLoc.getX();
        int y = forLoc.getY();

        return getTile(x, y);
    }

   /**
    * Returns the center point of the specified coordinate for the specified dimension.
    *
    * HACK : This does not work for triangles and non-regular hexagons.
    *
    * @param coord coordinate who's center point is desired.
    * @param dim dimension to be used in determining the point.
    * @return The point location.
    */
    @Override
   public Point2D coordToCentroidPoint(C coord, D dim) {
        Point2D origin = coordToPoint(coord, dim);

        origin.setLocation(origin.getX() + dim.getWidth() / 2, origin.getY() + dim.getHeight() / 2);

        return origin;
   }
}
