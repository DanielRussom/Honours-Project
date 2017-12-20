package honoursproject.view;

import java.util.Optional;

import honoursproject.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

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
