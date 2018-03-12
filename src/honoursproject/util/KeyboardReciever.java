package honoursproject.util;

import java.util.ArrayList;

import honoursproject.GameController;
import honoursproject.Main;
import honoursproject.model.Player;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardReciever {
	static ArrayList<KeyCode> pressedKeys = new ArrayList<KeyCode>();

	/**
	 * Handles key press events.
	 * 
	 * @param key
	 *            - key pressed
	 */
	public static void handleKeyPress(KeyEvent key) {
		// Ignore input if no humans are playing
		if (GameController.getCurrentPlayer() == null) {
			return;
		}
		// Ignore input if key is already pressed
		if (pressedKeys.contains(key.getCode())) {
			return;
		}

		Player currentPlayer = GameController.getCurrentPlayer();
		Player currentPlayer2 = GameController.getCurrentPlayer2();
		// Stores the key that was pressed
		pressedKeys.add(key.getCode());
		// TODO Find a way to implement switch
		// Add corresponding velocity for WASD keys
		if (key.getCode() == KeyCode.A) {
			currentPlayer.addXVel(currentPlayer.getMoveSpeed()*-1);
		} else if (key.getCode() == KeyCode.D) {
			currentPlayer.addXVel(currentPlayer.getMoveSpeed());
		} else if (key.getCode() == KeyCode.W) {
			currentPlayer.addYVel(currentPlayer.getMoveSpeed()*-1);
		} else if (key.getCode() == KeyCode.S) {
			currentPlayer.addYVel(currentPlayer.getMoveSpeed());
		} else if (key.getCode() == KeyCode.LEFT) {
			// Shoot in the corresponding direction to arrow keys
			currentPlayer.shoot('L');
		} else if (key.getCode() == KeyCode.RIGHT) {
			currentPlayer.shoot('R');
		} else if (key.getCode() == KeyCode.UP) {
			currentPlayer.shoot('U');
		} else if (key.getCode() == KeyCode.DOWN) {
			currentPlayer.shoot('D');
		} else if (key.getCode() == KeyCode.O) {
			Main.isSingleFrameActive = !Main.isSingleFrameActive;
		} else if (key.getCode() == KeyCode.P) {
			Main.isThisFrameRequested = true;
		} else if (key.getCode() == KeyCode.F) {
			System.out.println(GameController.getActiveElements());
		} else if (key.getCode() == KeyCode.NUMPAD8 || key.getCode() == KeyCode.DIGIT8) {
			if (currentPlayer2 != null) {
				currentPlayer2.addYVel(currentPlayer2.getMoveSpeed()*-1);
			}
		} else if (key.getCode() == KeyCode.NUMPAD5 || key.getCode() == KeyCode.DIGIT5) {
			if (currentPlayer2 != null) {
				currentPlayer2.addYVel(currentPlayer2.getMoveSpeed());
			}
		} else if (key.getCode() == KeyCode.NUMPAD4 || key.getCode() == KeyCode.DIGIT4) {
			if (currentPlayer2 != null) {
				currentPlayer2.addXVel(currentPlayer2.getMoveSpeed()*-1);
			}
		} else if (key.getCode() == KeyCode.NUMPAD6 || key.getCode() == KeyCode.DIGIT6) {
			if (currentPlayer2 != null) {
				currentPlayer2.addXVel(currentPlayer.getMoveSpeed());
			}
		} else if (key.getCode() == KeyCode.NUMPAD1 || key.getCode() == KeyCode.DIGIT1) {
			if (currentPlayer2 != null) {
				currentPlayer2.shoot('L');
			}
		} else if (key.getCode() == KeyCode.NUMPAD7 || key.getCode() == KeyCode.DIGIT7) {
			if (currentPlayer2 != null) {
				currentPlayer2.shoot('U');
			}
		} else if (key.getCode() == KeyCode.NUMPAD3 || key.getCode() == KeyCode.DIGIT3) {
			if (currentPlayer2 != null) {
				currentPlayer2.shoot('D');
			}
		} else if (key.getCode() == KeyCode.NUMPAD9 || key.getCode() == KeyCode.DIGIT9) {
			if (currentPlayer2 != null) {
				currentPlayer2.shoot('R');
			}
		} else if(key.getCode()==KeyCode.V) {
			GameController.spawnEnemy();
		}
	}

	/**
	 * Handles key release events.
	 * 
	 * @param key
	 *            - key released
	 */
	public static void handleKeyRelease(KeyEvent key) {
		// Ignore input if no humans are playing
		if (GameController.getCurrentPlayer() == null) {
			return;
		}
		if(!pressedKeys.contains(key.getCode())){
			return;
		}
		Player currentPlayer = GameController.getCurrentPlayer();
		Player currentPlayer2 = GameController.getCurrentPlayer2();
		// Removes storage of released key
		pressedKeys.remove(key.getCode());
		// Add corresponding velocity for WASD keys
		if (key.getCode() == KeyCode.A) {
			currentPlayer.addXVel(currentPlayer.getMoveSpeed());
		} else if (key.getCode() == KeyCode.D) {
			currentPlayer.addXVel(currentPlayer.getMoveSpeed()*-1);
		} else if (key.getCode() == KeyCode.W) {
			currentPlayer.addYVel(currentPlayer.getMoveSpeed());
		} else if (key.getCode() == KeyCode.S) {
			currentPlayer.addYVel(currentPlayer.getMoveSpeed()*-1);
		} else if (key.getCode() == KeyCode.NUMPAD8 || key.getCode() == KeyCode.DIGIT8) {
			if (currentPlayer2 != null) {
				currentPlayer2.addYVel(2);
			}
		} else if (key.getCode() == KeyCode.NUMPAD5 || key.getCode() == KeyCode.DIGIT5) {
			if (currentPlayer2 != null) {
				currentPlayer2.addYVel(-2);
			}
		} else if (key.getCode() == KeyCode.NUMPAD4 || key.getCode() == KeyCode.DIGIT4) {
			if (currentPlayer2 != null) {
				currentPlayer2.addXVel(2);
			}
		} else if (key.getCode() == KeyCode.NUMPAD6 || key.getCode() == KeyCode.DIGIT6) {
			if (currentPlayer2 != null) {
				currentPlayer2.addXVel(-2);
			}
		}
	}

}
