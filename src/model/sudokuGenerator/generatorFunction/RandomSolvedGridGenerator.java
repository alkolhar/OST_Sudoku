package model.sudokuGenerator.generatorFunction;

import enums.StaticGameDim;
import model.sudokuSolver.Solver;
import model.sudokuSolver.solverFunction.NoSolutionException;

public abstract class RandomSolvedGridGenerator {
	private final static int spawnRandomNumbers = 10;// Quantity that gets filled before Sudoku gets solved
	private final static int BOXSIZE = StaticGameDim.BOXSIZE;
	private final static int GRIDSIZE = StaticGameDim.GRIDSIZE;

	/**
	 * @return int[9][9] with a random solved grid
	 */
	public static int[][] generateRandomlySolvedGrid() {
		int[][] grid = filledGridWithZeros(GRIDSIZE, GRIDSIZE);

		grid = fillRandomNumbersIn(grid, spawnRandomNumbers);

		grid = solveSudoku(grid);

		return grid;
	}

	private static int[][] filledGridWithZeros(int row, int col) {
		int[][] grid = new int[row][col];

		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				grid[x][y] = 0;
			}
		}

		return grid;
	}

	private static int[][] fillRandomNumbersIn(int[][] grid, int count) {
		int counter = 0;
		int col;
		int row;
		int number;

		while (counter < count) {
			col = RandomNumbers.randomIntFrom0ToParameterMinusOne(GRIDSIZE);
			row = RandomNumbers.randomIntFrom0ToParameterMinusOne(GRIDSIZE);
			number = RandomNumbers.randomIntFrom0ToParameterMinusOne(GRIDSIZE) + 1;

			if (numberCanBePlaced(grid, row, col, number) && grid[row][col] == 0) {
				grid[row][col] = number;
				counter++;
			}
		}

		return grid;
	}

	/**
	 * @param Search in grid
	 * @param Search in row
	 * @param Search in col
	 * @param If     num is possible
	 * @return boolean if it's possible to place the number
	 */
	private static boolean numberCanBePlaced(int[][] grid, int row, int col, int num) {
		return (!usedInCol(grid, col, num) && !usedInRow(grid, row, num)
				&& !usedInBox(grid, row - row % 3, col - col % 3, num));
	}

	private static boolean usedInRow(int[][] grid, int row, int num) {
		for (int i = 0; i < grid.length; i++) {
			if (grid[row][i] == num) {
				return true;
			}
		}
		return false;
	}

	private static boolean usedInCol(int[][] grid, int col, int num) {
		for (int i = 0; i < grid.length; i++) {
			if (grid[i][col] == num) {
				return true;
			}
		}
		return false;
	}

	private static boolean usedInBox(int[][] grid, int row1Start, int col1Start, int num) {
		for (int row = 0; row < BOXSIZE; row++)
			for (int col = 0; col < BOXSIZE; col++)
				if (grid[row + row1Start][col + col1Start] == num) {
					return true;
				}
		return false;
	}

	private static int[][] solveSudoku(int[][] grid) {
		try {
			grid = Solver.solve(grid);
		} catch (NoSolutionException e) {
			System.out.println("RandomSolvedGridGenerator didn't found a solution for sudoku: " + e);
			grid = generateRandomlySolvedGrid();
		}
		return grid;
	}
}
