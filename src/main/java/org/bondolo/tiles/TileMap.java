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
package org.bondolo.tiles;

import java.awt.geom.Point2D;

/**
 *  A map composed of {@link Tile Tiles}.
 *
 * @param <T> Class of tiles in the map.
 * @param <C> Class of tile coordinates used in the map.
 * @param <D> Class of tile dimensions used for this tile class.
 */
public interface TileMap<T extends Tile<C>, C extends TileCoord, D extends TileDimension> {

    /**
     * Retrieves the tile associated with the specified coordinates.
     *
     * @param forTile The coordinates of the tile to be retrieved.
     * @return The tile.
     */
    T getTile(C forTile);

    /**
     * Return the coordinate associated with the specified point considering the
     * provided dimensions.
     *
     * @param point The point who's coordinate is desired.
     * @param dim The tile dimensions to use when mapping from point to
     * coordinate.
     * @return The coordinates of the tile for the specified point or
     * {@code null} if the point does not lie within a tile on the the map.
     */
    C pointToCoord(Point2D point, D dim);

    /**
     * Returns the point closest to the origin point for the tile at the
     * specified coordinates.
     *
     * @param coord The coordinate who's origin point is desired.
     * @param dim The tile dimensions to use when mapping from coordinates to
     * a point.
     * @return The point closest to the origin (0,0) for the tile at the
     * specified coordinate.
     */
    Point2D coordToPoint(C coord, D dim);

    /**
    * Returns the center point of the specified coordinate for the specified dimension.
    *
    * @param coord coordinate who's center point is desired.
    * @param dim dimension to be used in determining the point.
    * @return The point location.
    */
    Point2D coordToCentroidPoint(C coord, D dim);
}
