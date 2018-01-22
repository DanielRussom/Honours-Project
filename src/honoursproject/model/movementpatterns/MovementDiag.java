package honoursproject.model.movementpatterns;

import java.util.Random;

import honoursproject.model.Movable;
import honoursproject.model.Player;
import honoursproject.util.CollisionDetector;

public class MovementDiag implements Movable {
	private double maxOffset = 5;
	private double currentXOffset = 0;
	private double currentYOffset = 0;
	private Random rand = new Random();

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
		// Checks if there is noise to be applied
		if (maxOffset > 0) {
			// Applies a 5% chance to add noise
			int random = rand.nextInt(20);
			if (random == 0) {
				// Randomly decides which direction to add noise to
				random = rand.nextInt(4);
				switch (random) {
				case 0: 
					// Adds noise to the left
					random = (rand.nextInt((int) maxOffset) + 1) * -1;
					// Caps noise at the set limit
					if (currentXOffset + random < maxOffset * -1) {
						random = (int) ((maxOffset * -1) - currentXOffset);
						currentXOffset = maxOffset * -1;
					} else {
						currentXOffset += random;
					}
					// Applies the noise to the element
					player.addSingleTurnXVel(random);
					break;
				case 1:
					// Adds noise to the right
					random = rand.nextInt((int) maxOffset) + 1;
					// Caps noise at the set limit
					if (currentXOffset + random > maxOffset) {
						random = (int) (maxOffset - currentXOffset);
						currentXOffset = maxOffset;
					} else {
						currentXOffset += random;
					}
					// Applies the noise to the element
					player.addSingleTurnXVel(random);
					break;
				case 2: 
					// Adds noise to the top
					random = (rand.nextInt((int) maxOffset) + 1) * -1;
					// Caps noise at the set limit
					if (currentYOffset + random < maxOffset * -1) {
						random = (int) ((maxOffset * -1) - currentYOffset);
						currentYOffset = maxOffset * -1;
					} else {
						currentYOffset += random;
					}
					// Applies the noise to the element
					player.addSingleTurnYVel(random);
					break;
				case 3: 
					// Adds noise to the bottom
					random = rand.nextInt((int) maxOffset) + 1;
					// Caps noise at the set limit
					if (currentYOffset + random > maxOffset) {
						random = (int) (maxOffset - currentYOffset);
						currentYOffset = maxOffset;
					} else {
						currentYOffset += random;
					}
					// Applies the noise to the element
					player.addSingleTurnYVel(random);
					break;
				}
			}
		}
	}

	@Override
	public void moveSetUp(Player player) {
		player.setXVel(1);
		player.setYVel(3);
	}

	@Override
	public void moveSetUp(Player player, double maxNoise) {
		player.setXVel(1);
		player.setYVel(3);
		this.maxOffset = maxNoise;
	}
}
