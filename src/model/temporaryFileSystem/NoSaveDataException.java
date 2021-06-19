package model.temporaryFileSystem;

public class NoSaveDataException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * @param Message why the Exception gets thrown
	 */
	public NoSaveDataException(String msg) {
		super(msg);
	}
}
