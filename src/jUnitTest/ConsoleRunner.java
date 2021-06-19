package jUnitTest;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public class ConsoleRunner {
	public static void main(String arg[]) {

		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));

		junit.run(SolverTimeoutTest.class);
		junit.run(ModelGridTest.class);
		junit.run(SolutionTest.class);
		junit.run(SolverTest.class);
		junit.run(AlteTest.class);
		junit.run(SudokuSinglesTest.class);
	}
}
