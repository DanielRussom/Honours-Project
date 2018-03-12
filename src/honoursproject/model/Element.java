package honoursproject.model;

import java.awt.Point;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public abstract class Element implements Cloneable {
	protected double xPosition = 0;
	protected double yPosition = 0;
	protected Node image;
	protected boolean isPlayer1 = false;

	/**
	 * @return the isPlayer1
	 */
	public boolean isPlayer1() {
		return isPlayer1;
	}

	/**
	 * @param isPlayer1
	 *            the isPlayer1 to set
	 */
	public void setPlayer1(boolean isPlayer1) {
		this.isPlayer1 = isPlayer1;
	}

	// INCASE
	@Override
	public Element clone() throws CloneNotSupportedException {
		return (Element) super.clone();
	}

	public Element() {
	}

	public Element(Node image) {
		super();
		this.image = image;
		setXPosition(image.getLayoutX() + image.getTranslateX());
		setYPosition(image.getLayoutY() + image.getTranslateY());
	}

	/**
	 * Handles receiving a hit
	 * 
	 * @param hitter
	 *            - The element hitting this element
	 */
	public void handleBeingHit(Element hitter) {
		System.out.println("Default hit! - This should be overridden!");
		System.out.println(this.toString() + " was hit by " + hitter.toString());
	}

	/**
	 * Handles this element's movement for the next frame
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

	/**
	 * Returns the x coordinate of element's right side.
	 * 
	 * @return element's right side x coordinate
	 */
	public double getRightSide() {
		return getXPosition() + image.getLayoutBounds().getWidth();
	}

	/**
	 * Returns the x coordinate of the element's right side, if it were at the
	 * location passed in.
	 * 
	 * @param location
	 *            - location of element
	 * @return element's right side x coordinate at specified location
	 */
	public double getRightSide(Point location) {
		return location.getX() + image.getLayoutBounds().getWidth();
	}

	/**
	 * Returns the y coordinate of element's bottom side.
	 * 
	 * @return element's bottom side y coordinate
	 */
	public double getBottomSide() {
		return getYPosition() + image.getLayoutBounds().getHeight();
	}

	/**
	 * Returns the y coordinate of the element's bottom side, if it were at the
	 * location passed in.
	 * 
	 * @param location
	 *            - location of element
	 * @return element's bottom side y coordinate at specified location
	 */
	public double getBottomSide(Point location) {
		return location.getY() + image.getLayoutBounds().getHeight();
	}

	/**
	 * Checks for collision between this element and the passed in element
	 * 
	 * @param element
	 *            - passed in element to check for collision against
	 * @return - if collision is occurring
	 */
	public boolean collidesWith(Element element) {
		if (getXPosition() >= element.getRightSide() || getRightSide() <= element.getXPosition()) {
			return false;
		} else if (getYPosition() >= element.getBottomSide() || getBottomSide() <= element.getYPosition()) {
			return false;
		}
		return true;
	}
}
