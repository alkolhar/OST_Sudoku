/**
 * 
 */
package jUnitTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import model.sudokuSolver.Solver;
import model.sudokuSolver.solverFunction.NoSolutionException;

/**
 * @author mustafa
 *
 */
public class SolutionTest {
	@Before
	public void setUp() {
		System.out.println("Before Solution");
	}
	@After
	public void after() {
		System.out.println("After Solution");
	}
	@Test
	@Category(GoodTestsCategory.class)
	public void solutionTestOne() throws NoSolutionException {
		//SudokuSolver1 solver = new SudokuSolver1();
		
		int[][] sudoku = { { 8, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 3, 6, 0, 0, 0, 0, 0 }, { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
				{ 0, 5, 0, 0, 0, 7, 0, 0, 0 }, { 0, 0, 0, 0, 4, 5, 7, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
				{ 0, 0, 1, 0, 0, 0, 0, 6, 8 }, { 0, 0, 8, 5, 0, 0, 0, 1, 0 }, { 0, 9, 0, 0, 0, 0, 4, 0, 0 } };

		int[][] solution = { { 8, 1, 2, 7, 5, 3, 6, 4, 9 }, { 9, 4, 3, 6, 8, 2, 1, 7, 5 },
				{ 6, 7, 5, 4, 9, 1, 2, 8, 3 }, { 1, 5, 4, 2, 3, 7, 8, 9, 6 }, { 3, 6, 9, 8, 4, 5, 7, 2, 1 },
				{ 2, 8, 7, 1, 6, 9, 5, 3, 4 }, { 5, 2, 1, 9, 7, 4, 3, 6, 8 }, { 4, 3, 8, 5, 2, 6, 9, 1, 7 },
				{ 7, 9, 6, 3, 1, 8, 4, 5, 2 } };
		
		int[][] result = Solver.solve(sudoku);
		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution.length; j++) {
				Assert.assertEquals(solution[i][j], result[i][j]);
			}
		}
	}
	@Test
	@Category({GoodTestsCategory.class, BadCategory.class})
	public void solutionTestTwo() {
		/*
		 * Hier habe ich zwei werte falsch in Solution geschrieben, damit ich die resultaet überprüfen kann
		 * Solution = 9265714833514862798749235165823671941492583677631>>94<<8252387>>49<<651617835942495612738
		 * Result =   9265714833514862798749235165823671941492583677631>>49<<8252387>>94<<651617835942495612738
		 * */
		int[][] sudoku = { { 9, 0, 6, 0, 7, 0, 4, 0, 3 }, { 0, 0, 0, 4, 0, 0, 2, 0, 0 }, { 0, 7, 0, 0, 2, 3, 0, 1, 0 },
				{ 5, 0, 0, 0, 0, 0, 1, 0, 0 }, { 0, 4, 0, 2, 0, 8, 0, 6, 0 }, { 0, 0, 3, 0, 0, 0, 0, 0, 5 },
				{ 0, 3, 0, 7, 0, 0, 0, 5, 0 }, { 0, 0, 7, 0, 0, 5, 0, 0, 0 }, { 4, 0, 5, 0, 1, 0, 7, 0, 8 }, };

		int[][] solution = { { 9, 2, 6, 5, 7, 1, 4, 8, 3, }, { 3, 5, 1, 4, 8, 6, 2, 7, 9, },
				{ 8, 7, 4, 9, 2, 3, 5, 1, 6, }, { 5, 8, 2, 3, 6, 7, 1, 9, 4, }, { 1, 4, 9, 2, 5, 8, 3, 6, 7, },
				{ 7, 6, 3, 1, 4, 9, 8, 2, 5, }, { 2, 3, 8, 7, 9, 4, 6, 5, 1, }, { 6, 1, 7, 8, 3, 5, 9, 4, 2, },
				{ 4, 9, 5, 6, 1, 2, 7, 3, 8, } };

		int[][] result=  { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
		try {
			result = Solver.solve(sudoku);
		} catch (NoSolutionException e) {	e.printStackTrace();
		}
		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution.length; j++) {
				// the Value should be different : 9
				Assert.assertEquals(solution[i][j], result[i][j]);
				//System.out.print(solution[i][j]);
			}
		}
	}		
}
