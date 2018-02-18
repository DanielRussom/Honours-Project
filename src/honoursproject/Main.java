package honoursproject;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.anji.neat.Evolver;

import honoursproject.util.GameScreenLoader;
import honoursproject.util.KeyboardReciever;
import honoursproject.view.GameRootLayoutController;
import honoursproject.view.GameScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage;
	private static BorderPane rootLayout;
	private static GameScreenController gameScreenController;
	private static GameRootLayoutController gameRootLayoutController;
	private static FrameThreadController frameThreadController;
	public static boolean isSingleFrameActive = false;
	public static boolean isThisFrameRequested = false;
	public static boolean test = true;
	public static double testMultiple = 0.75;
	public static double testValue = 20;
	private static Logger logger = Logger.getLogger(Evolver.class);

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	/**
	 * Starts the application.
	 * 
	 * @param primaryStage
	 *            - the primary stage for this application.
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("XT-003");
		initRootLayout();
		showMainMenu();
	}

	/**
	 * Initializes the application's root layout.
	 */
	private void initRootLayout() {
		try {
			// Load root layout from FXML file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			// Show the scene containing the root layout
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			// Adds keyboard event handlers
			scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
				KeyboardReciever.handleKeyPress(key);
			});
			scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
				KeyboardReciever.handleKeyRelease(key);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the main menu.
	 */
	public static void showMainMenu() {
		try {
			// Load the main menu from FXML file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainMenu.fxml"));
			AnchorPane mainMenu = (AnchorPane) loader.load();
			// Displays the main menu in the center of the root layout
			rootLayout.setCenter(mainMenu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the training menu.
	 */
	public static void showTrainingMenu() {
		try {
			// Load the main menu from FXML file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/TrainingMenu.fxml"));
			AnchorPane trainingMenu = (AnchorPane) loader.load();
			// Displays the main menu in the center of the root layout
			rootLayout.setCenter(trainingMenu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the root for the game screen.
	 */
	public static void showGameRoot() {
		try {
			// Load the game root from FXML file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/GameRootLayout.fxml"));
			BorderPane gameRoot = (BorderPane) loader.load();
			gameRootLayoutController = (GameRootLayoutController) loader.getController();
			// Displays the game root in the center of the root layout
			rootLayout.setCenter(gameRoot);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the game screen.
	 */
	public static void showGameScreen() {
		try {
			// Load the game screen from FXML file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/GameScreen.fxml"));
			AnchorPane gameScreen = (AnchorPane) loader.load();
			gameScreenController = (GameScreenController) loader.getController();
			// Displays the game screen in the center of the game root layout
			gameRootLayoutController.setGameScreen(gameScreen);
			// Initializes the game elements from the passed in pane
			GameScreenLoader.initGameElements(gameScreen);
			// GameController.setCurrentPlayer(GameScreenLoader.getPlayer());
			// Begins the FrameThreadController
			setFrameThreadController(new FrameThreadController());
			getFrameThreadController().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the two player map
	 */
	public static void showTwoPlayerMap() {
		try {
			// Load the game screen from FXML file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/TwoPlayerMap.fxml"));
			AnchorPane gameScreen = (AnchorPane) loader.load();
			gameScreenController = (GameScreenController) loader.getController();
			// Displays the game screen in the center of the game root layout
			gameRootLayoutController.setGameScreen(gameScreen);
			// Initializes the game elements from the passed in pane
			GameScreenLoader.initGameElements(gameScreen);
			// GameController.setCurrentPlayer(GameScreenLoader.getPlayer());
			// GameController.setCurrentPlayer2(GameScreenLoader.getPlayer2());
			// Begins the FrameThreadController
			setFrameThreadController(new FrameThreadController());
			getFrameThreadController().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the second training area.
	 * 
	 * @throws Throwable
	 */
	public static void showTrainingArea1() throws Throwable {
		try {
			GameController.clearActiveElements();
			// Load the training area from FXML file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/TrainingArea.fxml"));
			AnchorPane trainingArea = (AnchorPane) loader.load();
			gameScreenController = (GameScreenController) loader.getController();
			// Displays the game screen into the center of game root layout
			gameRootLayoutController.setGameScreen(trainingArea);
			// Initializes the game elements from the passed in pane
			GameScreenLoader.initGameElements(trainingArea);
			// GameController.setCurrentPlayer(GameScreenLoader.getPlayer());
			// Begins the FrameThreadController
			setFrameThreadController(new FrameThreadController());
			getFrameThreadController().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the third training area.
	 * 
	 * @throws Throwable
	 */
	public static void showTrainingArea2() throws Throwable {
		try {
			GameController.clearActiveElements();
			// Load the training area from FXML file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/TrainingArea.fxml"));
			AnchorPane trainingArea = (AnchorPane) loader.load();
			gameScreenController = (GameScreenController) loader.getController();
			// Displays the game screen into the center of game root layout
			gameRootLayoutController.setGameScreen(trainingArea);
			// Initializes the game elements from the passed in pane
			GameScreenLoader.initGameElements(trainingArea);
			// GameController.setCurrentPlayer(GameScreenLoader.getPlayer());
			// Begins the FrameThreadController
			setFrameThreadController(new FrameThreadController());
			getFrameThreadController().start();
			getFrameThreadController().triggerEvolver = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the third training area.
	 * 
	 * @throws Throwable
	 */
	public static void showTrainingArea3() throws Throwable {
		try {
			//TODO
			GameController.clearActiveElements();
			// Load the training area from FXML file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/TrainingArea.fxml"));
			AnchorPane trainingArea = (AnchorPane) loader.load();
			gameScreenController = (GameScreenController) loader.getController();
			// Displays the game screen into the center of game root layout
			gameRootLayoutController.setGameScreen(trainingArea);
			// Initializes the game elements from the passed in pane
			GameScreenLoader.initGameElements(trainingArea);
			// GameController.setCurrentPlayer(GameScreenLoader.getPlayer());
			// Begins the FrameThreadController
			/*
			 * setFrameThreadController(new FrameThreadController());
			 * getFrameThreadController().start(); getFrameThreadController().triggerEvolver
			 * = true;
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the current primary stage.
	 * 
	 * @return primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Returns the game screen controller.
	 * 
	 * @return gameScreenController
	 */
	public static GameScreenController getGameScreenController() {
		return gameScreenController;
	}

	/**
	 * Returns the game root layout controller.
	 * 
	 * @return gameRootLayoutController
	 */
	public static GameRootLayoutController getGameRootLayoutController() {
		return gameRootLayoutController;
	}

	/**
	 * Returns the frame thread controller.
	 * 
	 * @return frameThreadController
	 */
	public static FrameThreadController getFrameThreadController() {
		return frameThreadController;
	}

	/**
	 * Sets the frame thread controller
	 * 
	 * @param frameThreadController
	 *            - Frame thread controller
	 */
	public static void setFrameThreadController(FrameThreadController frameThreadController) {
		Main.frameThreadController = frameThreadController;
	}

	/**
	 * Sets the game screen controller
	 * 
	 * @param controller
	 *            - Game Screen Controller
	 */
	public static void setGameScreenController(GameScreenController controller) {
		gameScreenController = controller;
	}
}