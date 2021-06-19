package model.sudokuSolver.solverFunction;

public class NoSolutionException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * @param Message why the Exception gets thrown
	 */
	public NoSolutionException(String msg) {
		super(msg);
	}

}
