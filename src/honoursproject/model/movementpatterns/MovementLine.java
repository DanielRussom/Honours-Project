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
		if (maxOffset > 0) {
			int random = rand.nextInt(20);
			if (random == 0) {
				random = rand.nextInt(4);
				switch (random) {
				case 0: // -X
					break;
				case 1: // +X
					break;
				case 2: // -Y

					currentYOffset += (maxOffset * -1);
					player.addSingleTurnYVel((int) (maxOffset * -1));

					break;
				case 3: // +Y
					player.addSingleTurnYVel((int) maxOffset);
					currentXOffset += maxOffset;
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
