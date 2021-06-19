package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import enums.*;
import model.ModelGrid;
import model.ModelOptions;

/**
 * class to create sudoku grid
 *
 */
public class ViewGrid extends JPanel {
	private static final long serialVersionUID = 1L;
	private ViewField[][] grid;

	/**
	 * Constructor takes a 2D-Array of Fields an distributes them to the spots in
	 * the grid
	 *
	 * @param p     ViewMain as parent
	 * @param table Hashtable with the listeners, the key is a String
	 */
	public ViewGrid(ViewMain p, Hashtable<String, EventListener> table) {
		int gs = StaticGameDim.GRIDSIZE;
		int bs = StaticGameDim.BOXSIZE;

		grid = new ViewField[gs][gs];

		// extract MousListeners in 2D-Array
		MouseListener[][] fieldListeners = new MouseListener[gs][gs];

		for (int y = 0; y < gs; y++) {
			for (int x = 0; x < gs; x++) {
				String k = ListenerKey2D.FIELDLISTENER.getKey(y, x);
				fieldListeners[y][x] = (MouseListener) table.get(k);
				grid[y][x] = new ViewField();
				grid[y][x].addMouseListener(fieldListeners[y][x]);
			}
		}

		FocusListener focusListener = (FocusListener) table.get(ListenerKey.FOCUSLISTENER.getKey());
		KeyListener keyListeners = (KeyListener) table.get(ListenerKey.KEYBOARDLISTENER.getKey());
		this.addFocusListener(focusListener);
		this.setFocusable(true);
		this.addKeyListener(keyListeners);

		// divide numField in Grid
		this.setLayout(new GridLayout(bs, bs));
		for (int i = 0; i < gs; i++) {
			// Startposition top left of the subfields 1-9
			int y = (i / bs) * bs; // Attention; int division
			int x = (i - y) * bs;
			JPanel numPanel = new JPanel();
			numPanel.setLayout(new GridLayout(bs, bs));
			numPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

			for (int yi = 0; yi < bs; yi++) {
				for (int xi = 0; xi < bs; xi++) {
					grid[x + xi][y + yi].updateValue(0);
					numPanel.add(grid[y + yi][x + xi]);
				}
				this.add(numPanel);
			}
		}
	}

	/**
	 * set helpEnabled for the whole grid
	 * 
	 * @param b true or false
	 */
	public void setHelpEnabledGrid(boolean b) {
		int gs = StaticGameDim.GRIDSIZE;
		for (int y = 0; y < gs; y++) {
			for (int x = 0; x < gs; x++) {
				setHelpEnabledField(y, x, b);
			}
		}
	}

	/**
	 * set helpEnabled at y,x in the grid
	 * 
	 * @param y,x location where to set the value
	 * @param b   true or false
	 */
	public void setHelpEnabledField(int y, int x, boolean b) {
		grid[y][x].setHelpEnabled(b);
	}

	/**
	 * reset all Fields in the grid to standard values
	 */
	public void resetGrid() {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				grid[y][x].reset();
			}
		}
	}

	/**
	 * grabs the InputFocus for keyboard inputs
	 */
	public void getInputFocus() {
		this.grabFocus();
	}

	public void update(ModelGrid m, ModelOptions o) {
		for (int y = 0; y < StaticGameDim.GRIDSIZE; y++) {
			for (int x = 0; x < StaticGameDim.GRIDSIZE; x++) {

				int startValue = m.getFieldAt(y, x).getStartValue();
				int value = m.getFieldAt(y, x).getValue();
				int solution = m.getFieldAt(y, x).getSolution();
				boolean[] possibility = m.getFieldAt(y, x).getPossibility();

				grid[y][x].updateBackground(m.getFieldAt(y, x).getHighlight());

				if (startValue != 0) {
					grid[y][x].updateValue(startValue);
				} else {
					grid[y][x].updateValue(value);
				}

				if (o.getShowSolution()) {
					grid[y][x].updateValue(solution);
				}

				grid[y][x].updatePossibility(possibility);

				grid[y][x].refresh();
			}
		}
	}
}
