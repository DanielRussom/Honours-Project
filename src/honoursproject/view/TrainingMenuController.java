package honoursproject.view;

import honoursproject.Main;
import javafx.fxml.FXML;

public class TrainingMenuController {

	@FXML
	public void playTrainingArea2() throws Throwable {
		Main.showGameRoot();
		Main.showTrainingArea2();
	}
	
	@FXML
	public void playTrainingArea3() throws Throwable {
		Main.showGameRoot();
		Main.showTrainingArea3();
	}
	
	public void showMainMenu() {
		Main.showMainMenu();
	}
}
