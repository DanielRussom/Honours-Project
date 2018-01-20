package honoursproject;

import java.util.ArrayList;

import honoursproject.model.MovingElement;
import honoursproject.model.Player;

public class GameController {
	private static ArrayList<MovingElement> activeElements = new ArrayList<MovingElement>();
	private static ArrayList<MovingElement> elementsToRemove = new ArrayList<MovingElement>();
	private static Player currentPlayer;
	private static Player currentPlayer2;
	public static ArrayList<MovingElement> resetState = new ArrayList<MovingElement>();

	/**
	 * Ends the current game
	 */
	public static void endGame() {
		Main.getFrameThreadController().stop();
		clearActiveElements();
	}

	/**
	 * Manually updates the game by 1 frame
	 */
	public static void manualGameUpdate() {
		// Creates a local copy of current active elements
		ArrayList<MovingElement> localActiveElements = new ArrayList<MovingElement>();
		for (MovingElement current : GameController.getActiveElements()) {
			localActiveElements.add(current);
		}
		// Iterates through each active element
		for (MovingElement current : localActiveElements) {
			// Skips the current element if it is flagged to be removed
			if (GameController.getElementsToRemove().contains(current)) {
				System.out.println("SKIPPED " + current.toString());
				continue;
			}
			// Performs the current element's move
			current.move();
		}
		// Removes all elements flagged to be removed from the global element list
		GameController.getActiveElements().removeAll(GameController.getElementsToRemove());
		for (MovingElement currentNode : GameController.getElementsToRemove()) {
			Main.getGameScreenController().removeNode(currentNode.getImage());
		}
	}

	/**
	 * Returns the active elements ArrayList.
	 * 
	 * @return the activeElements
	 */
	public static ArrayList<MovingElement> getActiveElements() {
		return activeElements;
	}

	/**
	 * Adds the passed in element to the activeElements ArrayList.
	 * 
	 * @param element
	 *            Element to be added to activeElements
	 */
	public static void addActiveElement(MovingElement element) {
		getActiveElements().add(element);
	}

	/**
	 * Clears the list of active elements.
	 */
	public static void clearActiveElements() {
		getActiveElements().clear();
	}

	/**
	 * Returns the elements to remove ArrayList.
	 * 
	 * @return the elementsToRemove
	 */
	public static ArrayList<MovingElement> getElementsToRemove() {
		return elementsToRemove;
	}

	/**
	 * Returns the current player.
	 * 
	 * @return the currentPlayer
	 */
	public static Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Sets the passed in player and adds it to the active elements list.
	 * 
	 * @param currentPlayer
	 *            - first player
	 */
	public static void setCurrentPlayer(Player currentPlayer) {
		GameController.currentPlayer = currentPlayer;
		getActiveElements().add(currentPlayer);
	}

	/**
	 * Returns the current second player.
	 * 
	 * @return the currentPlayer2
	 */
	public static Player getCurrentPlayer2() {
		return currentPlayer2;
	}

	/**
	 * Sets the passed in second player and adds it to the active elements list.
	 * 
	 * @param currentPlayer
	 *            - second player
	 */
	public static void setCurrentPlayer2(Player currentPlayer2) {
		GameController.currentPlayer2 = currentPlayer2;
		getActiveElements().add(currentPlayer2);
	}
}
