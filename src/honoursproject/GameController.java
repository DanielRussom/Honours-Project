package honoursproject;

import java.util.ArrayList;
import java.util.Random;

import honoursproject.model.Element;
import honoursproject.model.Enemy;
import honoursproject.model.Player;
import honoursproject.model.Spawner;

public class GameController {
	private static ArrayList<Element> activeElements = new ArrayList<Element>();
	private static ArrayList<Element> elementsToRemove = new ArrayList<Element>();
	public static ArrayList<Spawner> enemySpawnPoints = new ArrayList<Spawner>();
	private static Player currentPlayer;
	private static Player currentPlayer2;
	public static ArrayList<Element> resetState = new ArrayList<Element>();
	public static int minimumEnemyLimit = 1;

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
		// Creates list to store valid spawn points
		ArrayList<Spawner> validSpawnPoints = new ArrayList<Spawner>();
		// Iterates through all spawn points
		for (Spawner currentSpawn : enemySpawnPoints) {
			// Checks if the current spawn point is valid to be used
			if (isSpawnValid(currentSpawn)) {
				validSpawnPoints.add(currentSpawn);
			}
			// TODO Weighted Spawn Points?
		}
		// Exits the method if there are no valid spawn points
		if (validSpawnPoints.size() == 0) {
			return;
		}
		// Randomly selects a valid spawn point to use
		Random rand = new Random();
		int spawnId = rand.nextInt(validSpawnPoints.size());
		validSpawnPoints.get(spawnId).spawnEnemy();
	}

	/**
	 * Checks if the passed in spawner is in a situation where it can spawn an enemy
	 * 
	 * @param spawner
	 *            - the spawner to check the validity of
	 * @return - if the spawner is valid to be used
	 */
	private static boolean isSpawnValid(Spawner spawner) {
		// Iterates through each active element
		for (Element currentElement : activeElements) {
			if (spawner.collidesWith(currentElement)) {
				return false;
			}
		}
		return true;
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

	/**
	 * Gets the number of active enemies in the game
	 * 
	 * @return - number of enemies
	 */
	public static int getNumberOfEnemies() {
		int numberOfEnemies = 0;
		// Iterates through the active elements
		for (Element current : activeElements) {
			// Increments the counter for each enemy
			if (current instanceof Enemy) {
				numberOfEnemies++;
			}
		}
		return numberOfEnemies;
	}
}
