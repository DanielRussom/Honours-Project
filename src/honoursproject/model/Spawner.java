package honoursproject.model;

import honoursproject.Main;
import honoursproject.model.movementpatterns.MovementDiag;
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
		// TODO Auto-generated method stub
		
	}

}