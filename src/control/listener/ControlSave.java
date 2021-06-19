package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.ControlMain;
import model.xmlHandling.SaveGame;

/**
 * class for handling the ActionEvent of the save button
 */
public class ControlSave implements ActionListener {
	private final ControlMain parent;

	/**
	 * constructor for ControlSave
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlSave(ControlMain p) {
		this.parent = p;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SaveGame save = new SaveGame(parent.getModel());
		save.saveGame();
	}
}
