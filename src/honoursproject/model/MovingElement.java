package honoursproject.model;

import java.awt.Point;

import honoursproject.GameController;
import honoursproject.Main;
//import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class MovingElement extends Element {
	protected double xVel = 0;
	protected double xVelDecimal = 0;
	protected double yVel = 0;
	protected double yVelDecimal = 0;
	protected double moveSpeed = 2;
	protected final int SHOOTCOOLDOWN = 30;
	protected int currentShootCooldown = 0;
	protected int damage;

	public MovingElement() {
		// TODO PH?
	}

	public MovingElement(Node image) {
		super();
		this.image = image;
		setXPosition(image.getLayoutX() + image.getTranslateX());
		setYPosition(image.getLayoutY() + image.getTranslateY());
	}

	/**
	 * Moves this element to the specified location.
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
	 * Moves this element to the specified location.
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
	 * Updates the location of this element's image to the stored x and y positions.
	 */
	public void updatePosition() {
		image.setTranslateX(0);
		image.setTranslateY(0);
		image.setLayoutX(xPosition);
		image.setLayoutY(yPosition);
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
	 * Returns the y coordinate needed to align an object with this element
	 * vertically.
	 * 
	 * @param objectHeight
	 *            - height of object
	 * @return y coordinate for vertical center alignment
	 */
	public double getVerticalCenter(double objectHeight) {
		double verticalCenter = getYPosition() + (image.getLayoutBounds().getHeight() / 2);
		verticalCenter -= (objectHeight / 2);
		return verticalCenter;
	}

	/**
	 * Returns the x coordinate needed to align an object with this element
	 * horizontally.
	 * 
	 * @param objectWidth
	 *            - width of object
	 * @return x coordinate for horizontal center alignment
	 */
	public double getHorizontalCenter(double objectWidth) {
		double horizontalCenter = getXPosition() + (image.getLayoutBounds().getHeight() / 2);
		horizontalCenter -= (objectWidth / 2);
		return horizontalCenter;
	}

	/**
	 * Refreshes the cooldown for shooting
	 */
	public void resetShootCooldown() {
		currentShootCooldown = SHOOTCOOLDOWN;
	}

	/**
	 * Returns the current cooldown for shooting
	 * 
	 * @return the current shooting cooldown
	 */
	public int getCurrentShootCooldown() {
		return currentShootCooldown;
	}

	/**
	 * Fires a projectile from this object.
	 * 
	 * @param shootDirection
	 *            - Single character to select direction of projectile, where L is
	 *            left, R is right, U is up, and D is down
	 */
	public void shoot(char shootDirection) {
		if (getCurrentShootCooldown() > 0) {
			return;
		}
		int projectileXVel = 0;
		int projectileYVel = 0;
		// Sets the projectile velocity
		switch (shootDirection) {
		case 'L':
			projectileXVel = -1;
			break;
		case 'R':
			projectileXVel = 1;
			break;
		case 'U':
			projectileYVel = -1;
			break;
		case 'D':
			projectileYVel = 1;
			break;
		}
		// Creates a new projectile and adds it to the screen
		Projectile projectile = new Projectile(this, projectileXVel, projectileYVel);
		Main.getGameScreenController().addNode(projectile.getImage());
		GameController.addActiveElement(projectile);
		image.toFront();
		resetShootCooldown();
	}

	/**
	 * Checks wall collision
	 * 
	 * @param newPosition
	 * @return
	 */
	public Point checkCollision(Point newPosition) {
		double containerWidth = image.getParent().getLayoutBounds().getWidth();
		double containerHeight = image.getParent().getLayoutBounds().getHeight();
		// TODO Tidy up
		Point updatedPosition = newPosition;
		if (newPosition.getX() < 0) {
			updatedPosition.setLocation(0, updatedPosition.getY());
		} else if (getRightSide() > containerWidth) {
			updatedPosition.setLocation(containerWidth - image.getLayoutBounds().getWidth(), updatedPosition.getY());
		}
		if (newPosition.getY() < 0) {
			updatedPosition.setLocation(updatedPosition.getX(), 0);
		} else if (getBottomSide() > containerHeight) {
			updatedPosition.setLocation(updatedPosition.getX(), containerHeight - image.getLayoutBounds().getHeight());
		}
		return updatedPosition;
	}

	// TODO Come up with better method name
	public void handleBeingHit(MovingElement hitter) {
		System.out.println("Default hit! - This should be overridden!");
		System.out.println(this.toString() + " was hit by " + hitter.toString());
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
	 * @param image
	 *            the image to set
	 */
	public void setImage(ImageView image) {
		this.image = image;
	}

	public void move() {
		System.out.println("Move PH");
	}

	/**
	 * @return the xVel
	 */
	public double getXVel() {
		return xVel;
	}

	/**
	 * @param xVel
	 *            the xVel to set
	 */
	public void setXVel(double xVel) {
		this.xVel = xVel;
	}

	/**
	 * @param value
	 *            the value to add to xVel
	 */
	public void addXVel(double value) {
		this.xVel += value;
	}

	/**
	 * @return the yVel
	 */
	public double getYVel() {
		return yVel;
	}

	/**
	 * @param yVel
	 *            the yVel to set
	 */
	public void setYVel(double yVel) {
		this.yVel = yVel;
	}

	/**
	 * @param value
	 *            the value to add to yVel
	 */
	public void addYVel(double value) {
		this.yVel += value;
	}

	public double getXPosition() {
		return xPosition;
	}

	public void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getYPosition() {
		return yPosition;
	}

	public void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public double getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(double moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
}
