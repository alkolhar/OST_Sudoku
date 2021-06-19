package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.ControlMain;

/**
 * class for handling the ActionEvent of the undo button
 */
public class ControlUndo implements ActionListener {
	private final ControlMain parent;

	/**
	 * constructor for ControlUndo
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlUndo(ControlMain p) {
		this.parent = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		parent.getModel().undoInput();
	}

}
