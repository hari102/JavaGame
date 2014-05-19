/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.Color;
import java.awt.Graphics;

/**
 * A basic game object displayed as a black square, starting in the upper left
 * corner of the game court.
 * 
 */
public class Square extends GameObj {
	private static int SIZE = 50;
	private static int SIZE2 = 20;
	private static final int INIT_X = 150;
	private static final int INIT_Y = 500;
	private static final int INIT_VEL_X = 0;
	private static final int INIT_VEL_Y = 0;

	/**
	 * Note that because we don't do anything special when constructing a
	 * Square, we simply use the superclass constructor called with the correct
	 * parameters
	 */
	public Square(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, SIZE, SIZE2, courtWidth,
				courtHeight);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(pos_x, pos_y, width, height);
	}

	public void redraw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(pos_x, pos_y, width + 40, height);
	}

}
