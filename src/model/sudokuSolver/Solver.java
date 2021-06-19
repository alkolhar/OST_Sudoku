package model.sudokuSolver;

import model.sudokuSolver.solverFunction.*;

public abstract class Solver {

	/**
	 * @param unsolved sudoku grid int[9][9]
	 * @return solved sudoku grid int[9][9]
	 * @throws NoSolutionException if there is no solution for the sudoku
	 */
	public static int[][] solve(int[][] grid) throws NoSolutionException {
		int[][] coverProblem = ExactCover.createExcactCoverProblem(grid);

		ColumnNode header = DancingLinks.createDancingLinks(coverProblem);

		AlgorithmX AX = new AlgorithmX(header);
		AX.solve();

		if (AX.solutions >= 1) {
			int[][] result = DancingLinks.convertDancingLinksToGrid(AX.result);
			return result;
		} else {
			throw new NoSolutionException("There are no solutions for this Sudoku");
		}
	}

	/**
	 * @param unsolved sudoku grid int[9][9]
	 * @return boolean that gives true if there is only one solution. False for no
	 *         or multiple solutions
	 */
	public static boolean checkOnlyOneSolution(int[][] grid) {
		int[][] coverProblem = ExactCover.createExcactCoverProblem(grid);

		ColumnNode header = DancingLinks.createDancingLinks(coverProblem);

		AlgorithmX AX = new AlgorithmX(header);
		AX.solve();

		return AX.solutions == 1;
	}
}
