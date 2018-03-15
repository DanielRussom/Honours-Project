package honoursproject.model;

import honoursproject.GameController;
import honoursproject.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Spawner extends Element {
	public Spawner() {
		ImageView tempImage = new ImageView(new Image(Main.class.getResourceAsStream("Player2PH.png")));
		tempImage.setFitHeight(50);
		tempImage.setFitWidth(50);
		this.image = tempImage;
	}

	@Override
	public void move() {
	}
	
	@Override
	public void handleBeingHit(Element hitter) {
	}

	/**
	 * Handles spawning an enemy at this spawner's location
	 */
	public void spawnEnemy() {
		// Creates a new enemy
		Enemy enemy = new Enemy();
		// Sets the enemy's position to the spawner's location
		enemy.setXPosition(xPosition);
		enemy.setYPosition(yPosition);
		enemy.moveTo(enemy.getXPosition(), enemy.getYPosition());
		// Adds the enemy to the game screen
		GameController.addActiveElement(enemy);
		Main.getGameScreenController().addNode(enemy.getImage());
	}
}