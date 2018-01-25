package honoursproject.model.movementpatterns;

import honoursproject.model.Movable;
import honoursproject.model.Player;
import honoursproject.util.MovementNoiseApplier;

public class MovementLine implements Movable {
	private double maxOffset = 5;
	private double currentXOffset = 0;
	private double currentYOffset = 0;

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

	public void moveSetUp(Player player, double maxNoise) {
		player.setXVel(2);
		player.setYVel(0);
		maxOffset = maxNoise;
	}

}
