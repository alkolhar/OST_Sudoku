package model;

import enums.*;

/**
 * Class which represents one field of the grid for storing values and
 * attributes integer arguments will be tested for range (1-9), invalid entries
 * are set to 0 or false This class contains no logic and has just getter/setter
 */
public class ModelField {

	private int value = 0;
	private int startValue = 0;
	private int solution = 0;
	private boolean[] possibilities = new boolean[StaticGameDim.GRIDSIZE];

	private Highlight highlight = Highlight.NONE;
	private boolean isChangeable = true;

	/**
	 * compares value with solution
	 * 
	 * @return boolean
	 */
	public boolean isValueCorrect() {
		if (this.value == 0 && this.startValue == 0) {
			return false;
		}
		if (this.solution != this.value) {
			if (this.solution != this.startValue) {
				return false;
			}
		}
		return true;
	}

	/*
	 * setter and getter
	 */

	/**
	 * set startValue only 1 to 9 is valid, everything else will be changed to 0 0
	 * means empty if not empty -> isChangeable set to false
	 * 
	 * @param v startValue
	 */
	public void setStartValue(int v) {
		if (isInRange(v)) {
			startValue = v;
			setIsChangeable(false);
		} else {
			startValue = 0;
			setIsChangeable(true);
		}
	}

	/**
	 * get startValue
	 * 
	 * @return startValue
	 */
	public int getStartValue() {
		return startValue;
	}

	/**
	 * set isChangeable can this field be changed by the user?
	 * 
	 * @param b true or false
	 */
	public void setIsChangeable(boolean b) {
		isChangeable = b;
	}

	/**
	 * get isChangeable
	 * 
	 * @return true or false
	 */
	public boolean getIsChangeable() {
		return isChangeable;
	}

	/**
	 * set color
	 * 
	 * @param h the kind of Highlight to use
	 */
	public void setHighlight(Highlight h) {
		highlight = h;
	}

	/**
	 * get color
	 * 
	 * @return current kind of Highlight
	 */
	public Highlight getHighlight() {
		return highlight;
	}

	/**
	 * set solution or correct value also updates isChangeable
	 * 
	 * @param v value
	 */
	public void setSolution(int v) {
		if (isInRange(v)) {
			solution = v;
		} else {
			solution = 0;
		}
	}

	/**
	 * get solution or correct value
	 * 
	 * @return solution
	 */
	public int getSolution() {
		return solution;
	}

	/**
	 * set the value and returns true if the value has not changed, or false
	 * otherwise
	 * 
	 * @param v value
	 * @return if the value is equal to the current value
	 */
	public boolean setValue(int v) {
		boolean equal = false;
		if (v == value) {
			equal = true;
		}
		if (isInRange(v)) {
			value = v;
		} else {
			value = 0;
		}
		return equal;
	}

	/**
	 * get the value
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * set the possibility-array to true on v-1 and returns the possibilities array
	 * if v is not between 1-9 all will be set to false
	 * 
	 * @param v which value to set (v-1 -> place in array)
	 * @param p set to true or false
	 */
	public void setPossibility(int v, boolean b) {
		if (isInRange(v)) {
			int p = v - 1;
			possibilities[p] = b;
		} else {
			setAllPossibilitiesFalse();
		}
	}

	/**
	 * returns the possibilities array
	 * 
	 * @return possibilities array
	 */
	public boolean[] getPossibility() {
		return possibilities;
	}

	public boolean getPossibilityAt(int v) {
		if (isInRange(v)) {
			return possibilities[v - 1];
		} else {
			return false;
		}
	}

	/**
	 * set all false on the possibilities array
	 */
	public void setAllPossibilitiesFalse() {
		for (int i = 0; i < possibilities.length; i++) {
			possibilities[i] = false;
		}
	}

	/**
	 * set all true on the possibilities array
	 */
	public void setAllPossibilitiesTrue() {
		for (int i = 0; i < possibilities.length; i++) {
			possibilities[i] = true;
		}
	}

	/*
	 * helper
	 */

	/**
	 * checks if value is in range (1-9)
	 * 
	 * @param v value for testing
	 * @return true if in range
	 */
	private boolean isInRange(int v) {
		int min = 1;
		int max = StaticGameDim.GRIDSIZE;
		return (v >= min && v <= max);
	}

	/**
	 * reset every data field
	 */
	public void resetAll() {
		value = 0;
		startValue = 0;
		solution = 0;
		possibilities = new boolean[StaticGameDim.GRIDSIZE];

		highlight = Highlight.NONE;
		isChangeable = true;
	}
}
