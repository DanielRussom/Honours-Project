package honoursproject.model;

import java.awt.Point;

import honoursproject.GameController;
import honoursproject.Main;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Projectile extends MovingElement {
	final static int DEFAULTVELOCITY = 4;
	MovingElement shooter;

	public Projectile(ImageView image) {
		super(image);
		this.setXPosition(0);
		this.setYPosition(0);

		// image.setX(1);
		// image.setY(1);
		// //image.setFill(Color.WHITE);
	}

	public Projectile(MovingElement shooter, int xVel, int yVel) {
		super();
		this.shooter = shooter;
		Rectangle imageRectangle = new Rectangle(5, 5);

		if (xVel != 0) {
			imageRectangle = new Rectangle(10, 4);
		} else if (yVel != 0) {
			imageRectangle = new Rectangle(4, 10);
		}

		// this.xPosition = Shooter.xPosition;
		this.setXPosition(shooter.getHorizontalCenter(imageRectangle.getLayoutBounds().getWidth()));
		this.setYPosition(shooter.getVerticalCenter(imageRectangle.getLayoutBounds().getHeight()));

		if (xVel < 0) {
			this.setXPosition(shooter.getXPosition() - imageRectangle.getLayoutBounds().getWidth());
		} else if (xVel > 0) {
			this.setXPosition(shooter.getRightSide());
		} else if (yVel < 0) {
			this.setYPosition(shooter.getYPosition() - imageRectangle.getLayoutBounds().getHeight());
		} else if (yVel > 0) {
			this.setYPosition(shooter.getBottomSide());
		}
		imageRectangle.setX(0);
		imageRectangle.setY(0);
		damage = shooter.damage;
		this.xVel = xVel * DEFAULTVELOCITY;
		this.yVel = yVel * DEFAULTVELOCITY;
		imageRectangle.setFill(Color.BLUE);
		this.image = imageRectangle;
		moveTo(new Point((int) this.getXPosition(), (int) this.getYPosition()));
		// TODO Fix, or at least neaten, this work around
		if (getXPosition() < 0 || getRightSide() > shooter.image.getParent().getLayoutBounds().getWidth()) {
			((Rectangle) image).setWidth(0);
		}
		if (getYPosition() < 0 || getBottomSide() > shooter.image.getParent().getLayoutBounds().getWidth()) {
			((Rectangle) image).setHeight(0);
		}
		// checkCollision();
	}

	/**
	 * @return the shooter
	 */
	public MovingElement getShooter() {
		return shooter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see honoursproject.model.Element#move()
	 */
	@Override
	public void move() {
		setXPosition(getXPosition() + xVel);
		setYPosition(getYPosition() + yVel);
		moveTo(new Point((int) getXPosition(), (int) getYPosition()));
		checkCollision();
	}

	public void checkCollision() {
		double containerWidth = getImage().getParent().getLayoutBounds().getWidth();
		double containerHeight = getImage().getParent().getLayoutBounds().getHeight();
		Rectangle projectileImage = (Rectangle) getImage();
		for (MovingElement current : GameController.getActiveElements()) {
			if (current.equals(shooter) || current.equals(this)) {
				continue;
			}
			if (current instanceof Projectile && ((Projectile) current).shooter.equals(this.shooter)) {
				continue;
			}
			if (getXPosition() >= current.getRightSide() || getRightSide() <= current.getXPosition()) {
			} else if (getYPosition() >= current.getBottomSide() || getBottomSide() <= current.getYPosition()) {
			} else {
				handleBeingHit(current);
				current.handleBeingHit(this);
			}
		}

		System.out.println(getBottomSide() + " " + containerWidth);
		if (getXPosition() < 0) {
			projectileImage.setWidth(projectileImage.getWidth() + getXPosition());
			setXPosition(0);
			updatePosition();
			if (projectileImage.getWidth() < 1) {
				GameController.getElementsToRemove().add(this);
			}
		} else if (getRightSide() > containerWidth) {
			projectileImage.setWidth(projectileImage.getWidth() - (getRightSide() - containerWidth));
			setXPosition(containerWidth - projectileImage.getWidth());
			updatePosition();
			if (projectileImage.getWidth() < 1) {
				GameController.getElementsToRemove().add(this);
			}
		}
		if (getYPosition() < 0) {
			projectileImage.setHeight(projectileImage.getHeight() + getYPosition());
			setYPosition(0);
			updatePosition();
			if (projectileImage.getHeight() < 1) {
				GameController.getElementsToRemove().add(this);
			}
		} else if (getBottomSide() > containerHeight) {
			projectileImage.setHeight(projectileImage.getHeight() - (getBottomSide() - containerHeight));
			setYPosition(containerHeight - projectileImage.getHeight());
			updatePosition();
			if (projectileImage.getHeight() < 1) {
				GameController.getElementsToRemove().add(this);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * honoursproject.model.Element#handleBeingHit(honoursproject.model.Element)
	 */
	@Override
	public void handleBeingHit(MovingElement hitter) {
		GameController.getElementsToRemove().add(this);
	}

}
