package honoursproject.anji;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.jgap.BulkFitnessFunction;
import org.jgap.Chromosome;

import com.anji.imaging.IdentifyImageFitnessFunction;
import com.anji.integration.Activator;
import com.anji.integration.ActivatorTranscriber;
import com.anji.polebalance.DoublePoleBalanceFitnessFunction;
import com.anji.util.Configurable;
import com.anji.util.Properties;
import com.anji.util.Randomizer;

import honoursproject.GameController;
import honoursproject.model.Element;
import honoursproject.model.Enemy;
import honoursproject.model.Player;
import honoursproject.model.Projectile;

public class FitnessFunction implements BulkFitnessFunction, Configurable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1393904614081000622L;
	private final static String TIMESTEPS_KEY = "honours.timesteps";
	private final static String NUM_TRIALS_KEY = "honours.trials";

	private final static int DEFAULT_TIMESTEPS = 10000;
	private int maxTimesteps = DEFAULT_TIMESTEPS;

	private final static int DEFAULT_NUM_TRIALS = 10;
	private int numTrials = DEFAULT_NUM_TRIALS;
	private final static Logger logger = Logger.getLogger(DoublePoleBalanceFitnessFunction.class);

	private ActivatorTranscriber factory;
	// TODO possibly remove rand?
	private Random rand;

	/**
	 * Initializes properties from the passed in props.
	 * 
	 * @throws java.lang.Exception
	 * @see com.anji.util.Configurable#init(com.anji.util.Properties)
	 */
	@Override
	public void init(Properties props) throws Exception {
		try {
			// Loads the factory defined in the properties file
			factory = (ActivatorTranscriber) props.singletonObjectProperty(ActivatorTranscriber.class);
			// Loads the max timesteps and number of trials from the properties file
			maxTimesteps = props.getIntProperty(TIMESTEPS_KEY, DEFAULT_TIMESTEPS);
			numTrials = props.getIntProperty(NUM_TRIALS_KEY, DEFAULT_NUM_TRIALS);
			// TODO Possibly remove?
			Randomizer randomizer = (Randomizer) props.singletonObjectProperty(Randomizer.class);
			rand = randomizer.getRand();
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"invalid properties: " + e.getClass().toString() + ": " + e.getMessage());
		}
	}

	/**
	 * Evaluates a group of Chromosomes
	 * 
	 * @param genotypes
	 *            - group of chromosomes to be evaluated
	 * @see org.jgap.BulkFitnessFunction#evaluate(java.util.List)
	 * @see IdentifyImageFitnessFunction#evaluate(Chromosome)
	 */
	@Override
	public void evaluate(List genotypes) {
		Iterator it = genotypes.iterator();
		while (it.hasNext()) {
			Chromosome c = (Chromosome) it.next();
			evaluate(c);
		}
	}

	/**
	 * Evaluate chromosome and set fitness.
	 *
	 * @param c
	 */
	public void evaluate(Chromosome c) {
		try {
			Activator activator = factory.newActivator(c);
			int fitness = 0;
			// Run multiple trials to calculate fitness
			for (int i = 0; i < numTrials; i++) {
				// Clear the currently stored active elements
				GameController.getActiveElements().clear();
				// Load in new active elements from the saved reset state
				for (int j = 0; j < GameController.resetState.size(); j++) {
					Element newObject = GameController.resetState.get(j).clone();
					// Set the current player
					if (newObject instanceof Player && !(newObject instanceof Enemy)) {
						GameController.setCurrentPlayer((Player) newObject);
					} else {
						// Adds the new element to the active elements list
						GameController.getActiveElements().add(newObject);
					}
				}
				// Adds up fitness over multiple trials
				fitness += singleTrial(activator);
			}
			// Stores the average fitness of the chromosome
			c.setFitnessValue(fitness / numTrials);
		} catch (Throwable e) {
			logger.warn("error evaluating chromosome " + c.toString(), e);
			c.setFitnessValue(0);
		}
	}

	/**
	 * Handles performing a single trial
	 * 
	 * @param activator
	 *            -
	 * @return achieved fitness from this trial
	 */
	private int singleTrial(Activator activator) {
		// TODO Change History to list?
		HashSet<String> history = new HashSet<>();
		int fitness = 0;
		int stuckCounter = 100;

		// Run the simulation
		int currentTimestep = 0;
		for (currentTimestep = 0; currentTimestep < maxTimesteps; currentTimestep++) {

			double[] networkInput = getNetworkInput();

			double[] networkOutput = activator.next(networkInput);
			int maxI = -1;
			double maxV = Double.NEGATIVE_INFINITY;
			for (int i = 0; i < 8; ++i) {
				if (networkOutput[i] > maxV) {
					// if (networkOutput[i] > 0.5) {
					// System.out.println(networkOutput[i]);
					// }
					maxI = i;
					maxV = networkOutput[i];
				}
			}
			switch (maxI) {
			case 0:
				// Moves the player up
				GameController.getCurrentPlayer().moveUp();
				break;
			case 1:
				// Moves the player up and left
				GameController.getCurrentPlayer().moveUp();
				GameController.getCurrentPlayer().moveLeft();
				break;
			case 2:
				// Moves the player left
				GameController.getCurrentPlayer().moveLeft();
				break;
			case 3:
				// Moves the player down and left
				GameController.getCurrentPlayer().moveDown();
				GameController.getCurrentPlayer().moveLeft();
				break;
			case 4:
				// Moves the player down
				GameController.getCurrentPlayer().moveDown();
				break;
			case 5:
				// Moves the player down and right
				GameController.getCurrentPlayer().moveDown();
				GameController.getCurrentPlayer().moveRight();
				break;
			case 6:
				// Moves the player right
				GameController.getCurrentPlayer().moveRight();
				break;
			case 7:
				// Moves the player up and right
				GameController.getCurrentPlayer().moveUp();
				GameController.getCurrentPlayer().moveRight();
				break;
			default:
				throw new RuntimeException("This shouldn't happen");
			}

			// TODO Uncomment below when shooting is needed
			/*
			 * maxI = -1; maxV = Double.NEGATIVE_INFINITY; for (int i = 8; i < 12; ++i) { if
			 * (networkOutput[i] > maxV) { maxI = i; maxV = networkOutput[i]; } }
			 * 
			 * switch (maxI) { case 8: // Shoots upwards
			 * GameController.getCurrentPlayer().shoot('U'); break; case 9: // Shoots to the
			 * left GameController.getCurrentPlayer().shoot('L'); break; case 10: // Shoots
			 * down GameController.getCurrentPlayer().shoot('D'); break; case 11: // Shoots
			 * to the right GameController.getCurrentPlayer().shoot('R'); break; default:
			 * throw new RuntimeException("This shouldn't happen"); }
			 */

			// Updates the game
			GameController.manualGameUpdate();

			// TODO Test fitness +=
			// Calculates the current fitness
			fitness = GameController.getCurrentPlayer().getHealth() + (currentTimestep / 10);
			// Stores the player's current position
			String pos = GameController.getCurrentPlayer().getXPosition() + ":"
					+ GameController.getCurrentPlayer().getYPosition();
			// Tracks if the player has been stuck in the same location
			if (history.contains(pos)) {
				stuckCounter--;
			} else {
				stuckCounter = 100;
				history.add(pos);
			}
			// Ends the test if the player dies or is stuck
			if (stuckCounter <= 0 || GameController.getCurrentPlayer().getHealth() <= 0) {
				break;
			}
		}
		logger.debug("Trial took " + currentTimestep + " steps");
		return fitness;

	}

	/**
	 * Handles getting inputs from the game
	 * 
	 * @return game inputs
	 */
	private double[] getNetworkInput() {
		// Initializes input array to have more than enough needed spaces
		double[] input = new double[100];
		// Stores player variables
		Player player = GameController.getCurrentPlayer();
		input[0] = player.getXPosition();
		input[1] = player.getYPosition();
		input[2] = player.getHealth();
		ArrayList<Element> enemies = new ArrayList<Element>();
		ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
		// Iterates through active game elements
		for (Element current : GameController.getActiveElements()) {
			// Stores a copy of enemies
			if (current instanceof Enemy) {
				enemies.add(current);
			}
			// Stores a copy of enemy projectiles
			if (current instanceof Projectile) {
				Projectile currentProjectile = (Projectile) current;
				if (currentProjectile.getShooter() instanceof Enemy) {
					projectiles.add(currentProjectile);
				}
			}
		}

		// Stores enemy variables for each enemy
		int sizePerEnemy = 3;
		for (int i = 0; i < enemies.size(); i++) {
			// Gets initial storing point for this enemy
			int value = 3 + (i) * sizePerEnemy;
			// Stores the x and y coordinates of this enemy
			input[value] = enemies.get(i).getXPosition();
			input[value + 1] = enemies.get(i).getYPosition();
			// Stores this enemy's health
			input[value + 2] = ((Enemy) enemies.get(i)).getHealth();
		}
		// Stores projectile variables for each projectile
		int sizePerProjectile = 4;
		// Gets the initial storing point for projectiles
		int offset = (enemies.size() * 3) + 3;
		for (int i = 0; i < projectiles.size(); i++) {
			// Gets the initial storing point for this projectile
			int value = offset + (i * sizePerProjectile);
			// Stores the x and y coordinates of this projectile
			input[value] = projectiles.get(i).getXPosition();
			input[value + 1] = projectiles.get(i).getYPosition();
			// Stores the x and y velocities of this projectile
			input[value + 2] = projectiles.get(i).getXVel();
			input[value + 3] = projectiles.get(i).getYVel();

		}
		return input;
	}

	@Override
	public int getMaxFitnessValue() {
		return numTrials * maxTimesteps;
	}
}