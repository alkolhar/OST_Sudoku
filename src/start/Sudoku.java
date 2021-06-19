package start;

import java.util.EventListener;
import java.util.Hashtable;

import control.*;
import model.*;
import view.*;

/**
 * responsible for the connection of all classes --> Constructor of the app
 */

public class Sudoku {

	public static void main(String args[]) {
		ControlMain control = new ControlMain();

		control.createActionListeners();
		Hashtable<String, EventListener> table = control.getActionListeners();

		ViewMain view = new ViewMain(table);
		ModelMain model = new ModelMain();

		control.setMain(view);
		control.setMain(model);

		view.setMain(model);
		view.setMain(control);

		model.setMain(view);
		model.setMain(control);
	}
}
