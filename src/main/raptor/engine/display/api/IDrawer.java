package raptor.engine.display.api;

import java.awt.Graphics2D;

/**
 * Abstraction of rendering object.
 * <p> Example: {@link Graphics2D} </p>
 */
public interface IDrawer {
	public void drawOval(final int x, final int y, final int sizeX, final int sizeY);
	public void drawRect(final int x, final int y, final int width, final int height);
	public void clear(int x, int y, int width, int height);
}
