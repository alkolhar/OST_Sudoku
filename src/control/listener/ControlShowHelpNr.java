package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.ControlMain;

/**
 * class for handling the ActionEvent of the showHelpNr button
 */
public class ControlShowHelpNr implements ActionListener {
	private final ControlMain parent;

	/**
	 * constructor for ControlShowHelpNr
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlShowHelpNr(ControlMain p) {
		this.parent = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean state = parent.getModel().getOptions().getShowHelpNr();
		parent.getModel().showHelpInput(!state);
	}
}
