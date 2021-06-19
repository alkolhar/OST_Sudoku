package model;

import enums.Difficulty;
import enums.Highlight;
import enums.StaticGameDim;
import model.sudokuGenerator.Generator;
import model.sudokuSolver.Solver;
import model.sudokuSolver.solverFunction.NoSolutionException;
import model.temporaryFileSystem.FileSystem;
import model.temporaryFileSystem.NoSaveDataException;

/**
 * Class which represents the grid This Class stores the Fields it can be filled
 * based of a 2D-int-Array it can reset the Highlights and the possibilities for
 * the whole grid
 *
 */
public class ModelGrid {

	private FileSystem fileSystem = new FileSystem();
	private ModelField[][] game;
	private int activeY = -1;
	private int activeX = -1;

	public ModelGrid() {
		game = new ModelField[9][9];
		for (int y = 0; y < game.length; y++) {
			for (int x = 0; x < game[y].length; x++) {
				game[y][x] = new ModelField();
			}
		}
	}

	/**
	 * fill the Grid with values to reset the game use an array with zeros
	 * 
	 * @param v[][] 2D-Array with int values
	 */
	public void fillValues(int[][] v) {
		for (int y = 0; y < game.length; y++) {
			for (int x = 0; x < game[0].length; x++) {
				game[y][x].setValue(v[y][x]);
			}
		}
	}

	/**
	 * fill the Grid with startValues
	 * 
	 * @param s[][] 2D-Array with int values
	 */
	public void fillStartValues(int[][] s) {
		for (int y = 0; y < game.length; y++) {
			for (int x = 0; x < game[0].length; x++) {
				int value = s[y][x];
				game[y][x].setStartValue(value);
				if (value != 0) {
					game[y][x].setIsChangeable(false);
				} else {
					game[y][x].setIsChangeable(true);
				}
			}
		}
	}

	/**
	 * fill the grid with solution
	 * 
	 * @param s[][] 2D-Array with int values
	 */
	public void fillSolution(int[][] s) {
		for (int y = 0; y < game.length; y++) {
			for (int x = 0; x < game[0].length; x++) {
				game[y][x].setSolution(s[y][x]);
			}
		}
	}

	/**
	 * reset highlight of fields to standard field.isChangeable == true ->
	 * Highlight.NONE field.isChangeable == false -> Highlight.NOTSELECTABLE
	 */
	public void resetHighlight() {
		for (int y = 0; y < game.length; y++) {
			for (int x = 0; x < game[0].length; x++) {
				ModelField f = game[y][x];
				if (f.getIsChangeable()) {
					f.setHighlight(Highlight.NONE);
				} else {
					f.setHighlight(Highlight.NOTSELECTABLE);
				}
			}
		}
	}

	/**
	 * reset possibilities of fields to standard standard -> false
	 */
	private void resetPossibilityFalse() {
		for (int y = 0; y < game.length; y++) {
			for (int x = 0; x < game[0].length; x++) {
				game[y][x].setAllPossibilitiesFalse();
			}
		}
	}

	/**
	 * reset possibilities of fields to true standard -> false
	 */
	private void resetPossibilityTrue() {
		for (int y = 0; y < game.length; y++) {
			for (int x = 0; x < game[0].length; x++) {
				game[y][x].setAllPossibilitiesTrue();
			}
		}
	}

	/**
	 * reset values of fields to standard standard -> 0
	 */
	public void resetValue() {
		for (int y = 0; y < game.length; y++) {
			for (int x = 0; x < game[0].length; x++) {
				game[y][x].setValue(0);
			}
		}
	}

	/**
	 * fills all Posibilities in, it doesn't check for mistakes
	 */
	public void autoFillPossibility() {
		if (this.hasAnyFieldTruePossibility()) {
			this.resetPossibilityFalse();
			return;
		}

		this.resetPossibilityTrue();
		for (int y = 0; y < this.game.length; y++) {
			for (int x = 0; x < this.game[0].length; x++) {
				int startValue = this.game[y][x].getStartValue();
				int value = this.game[y][x].getValue();
				if (startValue != 0) {
					this.setPossibilityFalseBox(y, x, startValue);
					this.setPossibilityFalseColumn(x, startValue);
					this.setPossibilityFalseRow(y, startValue);
				} else {
					this.setPossibilityFalseBox(y, x, value);
					this.setPossibilityFalseColumn(x, value);
					this.setPossibilityFalseRow(y, value);
				}
			}
		}
	}

	/**
	 * checks if any field of the whole grid has an true entry in possibility
	 * 
	 * @return boolean true if any possibility is set true
	 */
	private boolean hasAnyFieldTruePossibility() {
		boolean hasTrue = false;
		for (int y = 0; y < this.game.length; y++) {
			for (int x = 0; x < this.game[0].length; x++) {
				boolean[] possibility = game[y][x].getPossibility();

				for (int i = 0; i < possibility.length; i++) {
					if (possibility[i] == true) {
						hasTrue = true;
					}
				}
			}
		}
		return hasTrue;
	}

	/*
	 * getter and setter
	 */
	/**
	 * get the grid
	 * 
	 * @return ModelGrid
	 */
	public ModelField[][] getGrid() {
		return game;
	}

	/**
	 * get the field at
	 * 
	 * @param y row
	 * @param x column
	 * @return ModelField
	 */
	public ModelField getFieldAt(int y, int x) {
		return game[y][x];
	}

	/**
	 * generates the game grid
	 * 
	 * @param d difficulty
	 */
	public void generateNewGame(Difficulty d) {
		int[][] start = new int[StaticGameDim.GRIDSIZE][StaticGameDim.GRIDSIZE];
		resetValue();
		resetPossibilityFalse();

		if (d.equals(Difficulty.EASY)) {
			start = Generator.generateEasy();
		} else if (d.equals(Difficulty.MEDIUM)) {
			start = Generator.generateMedium();
		} else if (d.equals(Difficulty.HARD)) {
			start = Generator.generateHard();
		}

		fillStartValues(start);
		try {
			fillSolution(Solver.solve(start));
		} catch (NoSolutionException e) {
			System.out.println(e);
		}

		resetHighlight();
		fileSystem.clear();
		fileSystem.save(game);
	}

	/**
	 * reset every field
	 */
	public void resetGrid() {
		for (int y = 0; y < game.length; y++) {
			for (int x = 0; x < game[0].length; x++) {
				game[y][x].resetAll();
			}
		}
		fileSystem.clear();
	}

	/**
	 * set the active field
	 * 
	 * @param y row
	 * @param x column
	 */
	public void setFieldActive(int y, int x) {
		if (game[y][x].getIsChangeable()) {
			clearFieldActive();
			activeY = y;
			activeX = x;
			game[activeY][activeX].setHighlight(Highlight.H1);
		}
	}

	/**
	 * set the active field to -1
	 */
	public void clearFieldActive() {
		if (activeY != -1 || activeX != -1) {
			game[activeY][activeX].setHighlight(Highlight.NONE);
			activeY = -1;
			activeX = -1;
		}
	}

	/**
	 * set the value at activeY and activeY and saves the current game as a Memento;
	 * if the value has changed
	 * 
	 * @param v value
	 */
	public void setValue(int v) {
		boolean isUnchanged = false;
		if (activeY != -1 || activeX != -1) {
			isUnchanged = game[activeY][activeX].setValue(v);
			game[activeY][activeX].setAllPossibilitiesFalse();
			setPossibilityFalseBox(activeY, activeX, v);
			setPossibilityFalseColumn(activeX, v);
			setPossibilityFalseRow(activeY, v);
		}
		if (!isUnchanged) {
			fileSystem.save(game);
		}
	}

	/**
	 * checks if the game is finished
	 * 
	 * @return boolean
	 */
	public boolean checkForGameWon() {
		for (int y = 0; y < StaticGameDim.GRIDSIZE; y++) {
			for (int x = 0; x < StaticGameDim.GRIDSIZE; x++) {
				if (!game[y][x].isValueCorrect()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * set the possibility of v-1 at acitveY and activeX
	 * 
	 * @param v value
	 * @param b set or reset
	 */
	public void setPossibility(int v, boolean b) {
		if (activeY != -1 || activeX != -1) {
			boolean isPossible = false; // falls v=0 wird b auf true gesetzt
			if (v != 0) {
				isPossible = game[activeY][activeX].getPossibility()[v - 1];
			}
			game[activeY][activeX].setPossibility(v, b != isPossible);
		}
		fileSystem.save(game);
	}

	/**
	 * highlights wrong fields or reset highlight
	 * 
	 * @param b set or reset
	 */
	public void highlightWrongFields(boolean b) {
		if (b) {
			for (int i = 0; i < StaticGameDim.GRIDSIZE; i++) {
				for (int j = 0; j < StaticGameDim.GRIDSIZE; j++) {
					if (game[i][j].getIsChangeable()) {
						if (game[i][j].getValue() == game[i][j].getSolution()) {
							game[i][j].setHighlight(Highlight.GOOD);
						} else {
							game[i][j].setHighlight(Highlight.BAD);
						}
					}
				}
			}
		} else {
			resetHighlight();
		}
		if (activeX != -1) {
			game[activeY][activeX].setHighlight(Highlight.H1);
		}
	}

	/**
	 * restarts the game and clears the fileSystem
	 */
	public void restart() {
		for (int i = 0; i < StaticGameDim.GRIDSIZE; i++) {
			for (int j = 0; j < StaticGameDim.GRIDSIZE; j++) {
				if (game[i][j].getIsChangeable()) {
					game[i][j].setValue(0);
					game[i][j].setAllPossibilitiesFalse();
				}
			}
		}
		fileSystem.clear();
		fileSystem.save(game);
	}

	/**
	 * one step back
	 */
	public void undo() {
		try {
			copyMementoIntoGame(fileSystem.undo());
		} catch (NoSaveDataException e) {
			System.out.println(e);
		}
	}

	/**
	 * one step forward
	 */
	public void redo() {
		try {
			copyMementoIntoGame(fileSystem.redo());
		} catch (NoSaveDataException e) {
			System.out.println(e);
		}
	}

	/**
	 * retrieves the data from Memento into the game
	 * 
	 * @param ModeField[][] Memento
	 */
	private void copyMementoIntoGame(ModelField[][] m) {
		for (int y = 0; y < StaticGameDim.GRIDSIZE; y++) {
			for (int x = 0; x < StaticGameDim.GRIDSIZE; x++) {
				game[y][x].setValue(m[y][x].getValue());

				for (int i = 0; i < 9; i++) {
					game[y][x].setPossibility(i, m[y][x].getPossibilityAt(i));
				}
			}
		}
	}

	/**
	 * set the possibility with the value in the row y to false
	 * 
	 * @param y location row
	 * @param value
	 */
	private void setPossibilityFalseRow(int y, int value) {
		if (value == 0) {
			return;
		}

		for (int tempX = 0; tempX < StaticGameDim.GRIDSIZE; tempX++) {
			game[y][tempX].setPossibility(value, false);
		}
	}

	/**
	 * set the possibility with the value in the column x to false
	 * 
	 * @param x location column
	 * @param value
	 */
	private void setPossibilityFalseColumn(int x, int value) {
		if (value == 0) {
			return;
		}

		for (int tempY = 0; tempY < StaticGameDim.GRIDSIZE; tempY++) {
			game[tempY][x].setPossibility(value, false);
		}
	}

	/**
	 * set the possibility with the value in the box to false
	 * 
	 * @param y location row
	 * @param x location column
	 * @param value
	 */
	private void setPossibilityFalseBox(int y, int x, int value) {
		if (value == 0) {
			return;
		}

		int boxSize = StaticGameDim.BOXSIZE;
		int startY = (y / boxSize) * boxSize; // volitional int division
		int startX = (x / boxSize) * boxSize; // volitional int division

		for (int tempY = startY; tempY < startY + boxSize; tempY++) {
			for (int tempX = startX; tempX < startX + boxSize; tempX++) {
				game[tempY][tempX].setPossibility(value, false);
			}
		}
	}

}
