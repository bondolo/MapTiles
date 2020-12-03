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

import java.util.Optional;
import java.util.stream.Stream;

/**
 * A map composed of {@link Tile Tiles}.
 *
 * @param <T> Class of tiles in the map.
 * @param <C> Class of tile coordinates used in the map.
 * @param <D> Class of tile dimensions used for this tile class.
 */
public interface TileMap<T extends Tile<C>, C extends TileCoord, D extends TileDimension> {

    /**
     * Retrieves the tile associated with the specified coordinates.
     *
     * @implSpec The default implementation is <strong>O</strong>(n)
     *
     * @param forTile The coordinates of the tile to be retrieved.
     * @return The tile or empty result if the coordinates do match any tile in the map.
     */
    default Optional<T> getTile(C forTile) {
        return tiles()
                .filter(t -> t.getCoord().equals(forTile))
                .findFirst();
    }

    /**
     * Returns a stream of the tiles in the map.
     *
     * @return a stream of the tiles in the map.
     */
    Stream<T> tiles();
}
