/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public void run() {
		// sets appropriate frames and sizes
		final JFrame frame1 = new JFrame("BrickBreaker FRAME");
		frame1.setLocation(300, 100);
		final JFrame frame2 = new JFrame("Instructions FRAME");

		ImageIcon iconLogo = new ImageIcon("Instructions.jpg");
		final JLabel instructions = new JLabel();
		instructions.setIcon(iconLogo);

		final JPanel instruct_panel = new JPanel();
		frame2.add(instruct_panel, BorderLayout.WEST);

		instruct_panel.add(instructions);

		final JPanel start_panel = new JPanel();
		frame1.add(start_panel, BorderLayout.CENTER);

		final JButton gamestart = new JButton("Start Game");
		gamestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame1.remove(start_panel);

				final JPanel status_panel = new JPanel();

				final JLabel status = new JLabel("Running...");
				status_panel.add(status);

				frame1.add(status_panel, BorderLayout.SOUTH);

				// Main playing area
				final GameCourt court = new GameCourt(status);
				frame1.add(court, BorderLayout.CENTER);

				// Reset button
				final JPanel control_panel = new JPanel();
				frame1.add(control_panel, BorderLayout.NORTH);

				final JButton reset = new JButton("Reset");
				reset.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						court.reset();
					}
				});
				control_panel.add(reset);

				// Start game
				court.reset();

				frame1.pack();

			}
		});
		start_panel.add(gamestart);

		final JButton instruct = new JButton("instruct");
		instruct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame2.pack();

				frame2.setVisible(true);

			}
		});
		start_panel.add(instruct, BorderLayout.CENTER);

		// Put the frame on the screen
		frame1.pack();
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it NOTE: Do NOT delete! You MUST include this
	 * in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
