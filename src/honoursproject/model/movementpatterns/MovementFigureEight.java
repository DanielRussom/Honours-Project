package honoursproject.model.movementpatterns;

import honoursproject.model.Movable;
import honoursproject.model.Player;

public class MovementFigureEight implements Movable {

	boolean isMovingUp = true;
	boolean isInTop = true;

	@Override
	public void move(Player player) {
		double containerHeight = player.getImage().getParent().getLayoutBounds().getHeight();
		double containerWidth = player.getImage().getParent().getLayoutBounds().getWidth();

		if (player.getYVel() < 0) {
			if (player.getYPosition() < 25) {
				player.setYVel(0);
				player.setXVel(2);
				isMovingUp = false;
			} else if (!isInTop && player.getYPosition() < containerHeight / 2) {
				System.out.println("2");
				player.setYVel(0);
				player.setXVel(-2);
				isInTop = true;
			}
		} else if (player.getYVel() > 0) {
			if (player.getBottomSide() > containerHeight - 30) {
				player.setYVel(0);
				player.setXVel(2);
				isMovingUp = true;
			} else if (isInTop && player.getBottomSide() > containerHeight / 2) {
				System.out.println("4 AAAAAAAAAA" + containerHeight);
				player.setYVel(0);
				player.setXVel(-2);
				isInTop = false;
			}
		}
		if (player.getXVel() < 0) {
			if (player.getXPosition() < 30) {
				player.setXVel(0);
				if (isMovingUp) {
					player.setYVel(-2);
				} else {
					player.setYVel(2);
				}
			}
		} else if (player.getXVel() > 0) {
			if (player.getRightSide() > containerWidth - 30) {
				player.setXVel(0);
				if (isMovingUp) {
					player.setYVel(-2);
				} else {
					player.setYVel(2);
				}
			}
		}
	}

	@Override
	public void moveSetUp(Player player) {
		player.setXVel(0);
		player.setYVel(-2);
	}
	
public void moveSetUp(Player player, double maxNoise) {
		
	}
}
