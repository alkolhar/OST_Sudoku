package control;

import java.util.EventListener;
import java.util.Hashtable;

import control.listener.ControlFieldListener;
import control.listener.ControlFocusListener;
import enums.*;

/**
 * Class for controlling events concerning the playing field, only necessary to
 * create an array of fieldlisteners
 */
public class ControlFieldGrid {
	private ControlFieldListener[][] fieldListeners;
	private ControlFocusListener focusListener;
	private final ControlMain parent;

	/**
	 * constructor for ControlFieldGrid
	 * 
	 * @param p ControlMain, the parent of this class
	 */
	public ControlFieldGrid(ControlMain p) {
		this.parent = p;
		int size = StaticGameDim.GRIDSIZE;

		fieldListeners = new ControlFieldListener[size][size];

		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				fieldListeners[y][x] = new ControlFieldListener(parent, y, x);
			}
		}
		focusListener = new ControlFocusListener(parent);
	}

	/**
	 * generates a Hashtable of the fieldListeners
	 * 
	 * @return Hashtable with the fieldListeners
	 */
	public Hashtable<String, EventListener> getFieldListeners() {
		Hashtable<String, EventListener> table = new Hashtable<String, EventListener>();

		for (int y = 0; y < fieldListeners.length; y++) {
			for (int x = 0; x < fieldListeners[y].length; x++) {
				String k = ListenerKey2D.FIELDLISTENER.getKey(y, x);
				table.put(k, fieldListeners[y][x]);
			}
		}
		return table;
	}

	/**
	 * generates a Hashtable of the focusListener
	 * 
	 * @return Hashtable with the focusListener
	 */
	public Hashtable<String, EventListener> getFocusListener() {
		Hashtable<String, EventListener> table = new Hashtable<String, EventListener>();
		table.put(ListenerKey.FOCUSLISTENER.getKey(), focusListener);
		return table;
	}

}
