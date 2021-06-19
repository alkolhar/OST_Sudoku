package model.sudokuGenerator.generatorFunction;

public class GridNotSolvedException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * @param Message why the Exception gets thrown
	 */
	public GridNotSolvedException(String msg) {
		super(msg);
	}
}
