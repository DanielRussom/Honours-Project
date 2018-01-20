package honoursproject.model;

import java.awt.Point;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public abstract class Element implements Cloneable {
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

	public Element(Node image) {
		super();
		this.image = image;
		setXPosition(image.getLayoutX() + image.getTranslateX());
		setYPosition(image.getLayoutY() + image.getTranslateY());
	}
	
	/**
	 * 
	 */
	public abstract void move();

	/**
	 * Moves this element to the specified location point.
	 * 
	 * @param newLocation
	 *            - the new location for this element
	 */
	public void moveTo(Point newLocation) {
		image.setTranslateX(0);
		image.setTranslateY(0);
		image.setLayoutX(newLocation.getX());
		image.setLayoutY(newLocation.getY());
	}

	/**
	 * Moves this element to the specified location coordinates.
	 * 
	 * @param newX
	 *            - the new X coordinate for this element
	 * @param newY
	 *            - the new Y coordinate for this element
	 */
	public void moveTo(double newX, double newY) {
		image.setTranslateX(0);
		image.setTranslateY(0);
		image.setLayoutX(newX);
		image.setLayoutY(newY);
	}

	/**
	 * Returns the element's image.
	 * 
	 * @return the image
	 */
	public Node getImage() {
		return image;
	}

	/**
	 * Sets the element's image
	 * 
	 * @param image
	 *            - the image to set
	 */
	public void setImage(ImageView image) {
		this.image = image;
	}

	/**
	 * Gets the current X coordinate
	 * 
	 * @return the xPosition
	 */
	public double getXPosition() {
		return xPosition;
	}

	/**
	 * Sets the current X coordinate
	 * 
	 * @param xPosition
	 *            - The new X coordinate
	 */
	public void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * Gets the current Y coordinate
	 * 
	 * @return the yPosition
	 */
	public double getYPosition() {
		return yPosition;
	}

	/**
	 * Sets the current Y coordinate
	 * 
	 * @param yPosition
	 *            - The new Y coordinate
	 */
	public void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}
}
