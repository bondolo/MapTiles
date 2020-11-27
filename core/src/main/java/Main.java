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
import static java.awt.EventQueue.invokeLater;
import org.bondolo.tiles.hex.HexMapView;
import org.bondolo.tiles.hex.HexTile;
import org.bondolo.tiles.hex.HexTileCoord;
import org.bondolo.tiles.hex.HexTileDimension;
import org.bondolo.tiles.hex.HexTileMap;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.stream.IntStream;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Mini-demo for tiles. Click on tiles to see the tile highlighted
 */
public final class Main {

    /**
     * Size of the tile grid
     */
    private static final int MAP_SIZE = 17;

    /**
     * Tile size scales available
     */
    private static final int TILE_SCALES = 5;
    /**
     * Initial tile size
     */
    private static final int INITIAL_SCALE = 0;
    /**
     * base scaling factor for tile size
     */
    private static final int BASE_SCALE_FACTOR = 5;

    /**
     * Tile scales, powers of 2 starting at {@link #BASE_SCALE_FACTOR}
     */
    private final static HexTileDimension TILE_SCALE_DIMENSIONS[]
            = IntStream.range(0, TILE_SCALES)
                    .map(scale -> 1 << (scale + BASE_SCALE_FACTOR))
                    .mapToObj(HexTileDimension::new)
                    .toArray(HexTileDimension[]::new);

    /**
     * no instances
     */
    private Main() {

    }

    /**
     * @param args the command line arguments (ignored)
     */
    public static void main(String[] args) {

        var tiles = new HexTile[MAP_SIZE][MAP_SIZE];

        for (var x = 0; x < tiles.length; x++) {
            for (var y = 0; y < tiles[x].length; y++) {
                tiles[x][y] = new HexTile(new HexTileCoord(x, y), "(" + x + "," + y + ")");
            }
        }

        var map = new HexTileMap<HexTile>(tiles);
        var view = new HexMapView<HexTile>(map, TILE_SCALE_DIMENSIONS, INITIAL_SCALE);
        view.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Point2D click = new Point2D.Double((double) e.getX(), (double) e.getY());
                System.out.println("Click at " + click);
                var coord = view.pointToCoord(click);

                if (null != coord) {
                    var origin = view.coordToPoint(coord);
                    var dim = view.getDimension(view.getScale());
                    var tile = map.getTile(coord);
                    var wasSelected = view.isSelected(tile);
                    if (!wasSelected) {
                        view.addToSelection(tile);
                    } else {
                        view.removeFromSelection(tile);
                    }
                    System.out.println((wasSelected ? "Deselected" : "Selected") + " tile " + tile);
                    tile.draw((Graphics2D) view.getGraphics(), origin, dim, !wasSelected);
                }
            }
        });

        var f = new JFrame("Map");
        f.setTitle("Map Tiles Demonstration (click on tiles)");
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

        f.getContentPane().add("Center", view);

        invokeLater(() -> {
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
