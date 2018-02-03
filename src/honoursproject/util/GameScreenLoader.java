package honoursproject.util;

import java.awt.Point;
import java.util.ArrayList;

import honoursproject.GameController;
import honoursproject.Main;
import honoursproject.model.Enemy;
import honoursproject.model.Player;
import honoursproject.model.Wall;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GameScreenLoader {
	private static Player player;
	private static Player player2;

	/**
	 * Initialises and stores the preset game elements
	 * 
	 * @param gamePane
	 *            - Game screen to be initialised from
	 */
	public static void initGameElements(AnchorPane gamePane) {
		ArrayList<Node> nodesToAdd = new ArrayList<Node>();
		// Iterates through each node on the gamePane
		for (Node current : gamePane.getChildren()) {
			// Ignores nodes within an ID
			if (current.getId() == null) {
				continue;
			}
			// Initialises the player element
			if (current.getId().equals("player")) {
				player = new Player();
				player.setXPosition(current.getLayoutX());
				player.setYPosition(current.getLayoutY());
				player.moveTo(new Point((int) player.getXPosition(), (int) player.getYPosition()));
				nodesToAdd.add(player.getImage());
				current.setVisible(!current.isVisible());
				Main.getGameRootLayoutController().setLblPlayerHealthValue(player.getHealth());
				GameController.setCurrentPlayer(player);
				player.setPlayer1(true);
				continue;
			}
			// Initialises the second player
			if (current.getId().equals("player2")) {
				ImageView currentImage = (ImageView) current;
				player2 = new Player(currentImage.getImage());
				player2.setXPosition(current.getLayoutX());
				player2.setYPosition(current.getLayoutY());
				player2.moveTo(new Point((int) player2.getXPosition(), (int) player2.getYPosition()));
				nodesToAdd.add(player2.getImage());
				current.setVisible(false);
				GameController.setCurrentPlayer2(player2);
				continue;
			}
			// Initialises enemies
			if (current.getId().equals("enemy")) {
				Enemy enemy = new Enemy();
				enemy.setXPosition(current.getLayoutX());
				enemy.setYPosition(current.getLayoutY());
				enemy.moveTo(new Point((int) enemy.getXPosition(), (int) enemy.getYPosition()));
				nodesToAdd.add(enemy.getImage());
				current.setVisible(!current.isVisible());
				GameController.addActiveElement(enemy);
			}
			if(current.getId().equals("wall")) {
				Wall wall = new Wall();
				wall.setXPosition(current.getLayoutX());
				wall.setYPosition(current.getLayoutY());
				wall.moveTo(new Point((int) wall.getXPosition(), (int) wall.getYPosition()));
				nodesToAdd.add(wall.getImage());
				current.setVisible(false);
				GameController.addActiveElement(wall);
			}
		}
		Main.getGameScreenController().addNode(nodesToAdd);
	}
}
