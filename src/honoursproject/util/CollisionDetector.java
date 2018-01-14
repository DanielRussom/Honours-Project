package honoursproject.util;

import honoursproject.GameController;
import honoursproject.Main;
import honoursproject.model.Element;
import honoursproject.model.Projectile;
import javafx.scene.shape.Rectangle;

public class CollisionDetector {
	// TODO
	static double containerWidth = 0;
	static double containerHeight = 0;

	public static void initContainerValues(Element element) {
		containerWidth = element.getImage().getParent().getLayoutBounds().getWidth();
		containerHeight = element.getImage().getParent().getLayoutBounds().getHeight();
		System.out.println(containerWidth + "," + containerHeight + " AAAAAA");
	}

	/**
	 * Checks for, and handles, wall collision.
	 * 
	 * @param element
	 *            - element being checked
	 */
	public static void handleBoundaryCollision(Element element) {
		//double containerWidth = element.getImage().getParent().getLayoutBounds().getWidth();
		//double containerHeight = element.getImage().getParent().getLayoutBounds().getHeight();
		if (element.getXPosition() < 0) {
			element.setXPosition(0);
		} else if (element.getRightSide() > containerWidth) {
			element.setXPosition(containerWidth - element.getImage().getLayoutBounds().getWidth());
		}
		if (element.getYPosition() < 0) {
			element.setYPosition(0);
		} else if (element.getBottomSide() > containerHeight) {
			element.setYPosition(containerHeight - element.getImage().getLayoutBounds().getHeight());
		}
	}

	/**
	 * Checks for, and handles, wall collision for projectiles.
	 * 
	 * @param projectile
	 *            - projectile being checked
	 */
	public static void handleBoundaryCollision(Projectile projectile) {
		//double containerWidth = projectile.getImage().getParent().getLayoutBounds().getWidth();
		//double containerHeight = projectile.getImage().getParent().getLayoutBounds().getHeight();
		Rectangle projectileImage = (Rectangle) projectile.getImage();
		if (projectile.getXPosition() < 0) {
			projectileImage.setWidth(projectileImage.getWidth() + projectile.getXPosition());
			projectile.setXPosition(0);
			projectile.updatePosition();
			if (projectileImage.getWidth() < 1) {
				GameController.getElementsToRemove().add(projectile);
			}
		} else if (projectile.getRightSide() > containerWidth) {
			projectileImage.setWidth(projectileImage.getWidth() - (projectile.getRightSide() - containerWidth));
			projectile.setXPosition(containerWidth - projectileImage.getWidth());
			projectile.updatePosition();
			if (projectileImage.getWidth() < 1) {
				GameController.getElementsToRemove().add(projectile);
			}
		}
		if (projectile.getYPosition() < 0) {
			projectileImage.setHeight(projectileImage.getHeight() + projectile.getYPosition());
			projectile.setYPosition(0);
			projectile.updatePosition();
			if (projectileImage.getHeight() < 1) {
				GameController.getElementsToRemove().add(projectile);
			}
		} else if (projectile.getBottomSide() > containerHeight) {
			projectileImage.setHeight(projectileImage.getHeight() - (projectile.getBottomSide() - containerHeight));
			projectile.setYPosition(containerHeight - projectileImage.getHeight());
			projectile.updatePosition();
			if (projectileImage.getHeight() < 1) {
				GameController.getElementsToRemove().add(projectile);
			}
		}
	}
}