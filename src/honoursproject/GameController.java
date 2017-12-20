package honoursproject;

import java.util.ArrayList;

import honoursproject.model.Element;
import honoursproject.model.Player;

public class GameController {
	private static ArrayList<Element> activeElements = new ArrayList<Element>();
	private static ArrayList<Element> elementsToRemove = new ArrayList<Element>();
	private static Player currentPlayer;
	private static Player currentPlayer2;

	public static void endGame() {
		Main.getFrameThreadController().stop();
		clearActiveElements();
	}

	/**
	 * Returns the active elements ArrayList.
	 * 
	 * @return the activeElements
	 */
	public static ArrayList<Element> getActiveElements() {
		return activeElements;
	}

	/**
	 * Adds the passed in element to the activeElements ArrayList.
	 * 
	 * @param element
	 *            Element to be added to activeElements
	 */
	public static void addActiveElement(Element element) {
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
	public static ArrayList<Element> getElementsToRemove() {
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
