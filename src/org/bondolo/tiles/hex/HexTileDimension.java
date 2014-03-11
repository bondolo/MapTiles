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
package org.bondolo.tiles.hex;

import org.bondolo.tiles.grid.GridTileDimension;

/**
 * Tile dimension calculations for a hex tile.
 *
 */
public final class HexTileDimension extends GridTileDimension {
    /*
     *
     *        R
     *   __  |-|  ____
     *   |  /\     | H
     *   | /  \   _|__
     * B ||    |   | S
     *   ||    |  _|_
     *   | \  /    | H
     *   |  \/    _|
     *   +-----|
     *       A
     */
    
    private final double h;
    private final double r;
    private final double a;
    private final int b;

    /**
     * Construct a new hex tile dimension object for the specified scale.
     * 
     * @param side scale of the tile.
     */
    public HexTileDimension(final int side) {
        super(side);
        this.h = side / 2.0;   // same as tileSize * Math.sin(Math.toRadians(30));
        this.r = side * Math.cos(Math.PI / 6.0); // 30 degrees
        this.a = Math.scalb(r, 1); // same as 2 * r
        this.b = side * 2; // same as side + 2 * h;
    }
    
    @Override
    public String toString() {
        return super.toString() + "{" + String.format("s=%d h=%f r=%f a=%f b=%d", side, h, r, a, b) + "}";
    }
    
    public double getHeight() {
        return b;
    }

    public double getWidth() {
        return a;
    }
    
    public double area() {
        // per Wikipedia (rearranged to minimize propagated error)
        return 3.0 * Math.sqrt(3.0) * Math.pow(side, 2.0) / 2.0;
    }
    
    public double perimeter() {
        return 6.0 * side;
    }
    
    public double boundingArea() {
        return a * b;
    }
    
    public double boundingPerimeter() {
        return Math.scalb(r, 2) + (2 * b);
    }

    /**
     * @return the h
     */
    public double getH() {
        return h;
    }

    /**
     * @return the r
     */
    public double getR() {
        return r;
    }

    /**
     * @return the a
     */
    public double getA() {
        return a;
    }

    /**
     * @return the b
     */
    public int getB() {
        return b;
    }    
}
