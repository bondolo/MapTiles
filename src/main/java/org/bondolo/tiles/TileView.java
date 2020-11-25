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

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * A view onto a {@link TimeMap map} of {@link Tile tiles}. The methods of this
 * object should only be called on the UI event thread and are not otherwise thread safe
 *
 * @param <D> Class of dimensions for this tile view class.
 */
public interface TileView<D extends TileDimension> {

    /**
     * Draw the tile.
     *
     * @param g The graphics environment to draw into.
     * @param origin The origin point at which to draw the tile.
     * @param dim The scaling dimensions to use for tile drawing.
     * @param highlight Draw the tile highlighted.
     */
    void draw(Graphics2D g, Point2D origin, D dim, boolean highlight);
}
