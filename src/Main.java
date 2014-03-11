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
import org.bondolo.tiles.hex.HexMapView;
import org.bondolo.tiles.hex.HexTile;
import org.bondolo.tiles.hex.HexTileCoord;
import org.bondolo.tiles.hex.HexTileDimension;
import org.bondolo.tiles.hex.HexTileMap;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Mini-demo for tiles.
 */
public final class Main {

    private static final int MAP_SIZE = 17;
    private static final int TILE_SCALES = 5;
    private static final int INITIAL_SCALE = 0;
    private static final int BASE_SCALE_FACTOR = 5;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HexTileDimension scales[] = new HexTileDimension[TILE_SCALES];
        for (int scale = 0; scale < scales.length; scale++) {
            scales[scale] = new HexTileDimension(1 << (scale + BASE_SCALE_FACTOR));
            System.out.println(scales[scale]);
        }

        HexTile tiles[][] = new HexTile[MAP_SIZE][MAP_SIZE];

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {

                tiles[x][y] = new HexTile(new HexTileCoord(x, y), "(" + x + "," + y + ")");
            }
        }

        final HexTileMap map = new HexTileMap(tiles);
        final HexMapView view = new HexMapView(map, scales, INITIAL_SCALE);

        final JFrame f = new JFrame("Map");

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                f.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                view.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Point2D click = new Point2D.Double((double) e.getX(), (double) e.getY());
                        HexTileCoord coord = view.pointToCoord(click);

                        if (null != coord) {
                            final Point2D origin = view.coordToPoint(coord);
                            final HexTileDimension dim = view.getDimension(view.getScale());
                            final HexTile tile = map.getTile(coord);
                            tile.draw((Graphics2D) view.getGraphics(), origin, dim, true);
                        }
                    }
                });

                f.getContentPane().add("Center", view);
                f.pack();
                f.setVisible(true);
            }
        });
    }
}
