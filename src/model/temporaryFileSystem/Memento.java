package model.temporaryFileSystem;

import enums.StaticGameDim;
import model.ModelField;

public class Memento {
	private ModelField[][] spielstand;
	private final static int GRIDSIZE = StaticGameDim.GRIDSIZE;

	/**
	 * @param Data that should be saved
	 */
	public Memento(ModelField[][] grid) {
		if (grid == null) {
			return;
		}

		copyData(grid);
	}

	/**
	 * @return Data
	 */
	public ModelField[][] getState() {
		return spielstand;
	}

	private void copyData(ModelField[][] grid) {
		ModelField[][] copy = new ModelField[grid.length][grid[0].length];

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				copy[row][col] = new ModelField();

				if (grid[row][col] != null) {
					copy[row][col].setValue(grid[row][col].getValue());
					copy[row][col].setStartValue(grid[row][col].getStartValue());
					copy[row][col].setSolution(grid[row][col].getSolution());
					copy[row][col].setHighlight(grid[row][col].getHighlight());

					for (int i = 0; i < GRIDSIZE; i++) {
						copy[row][col].setPossibility(i, grid[row][col].getPossibilityAt(i));
					}
				}
			}
		}

		this.spielstand = copy;
	}
}