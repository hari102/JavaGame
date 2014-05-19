/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * GameCourt This class holds the primary game logic of how different objects
 * interact with one another.
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the Black Square, keyboard control
	private Square square;
	// the first ball
	private Circle snitch;
	// brick array
	private Brick[][] brick = new Brick[6][8];
	// the second ball
	private Circle bullet;

	// whether the game is running
	public boolean playing = false;
	// current status text
	private JLabel status;

	// Game constants
	private static final int COURT_WIDTH = 300;
	private static final int COURT_HEIGHT = 500;
	private static final int SQUARE_VELOCITY = 8;
	// Update interval for timer in milliseconds
	private static final int INTERVAL = 35;
	// score
	private int Score = 0;
	// current number of balls in play
	private int balls = 1;
	// power up text
	private String power = "none";
	private int lives = 3;

	public GameCourt(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			// first class function for use
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		// starts the timer
		timer.start();

		// Enable keyboard focus on the court area
		setFocusable(true);

		// this key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly.
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					square.v_x = -SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					square.v_x = SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
					square.v_y = 0;
				else if (e.getKeyCode() == KeyEvent.VK_UP)
					square.v_y = 0;
			}

			public void keyReleased(KeyEvent e) {
				square.v_x = 0;
				square.v_y = 0;
			}
		});
		// initialization of variable
		this.status = status;
	}

	/**
	 * (Re-)set the state of the game to its initial state.
	 */
	@SuppressWarnings("static-access")
	public void reset() {
		balls = 1;
		Score = 0;
		power = "none";

		square = new Square(COURT_WIDTH, COURT_HEIGHT);
		bullet = new Circle(COURT_WIDTH, COURT_HEIGHT);
		bullet.pos_y = 170;

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {

				snitch = new Circle(COURT_WIDTH, COURT_HEIGHT);

				brick[i][j].INIT_X = i * 50;
				brick[i][j].INIT_Y = j * 20;

				brick[i][j] = new Brick(COURT_WIDTH, COURT_HEIGHT);

				brick[i][j].life = 5 - j;

			}

		}
		lives = 3;

		playing = true;
		status.setText("Running...");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */

	void tick() {
		if (playing) {

			// update the display
			repaint();

			// loop that looks through each brick
			// to make appropriate changes

			for (int i = 0; i < 6; i++) {

				for (int j = 0; j < 8; j++) {

					if (lives <= 0) {
						bullet.fin = false;
						snitch.fin = false;
						status.setText("You Lose");
						playing = false;

					} else {

						status.setText("Score:" + Score + ""
								+ "               Power-ups: " + power
								+ "      Lives: " + lives);

						if (balls <= 0) {

							lives--;

							if (snitch.v_x == 0)
								snitch.v_x = 3;
							snitch.v_y = 3;

							snitch.fin = true;
							balls = 1;

						}
						// make the snitch bounce off walls...
						snitch.bounce(snitch.hitWall());

						snitch.bounce(snitch.hitObj(square));

						if (balls == 2)
							snitch.bounce(snitch.hitObj(bullet));

						if (snitch.intersects(brick[i][j])) {
							brick[i][j].life--;

							if (brick[i][j].life > 0) {
								snitch.bounce(snitch.hitObj(brick[i][j]));

								Score++;
							}

						}
						if (Score >= 10) {
							bullet.bounce(bullet.hitWall());
							bullet.bounce(bullet.hitObj(square));
							if (Score < 35)
								power = "Extra Ball";
							if (balls == 2)
								snitch.bounce(snitch.hitObj(bullet));
							if (bullet.intersects(brick[i][j])) {
								brick[i][j].life--;

								if (brick[i][j].life > 0) {
									bullet.bounce(bullet.hitObj(brick[i][j]));

									Score++;
								}
							}
						}

					}
				}

			}

			if (Score >= 5) {
				snitch.move();
				power = "High Speed";

			}
			if (Score >= 35) {
				snitch.move();
				bullet.move();
				power = "";
				power = "High Speed";

			}

			// adds the extra ball if the right conditions are met
			if (Score >= 10 && Score <= 12 && balls < 2) {

				balls++;
				bullet.move();

				Score++;

				status.setText("Extra Ball Bonus");
			}

			// if ball1 is out of bounds, its gone
			if (snitch.pos_y > 470) {
				snitch.fin = false;
				snitch.pos_y = 150;
				snitch.v_x = 0;
				snitch.v_y = 0;
				balls--;

			}
			// if ball2 is out of bounds, its gone
			if (bullet.pos_y > 470) {
				bullet.fin = false;
				bullet.pos_y = 150;
				bullet.v_x = 0;
				bullet.v_y = 0;
				balls--;
			}

			// advances the paddle and ball1
			square.move();

			snitch.move();

		}
	}

	@Override
	public void paintComponent(Graphics g) {
		// inherits the paintComponent of the previous interface/class
		super.paintComponent(g);
		square.draw(g);

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {

				snitch.draw(g);

				brick[i][j].draw(g);

			}
		}
		if (Score >= 10) {
			bullet.move();
			bullet.draw(g);

		}

		if (Score >= 62) {

			status.setText("You Win");
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
