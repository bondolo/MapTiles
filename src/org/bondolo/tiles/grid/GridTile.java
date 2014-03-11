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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * A single tile in a cartesian grip map.
 *
 * @param <C> Coordinate class for this class.
 * @param <D> Dimension class for this class.
 */
public abstract class GridTile<C extends GridTileCoord, D extends GridTileDimension> implements Tile<C>, TileView<D> {

    /**
     * Coordinates of this tile.
     */
    protected final C coord;

    /**
     * Label for this tile.
     */
    protected final String label;
    
    /**
     * Construct a new grid tile.
     * 
     * @param coord
     * @param label
     */
    protected GridTile(C coord, String label) {
        assert coord != null;
        
        this.coord = coord;
        this.label = label;
    }
    
    @Override
    public boolean equals(Object target) {
        if(target.getClass() == this.getClass()) {
            GridTile<C,D> likeMe = (GridTile<C,D>) target;
            
            return coord.equals(likeMe.coord);
        }
        
        return super.equals(target);
    }

    @Override
    public int hashCode() {
        return coord.hashCode();
    }
    
    public C getCoord() {
        return coord;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    public void draw(Graphics2D g, Point2D origin, D dim, boolean highlight) {
        String drawing = (null != getLabel()) ? getLabel() : coord.toString();

        // XXX This is too much work to be doing for each tile
        Font font = g.getFont().deriveFont((float)(dim.side / 3.0));
        if(highlight) {
            font = font.deriveFont(Font.BOLD);
        }
        g.setFont(font);
        
        FontMetrics fm = g.getFontMetrics();
        
        double drawx = origin.getX() + (dim.getWidth() - fm.stringWidth(drawing)) / 2.0;
        double drawy = origin.getY() + (dim.getHeight() + fm.getAscent()) / 2.0;

        g.drawString( drawing, (float) drawx, (float) drawy);
    }
}
