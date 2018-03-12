package honoursproject.model;

import java.awt.Point;

import honoursproject.GameController;
import honoursproject.Main;
import honoursproject.util.CollisionDetector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends MovingElement{

	protected int health = 100;
	protected int meleeDamage = 2;
	protected double xKnockBack = 0;
	protected double yKnockBack = 0;
	protected double singleTurnXVel = 0;
	protected double singleTurnYVel = 0;
	protected Movable moveBehaviour;

	public Player(Image image) {
		ImageView tempImage = new ImageView(image);
		damage = 5;
		tempImage.setFitHeight(50);
		tempImage.setFitWidth(50);
		this.image = tempImage;
	}

	public Player() {
		ImageView tempImage = new ImageView(new Image(Main.class.getResourceAsStream("PlayerPH.png")));
		damage = 5;
		tempImage.setFitHeight(50);
		tempImage.setFitWidth(50);
		this.image = tempImage;
	}

	public double reduceKnockBack(double knockBackVel) {
		if (knockBackVel > 0) {
			knockBackVel = Math.floor(knockBackVel * Main.testMultiple);
			if (knockBackVel < 0) {
				knockBackVel = 0;
			}
		} else if (knockBackVel < 0) {
			knockBackVel = Math.ceil(knockBackVel * Main.testMultiple);
			if (knockBackVel > 0) {
				knockBackVel = 0;
			}
		}
		return knockBackVel;
	}

	@Override
	public void move() {
		// Update the shooting cooldown
		if (currentShootCooldown > 0) {
			currentShootCooldown -= 1;
		}
		double updatedXVel = xVel + xKnockBack + singleTurnXVel;
		double updatedYVel = yVel + yKnockBack + singleTurnYVel;
		singleTurnXVel = 0;
		singleTurnYVel = 0;

		// Handle knockback for x and y velocities
		xKnockBack = reduceKnockBack(xKnockBack);
		yKnockBack = reduceKnockBack(yKnockBack);

		// Reduce diagonal speed
		if (updatedXVel != 0 && updatedYVel != 0) {
			updatedXVel = updatedXVel*0.60;
			updatedYVel = updatedYVel*0.60;
			
			
			//updatedXVel = updatedXVel / 2;
			//updatedYVel = updatedYVel / 2;
		}

		xVelDecimal += updatedXVel % 1;
		//System.out.println(updatedXVel%1 + " " + xVelDecimal + " " + updatedXVel);
		if(xVelDecimal>=1||xVelDecimal<=-1) {
			int wholeNumber = (int) (xVelDecimal-(xVelDecimal%1));
			xVelDecimal-=wholeNumber;
			updatedXVel+=wholeNumber;
		}
		if(updatedXVel < 0) {
			updatedXVel = Math.ceil(updatedXVel);
		} else if (updatedXVel > 0) {
			updatedXVel = Math.floor(updatedXVel);
		}
		yVelDecimal += updatedYVel % 1;
//		System.out.println(updatedXVel%1 + " " + xVelDecimal + " " + updatedXVel);
		if(yVelDecimal>=1||yVelDecimal<=-1) {
			int wholeNumber = (int) (yVelDecimal-(yVelDecimal%1));
			yVelDecimal-=wholeNumber;
			updatedYVel+=wholeNumber;
		}
		if(updatedYVel < 0) {
			updatedYVel = Math.ceil(updatedYVel);
		} else if (updatedYVel > 0) {
			updatedYVel = Math.floor(updatedYVel);
		}
		//System.out.println(updatedXVel);
		setXPosition(getXPosition() + updatedXVel);
		setYPosition(getYPosition() + updatedYVel);
		
		Point newPosition = new Point((int) getXPosition(), (int) getYPosition());
		newPosition = checkCollision(newPosition);
		
		setXPosition(newPosition.getX());
		setYPosition(newPosition.getY());
		updatePosition();
	}

		/**
		 * Moves left for a single turn
		 */
		public void moveLeft() {
			singleTurnXVel -= moveSpeed;
		}

		/**
		 * Moves right for a single turn
		 */
		public void moveRight() {
			singleTurnXVel += moveSpeed;
		}

		/**
		 * @return the health
		 */
		public int getHealth() {
			return health;
		}

		/**
		 * Moves down for a single turn
		 */
		public void moveDown() {
			singleTurnYVel += moveSpeed;
		}

		/**
		 * Moves up for a single turn
		 */
		public void moveUp() {
			singleTurnYVel -= moveSpeed;
		}
	
	public Point checkCollision(Point newPosition) {
		CollisionDetector.handleBoundaryCollision(this);
		Point test = new Point((int) getXPosition(), (int) getYPosition());
		for (Element current : GameController.getActiveElements()) {
			if (current.equals(this)) {
				continue;
			}
			if (current instanceof Projectile && ((Projectile) current).shooter.equals(this)) {
				continue;
			}
			//Checks for collision
			if(collidesWith(current)) {
				if (current instanceof Projectile) {
					handleBeingHit((MovingElement) current);
					current.handleBeingHit(this);
					continue;
				}
				int xOffset = 1000;
				int yOffset = 1000;
				if (xVel < 0) {
					xOffset = (int) (getXPosition() - current.getRightSide());
				} else if (xVel > 0) {
					xOffset = (int) (getRightSide() - current.getXPosition());
				}
				if (yVel < 0) {
					yOffset = (int) (getYPosition() - current.getBottomSide());
				} else if (yVel > 0) {
					yOffset = (int) (getBottomSide() - current.getYPosition());
				}
				if (Math.abs(xOffset) < Math.abs(yOffset)) {
					test.x -= xOffset;
				} else if (Math.abs(yOffset) < Math.abs(xOffset)) {
					test.y -= yOffset;
				} else {
					if (xOffset != 500 || yOffset != 500) {
						test.x -= xOffset;
						test.y -= yOffset;
					} else {
						// TODO Error handling - This only occurs if a play has collided while still
					}
				}
				setXPosition(test.x);
				setYPosition(test.y);
				handleBeingHit(current);
				current.handleBeingHit(this);
			}
		}
		return test;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * honoursproject.model.Element#handleBeingHit(honoursproject.model.Element)
	 */
	@Override
	public void handleBeingHit(Element hitter) {
		if (hitter instanceof Projectile) {
			Projectile hittingProjectile = (Projectile) hitter;
			health -= hittingProjectile.damage;
		} else if (hitter instanceof Player) {
			// TODO Tidy melee combat
			// Player hittingPlayer = (Player) hitter;
			health -= ((Player) hitter).meleeDamage;
			if (hitter.getXPosition() == getRightSide()) {
				xKnockBack = Main.testValue * -1;
			} else if (getXPosition() == hitter.getRightSide()) {
				xKnockBack = Main.testValue;
			}
			if (hitter.getYPosition() == getBottomSide()) {
				yKnockBack = Main.testValue * -1;
				//System.out.println("YVEL");
			} else if (getYPosition() == hitter.getBottomSide()) {
				yKnockBack = Main.testValue;
				//System.out.println("YVEL-");
			}
		}
		//System.out.println(toString() + "'s health is now: " + health);
		if (this.equals(GameController.getCurrentPlayer())) {
			Main.getGameRootLayoutController().setLblPlayerHealthValue(health);
		}
		if (health <= 0) {
			GameController.getElementsToRemove().add(this);
		}
	}
	

	/**
	 * @return the moveBehaviour
	 */
	public Movable getMoveBehaviour() {
		return moveBehaviour;
	}

	/**
	 * @param moveBehaviour the moveBehaviour to set
	 */
	public void setMoveBehaviour(Movable moveBehaviour) {
		this.moveBehaviour = moveBehaviour;
		moveBehaviour.moveSetUp(this);
	}

	public void addSingleTurnXVel(double xVelIncrease) {
		singleTurnXVel += xVelIncrease;
		
	}

	public void addSingleTurnYVel(int yVelIncrease) {
		singleTurnYVel += yVelIncrease;		
	}
}