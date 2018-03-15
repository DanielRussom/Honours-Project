package honoursproject;

import java.util.ArrayList;

import honoursproject.anji.Evolver;
import honoursproject.model.Element;
import honoursproject.model.Enemy;
import honoursproject.model.MovingElement;
import honoursproject.model.Player;
import honoursproject.util.CollisionDetector;
import javafx.animation.AnimationTimer;

public class FrameThreadController extends AnimationTimer {

	int setupTime = 10;
	boolean triggerEvolver = false;

	@Override
	public void handle(long now) {
		// Checks if the single frame display option is enabled
		if (Main.isSingleFrameActive) {
			// Checks if the next frame has been requested
			if (Main.isThisFrameRequested) {
				// Sets a flag that the next frame has not been requested
				Main.isThisFrameRequested = false;
			} else {
				// Does nothing if the frame was not requested
				return;
			}
		}
		// TODO Tidy evolver stuff
		// Checks if the game is ready to start
		if (setupTime > 0) {
			// Decrements the setup time
			setupTime -= 1;
			// Checks if the game is going to start next turn
			if (setupTime == 0) {
				// Initializes container values
				CollisionDetector.initContainerValues(GameController.getActiveElements().get(0));
				if (triggerEvolver) {
					for (int i = 0; i < GameController.getActiveElements().size(); i++) {
						try {
							GameController.resetState.add(GameController.getActiveElements().get(i).clone());
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					// TODO Remove items from UI
					// Clear the currently stored active elements
					GameController.getActiveElements().clear();

					// Load in new active elements from the saved reset state
					for (int j = 0; j < GameController.resetState.size(); j++) {
						try {

							Element newObject;

							newObject = GameController.resetState.get(j).clone();

							// Set the current player
							if (newObject.isPlayer1()) {
								GameController.setCurrentPlayer((Player) newObject);
							} else {
								// Adds the new element to the active elements list
								GameController.getActiveElements().add(newObject);
							}

						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			return;
		}
		// Checks if the evolver should be started
		if (triggerEvolver) {
			try {
				// Calls a method to setup the evolver
				Evolver.setup();
			} catch (Exception e) {
				e.printStackTrace();
			}
			triggerEvolver = false;
		}
		// Creates a local copy of current active elements
		ArrayList<Element> activeElements = new ArrayList<Element>();
		for (Element current : GameController.getActiveElements()) {
			activeElements.add(current);
		}
		// Iterates through each active element
		for (Element current : activeElements) {
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
		
		// Checks if there are enough enemies in the game
		if (GameController.getNumberOfEnemies() < GameController.minimumEnemyLimit) {
			// Spawns an enemy
			GameController.spawnEnemy();
		}
	}
}
