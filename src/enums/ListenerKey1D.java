package enums;

public enum ListenerKey1D {
	// definition of the Enums
	NUMPADLISTENER,
	;// Enum implementation

	public String getKey(int id) {
		return this.toString() + id;
	}
}
