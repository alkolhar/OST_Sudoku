package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.ControlMain;
import enums.Difficulty;

/**
 * class for handling the ActionEvent of the hard button
 */
public class ControlRbHard implements ActionListener {
	private final ControlMain parent;

	/**
	 * constructor for ControlRbHard
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlRbHard(ControlMain p) {
		this.parent = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		parent.getModel().getOptions().setNextRoundDiff(Difficulty.HARD);
	}

}
