package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.ControlMain;

/**
 * class for handling the ActionEvent of the newGame button
 */
public class ControlNewGame implements ActionListener {
	private final ControlMain parent;

	/**
	 * constructor for ControlNewGame
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlNewGame(ControlMain p) {
		this.parent = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		parent.getModel().newGameInput();
	}

}
