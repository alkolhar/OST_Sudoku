package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.ControlMain;

/**
 * class for handling the ActionEvent of the autFillPossibility button
 */
public class ControlAutoFillPossibility implements ActionListener {
	private final ControlMain parent;

	/**
	 * constructor for ControlAutoFillPossibility
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlAutoFillPossibility(ControlMain p) {
		this.parent = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.parent.getModel().autoFillPossibilityInput();
	}
}
