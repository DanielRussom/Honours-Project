package honoursproject.model.movementpatterns;

import java.util.Random;

import honoursproject.model.Movable;
import honoursproject.model.Player;

public class MovementDiag implements Movable {
	double maxOffset = 5;
	double currentXOffset = 0;
	double currentYOffset = 0;
	Random rand = new Random();

	@Override
	public void move(Player player) {
		if (player.getYVel() < 0) {
			if (player.getYPosition() < 30) {
				player.setYVel(player.getYVel() * -1);
			}
		} else if (player.getYVel() > 0) {

			// if (player.getBottomSide() >
			// player.getImage().getParent().getLayoutBounds().getHeight() - 30) {
			//TODO Workaround
			if (player.getBottomSide() > 375 - 30) {
				player.setYVel(player.getYVel() * -1);
			}

		}
		if (player.getXVel() < 0) {
			if (player.getXPosition() < 30) {
				player.setXVel(player.getXVel() * -1);
			}
		} else if (player.getXVel() > 0) {
			// if (player.getRightSide() >
			// player.getImage().getParent().getLayoutBounds().getWidth() - 30) {
			if (player.getRightSide() > 575 - 30) {
				player.setXVel(player.getXVel() * -1);
			}
		}
		if (maxOffset > 0)

		{
			int random = rand.nextInt(20);
			if (random == 0) {
				random = rand.nextInt(4);
				switch (random) {
				case 0: // -X
					currentXOffset += (maxOffset * -1);
					player.addSingleTurnXVel(maxOffset * -1);
					break;
				case 1: // +X
					currentXOffset += maxOffset;
					player.addSingleTurnXVel(maxOffset);
					break;
				case 2: // -Y

					currentYOffset += (maxOffset * -1);
					player.singleTurnYVel += (maxOffset * -1);

					break;
				case 3: // +Y
					player.singleTurnYVel = maxOffset;
					currentXOffset += maxOffset;
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

	public void moveSetUp(Player player, double maxNoise) {

	}
}
