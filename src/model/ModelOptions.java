package model;

import enums.Difficulty;

/**
 * representing the options state of the game for storing the different modes
 * and options
 */
public class ModelOptions {

	private boolean showHelpNr = false;
	private boolean showSolution = false;
	private boolean showWrongField = false;
	private Difficulty difficulty = Difficulty.MEDIUM;
	private Difficulty nextRoundDiff = Difficulty.MEDIUM;
	private boolean timerStarted = false;
	private boolean focusEnabled = false;

	/**
	 * set showHelpNr
	 * 
	 * @param b set helpEnabled to this value
	 */
	public void setShowHelpNr(boolean b) {
		showHelpNr = b;
	}

	/**
	 * returns showHelpNr value
	 * 
	 * @return showHelpNr
	 */
	public boolean getShowHelpNr() {
		return showHelpNr;
	}

	/**
	 * set showSolution value
	 * 
	 * @param b true or false
	 */
	public void setShowSolution(boolean b) {
		showSolution = b;
	}

	/**
	 * get showSolution value
	 * 
	 * @return showSolution
	 */
	public boolean getShowSolution() {
		return showSolution;
	}

	/**
	 * set showWrongField value
	 * 
	 * @param b true or false
	 */
	public void setShowWrongField(boolean b) {
		showWrongField = b;
	}

	/**
	 * get showWrongField value
	 * 
	 * @return showWrongField
	 */
	public boolean getShowWrongField() {
		return showWrongField;
	}

	/**
	 * set nextRoundDiff
	 * 
	 * @param s set nextRoundDiff to this value
	 */
	public void setNextRoundDiff(Difficulty d) {
		nextRoundDiff = d;
	}

	/**
	 * returns nextRoundDiff value
	 * 
	 * @return nextRoundDiff
	 */
	public Difficulty getNextRoundDiff() {
		return nextRoundDiff;
	}

	/**
	 * set difficulty
	 * 
	 * @param s set difficulty to this value
	 */
	public void setDifficulty(Difficulty d) {
		difficulty = d;
	}

	/**
	 * returns difficulty value
	 * 
	 * @return difficulty
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setFocusEnabled(boolean b) {
		focusEnabled = b;
	}

	public boolean getFocusEnabled() {
		return focusEnabled;
	}

	/**
	 * set timerStarted
	 * 
	 * @param b set timerStarted to this value
	 */
	public void setTimerStarted(boolean b) {
		timerStarted = b;
	}

	/**
	 * returns timerStarted value
	 * 
	 * @return timerStarted
	 */
	public boolean getTimerStarted() {
		return timerStarted;
	}

}
