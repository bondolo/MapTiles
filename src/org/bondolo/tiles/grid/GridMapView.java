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
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;

/**
 * A view onto a cartesian grid map.
 * 
 * @param <M> The tile map class
 * @param <T> The class of tiles in the map.
 * @param <D> The class of tile dimensions.
 * @param <C> The class of tile coordinates used.
 */
public abstract class GridMapView<M extends GridTileMap<T, C, D>, T extends GridTile<C, D>, D extends GridTileDimension, C extends GridTileCoord> extends TileMapView<M, T, D, C> {

    public GridMapView(M map, final D scales[], int initialScale) {
        super(map, scales, initialScale);
    }

    /**
     * Draw the map at the current scale to the provided canvas.
     * 
     * @param g the target canvas.
     */
    protected void drawMap(final Graphics2D g) {
        final D dim = getDimension(getScale());

        // Draw the tiles.
        Shape currentclip = g.getClip();
        for (int y = 0; y < map.getYSize(); y++) {
            for (int x = map.getXSize() - 1; x >= 0; x--) {

                final T tile = map.getTile(x, y);

                final C coord = tile.getCoord();
                final Point2D origin = map.coordToPoint(coord, dim);

                if ((null == currentclip) ||
                        currentclip.intersects(origin.getX(), origin.getY(), dim.getWidth(), dim.getHeight())) {
                    g.setColor(getForeground());
                    g.setBackground(getBackground());
                    tile.draw(g, origin, dim, isSelected(tile));
                }
            }
        }
    }

    protected void drawTile(final Graphics2D g, final T tile) {
         final D dim = getDimension(getScale());
         final C coord = tile.getCoord();
         final Point2D origin = coordToPoint(coord);

         g.setBackground(getBackground());
         g.setColor(getForeground());

         tile.draw(g, origin, dim, isSelected(tile));
    }
}
