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
package org.bondolo.tiles.hex;

import java.awt.Dimension;
import org.bondolo.tiles.TileMapView;

/**
 * A view onto a map composed of hex tiles.
 */
@SuppressWarnings("serial")
public class HexMapView extends TileMapView<HexTileMap, HexTile, HexTileDimension, HexTileCoord> {

    /**
     * Construct a new map view.
     *
     * @param map The map.
     * @param scales The scales for this map.
     * @param initialScale The initial scale.
     */
    public HexMapView(HexTileMap map, HexTileDimension scales[], int initialScale) {
        super(map, scales, initialScale);
    }

    @Override
    public Dimension getPreferredSize() {
        var dim = getDimension(getScale());

        return new Dimension(
                (int) (dim.getA() * map.getXSize() + dim.getR()) + 2,
                (int) ((dim.getH() + dim.getSide()) * map.getYSize() + dim.getH() + 1));
    }
}
