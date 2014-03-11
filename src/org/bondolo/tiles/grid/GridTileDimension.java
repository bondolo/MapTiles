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
package org.bondolo.tiles.grid;

import org.bondolo.tiles.*;

/**
 * Dimension of a tile in a cartesian grid.
 */
public abstract class GridTileDimension implements TileDimension {

    /**
     * the length of a side.
     */
    protected final int side;

    /**
     * Construct a new dimension for a grid of tiles.
     *
     * @param side length of a side.
     */
    protected GridTileDimension(int side) {
        assert side > 0;
        
        this.side = side;
    }

    /**
     * Return the length of a side.
     * 
     * @return the length of a side
     */
    public int getSide() {
        return side;
    }

    /**
     * The getWidth of the bounding box for this tile.
     * 
     * @return getWidth of the bounding box for this tile.
     */
    public abstract double getWidth();

    /**
     * The getHeight of the bounding box for this tile.
     *
     * @return getHeight of the bounding box for this tile.
     */
    public abstract double getHeight();
}
