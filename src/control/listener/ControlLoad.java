package control.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.ControlMain;
import model.xmlHandling.LoadGame;

/**
 * class for handling the ActionEvent of the load button
 */
public class ControlLoad implements ActionListener {
	private final ControlMain parent;

	/**
	 * constructor for ControlLoad
	 * 
	 * @param p the parent of this class (ControlMain)
	 */
	public ControlLoad(ControlMain p) {
		this.parent = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LoadGame load = new LoadGame(parent.getModel());
		load.loadGame();
	}

}
