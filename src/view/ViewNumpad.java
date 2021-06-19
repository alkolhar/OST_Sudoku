package view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JPanel;

import enums.ListenerKey1D;

/**
 * Class for the numbers panel
 *
 */
public class ViewNumpad extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int BUTTONCOUNT = 10;

	/**
	 * Add Buttons for number input (1 .. 9)
	 */
	public ViewNumpad(ViewMain p, Hashtable<String, EventListener> table) {
		// extract ActionListener in Array
		ActionListener[] listeners = new ActionListener[BUTTONCOUNT];
		for (int i = 0; i < BUTTONCOUNT; i++) {
			String k = ListenerKey1D.NUMPADLISTENER.getKey(i);
			listeners[i] = (ActionListener) table.get(k);
		}

		this.setLayout(new GridLayout(1, BUTTONCOUNT, 0, 5));

		for (int i = 0; i < BUTTONCOUNT; i++) {
			JButton btn;
			if (i == 0) {
				btn = new JButton("del");
			} else {
				btn = new JButton(Integer.toString(i));
			}
			btn.setFocusable(false); // otherwise grid loses focus when button clicked
			add(btn);
			btn.addActionListener(listeners[i]);
		}
	}

}
