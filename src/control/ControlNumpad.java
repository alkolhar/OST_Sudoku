package control;

import java.util.EventListener;
import java.util.Hashtable;
import control.listener.ControlNumpadListener;
import enums.ListenerKey1D;

/**
 * class for controlling the events concerning the numpad
 */
public class ControlNumpad {
	private ControlNumpadListener[] listeners;
	private final ControlMain parent;

	private final int BUTTONCOUNT = 10;

	/**
	 * constructor for ControlNumpad, generates 10 ControlNumpadListeners with this
	 * as parent
	 * 
	 * @param p ControlMain, the parent of this class
	 */
	public ControlNumpad(ControlMain p) {
		this.parent = p;
		listeners = new ControlNumpadListener[BUTTONCOUNT];
		for (int i = 0; i < BUTTONCOUNT; i++) {
			listeners[i] = new ControlNumpadListener(parent, i);
		}
	}

	/**
	 * generates a Hashtable of the listeners
	 * 
	 * @return Hashtable with the listeners
	 */
	public Hashtable<String, EventListener> getListeners() {
		Hashtable<String, EventListener> table = new Hashtable<String, EventListener>();
		for (int i = 0; i < listeners.length; i++) {
			String k = ListenerKey1D.NUMPADLISTENER.getKey(i);
			table.put(k, listeners[i]);
		}
		return table;
	}
}
