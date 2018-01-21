package honoursproject.view;

import honoursproject.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {
	@FXML
	private Button btnQuit;

	public void playGame() {
		Main.showGameRoot();
		Main.showGameScreen();
	}

	public void playTwoPlayer() {
		Main.showGameRoot();
		Main.showTwoPlayerMap();
	}

	public void showTrainingMenu() {
		Main.showTrainingMenu();
	}

	public void quit() {
			Platform.exit();
	}
}
