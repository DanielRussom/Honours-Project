package honoursproject.model;

import javafx.scene.Node;

public class Element implements Cloneable {
	protected double xPosition = 0;
	protected double yPosition = 0;
	protected Node image;

	// INCASE
	@Override
	public MovingElement clone() throws CloneNotSupportedException {
		return (MovingElement) super.clone();
	}

	public Element() {
		// TODO Auto-generated constructor stub
	}

}
