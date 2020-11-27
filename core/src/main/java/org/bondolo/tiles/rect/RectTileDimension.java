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

import org.bondolo.tiles.grid.GridTileDimension;

/**
 * Tile dimension calculations for a square tile.
 */
public class RectTileDimension extends GridTileDimension {

    /**
     *         s
     *   +-----------+
     *   |           |
     *   |           |
     *   |           | s
     *   |           |
     *   |           |
     *   +-----------+
     */

    /**
     * Construct a new square tile dimension object for the specified scale.
     * 
     * @param side scale of the tile.
     */
    public RectTileDimension(final int side) {
        super(side);
    }
    
    @Override
    public String toString() {
        return super.toString() + "{" + String.format("s=%d", side) + "}";
    }
    
    @Override
    public double getHeight() {
        return side;
    }

    @Override
    public double getWidth() {
        return side;
    }
    
    @Override
    public double area() {
        return side * side;
    }
    
    @Override
    public double perimeter() {
        return 4.0 * side;
    }
    
    @Override
    public double boundingArea() {
        return area();
    }
    
    @Override
    public double boundingPerimeter() {
        return perimeter();
    }
}
