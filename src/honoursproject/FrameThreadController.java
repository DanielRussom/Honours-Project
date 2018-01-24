package honoursproject;

import java.util.ArrayList;

import honoursproject.model.Element;
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
		// Checks if the game is ready to start
		if (setupTime > 0) {
			// Decrements the setup time
			setupTime -= 1;
			// Checks if the game is going to start next turn
			if(setupTime == 0) {
				// Initializes container values
				CollisionDetector.initContainerValues(GameController.getActiveElements().get(0));
			}
			return;
		}
		// Checks if the evolver should be started
		if (triggerEvolver) {
			try {
				// Stores the initial elements as the reset state
				for (int i = 0; i < GameController.getActiveElements().size(); i++) {
					GameController.resetState.add(GameController.getActiveElements().get(i).clone());
				}
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
				System.out.println("SKIPPED " + current.toString());
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
}
