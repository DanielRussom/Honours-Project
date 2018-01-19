package honoursproject.model;

import honoursproject.Main;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wall extends Element {
	protected Node image;
	protected double xPosition = 0;
	protected double yPosition = 0;

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

	public void handleBeingHit(Element hitter) {
		System.out.println(this.toString() + " was hit by " + hitter.toString());
	}
}
