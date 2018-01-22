package honoursproject.model;

import honoursproject.Main;
import honoursproject.model.movementpatterns.MovementSquare;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy extends Player{

	public Enemy() {
		//TODO Seperate constructor to pass in enemy image?
		ImageView tempImage = new ImageView(new Image(Main.class.getResourceAsStream("enemy.png")));
		tempImage.setFitHeight(50);
		tempImage.setFitWidth(50);
		this.image = tempImage;
		damage = 5;
		setMoveBehaviour(new MovementSquare());
		
	}

	@Override
	public void move() {
		moveBehaviour.move(this);
		super.move();
	}
}
