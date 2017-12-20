package honoursproject.model.movementpatterns;

import honoursproject.model.Movable;
import honoursproject.model.Player;

public class MovementSquare implements Movable {

	@Override
	public void move(Player player) {
		if(player.getYVel()<0) {
			if(player.getYPosition() < 30) {
				player.setYVel(0);
				player.setXVel(-2);
			}
		} else if(player.getYVel()>0) {
			if(player.getBottomSide() > player.getImage().getParent().getLayoutBounds().getHeight()-30) {
				player.setYVel(0);
				player.setXVel(2);
			}
		}
		if(player.getXVel()<0) {
			if(player.getXPosition() < 30) {
				player.setYVel(2);
				player.setXVel(0);
			}
		} else if(player.getXVel()>0) {
			if(player.getRightSide() > player.getImage().getParent().getLayoutBounds().getWidth()-30) {
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
