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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import javax.swing.JComponent;

/**
 * A view onto a {@link TimeMap map} of {@link Tile tiles}. The methods of this
 * object should only be called on the UI event thread and are not otherwise thread safe
 *
 * @param <M> The class of the tile map.
 * @param <T> The class of a tile in the map.
 * @param <D> The class of a tile dimension.
 * @param <C> The class of a tile coordinate.
 */
@SuppressWarnings("serial")
public abstract class TileMapView<M extends TileMap<T, C, D>, T extends Tile<C> & TileView<D>, D extends TileDimension, C extends TileCoord> extends JComponent {

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
    private final HashSet<T> selection = new HashSet<>();

    /**
     * Construct a new map view.
     *
     * @param map The map for this view.
     * @param scales The tile dimensions (scales) defined for this view.
     * @param initialScale The initial scale value to use for this view.
     */
    protected TileMapView(M map, final D scales[], int initialScale) {
        super();

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
        assert g instanceof Graphics2D : "graphics environment should be a Graphics2D";
        Graphics2D g2 = (Graphics2D) g;

        // Turn on anti-aliasing.
        RenderingHints hints = new RenderingHints(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        g2.addRenderingHints(hints);

        // Wipe the old.
        super.paintComponent(g);

        drawMap(g2);
    }

    /**
     * The current scale value used for this view.
     * @return the scale
     */
    public int getScale() {
        return scale;
    }

    /**
     * Set the current scale being used for this view.
     *
     * @param scale
     * @return the scale value set.
     */
    public int setScale(int scale) {
        assert (scale >= 0) && (scale < getScalesCount());

        this.scale = scale;

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
     * Return an array containing the tile dimensions (scales) defined for this
     * view.
     *
     * @return an array containing the tile dimensions (scales) defined for this
     * view.
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
     * Return the tile coordinate associated with the specified point in 2D
     * space.
     *
     * @param point The pixel position to translate.
     * @return The tile coordinates.
     */
    public final C pointToCoord(Point2D point) {
        return map.pointToCoord(point, getDimension(scale));
    }

    /**
     * Returns the point closest to the origin (0,0) for the tile at the
     * specified coordinates.
     *
     * @param coord The coordinate who's origin point is desired.
     * @return The point closest to the origin (0,0) for the tile at the
     * specified coordinate.
     */
    public final Point2D coordToPoint(C coord) {
        return map.coordToPoint(coord, getDimension(scale));
    }

    /**
     * Add the specified tile to the selection.
     *
     * @param tile the tile to be selected.
     */
    public void addToSelection(T tile) {
        if (selection.add(tile)) {
            final D dim = getDimension(scale);
            Point2D origin = map.coordToPoint(tile.getCoord(), dim);
            repaint((int) origin.getX(), (int) origin.getY(), (int) dim.getWidth(), (int) dim.getHeight());
        }
    }

    /**
     * Add the specified tile to the selection.
     *
     * @param tile the tile to be selected.
     */
    public void removeFromSelection(T tile) {
        if (selection.remove(tile)) {
            final D dim = getDimension(scale);
            Point2D origin = map.coordToPoint(tile.getCoord(), dim);
            repaint((int) origin.getX(), (int) origin.getY(), (int) dim.getWidth(), (int) dim.getHeight());
        }
    }

    /**
     * Clear the slection for this view.
     */
    public void clearSelection() {
        final Set<T> clear = Collections.emptySet();
        setSelection(clear);
    }

    public void setSelection(final Set<? extends T> newSelection) {
        boolean selectionChanged = !selection.equals(newSelection);

        if (selectionChanged) {
            selection.clear();
            selection.addAll(newSelection);
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
     * @return An iterator over the tiles in the selection. The iterator is not
     * thread safe. Any changes to the selection will cause the iterator to
     * fail.
     */
    public Iterator<T> selectionIterator() {
        return selection.iterator();
    }

    /**
     * Draw the map at the current scale into the provided graphics environment.
     *
     * @param g the destination graphics environment for the drawing
     */
    protected abstract void drawMap(final Graphics2D g);

    /**
     * Draw the map at the current scale into the provided graphics environment.
     *
     * @param g the destination graphics environment for the drawing
     * @param tile the tile to draw.
     */
    protected abstract void drawTile(final Graphics2D g, T tile);
}
