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
package org.bondolo.tiles;

/**
 * Unitless dimensions of a {@link Tile}.
 */
public interface TileDimension {

    /**
     * The getHeight of the map tile.
     *
     * @return map tile getHeight as a unitless dimension.
     */
    double getHeight();

    /**
     * The getWidth of the map tile.
     *
     * @return map tile getWidth as a unitless dimension.
     */
    double getWidth();

    /**
     * The area of the map tile.
     *
     * @return map tile area as a unitless dimension.
     */
    double area();

    /**
     * The perimeter of the map tile.
     *
     * @return map tile perimeter as a unitless dimension.
     */
    double perimeter();

    /**
     * The area of the tile bounding box (smallest box which completely
     * encompasses the tile).
     *
     * @return map tile bounding box area as a unitless dimension.
     */
    double boundingArea();

    /**
     * The perimeter of the tile bounding box (smallest box which completely
     * encompasses the tile).
     *
     * @return map tile bounding box perimeter as a unitless dimension.
     */
    double boundingPerimeter();
}
