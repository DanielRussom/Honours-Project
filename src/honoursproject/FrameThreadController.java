package honoursproject;

import java.util.ArrayList;

import honoursproject.model.Element;
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
		// Decrements the set up time
		if (setupTime > 0) {
			setupTime -= 1;
			return;
		}
		if(triggerEvolver) {
			try {
				for(int i = 0; i < GameController.getActiveElements().size(); i++) {
					GameController.resetState.add(GameController.getActiveElements().get(i).clone());
				}
				Evolver.main2();
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
