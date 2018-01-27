package honoursproject.model.movementpatterns;

import honoursproject.GameController;
import honoursproject.model.Movable;
import honoursproject.model.Player;
import honoursproject.util.MovementNoiseApplier;

public class MovementSquare implements Movable {
	private double maxOffset = 5;
	private double currentXOffset = 0;
	private double currentYOffset = 0;
	private int shootingType = 1;

	@Override
	public void move(Player player) {
		// Checks if the element is moving up
		if (player.getYVel() < 0) {
			// Checks if the element has reached the top
			if (player.getYPosition() < 30) {
				// Moves the element left
				player.setYVel(0);
				player.setXVel(-2);
			}
			// Checks if the element is moving down
		} else if (player.getYVel() > 0) {
			// Checks if the element has reached the bottom
			if (player.getBottomSide() > player.getImage().getParent().getLayoutBounds().getHeight() - 30) {
				// Moves the element right
				player.setYVel(0);
				player.setXVel(2);
			}
		}
		// Checks if the element is moving left
		if (player.getXVel() < 0) {
			// Checks if the element has reached the left edge
			if (player.getXPosition() < 30) {
				// Moves the element down
				player.setYVel(2);
				player.setXVel(0);
			}
			// Checks if the element is moving right
		} else if (player.getXVel() > 0) {
			// Checks if the element has reached the right edge
			if (player.getRightSide() > player.getImage().getParent().getLayoutBounds().getWidth() - 30) {
				// Move the element up
				player.setYVel(-2);
				player.setXVel(0);
			}
		}
		// Checks if this element is able to shoot
		if (shootingType == 1) {
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
		player.setXVel(0);
		player.setYVel(2);
	}

	@Override
	public void moveSetUp(Player player, int shootingType) {
		player.setXVel(0);
		player.setYVel(2);
		this.shootingType = shootingType;
	}

	@Override
	public void moveSetUp(Player player, double maxNoise) {
		player.setXVel(0);
		player.setYVel(2);
		this.maxOffset = maxNoise;
	}

	@Override
	public void moveSetUp(Player player, double maxNoise, int shootingType) {
		player.setXVel(0);
		player.setYVel(2);
		this.shootingType = shootingType;
		this.maxOffset = maxNoise;
	}

}
