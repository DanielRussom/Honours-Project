package honoursproject;

import java.util.ArrayList;

import honoursproject.model.Element;
import javafx.animation.AnimationTimer;

public class FrameThreadController extends AnimationTimer {

	int setupTime = 1;

	@Override
	public void handle(long now) {
		if (Main.isSingleFrameActive) {
			if (Main.isThisFrameRequested) {
				Main.isThisFrameRequested = false;
			} else {
				return;
			}
		}
		if (setupTime > 0) {
			setupTime -= 1;
			return;
		}
		ArrayList<Element> activeElements = new ArrayList<Element>();
		for (Element current : GameController.getActiveElements()) {
			activeElements.add(current);
		}
		for (Element current : activeElements) {
			if (GameController.getElementsToRemove().contains(current)) {
				System.out.println("SKIPPED " + current.toString());
				continue;
			}
			current.move();
		}
		GameController.getActiveElements().removeAll(GameController.getElementsToRemove());
		for (Element currentNode : GameController.getElementsToRemove()) {
			Main.getGameScreenController().removeNode(currentNode.getImage());
		}
	}
}
