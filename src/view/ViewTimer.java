package view;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * class for calculating and showing the elapsed game time
 *
 */
public class ViewTimer extends JPanel {
	private static final long serialVersionUID = 1L;

	// Initialize time values
	private int seconds = 0;
	private int minutes = 0;
	private int hours = 0;

	// Format Strings to display
	private String secondString = String.format("%02d", seconds);
	private String minuteString = String.format("%02d", seconds);
	private String hourString = String.format("%02d", seconds);

	// Create JLabel for displaying
	private JLabel timeLabel = new JLabel();

	/**
	 * Create JLabel with Stopwatch, format hh:mm:ss
	 */
	public ViewTimer(ViewButtons p) {
		timeLabel.setText(hourString + ":" + minuteString + ":" + secondString);
		timeLabel.setBounds(100, 100, 200, 100);
		timeLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
		timeLabel.setBorder(BorderFactory.createBevelBorder(1));
		timeLabel.setOpaque(true);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);

		add(timeLabel);
	}

	/**
	 * Updates the stopwatch of the game
	 * 
	 * @param elapsedTime
	 */
	public void tick(int elapsedTime) {
		// Calculate hours
		hours = (elapsedTime / 3600);
		// Calculate minutes
		minutes = (elapsedTime / 60) % 60;
		// Calculate seconds
		seconds = elapsedTime % 60;
		// Format Strings
		secondString = String.format("%02d", seconds);
		minuteString = String.format("%02d", minutes);
		hourString = String.format("%02d", hours);
		// Update Label
		timeLabel.setText(hourString + ":" + minuteString + ":" + secondString);

	}
}