package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.ControlMain;

/**
 * class for handling the ActionEvent of a numpad button
 */
public class ControlNumpadListener implements ActionListener {
	private final int id;
	private final ControlMain parent;

	/**
	 * constructor for ControlNumpadListener
	 * 
	 * @param p  the parent of this class (ControlMain)
	 * @param id value of the button
	 */
	public ControlNumpadListener(ControlMain p, int id) {
		this.id = id;
		this.parent = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		parent.getModel().numberInput(id);
	}

}
