package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.ControlMain;

/**
 * class for handling the ActionEvent of the showWrongField button
 */
public class ControlShowWrongField implements ActionListener {
	private final ControlMain parent;

	/**
	 * constructor for ControlShowWrongField
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlShowWrongField(ControlMain p) {
		this.parent = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean state = parent.getModel().getOptions().getShowWrongField();
		parent.getModel().showWrongFieldInput(!state);
	}

}
