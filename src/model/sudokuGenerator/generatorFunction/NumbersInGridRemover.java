package model.sudokuGenerator.generatorFunction;

import enums.StaticGameDim;
import model.sudokuSolver.Solver;

public abstract class NumbersInGridRemover {
	private final static int GRIDSIZE = StaticGameDim.GRIDSIZE;
	private final static int NUMBERSINGRID = GRIDSIZE * GRIDSIZE;

	/**
	 * @param int[GRIDSIZE][GRIDSIZE] with already solved grid
	 * @param How                     much numbers shouldn't get deletet from the
	 *                                grid
	 * @return playable sudoku grid with only one solution
	 * @throws GridNotSolvedException if the input is not int[GRIDSIZE][GRIDSIZE]
	 */
	public static int[][] removeRandomNumbersFromSolvedGrid(int[][] grid, int leaveNumbers)
			throws GridNotSolvedException {
		if (!sudokuGridSolved(grid, leaveNumbers)) {
			throw new GridNotSolvedException("Grid isn't solved or isn't " + GRIDSIZE + "x" + GRIDSIZE);
		}

		grid = removeRandomNumbers(grid, leaveNumbers);

		return grid;
	}

	private static boolean sudokuGridSolved(int[][] grid, int leaveNumbers) {
		if (grid.length != GRIDSIZE || grid[0].length != GRIDSIZE) {
			return false;
		}

		for (int row = 0; row < GRIDSIZE; row++)
			for (int col = 0; col < GRIDSIZE; col++)
				if (grid[row][col] <= 0 || grid[row][col] >= GRIDSIZE + 1) {
					return false;
				}
		return true;
	}

	private static int[][] removeRandomNumbers(int[][] grid, int leaveNumbers) {
		int counter = NUMBERSINGRID;
		int col;
		int row;
		int number;
		boolean[][] fieldUsed = new boolean[GRIDSIZE][GRIDSIZE];
		fieldUsed = fill2DBooleanArray(fieldUsed, false);

		while (counter > leaveNumbers) {
			col = RandomNumbers.randomIntFrom0ToParameterMinusOne(GRIDSIZE);
			row = RandomNumbers.randomIntFrom0ToParameterMinusOne(GRIDSIZE);

			if (!fieldUsed[row][col]) {
				fieldUsed[row][col] = true;
				number = grid[row][col];
				grid[row][col] = 0;

				if (Solver.checkOnlyOneSolution(grid)) {
					counter--;
				} else {
					grid[row][col] = number;
				}
			} else {
				try {
					checkAnyPossibilitiesLeft(fieldUsed);
				} catch (MultipleSolutionsException e) {
					System.out.println("NumbersInGridRemover starts from the beginning: " + e);
					counter = NUMBERSINGRID;
					fieldUsed = fill2DBooleanArray(fieldUsed, false);
					grid = RandomSolvedGridGenerator.generateRandomlySolvedGrid();
				}
			}
		}

		return grid;
	}

	private static boolean[][] fill2DBooleanArray(boolean[][] array, boolean bool) {
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++)
				array[i][j] = bool;

		return array;
	}

	private static void checkAnyPossibilitiesLeft(boolean[][] fieldUsed) throws MultipleSolutionsException {
		for (int i = 0; i < fieldUsed.length; i++)
			for (int j = 0; j < fieldUsed[i].length; j++)
				if (!fieldUsed[i][j]) {
					return;
				}
		throw new MultipleSolutionsException("Can't remove more Numbers without generating multiple solutions");
	}
}
