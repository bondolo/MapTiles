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
package org.bondolo.tiles.tri;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import org.bondolo.tiles.TileMapView;

/**
 * A view onto a map composed of triangle tiles.
 *
 * @param <TT> type of tiles in the map
 */
@SuppressWarnings("serial")
public class TriMapView<TT extends TriTile> extends TileMapView<TriTileMap<TT>, TT, TriTileDimension, TriTileCoord> {

    /**
     * Construct a new map view.
     *
     * @param map The map.
     * @param scales The scales for this map.
     * @param initialScale The initial scale.
     */
    public TriMapView(TriTileMap<TT> map, TriTileDimension scales[], int initialScale) {
        super(map, scales, initialScale);
    }

    @Override
    public Dimension getPreferredSize() {
        var dim = getDimension(getScale());

        return new Dimension(
                (int) (dim.getWidth() * (map.getXSize() + 1) ) / 2 + 1,
                (int) (dim.getHeight() * map.getYSize()) + 2);
    }


    @Override
    public TriTileCoord pointToCoord(Point2D point, TriTileDimension dim) {
        int section_x = (int) (point.getX() / (dim.getSide() / 2.0));
        int sectionPxl_x = (int) (point.getX() % (dim.getSide() / 2.0));
        int coord_y = (int) (point.getY() / dim.getHeight());
        int sectionPxl_y = (int) (point.getY() % dim.getHeight());
        double m = dim.getHeight() / (dim.getSide() / 2.0);

        int coord_x;

        int yforx = (int) (sectionPxl_x * m);

        if((0 != (section_x % 2) ^ (0 != (coord_y % 2)))) {
            // points down
            if(sectionPxl_y > yforx) {
                coord_x = section_x -1;
            } else {
                coord_x = section_x;
            }
        } else {
            // points up
            if(sectionPxl_y < (dim.getHeight() - yforx)) {
                coord_x = section_x - 1;
            } else {
                coord_x = section_x;
            }
        }

        if ((coord_x < 0) || (coord_x >= map.getXSize()) ||
                (coord_y < 0) || (coord_y >= map.getYSize())) {
            return null;
        } else {
            return new TriTileCoord(coord_x, coord_y);
        }    }

    @Override
    public Point2D coordToPoint(TriTileCoord coord, TriTileDimension dim) {
        int coord_x = coord.getX();
        int coord_y = coord.getY();
        double pixel_x = coord_x * (dim.getWidth() / 2.0);
        double pixel_y = coord_y * dim.getHeight();

        return new Point2D.Double(pixel_x, pixel_y);
    }
}
