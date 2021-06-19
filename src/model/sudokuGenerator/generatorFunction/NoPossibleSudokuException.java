package model.sudokuGenerator.generatorFunction;

public class NoPossibleSudokuException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * @param Message why the Exception gets thrown
	 */
	public NoPossibleSudokuException(String msg) {
		super(msg);
	}
}
