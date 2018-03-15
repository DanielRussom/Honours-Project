package honoursproject;

import java.util.ArrayList;
import java.util.Random;

import honoursproject.model.Element;
import honoursproject.model.Player;
import honoursproject.model.Spawner;

public class GameController {
	private static ArrayList<Element> activeElements = new ArrayList<Element>();
	private static ArrayList<Element> elementsToRemove = new ArrayList<Element>();
	public static ArrayList<Spawner> enemySpawnPoints = new ArrayList<Spawner>();
	private static Player currentPlayer;
	private static Player currentPlayer2;
	public static ArrayList<Element> resetState = new ArrayList<Element>();

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
		ArrayList<Element> localActiveElements = new ArrayList<Element>();
		for (Element current : GameController.getActiveElements()) {
			localActiveElements.add(current);
		}
		// Iterates through each active element
		for (Element current : localActiveElements) {
			// Skips the current element if it is flagged to be removed
			if (GameController.getElementsToRemove().contains(current)) {
				continue;
			}
			// Performs the current element's move
			current.move();
		}
		// Removes all elements flagged to be removed from the global element list
		GameController.getActiveElements().removeAll(GameController.getElementsToRemove());
		for (Element currentNode : GameController.getElementsToRemove()) {
			Main.getGameScreenController().removeNode(currentNode.getImage());
		}
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
	 *            - Element to be added to activeElements
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
	 * Adds the passed in spawner to the enemySpawnPoints ArrayList.
	 * 
	 * @param spawner
	 *            - Spawner to be added to enemySpawnPoints
	 */
	public static void addSpawner(Spawner spawner) {
		enemySpawnPoints.add(spawner);
		System.out.println("Added!");
	}

	/**
	 * Spawns an enemy at a random valid spawn point
	 */
	public static void spawnEnemy() {
		System.out.println("Triggered");
		ArrayList<Spawner> validSpawnPoints = new ArrayList<Spawner>();
		for(Spawner currentSpawn: enemySpawnPoints) {
			for(Element currentElement : activeElements) {
				if(!currentSpawn.collidesWith(currentElement)) {
					validSpawnPoints.add(currentSpawn);
				}
			}
			//TODO Weighted Spawn Points
		}
		//TODO Tidy
		Random rand = new Random();
		int test = rand.nextInt(validSpawnPoints.size());
		validSpawnPoints.get(test).spawnEnemy();
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
