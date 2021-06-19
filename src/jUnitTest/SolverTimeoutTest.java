package jUnitTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import model.sudokuSolver.Solver;
import model.sudokuSolver.solverFunction.NoSolutionException;
import start.Sudoku;
import org.junit.After;
import org.junit.AfterClass;

/**
 * @author mustafa
 *
 */
public class SolverTimeoutTest {
	int[][] solved = null;

	@BeforeClass
	public static void before() {
		System.out.println("Befor Class");
	}

	@AfterClass
	public static void after() {
		System.out.println("After Class");
	}

	@Before
	public void setUp() {
		System.out.println("Before");
		new Sudoku();
	}

	@After
	public void tearDown() {
		System.out.println("After");
	}

	@Test(timeout = 200)
	@Category(GoodTestsCategory.class)
	public void timeoutTest1() throws NoSolutionException {
		int grid1[][] = { { 3, 0, 6, 5, 0, 8, 4, 0, 0 }, { 5, 2, 0, 0, 0, 0, 0, 0, 0 }, { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
				{ 0, 0, 3, 0, 1, 0, 0, 8, 0 }, { 9, 0, 0, 8, 6, 3, 0, 0, 5 }, { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
				{ 1, 3, 0, 0, 0, 0, 2, 5, 0 }, { 0, 0, 0, 0, 0, 0, 0, 7, 4 }, { 0, 0, 5, 2, 0, 6, 3, 0, 0 } };

		solved = Solver.solve(grid1);
		assertTrue(Solver.checkOnlyOneSolution(grid1));
		for (int i = 0; i < 100; i++) {

			solved = Solver.solve(grid1);

		}
	}

	@Test(timeout = 300)
	@Category(GoodTestsCategory.class)
	public void timoutTest2() throws NoSolutionException {
		int[][] grid2 = { { 6, 0, 0, 4, 0, 0, 0, 5, 3 }, { 0, 0, 1, 8, 0, 0, 0, 0, 0 }, { 2, 0, 0, 0, 0, 0, 7, 0, 0 },
				{ 0, 5, 0, 3, 0, 0, 0, 7, 0 }, { 0, 0, 0, 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 4, 0, 0, 0, 0, 0, 0, 9 }, { 0, 0, 0, 0, 1, 6, 0, 0, 0 }, { 0, 0, 0, 0, 2, 0, 0, 0, 0 } };

		solved = Solver.solve(grid2);

		assertTrue(Solver.checkOnlyOneSolution(grid2));
		for (int i = 0; i < 100; i++) {
			solved = Solver.solve(grid2);
		}
	}

	@Test(timeout = 200)
	@Category(GoodTestsCategory.class)
	public void timeoutTest3() throws NoSolutionException {
		int[][] grid3 = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

		solved = Solver.solve(grid3);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(solved[i][j]);
			}
		}
		System.out.println();

		assertFalse(Solver.checkOnlyOneSolution(grid3));
		for (int i = 0; i < 100; i++) {
			solved = Solver.solve(grid3);
		}
	}

	@Test(expected = NoSolutionException.class)
	@Category({ GoodTestsCategory.class, BadCategory.class })
	public void whensolverIstSolvedthenExceptionIsThrowen() throws NoSolutionException {

		int board[][] = { { 3, 0, 6, 5, 0, 8, 4, 0, 0 }, { 5, 2, 0, 0, 0, 0, 0, 0, 0 }, { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
				{ 0, 0, 3, 0, 1, 0, 0, 8, 0 }, { 9, 0, 0, 8, 6, 3, 0, 0, 5 }, { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
				{ 1, 3, 0, 0, 0, 0, 2, 5, 0 }, { 0, 0, 0, 0, 0, 0, 0, 7, 4 }, { 0, 0, 5, 2, 0, 6, 3, 0, 0 } };

		solved = Solver.solve(board);
		if (solved != null)
			throw new NoSolutionException("the Grid is not Empty");

		assertTrue(Solver.checkOnlyOneSolution(board));

	}
}