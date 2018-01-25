package honoursproject.util;

import java.util.Random;

import honoursproject.model.Player;

public class MovementNoiseApplier {
	 static Random rand = new Random();

	public static void applyNoise(Player player, double maxOffset, double currentXOffset, double currentYOffset) {
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
