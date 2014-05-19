import java.awt.Color;
import java.awt.Graphics;

public class Brick extends GameObj {

	// value fields
	final static int SIZE = 50;
	final static int SIZE2 = 20;
	static int INIT_X = 120;
	static int INIT_Y = 160;
	int life = 1;

	// value if a brick has been hit
	// public boolean hit = false;

	// constructor for Brick
	public Brick(int courtWidth, int courtHeight) {
		super(0, 0, INIT_X, INIT_Y, SIZE, SIZE2, courtWidth, courtHeight);
	}

	// draw method with graphics context
	@Override
	public void draw(Graphics g) {
		// array that utilizes different colors for bricks
		Color[] colorlist = new Color[8];

		// initializes the brick colors
		for (int i = 0; i < 7; i++) {
			colorlist[i] = Color.getHSBColor(50, 75, 187 - i);
		}

		// draws appropriate 3d rectangles
		if (life > 0) {

			g.setColor(Color.BLACK);

			g.draw3DRect(pos_x, pos_y, width, height, true);

			g.setColor(colorlist[life - 1]);

			g.fill3DRect(pos_x, pos_y, width, height, true);

		}
	}

}
