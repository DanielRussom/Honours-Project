package honoursproject.model.movementpatterns;

import honoursproject.GameController;
import honoursproject.model.Movable;
import honoursproject.model.Player;
import honoursproject.util.CollisionDetector;
import honoursproject.util.MovementNoiseApplier;

public class MovementDiag implements Movable {
	private boolean isShootingEnabled = true;
	private double maxOffset = 5;
	private double currentXOffset = 0;
	private double currentYOffset = 0;

	@Override
	public void move(Player player) {
		// Checks if this element is moving up
		if (player.getYVel() < 0) {
			// If too high, the element starts moving downwards
			if (player.getYPosition() < 30) {
				player.setYVel(player.getYVel() * -1);
			}
			// Checks if this element is moving downwards
		} else if (player.getYVel() > 0) {
			// If too low, the element starts moving up
			if (player.getBottomSide() > CollisionDetector.getContainerHeight() - 30) {
				player.setYVel(player.getYVel() * -1);
			}

		}
		// Checks if the element is moving left
		if (player.getXVel() < 0) {
			// If too far left, the element starts moving right
			if (player.getXPosition() < 30) {
				player.setXVel(player.getXVel() * -1);
			}
			// Checks if this element is moving right
		} else if (player.getXVel() > 0) {
			// If too far right, the element starts moving left
			if (player.getRightSide() > CollisionDetector.getContainerWidth() - 30) {
				player.setXVel(player.getXVel() * -1);
			}
		}
		// Checks if this element is able to shoot
		if(isShootingEnabled) {
			//TODO
			player.shoot('L');
		}
		// Checks if there is noise to be applied
		if (maxOffset > 0) {
			// Calls a utility to apply noise
			MovementNoiseApplier.applyNoise(player, maxOffset, currentXOffset, currentYOffset);
		}
	}

	@Override
	public void moveSetUp(Player player) {
		player.setXVel(1);
		player.setYVel(3);
	}
	

	@Override
	public void moveSetUp(Player player, boolean isShootingEnabled) {
		player.setXVel(1);
		player.setYVel(3);
		this.isShootingEnabled = isShootingEnabled;
	}

	@Override
	public void moveSetUp(Player player, double maxNoise) {
		player.setXVel(1);
		player.setYVel(3);
		this.maxOffset = maxNoise;
	}
	
	@Override
	public void moveSetUp(Player player, double maxNoise, boolean isShootingEnabled) {
		player.setXVel(1);
		player.setYVel(3);
		this.isShootingEnabled = isShootingEnabled;
		this.maxOffset = maxNoise;
	}

}
