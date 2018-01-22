package honoursproject.model.movementpatterns;

import honoursproject.model.Movable;
import honoursproject.model.Player;

public class MovementSquare implements Movable {

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
	}

	@Override
	public void moveSetUp(Player player) {
		player.setXVel(0);
		player.setYVel(2);
	}

	public void moveSetUp(Player player, double maxNoise) {

	}

}
