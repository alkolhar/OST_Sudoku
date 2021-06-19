package enums;

public enum ListenerKey {
	// definition of the Enums
	SHOWWRONGFIELD,
	SHOWHELPNR,
	SHOWSOLUTION,
	AUTOFILLPOSSIBILITY,
	LOAD,
	SAVE,
	RBEASY,
	RBMEDIUM,
	RBHARD,
	UNDO,
	REDO,
	RESTART,
	FOCUSLISTENER,
	KEYBOARDLISTENER,
	NEWGAME,
	;// Enum implementation

	public String getKey() {
		return this.toString();
	}
}
