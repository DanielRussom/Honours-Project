package honoursproject.model;

import honoursproject.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wall extends MovingElement {

	//TODO Create StationaryElement class?
	public Wall() {
		ImageView tempImage = new ImageView(new Image(Main.class.getResourceAsStream("Wall.png")));
		tempImage.setFitHeight(50);
		tempImage.setFitWidth(50);
		this.image = tempImage;
	}

	public Wall(Image image) {
		ImageView tempImage = new ImageView(image);
		tempImage.setFitHeight(50);
		tempImage.setFitWidth(50);
		this.image = tempImage;
	}

	public void move() {
	}
	
	public void handleBeingHit(MovingElement hitter) {
	}
}
