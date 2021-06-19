package model.sudokuGenerator.generatorFunction;

public class MultipleSolutionsException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * @param Message why the Exception gets thrown
	 */
	public MultipleSolutionsException(String msg) {
		super(msg);
	}
}
