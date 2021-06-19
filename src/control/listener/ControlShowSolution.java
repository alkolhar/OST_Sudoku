package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.ControlMain;

/**
 * class for handling the ActionEvent of the showSolution button
 */
public class ControlShowSolution implements ActionListener {
	private final ControlMain parent;

	/**
	 * constructor for ControlShowSolution
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlShowSolution(ControlMain p) {
		this.parent = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean state = parent.getModel().getOptions().getShowSolution();
		parent.getModel().showSolutionInput(!state);
	}

}
