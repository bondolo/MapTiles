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
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A map of tiles composed as a Cartesian grid of tiles.
 *
 * @param <T> Class of tiles in this map.
 * @param <C> Class of tile coordinates in this map.
 * @param <D> Class of tile dimensions in this map.
 */
public abstract class GridTileMap<T extends GridTile<C, D>, C extends GridTileCoord, D extends GridTileDimension> implements TileMap<T, C, D> {

    /**
     * Tiles for this map.
     */
    private final T tiles[][];

    /**
     * Construct a new map of tiles.
     *
     * @param tiles The tiles for this map.
     * @throws IllegalArgumentException if tiles is empty, the first row of tiles is empty or any of the
     * rows length does not match the length of the first row.
     * @throws NullPointerException if the tiles, or any row is null.
     */
    protected GridTileMap(T tiles[][]) {
        if (Objects.requireNonNull(tiles, "Null tiles").length == 0) {
            throw new IllegalArgumentException("empty tiles");
        }
        var firstRow = tiles[0];
        if (Objects.requireNonNull(firstRow, "Null tiles row").length == 0) {
            throw new IllegalArgumentException("empty tile row");
        }

        // clone and check the input arrays shape
        this.tiles = java.util.Arrays.stream(tiles)
                .peek(row -> {
                    if (Objects.requireNonNull(row, "null row").length != firstRow.length) {
                        throw new IllegalArgumentException("inconsistent row length");
                    }
                })
                .map(T[]::clone)
                .toArray(size -> {
                    @SuppressWarnings("unchecked")
                    T[][] map = (T[][]) Array.newInstance(tiles.getClass().getComponentType(), size);
                    return map;
                });
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
     * @throws IllegalArgumentException if x or y is not in range
     */
    public T getTile(int x, int y) {
        if ((x < 0) || (x >= tiles.length)) {
            throw new IllegalArgumentException("invalid X coordinate");
        }

        if ((y < 0) || (y >= tiles[x].length)) {
            throw new IllegalArgumentException("invalid Y coordinate");
        }

        return tiles[x][y];
    }

    @Override
    public Stream<T> tiles() {
        return Arrays.stream(tiles)
                .flatMap(row -> Arrays.stream(row));
    }

    @Override
    public Optional<T> getTile(C forLoc) {
        int x = forLoc.getX();
        int y = forLoc.getY();

        return Optional.of(getTile(x, y));
    }
}
