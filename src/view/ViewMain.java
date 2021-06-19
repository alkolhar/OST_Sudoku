package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.EventListener;
import java.util.Hashtable;

import javax.swing.JFrame;

import control.ControlMain;
import model.ModelGrid;
import model.ModelMain;
import model.ModelOptions;

/**
 * Facade for all View subclasses. all calls (in- & outward) going trough this
 * class
 */
public class ViewMain extends JFrame {
	private static final long serialVersionUID = 1L;
	private BorderLayout layout = new BorderLayout();

	private ModelMain model;
	private ControlMain control;

	private ViewButtons buttons;
	private ViewGrid grid;
	private ViewNumpad numpad;

	/**
	 * Constructor for GUI, calling all methods for separate functional blocks
	 * 
	 */
	public ViewMain(Hashtable<String, EventListener> table) {
		// Set standard JFrame properties
		initialize();

		// Add Grid for Numbers
		grid = new ViewGrid(this, table);
		add(grid, BorderLayout.CENTER);

		// Add Control Section on West side
		buttons = new ViewButtons(table);
		add(buttons, BorderLayout.WEST);

		// Add numpad
		numpad = new ViewNumpad(this, table);
		add(numpad, BorderLayout.SOUTH);

		// Show GUI
		pack();
		setVisible(true);
	}

	/**
	 * setter for the other main classes
	 */
	public void setMain(ControlMain c) {
		control = c;
	}

	public void setMain(ModelMain m) {
		model = m;
	}

	/**
	 * getter for the other main classes
	 */
	public ControlMain getControlMain() {
		return control;
	}

	public ModelMain getModelMain() {
		return model;
	}

	/**
	 * Method for setting JFrame properties on startup
	 * 
	 */
	private void initialize() {
		// Adjust Game size to monitor resolution
		// -> maybe a bad idea because of rescaling
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension((int) screenSize.getWidth() / 2, (int) screenSize.getHeight() / 2);
		setSize(new Dimension(frameSize));

		// Move Game to center of screen
		setLocation((screenSize.width - frameSize.width) >> 1, (screenSize.height - frameSize.height) >> 1);

		// Set Title
		setTitle("Sudoku");

		// Set Layout
		setLayout(layout);
		// setResizable(false);

		// Closing operations
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * ObserverIntegration has to be called after manipulation of Model
	 */
	public void update() {
		ModelGrid modelGrid = model.getGrid();
		ModelOptions modelOptions = model.getOptions();
		grid.update(modelGrid, modelOptions);
		buttons.update(modelOptions);
	}

	/**
	 * Transmits the call to the ViewGrid
	 */
	public void getInputFocus() {
		grid.getInputFocus();
	}

	/**
	 * Transmits the call to the ViewTimer
	 */
	public void doTick(int time) {
		buttons.doTick(time);
	}

}
