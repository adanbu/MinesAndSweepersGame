package mines;

import javafx.scene.control.Button;

public class buttons extends Button {
	private int x, y;

	public buttons(String str, int x, int y) {// constructor for the buttons class
		super(str);
		this.x = x;
		this.y = y;
	}

	public int getX() {//returns x
		return x;
	}

	public int getY() {// returns y
		return y;
	}
}
