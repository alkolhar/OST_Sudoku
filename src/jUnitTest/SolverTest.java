/**
 * 
 */
package jUnitTest;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import model.sudokuSolver.Solver;
import model.sudokuSolver.solverFunction.NoSolutionException;

/**
 * @author mustafa
 *
 */
public class SolverTest {
	@Test
	@Category(GoodTestsCategory.class)
	public void solveTest() throws NoSolutionException {
		int[][] puzzle = { { 0, 0, 8, 6, 0, 4, 3, 0, 0 }, { 0, 4, 0, 0, 7, 0, 0, 1, 0 }, { 7, 0, 0, 0, 0, 0, 0, 0, 2 },
				{ 4, 0, 0, 0, 8, 0, 0, 0, 9 }, { 0, 1, 0, 9, 0, 5, 0, 3, 0 }, { 3, 0, 0, 0, 2, 0, 0, 0, 1 },
				{ 6, 0, 0, 0, 0, 0, 0, 0, 5 }, { 0, 5, 0, 0, 3, 0, 0, 9, 0 }, { 0, 0, 2, 1, 0, 6, 7, 0, 0 } };

		int[][] expResult = { { 2, 9, 8, 6, 1, 4, 3, 5, 7 }, { 5, 4, 6, 2, 7, 3, 9, 1, 8 },
				{ 7, 3, 1, 5, 9, 8, 4, 6, 2 }, { 4, 2, 5, 3, 8, 1, 6, 7, 9 }, { 8, 1, 7, 9, 6, 5, 2, 3, 4 },
				{ 3, 6, 9, 4, 2, 7, 5, 8, 1 }, { 6, 7, 3, 8, 4, 9, 1, 2, 5 }, { 1, 5, 4, 7, 3, 2, 8, 9, 6 },
				{ 9, 8, 2, 1, 5, 6, 7, 4, 3 } };

		int[][] result = Solver.solve(puzzle);
		compare(expResult, result);

	}

	private void compare(int[][] expResult, int[][] result) {

		for (int i = 0; i < expResult.length; i++) {
			for (int j = 0; j < expResult[0].length; j++) {
				assertEquals(expResult[i][j], result[i][j]);
			}
		}
		return;
	}

	static boolean solve(int i, int j, int[][] cells) {
		if (i == 9) {
			i = 0;
			if (++j == 9)
				return true;
		}
		if (cells[i][j] != 0) // skip filled cells
			return solve(i + 1, j, cells);

		for (int val = 1; val <= 9; ++val) {
			if (legal(i, j, val, cells)) {
				cells[i][j] = val;
				if (solve(i + 1, j, cells))
					return true;
			}
		}
		cells[i][j] = 0; // reset on backtrack
		return false;
	}

	static boolean legal(int i, int j, int val, int[][] cells) {
		for (int k = 0; k < 9; ++k) // row
			if (val == cells[k][j])
				return false;

		for (int k = 0; k < 9; ++k) // col
			if (val == cells[i][k])
				return false;

		int boxRowOffset = (i / 3) * 3;
		int boxColOffset = (j / 3) * 3;
		for (int k = 0; k < 3; ++k) // box
			for (int m = 0; m < 3; ++m)
				if (val == cells[boxRowOffset + k][boxColOffset + m])
					return false;

		return true; // no violations, so it's legal
	}

	@Test
	@Category(GoodTestsCategory.class)
	public void solveCheckOnlyOneSolutionTest() {
		// False for no or multiple solutions
		int[][] sudo = { { 9, -1, 3, -1, 1, -1, 7, -1, -1 }, { -1, 6, -1, -1, -1, 8, 5, -1, -1 },
				{ -1, 5, -1, -1, -1, -1, -1, 9, 6 }, { -1, -1, -1, -1, 3, -1, 6, -1, 4 },
				{ -1, 4, -1, 9, -1, 1, 3, 7, 2 }, { -1, -1, -1, 8, 7, 4, -1, 1, -1 },
				{ -1, -1, 5, -1, -1, 3, 2, -1, 1 }, { 6, 8, 7, -1, -1, -1, -1, -1, -1 },
				{ 3, -1, -1, -1, -1, -1, -1, 5, -1 } };
		assertFalse(Solver.checkOnlyOneSolution(sudo));
	}
}
