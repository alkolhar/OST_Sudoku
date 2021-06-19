package enums;

public enum ListenerKey2D {
	// definition of the Enums
	FIELDLISTENER,
	;// Enum implementation

	public String getKey(int y, int x) {
		return this.toString() + y + "" + x;
	}
}
