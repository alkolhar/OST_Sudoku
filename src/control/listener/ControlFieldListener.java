package control.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import control.ControlMain;

/**
 * class for handling the MouseEvents for a field of the grid
 */
public class ControlFieldListener implements MouseListener {

	private final int y;
	private final int x;
	private final ControlMain parent;

	/**
	 * constructor for ControlFieldListener
	 * 
	 * @param p the parent of this class (ControlMain)
	 * @param y the row of the field
	 * @param x the column of the field
	 */
	public ControlFieldListener(ControlMain p, int y, int x) {
		this.y = y;
		this.x = x;
		parent = p;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		parent.getModel().focusGainedInput(y, x);
	}

	// not used
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
