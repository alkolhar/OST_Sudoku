package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.ControlMain;

/**
 * class for handling the ActionEvent of the restart button
 */
public class ControlRestart implements ActionListener {
	private final ControlMain parent;

	/**
	 * constructor for ControlRestart
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlRestart(ControlMain p) {
		this.parent = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("restart");
		parent.getModel().restartInput();
	}

}
