package honoursproject.util;

import java.awt.Point;
import java.util.ArrayList;

import honoursproject.GameController;
import honoursproject.Main;
import honoursproject.model.Enemy;
import honoursproject.model.Player;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GameScreenLoader {
	static Player player;
	static Player player2;

	public static void initGameElements(AnchorPane gamePane) {
		ArrayList<Node> nodesToAdd = new ArrayList<Node>();

		for (Node current : gamePane.getChildren()) {
			System.out.println(current.getId());
			if (current.getId() == null) {
				continue;
			}
			if (current.getId().equals("player")) {
				player = new Player();
				player.setXPosition(current.getLayoutX());
				player.setYPosition(current.getLayoutY());
				player.moveTo(new Point((int) player.getXPosition(), (int) player.getYPosition()));
				nodesToAdd.add(player.getImage());
				current.setVisible(!current.isVisible());
				continue;
			}
			if(current.getId().equals("player2")) {
				ImageView currentImage = (ImageView)current;
				player2 = new Player(currentImage.getImage());
				player2.setXPosition(current.getLayoutX());
				player2.setYPosition(current.getLayoutY());
				player2.moveTo(new Point((int) player2.getXPosition(), (int)player2.getYPosition()));
				nodesToAdd.add(player2.getImage());
				current.setVisible(false);
				continue;
			}
			if (current.getId().equals("enemy")) {
				Enemy enemy = new Enemy();
				enemy.setXPosition(current.getLayoutX());
				enemy.setYPosition(current.getLayoutY());
				enemy.moveTo(new Point((int) enemy.getXPosition(), (int) enemy.getYPosition()));
				nodesToAdd.add(enemy.getImage());
				current.setVisible(!current.isVisible());
				GameController.addActiveElement(enemy);
			}
		}
		Main.getGameScreenController().addNode(nodesToAdd);
	}

	public static Player getPlayer() {
		return player;
	}

	public static Player getPlayer2() {
		return player2;
	}
}
