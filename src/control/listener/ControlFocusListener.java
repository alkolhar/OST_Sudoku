package control.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import control.ControlMain;

/**
 * class for handling the FocusEvents of the grid
 */
public class ControlFocusListener implements FocusListener {

	private final ControlMain parent;

	/**
	 * constructor for ControlFocusListener
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlFocusListener(ControlMain p) {
		parent = p;
	}

	@Override
	public void focusLost(FocusEvent e) {
		parent.getModel().focusLostInput();
	}

	// not used
	@Override
	public void focusGained(FocusEvent e) {}

}
