package model;

import javax.swing.JOptionPane;

import control.ControlMain;
import enums.Difficulty;
import view.ViewMain;

/**
 * Facade for all model subclasses, every call (from/to outside) runs through
 * this class
 */
public class ModelMain {
	private ControlMain control;
	private ViewMain view;

	private ModelGrid grid = new ModelGrid();
	private ModelSession session = new ModelSession();
	private ModelOptions options = new ModelOptions();

	private Thread currentTimer;

	/**
	 * setter for the other main classes
	 */
	public void setMain(ControlMain c) {
		control = c;
	}

	public void setMain(ViewMain v) {
		view = v;
		session.setViewReference(v);
	}

	/**
	 * getter for the other main classes
	 */
	public ControlMain getControlMain() {
		return control;
	}

	public ViewMain getViewMain() {
		return view;
	}

	/**
	 * get the grid
	 * 
	 * @return ModelGrid
	 */
	public ModelGrid getGrid() {
		return grid;
	}

	/**
	 * get the session
	 * 
	 * @return ModelSession
	 */
	public ModelSession getSession() {
		return session;
	}

	/**
	 * get the options
	 * 
	 * @return ModelOptions
	 */
	public ModelOptions getOptions() {
		return options;
	}

	/**
	 * handles the start of a new game
	 */
	public void newGameInput() {
		resetTimer();
		grid.resetGrid();
		options.setFocusEnabled(true);
		resetButtons();

		options.setDifficulty(options.getNextRoundDiff());
		grid.generateNewGame(options.getDifficulty());

		view.update();
	}

	/**
	 * stops the timer and returns the stopped time, the elapsed time is not reseted
	 * 
	 * @return stoppedTime
	 */
	public int stopTimer() {
		int elapsedTime = 0;
		if (options.getTimerStarted()) {
			elapsedTime = session.getElapsedTime();
			currentTimer.interrupt();
			currentTimer = null;
			while (session.getElapsedTime() != 0)
				;
			session.setElapsedTime(elapsedTime);
			options.setTimerStarted(false);
		}
		return elapsedTime;
	}

	/**
	 * stops the timer and set the elapsedTime to zero
	 */
	public void resetTimer() {
		if (options.getTimerStarted()) {
			currentTimer.interrupt();
			currentTimer = null;
			options.setTimerStarted(false);
		}
		session.setElapsedTime(0);
	}

	/**
	 * handles the selecting of a new field
	 * 
	 * @param y row of the field
	 * @param x column of the field
	 */
	public void focusGainedInput(int y, int x) {
		if (options.getFocusEnabled()) {
			view.getInputFocus();

			if (!options.getTimerStarted()) {
				startTimer();
			}

			grid.setFieldActive(y, x);
			grid.highlightWrongFields(options.getShowWrongField());

			view.update();
		}
	}

	/**
	 * starts the timer
	 */
	public void startTimer() {
		currentTimer = new Thread(session);
		currentTimer.start();
		options.setTimerStarted(true);
	}

	/**
	 * reset the active field when focus is lost
	 */
	public void focusLostInput() {
		grid.clearFieldActive();

		if (options.getShowWrongField())
			grid.highlightWrongFields(true);

		view.update();
	}

	/**
	 * handles the input of a number
	 * 
	 * @param v value
	 */
	public void numberInput(int v) {
		if (options.getShowHelpNr()) {
			grid.setPossibility(v, true);
		} else {
			grid.setValue(v);
		}
		view.update();
	}

	/**
	 * if the game is finished this gets called
	 */
	private void gameWon(int stoppedTime) {
		Difficulty currentDiff = this.options.getDifficulty();
		Difficulty nextDiff = this.options.getNextRoundDiff();

		StringBuilder message = new StringBuilder();

		// Calculate hours
		int hours = (stoppedTime / 3600);
		// Calculate minutes
		int minutes = (stoppedTime / 60) % 60;
		// Calculate seconds
		int seconds = (stoppedTime / 1) % 60;
		// Format Strings
		String secondString = String.format("%02d", seconds);
		String minuteString = String.format("%02d", minutes);
		String hourString = String.format("%02d", hours);

		// Zusammensetzten der Message
		message.append("Sie haben das Sudoku beendet.\n");
		message.append("benötigte Zeit: ");
		message.append(hourString);
		message.append(":");
		message.append(minuteString);
		message.append(":");
		message.append(secondString);
		message.append("\n");
		message.append("Schwierigkeitsgrad: ");
		if (currentDiff == Difficulty.EASY) {
			message.append("leicht\n");
		} else if (currentDiff == Difficulty.MEDIUM) {
			message.append("mittel\n");
		} else {
			message.append("schwer\n");
		}
		message.append("Nach dem Schliessen dieses Fenster wird ein neues Spiel der Schwierigkeit ");
		if (nextDiff == Difficulty.EASY) {
			message.append("leicht gestartet.");
		} else if (nextDiff == Difficulty.MEDIUM) {
			message.append("mittel gestartet.");
		} else {
			message.append("schwer gestartet.");
		}
		String msg = message.toString();

		int input = JOptionPane.showConfirmDialog(null, msg, "Game Over", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (input == JOptionPane.OK_OPTION || input == JOptionPane.CLOSED_OPTION) {
			this.newGameInput();
		}

	}

	/**
	 * handles the showHelpInput
	 * 
	 * @param b help enable
	 */
	public void showHelpInput(boolean b) {
		if (options.getFocusEnabled()) {
			options.setShowHelpNr(b);

			view.update();
		}
	}

	/**
	 * handles the showSolutionInput
	 * 
	 * @param b solution enable
	 */
	public void showSolutionInput(boolean b) {
		if (options.getFocusEnabled()) {
			if (options.getShowWrongField()) {
				showWrongFieldInput(false);
			}
			options.setShowSolution(b);

			view.update();
		}
	}

	/**
	 * handles the showWrongFieldInput
	 * 
	 * @param b showWrongField enable
	 */
	public void showWrongFieldInput(boolean b) {
		if (options.getFocusEnabled()) {
			if (options.getShowSolution()) {
				showSolutionInput(false);
			}
			options.setShowWrongField(b);
			grid.highlightWrongFields(b);

			if (grid.checkForGameWon()) {
				int stoppedTime = stopTimer();
				this.gameWon(stoppedTime);
			}
			view.update();
		}
	}

	/**
	 * handles the restartInput
	 */
	public void restartInput() {
		this.resetButtons();
		grid.restart();

		view.update();
	}

	/**
	 * handles the undoInput
	 */
	public void undoInput() {
		this.resetButtons();
		grid.undo();

		view.update();
	}

	/**
	 * handles the redoInput
	 */
	public void redoInput() {
		this.resetButtons();
		grid.redo();

		view.update();
	}

	/**
	 * handles the autoFillPossibilityInput
	 */
	public void autoFillPossibilityInput() {
		this.grid.autoFillPossibility();

		view.update();
	}

	/**
	 * set buttons in options to false
	 */
	public void resetButtons() {
		showWrongFieldInput(false);
		showSolutionInput(false);
		showHelpInput(false);
	}

}
