
package control;

import java.util.EventListener;
import java.util.Hashtable;

import control.listener.*;
import control.listener.ControlKeyboardListener;
import enums.ListenerKey;
import model.ModelMain;
import view.ViewMain;

/**
 * Facade for all control subclasses, all calls (in- & outward) go through this
 * class
 *
 */
public class ControlMain {

	private ModelMain model;
	private ViewMain view;
	private Hashtable<String, EventListener> table = new Hashtable<String, EventListener>();

	public Hashtable<String, EventListener> getActionListeners() {
		return table;
	}

	public void createActionListeners() {
		ControlAutoFillPossibility autoFillPossibility = new ControlAutoFillPossibility(this);
		ControlKeyboardListener keyboardListener = new ControlKeyboardListener(this);
		ControlLoad load = new ControlLoad(this);
		ControlRbEasy easy = new ControlRbEasy(this);
		ControlRbHard hard = new ControlRbHard(this);
		ControlRbMedium medium = new ControlRbMedium(this);
		ControlRedo redo = new ControlRedo(this);
		ControlRestart restart = new ControlRestart(this);
		ControlSave save = new ControlSave(this);
		ControlShowHelpNr showHelpNr = new ControlShowHelpNr(this);
		ControlShowSolution showSolution = new ControlShowSolution(this);
		ControlShowWrongField showWrongField = new ControlShowWrongField(this);
		ControlUndo undo = new ControlUndo(this);
		ControlNewGame newGame = new ControlNewGame(this);

		ControlFieldGrid fieldGrid = new ControlFieldGrid(this);
		ControlNumpad numpad = new ControlNumpad(this);

		table.put(ListenerKey.AUTOFILLPOSSIBILITY.getKey(), autoFillPossibility);
		table.put(ListenerKey.KEYBOARDLISTENER.getKey(), keyboardListener);
		table.put(ListenerKey.LOAD.getKey(), load);
		table.put(ListenerKey.RBEASY.getKey(), easy);
		table.put(ListenerKey.RBHARD.getKey(), hard);
		table.put(ListenerKey.RBMEDIUM.getKey(), medium);
		table.put(ListenerKey.REDO.getKey(), redo);
		table.put(ListenerKey.RESTART.getKey(), restart);
		table.put(ListenerKey.SAVE.getKey(), save);
		table.put(ListenerKey.SHOWHELPNR.getKey(), showHelpNr);
		table.put(ListenerKey.SHOWSOLUTION.getKey(), showSolution);
		table.put(ListenerKey.SHOWWRONGFIELD.getKey(), showWrongField);
		table.put(ListenerKey.UNDO.getKey(), undo);
		table.put(ListenerKey.NEWGAME.getKey(), newGame);

		table.putAll(fieldGrid.getFieldListeners());
		table.putAll(fieldGrid.getFocusListener());
		table.putAll(numpad.getListeners());
	}

	/*
	 * setter for the other main classes
	 */
	
	public void setMain(ViewMain v) {
		view = v;
	}

	public void setMain(ModelMain m) {
		model = m;
	}

	/*
	 * getter for the other main classes
	 */

	public ViewMain getView() {
		return view;
	}

	public ModelMain getModel() {
		return model;
	}

}
