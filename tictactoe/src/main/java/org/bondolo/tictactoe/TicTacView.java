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
package org.bondolo.tictactoe;

import java.awt.BasicStroke;
import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_ROUND;
import java.awt.Graphics2D;
import static javax.swing.UIManager.getColor;

import org.bondolo.tiles.rect.RectMapView;
import org.bondolo.tiles.rect.RectTileDimension;

/**
 * A view of a Tic Tac Toe game.
 *
 * @serial exclude
 */
@SuppressWarnings("serial")
public class TicTacView extends RectMapView<TicTacTile> {

    public TicTacView(TicTacMap board, RectTileDimension scales[], int initialScale) {
        super(board, scales, initialScale);
    }

    @Override
    protected void drawMap(final Graphics2D g) {
        var dim = getDimension(getScale());
        double insety = dim.getHeight() / 10;
        double insetx = dim.getWidth() / 10;

        // draw grid.
        g.setColor(getColor("Separator.foreground"));
        g.setStroke(new BasicStroke((float) ((insetx + insety) / 3.0), CAP_ROUND, JOIN_ROUND));

        // column lines
        var columnTop = coordToPoint(map.getTile(0, 0).getCoord(), dim);
        var columnBottom = coordToPoint(map.getTile(0, map.getYSize() - 1).getCoord(), dim);
        double top = columnTop.getY() + insety;
        double bottom = columnBottom.getY() + dim.getHeight() - insety;
        for(int x = 1; x < map.getXSize(); x++) {
            var column = coordToPoint(map.getTile(x, 0).getCoord(), dim);
            g.drawLine((int) column.getX(), (int) top, (int) column.getX(), (int) bottom);
        }

        // row lines
        var rowLeft = coordToPoint(map.getTile(0, 0).getCoord(), dim);
        var rowRight = coordToPoint(map.getTile(map.getXSize() - 1, 0).getCoord(), dim);
        double left = rowLeft.getX() + insetx;
        double right = rowRight.getX() + dim.getWidth() - insetx;
        for(int y = 1; y < map.getXSize(); y++) {
            var row = coordToPoint(map.getTile(0,y).getCoord(), dim);
            g.drawLine((int) left, (int) row.getY(), (int) right, (int) row.getY());
        }

        // handles tile drawing.
        super.drawMap(g);
    }
}
