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

import static java.awt.EventQueue.isDispatchThread;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import javax.swing.JComponent;

/**
 * A view onto a {@link TileMap map} of {@link Tile tiles}. The view has associated dimensioned scales for the
 * view, a current scale and the current selection of tiles within the view.
 *
 * @param <M> The class of the tile map.
 * @param <T> The class of a tile in the map.
 * @param <D> The class of a tile dimension.
 * @param <C> The class of a tile coordinate.
 */
@SuppressWarnings("serial")
public abstract class TileMapView<M extends TileMap<T, C, D>, T extends Tile<C> & TileView<D>, D extends TileDimension, C extends TileCoord> extends JComponent {

    /**
     * Rendering hints we will use for drawing.
     *
     * Turn on anti-aliasing.
     */
    private static final RenderingHints HINTS = new RenderingHints(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
    /**
     * The map associated with this view.
     */
    protected final M map;
    /**
     * The view scales associated with this views.
     */
    private final D scales[];
    /**
     * The current view scale.
     */
    private int scale;
    /**
     * The current selection in this view.
     */
    private final Set<T> selection = new HashSet<>();

    /**
     * Construct a new map view.
     *
     * @param map The map for this view.
     * @param scales The tile dimensions (scales) defined for this view.
     * @param initialScale The initial scale value to use for this view.
     * @throws IllegalArgumentException if scales is empty or initial scale is invalid
     * @throws NullPointerException if map or scales are null
     */
    protected TileMapView(M map, final D scales[], int initialScale) {
        this.map = Objects.requireNonNull(map, "Null map");
        this.scales = Objects.requireNonNull(scales, "Null scales").clone();
        this.scale = initialScale;

        if (scales.length == 0) {
            throw new IllegalArgumentException("empty scales");
        }

        if ((initialScale < 0) || (initialScale >= scales.length)) {
            throw new IllegalArgumentException("Invalid initialScale");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        assert isDispatchThread() : "Drawing on wrong thread " + Thread.currentThread();
        assert g instanceof Graphics2D : "graphics environment should be a Graphics2D";
        // Wipe the old.
        super.paintComponent(g);

        var g2 = (Graphics2D) g;
        g2.addRenderingHints(HINTS);

        drawMap(g2);
    }

    /**
     * The current scale value used for this view.
     *
     * @return the scale
     */
    public int getScale() {
        return scale;
    }

    /**
     * Set the scale being used for this view.
     *
     * @param scale the scale being used for this view. Must be {@code 0} – {@link #getScalesCount()}-1
     * @return the scale value set.
     * @throws IllegalArgumentException if the scale is not in range
     */
    public int setScale(int scale) throws IllegalArgumentException {
        assert isDispatchThread() : "Drawing on wrong thread " + Thread.currentThread();
        if ((scale < 0) || (scale >= getScalesCount())) {
            throw new IllegalArgumentException("Illegal scale value (0-" + (getScalesCount() - 1) + ") : " + scale);
        }

        this.scale = scale;

        repaint();

        return scale;
    }

    /**
     * Return he number of tile dimensions (scales) defined for this view.
     *
     * @return The number of tile dimensions (scales) defined for this view.
     */
    public int getScalesCount() {
        return scales.length;
    }

    /**
     * Return an array containing the tile dimensions (scales) defined for this view.
     *
     * @return an array containing the tile dimensions (scales) defined for this view.
     */
    public D[] getScales() {
        return scales.clone();
    }

    /**
     *
     * @param forScale The desired scale.
     *
     * @return The tile dimension for the specified scale.
     */
    public D getDimension(int forScale) {
        assert (forScale >= 0) && (forScale < scales.length);

        return scales[forScale];
    }

    /**
     * Return the tile coordinate associated with the specified point in 2D space.
     *
     * @param point The pixel position to translate.
     * @return The tile coordinates.
     */
    public final C pointToCoord(Point2D point) {
        return pointToCoord(point, getDimension(scale));
    }

    /**
     * Returns the point closest to the origin (0,0) for the tile at the specified coordinates.
     *
     * @param coord The coordinate who's origin point is desired.
     * @return The point closest to the origin (0,0) for the tile at the specified coordinate.
     */
    public final Point2D coordToPoint(C coord) {
        return coordToPoint(coord, getDimension(scale));
    }

    /**
     * Add the specified tile to the selection.
     *
     * @param tile the tile to be selected.
     */
    public void addToSelection(T tile) {
        assert isDispatchThread() : "Drawing on wrong thread " + Thread.currentThread();
        if (selection.add(tile)) {
            // repaint tile if added
            var dim = getDimension(scale);
            var origin = coordToPoint(tile.getCoord(), dim);
            repaint((int) origin.getX(), (int) origin.getY(), (int) dim.getWidth(), (int) dim.getHeight());
        }
    }

    /**
     * Add the specified tile to the selection.
     *
     * @param tile the tile to be selected.
     */
    public void removeFromSelection(T tile) {
        assert isDispatchThread() : "Drawing on wrong thread " + Thread.currentThread();
        if (selection.remove(tile)) {
            // repaint tile if removed
            var dim = getDimension(scale);
            var origin = coordToPoint(tile.getCoord(), dim);
            repaint((int) origin.getX(), (int) origin.getY(), (int) dim.getWidth(), (int) dim.getHeight());
        }
    }

    /**
     * Clear the selection for this view.
     */
    public void clearSelection() {
        setSelection(Set.of());
    }

    /**
     * Replace the current view selection with the provided selection of tiles.
     *
     * @param replacement The replacement view selection
     * @throws NullPointerException if the replacement set is null
     */
    public void setSelection(Set<? extends T> replacement) {
        assert isDispatchThread() : "Drawing on wrong thread " + Thread.currentThread();
        boolean selectionChanged = !selection.equals(Objects.requireNonNull(replacement, "null replacement selection"));

        if (selectionChanged) {
            selection.clear();
            selection.addAll(replacement);
            repaint();
        }
    }

    /**
     * Returns {@code true} if the specified tile is selected.
     *
     * @param tile The tile of interest.
     * @return {@code true} if the tile is selected.
     */
    public boolean isSelected(T tile) {
        return selection.contains(tile);
    }

    /**
     * Returns an iterator over the tiles in the selection.
     *
     * @return An iterator over the tiles in the selection. The iterator is not thread safe. Any changes to
     * the selection will cause the iterator to fail.
     */
    public Iterator<T> selectionIterator() {
        return selection.iterator();
    }

    /**
     * Returns a stream of the tiles in the selection.
     *
     * @return a stream of the tiles in the selection.
     */
    public Stream<T> selection() {
        return selection.stream();
    }

    /**
     * Draw the map at the current scale into the provided graphics environment.
     *
     * @param g2 the destination graphics environment for the drawing
     */
    protected void drawMap(Graphics2D g2) {
        map.tiles().forEach(tile -> drawTile(g2, tile));
    }

    /**
     * Draw a map at the current scale into the provided graphics environment.
     *
     * @param g the destination graphics environment for the drawing
     * @param tile the tile to draw.
     */
    protected void drawTile(Graphics2D g, T tile) {
        var coord = tile.getCoord();
        var origin = coordToPoint(coord);
        var dim = getDimension(getScale());

        var currentclip = g.getClip();
        if ((null == currentclip)
                || currentclip.intersects(origin.getX(), origin.getY(), dim.getWidth(), dim.getHeight())) {
            g.setBackground(getBackground());
            g.setColor(getForeground());

            tile.draw(g, origin, dim, isSelected(tile));
        }
    }

    /**
     * Return the coordinate associated with the specified point considering the
     * provided dimensions.
     *
     * @param point The point who's coordinate is desired.
     * @param dim The tile dimensions to use when mapping from point to
     * coordinate.
     * @return The coordinates of the tile for the specified point or
     * {@code null} if the point does not lie within a tile on the the map.
     */
    public abstract C pointToCoord(Point2D point, D dim);

    /**
     * Returns the point closest to the origin point for the tile at the
     * specified coordinates.
     *
     * @param coord The coordinate who's origin point is desired.
     * @param dim The tile dimensions to use when mapping from coordinates to
     * a point.
     * @return The point closest to the origin (0,0) for the tile at the
     * specified coordinate.
     */
    public abstract Point2D coordToPoint(C coord, D dim);

    /**
     * Returns the center point of the specified coordinate for the specified dimension.
     *
     * HACK : This does not work for triangles and non-regular hexagons.
     *
     * @param coord coordinate who's center point is desired.
     * @param dim dimension to be used in determining the point.
     * @return The point location.
     */
    public Point2D coordToCentroidPoint(C coord, D dim) {
        var origin = coordToPoint(coord, dim);

        origin.setLocation(origin.getX() + dim.getWidth() / 2, origin.getY() + dim.getHeight() / 2);

        return origin;
    }
}
