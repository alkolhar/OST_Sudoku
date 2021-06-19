package view;

import java.awt.*;
import javax.swing.*;

import enums.*;

/**
 * Class for single number field. this class doesn't save the state of the
 * field, it just displays it
 */
public class ViewField extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel valueDisplay = new JLabel();
	private JPanel possDisplay = new JPanel();
	private boolean helpEnabled = false;

	final static float BIGFONTSIZE = 35f;
	final static float SMALLFONTSIZE = 15f;
	final static int PANELSIZE = 50;

	public ViewField() {
		Font fSmall = valueDisplay.getFont().deriveFont(SMALLFONTSIZE);
		Font fBig = valueDisplay.getFont().deriveFont(BIGFONTSIZE);
		// options
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setPreferredSize(new Dimension(PANELSIZE, PANELSIZE));
		setLayout(new GridLayout(1, 1));

		// options valueDisplay
		valueDisplay.setHorizontalAlignment(JTextField.CENTER);
		valueDisplay.setFont(fBig);

		// options possDisplay
		int b = StaticGameDim.BOXSIZE;
		int g = StaticGameDim.GRIDSIZE;
		possDisplay.setLayout(new GridLayout(b, b));
		for (int i = 0; i < g; i++) {
			JLabel l = new JLabel("", JTextField.CENTER);
			l.setFont(fSmall);
			possDisplay.add(l);
		}

		add(valueDisplay);
	}

	/**
	 * reset field to standard state - help disabled - value empty - all
	 * possibilities false - background to
	 */
	public void reset() {
		helpEnabled = false;
		updateValue(0);
		boolean[] b = { false, false, false, false, false, false, false, false, false };
		updatePossibility(b);
		updateBackground(Highlight.NONE);
	}

	/*
	 * update methods
	 */

	/**
	 * update value of field v == 0 -> field is empty ""
	 * 
	 * @param v value
	 */
	public void updateValue(int v) {
		if (isInRange(v)) {
			valueDisplay.setText(Integer.toString(v));
		} else {
			valueDisplay.setText("");
		}
	}

	/**
	 * update possibility of field the number is index + 1 if true -> number gets
	 * displayed else -> text is empty ""
	 * 
	 * @param p[] boolean Array
	 */
	public void updatePossibility(boolean[] p) {
		for (int i = 0; i < StaticGameDim.GRIDSIZE; i++) {
			JLabel l = (JLabel) possDisplay.getComponent(i);
			if (p[i]) {
				l.setText(Integer.toString(i + 1));
			} else {
				l.setText("");
			}
		}
	}

	/**
	 * update background
	 *
	 * @param b Highlight-Enum
	 */
	public void updateBackground(Highlight b) {
		this.setBackground(b.color);
		this.valueDisplay.setBackground(b.color);
		this.possDisplay.setBackground(b.color);
	}

	/*
	 * setter and getter
	 */

	public void setHelpEnabled(boolean b) {
		helpEnabled = b;
	}

	public boolean getHelpEnabled() {
		return helpEnabled;
	}

	/*
	 * helper
	 */

	/**
	 * checks if value is in range (1-9)
	 * 
	 * @param v value for testing
	 * @return true if in range
	 */
	private boolean isInRange(int v) {
		int min = 1;
		int max = StaticGameDim.GRIDSIZE;
		return (v >= min && v <= max);
	}

	/**
	 * updates the Field as a whole if value is empty and help is enabled ->
	 * possibility gets displayed otherwise the value gets displayed
	 */
	public void refresh() {
		this.removeAll();
		this.repaint();
		if (!this.valueDisplay.getText().equals("")) {
			this.add(this.valueDisplay);
		} else {
			this.add(this.possDisplay);
		}
	}
}
