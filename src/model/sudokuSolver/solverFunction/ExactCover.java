package model.sudokuSolver.solverFunction;

import java.util.Arrays;
import enums.StaticGameDim;

public abstract class ExactCover {
	private final static int GRIDSIZE = StaticGameDim.GRIDSIZE;
	private final static int BOXSIZE = StaticGameDim.BOXSIZE;
	private final static int MAXNUMBER = StaticGameDim.GRIDSIZE;
	private final static int CONSTRAINTS = 4; // The number of Rules/Constraints in a Sudoku

	/**
	 * @param Sudoku grid thats should get convertet to a cover matrix
	 * @return Cover matrix
	 */
	public static int[][] createExcactCoverProblem(int[][] grid) {
		int[][] coverMatrix = createCoverProblem();

		coverMatrix = removePossibilitiesFromExistingNumbers(coverMatrix, grid);

		return coverMatrix;
	}

	private static int[][] createCoverProblem() {
		int[][] coverMatrix = new int[GRIDSIZE * GRIDSIZE * MAXNUMBER][GRIDSIZE * GRIDSIZE * CONSTRAINTS];
		int header = 0;
		header = createCellConstraints(coverMatrix, header);
		header = createRowConstraints(coverMatrix, header);
		header = createColumnConstraints(coverMatrix, header);
		createBoxConstraints(coverMatrix, header);

		return coverMatrix;
	}

	private static int createCellConstraints(int[][] matrix, int header) {
		for (int row = 1; row <= GRIDSIZE; row++) {
			for (int column = 1; column <= GRIDSIZE; column++) {
				for (int n = 1; n <= GRIDSIZE; n++) {
					int index = indexInCoverProblem(row, column, n);
					matrix[index][header] = 1;
				}
				header++;
			}
		}

		return header;
	}

	private static int indexInCoverProblem(int row, int column, int num) {
		return (row - 1) * GRIDSIZE * GRIDSIZE + (column - 1) * GRIDSIZE + (num - 1);
	}

	private static int createRowConstraints(int[][] matrix, int header) {
		for (int row = 1; row <= GRIDSIZE; row++) {
			for (int n = 1; n <= GRIDSIZE; n++) {
				for (int column = 1; column <= GRIDSIZE; column++) {
					int index = indexInCoverProblem(row, column, n);
					matrix[index][header] = 1;
				}
				header++;
			}
		}

		return header;
	}

	private static int createColumnConstraints(int[][] matrix, int header) {
		for (int column = 1; column <= GRIDSIZE; column++) {
			for (int n = 1; n <= GRIDSIZE; n++) {
				for (int row = 1; row <= GRIDSIZE; row++) {
					int index = indexInCoverProblem(row, column, n);
					matrix[index][header] = 1;
				}
				header++;
			}
		}

		return header;
	}

	private static int createBoxConstraints(int[][] matrix, int header) {
		for (int row = 1; row <= GRIDSIZE; row += BOXSIZE) {
			for (int column = 1; column <= GRIDSIZE; column += BOXSIZE) {
				for (int n = 1; n <= GRIDSIZE; n++) {
					for (int rowDelta = 0; rowDelta < BOXSIZE; rowDelta++) {
						for (int columnDelta = 0; columnDelta < BOXSIZE; columnDelta++) {
							int index = indexInCoverProblem(row + rowDelta, column + columnDelta, n);
							matrix[index][header] = 1;
						}
					}
					header++;
				}
			}
		}

		return header;
	}

	private static int[][] removePossibilitiesFromExistingNumbers(int[][] coverMatrix, int[][] grid) {
		for (int row = 1; row <= GRIDSIZE; row++) {
			for (int column = 1; column <= GRIDSIZE; column++) {
				int n = grid[row - 1][column - 1];

				// If there is a number in the Field
				if (n != 0) {
					for (int num = 1; num <= GRIDSIZE; num++) {
						// remove all other numbers
						if (num != n) {
							Arrays.fill(coverMatrix[indexInCoverProblem(row, column, num)], 0);
						}
					}
				}
			}
		}
		return coverMatrix;
	}
}
