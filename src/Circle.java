/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.Color;
import java.awt.Graphics;

/**
 * A basic game object which acts as the ball
 * 
 */
public class Circle extends GameObj {

	// value fields for ball
	public static final int SIZE = 20;
	public static int INIT_POS_X = 170;
	public static final int INIT_POS_Y = 170;
	public static final int INIT_VEL_X = 2;
	public static final int INIT_VEL_Y = 3;
	// variable determining if game is still running
	public boolean fin = true;

	// constructor for ball
	public Circle(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE,
				courtWidth, courtHeight);
	}

	@Override
	public void draw(Graphics g) {
		// sets color of black to ball everytime
		g.setColor(Color.BLACK);
		// draws ball depending on whether game is still running
		if (fin) {
			g.fillOval(pos_x, pos_y, width, height);
		}
	}

}