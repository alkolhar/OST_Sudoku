package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.ControlMain;
import enums.Difficulty;

/**
 * class for handling the ActionEvent of the medium button
 */
public class ControlRbMedium implements ActionListener {
	private final ControlMain parent;

	/**
	 * constructor for ControlRbMedium
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlRbMedium(ControlMain p) {
		this.parent = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		parent.getModel().getOptions().setNextRoundDiff(Difficulty.MEDIUM);
	}

}
