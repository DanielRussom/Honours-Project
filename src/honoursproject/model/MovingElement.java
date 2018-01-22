package honoursproject.model;

import honoursproject.GameController;
import honoursproject.Main;
import javafx.scene.Node;

public abstract class MovingElement extends Element {
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
		super(image);
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

	/**
	 * Gets the current movement speed
	 * 
	 * @return the movement speed
	 */
	public double getMoveSpeed() {
		return moveSpeed;
	}

	/**
	 * Sets the current movement speed
	 * 
	 * @param moveSpeed
	 *            - The new movement speed
	 */
	public void setMoveSpeed(double moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
}
