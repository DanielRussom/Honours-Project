package honoursproject.view;

import honoursproject.GameController;
import honoursproject.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class GameRootLayoutController {
 
	
	
	@FXML
	private BorderPane pane;
	
	@FXML
	private Label lblPlayerHealth;
	
	@FXML
	private TextField testMultiple;
	
	@FXML
	private TextField testValue;
	
	public void setGameScreen(AnchorPane gameScreen){
		pane.setCenter(null);
		pane.setCenter(gameScreen);
	}
	
	public void setLblPlayerHealthValue(int health) {
		lblPlayerHealth.setText("Player Health: " + health + "/" + "100");
	}
	
	public void setValues() {
		Main.testValue = Integer.parseInt(testValue.getText());
		Main.testMultiple = Double.parseDouble(testMultiple.getText());
	}
	
	public void returnToMenu() {
		Main.showMainMenu();
		GameController.endGame();
	}
}
