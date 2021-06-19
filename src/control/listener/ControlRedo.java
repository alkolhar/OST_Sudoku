package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.ControlMain;

/**
 * class for handling the ActionEvent of the redo button
 */
public class ControlRedo implements ActionListener {
	private final ControlMain parent;

	/**
	 * constructor for ControlRedo
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlRedo(ControlMain p) {
		this.parent = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("redo");
		parent.getModel().redoInput();
	}

}
