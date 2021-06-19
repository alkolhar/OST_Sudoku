package control.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import control.ControlMain;

/**
 * class for handling the KeyEvents of the keyboard
 */
public class ControlKeyboardListener implements KeyListener {
	private final ControlMain parent;

	private static final int KEYNULL = 48;
	private static final int KEYNINE = 57;

	/**
	 * constructor for ControlKeyboardListener
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlKeyboardListener(ControlMain p) {
		this.parent = p;
	}

	/**
	 * Checks if the keyboardInput is a number and then calls numberInput on the
	 * parent with the value
	 * 
	 * @param e KeyEvent
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		int v = (int) c;
		if (ControlKeyboardListener.KEYNULL <= v && v <= ControlKeyboardListener.KEYNINE) { // Range 0->9
			v -= ControlKeyboardListener.KEYNULL; // KeyID -> value of the key
			parent.getModel().numberInput(v);
		} else if (c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
			v = 0; // del is equal to 0
			parent.getModel().numberInput(v);
		}
	}

	// not used
	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
