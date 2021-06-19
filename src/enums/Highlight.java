package enums;

import java.awt.Color;

public enum Highlight {
	// definition of the Enums
	NOTSELECTABLE(Color.lightGray),
	NONE(new Color(237, 237, 237)), // very lightGray
	GOOD(Color.green),
	BAD(Color.red),
	H1(Color.yellow),
	H2(Color.orange),
	;// Enum implementation

	public final Color color;

	private Highlight(Color color) {
		this.color = color;
	}
}
