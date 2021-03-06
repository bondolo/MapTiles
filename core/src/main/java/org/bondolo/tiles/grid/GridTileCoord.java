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

/**
 * Coordinates of a tile in a Cartesian grid.
 */
public abstract class GridTileCoord implements TileCoord {

    /**
     * horizontal location
     */
    private final int x;

    /**
     * vertical location.
     */
    private final int y;

    /**
     * Construct a new tile coordinate.
     *
     * @param x non-negative tile horizontal location.
     * @param y non-negative tile vertical location.
     * @throws IllegalArgumentException if x or y coordinates are negative
     */
    protected GridTileCoord(final int x, final int y) throws IllegalArgumentException {
        if (x < 0) {
            throw new IllegalArgumentException("x must be non-negative");
        }
        if (y < 0) {
            throw new IllegalArgumentException("y must be non-negative");
        }

        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object target) {
        if(target.getClass() == this.getClass()) {
            var likeMe = (GridTileCoord) target;

            return (likeMe.x == x) && (likeMe.y == y);
        }

        return super.equals(target);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
        return hash;
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        int reducingx = x;
        boolean first = true;
        do {
           result.insert(0, (char)('A' + (reducingx % 26) - (first ? 0 : 1)));
           reducingx /= 26;
           first = false;
        } while(0 != reducingx);

        result.append(y);

        return result.toString();
    }

    /**
     * The x value of the coordinate.
     *
     * @return x value of the coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * The y value of the coordinate.
     *
     * @return y value of the coordinate.
     */
    public int getY() {
        return y;
    }
}
