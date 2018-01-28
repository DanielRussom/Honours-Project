package honoursproject.model.movementpatterns;

import java.util.Random;

import honoursproject.GameController;
import honoursproject.model.Movable;
import honoursproject.model.Player;
import honoursproject.util.MovementNoiseApplier;

public class MovementLine implements Movable {
	private double maxOffset = 5;
	private double currentXOffset = 0;
	private double currentYOffset = 0;
	private int shootingType = 1;

	@Override
	public void move(Player player) {
		// Checks if the element is moving left
		if (player.getXVel() < 0) {
			// If at the left edge, move right
			if (player.getXPosition() < 30) {
				player.setXVel(2);
			}
			// Checks if the element is moving right
		} else if (player.getXVel() > 0) {
			// If at the right edge, move left
			if (player.getRightSide() > player.getImage().getParent().getLayoutBounds().getWidth() - 30) {
				player.setXVel(-2);
			}
		} 
		// Checks the shooting type of this element
		if (shootingType == 1) {
			Random rand = new Random();
			int direction = rand.nextInt(4);
			// Randomly decides a direction to fire
			switch (direction) {
			case 0:
				player.shoot('U');
				break;
			case 1:
				player.shoot('D');
				break;
			case 2:
				player.shoot('L');
				break;
			case 3:
				player.shoot('R');
				break;
			// INCASE
			default:
				System.out.println("Shoot error");
			}
		} else if (shootingType == 2) {
			// Stores a local copy of the player
			Player target = GameController.getCurrentPlayer();
			// Gets the x and y distances between this element and the target
			double yDistance = player.getVerticalCenter(0) - target.getVerticalCenter(0);
			double xDistance = player.getHorizontalCenter(0) - target.getHorizontalCenter(0);
			// Checks which distance is larger
			if (Math.abs(yDistance) > Math.abs(xDistance)) {
				// Shoots in the corresponding vertical direction
				if (yDistance > 0) {
					player.shoot('U');
				} else {
					player.shoot('D');
				}
			} else {
				// Shoots in the corresponding horizontal direction
				if (xDistance > 0) {
					player.shoot('L');
				} else {
					player.shoot('R');
				}
			}
		}
		// Checks if there is noise to be applied
		if (maxOffset > 0) {
			// Calls a utility to apply noise
			MovementNoiseApplier.applyNoise(player, maxOffset, currentXOffset, currentYOffset);
		}
	}

	@Override
	public void moveSetUp(Player player) {
		player.setXVel(2);
		player.setYVel(0);
	}

	@Override
	public void moveSetUp(Player player, int shootingType) {
		player.setXVel(2);
		player.setYVel(0);
		this.shootingType = shootingType;
	}

	@Override
	public void moveSetUp(Player player, double maxNoise) {
		player.setXVel(2);
		player.setYVel(0);
		maxOffset = maxNoise;
	}

	@Override
	public void moveSetUp(Player player, double maxNoise, int shootingType) {
		player.setXVel(2);
		player.setYVel(0);
		this.shootingType = shootingType;
		maxOffset = maxNoise;
	}

}
