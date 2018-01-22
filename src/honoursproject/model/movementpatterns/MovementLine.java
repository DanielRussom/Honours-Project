package honoursproject.model.movementpatterns;

import java.util.Random;

import honoursproject.model.Movable;
import honoursproject.model.Player;

public class MovementLine implements Movable {
	double maxOffset = 5;
	double currentXOffset = 0;
	double currentYOffset = 0;
	Random rand = new Random();

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
		player.setXVel(2);
		player.setYVel(0);
	}

	public void moveSetUp(Player player, double maxNoise) {
		player.setXVel(2);
		player.setYVel(0);
		maxOffset = maxNoise;
	}

}
