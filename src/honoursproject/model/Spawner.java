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
		System.out.println("An enemy should be spawned at " + xPosition + ":" + yPosition);
		Enemy enemy = new Enemy();
		enemy.setXPosition(xPosition);
		enemy.setYPosition(yPosition);
		enemy.moveTo(enemy.getXPosition(), enemy.getYPosition());
		GameController.addActiveElement(enemy);
		Main.getGameScreenController().addNode(enemy.getImage());
	}
}