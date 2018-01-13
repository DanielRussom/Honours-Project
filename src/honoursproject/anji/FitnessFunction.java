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
	private final static String TIMESTEPS_KEY = "honours.timesteps";
	private final static String NUM_TRIALS_KEY = "honours.trials";

	private final static int DEFAULT_TIMESTEPS = 10000;
	private int maxTimesteps = DEFAULT_TIMESTEPS;

	private final static int DEFAULT_NUM_TRIALS = 10;
	private int numTrials = DEFAULT_NUM_TRIALS;
	private final static Logger logger = Logger.getLogger(DoublePoleBalanceFitnessFunction.class);

	private ActivatorTranscriber factory;
	private Random rand;

	/**
	 * @throws java.lang.Exception
	 * @see com.anji.util.Configurable#init(com.anji.util.Properties)
	 */
	@Override
	public void init(Properties props) throws Exception {
		try {
			factory = (ActivatorTranscriber) props.singletonObjectProperty(ActivatorTranscriber.class);
			maxTimesteps = props.getIntProperty(TIMESTEPS_KEY, DEFAULT_TIMESTEPS);
			numTrials = props.getIntProperty(NUM_TRIALS_KEY, DEFAULT_NUM_TRIALS);
			Randomizer randomizer = (Randomizer) props.singletonObjectProperty(Randomizer.class);
			rand = randomizer.getRand();
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"invalid properties: " + e.getClass().toString() + ": " + e.getMessage());
		}
	}

	/**
	 * @param genotypes
	 * @see org.jgap.BulkFitnessFunction#evaluate(java.util.List)
	 * @see IdentifyImageFitnessFunction#evaluate(Chromosome)
	 */
	@Override
	public void evaluate(List genotypes) {
		// TODO Auto-generated method stub
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

			// calculate fitness, sum of multiple trials
			int fitness = 0;
			for (int i = 0; i < numTrials; i++) {
				// Loops through trials
				fitness += singleTrial(activator);
				// if (showGame) {
				// break;
				// }
			}
			c.setFitnessValue(fitness);
		} catch (Throwable e) {
			logger.warn("error evaluating chromosome " + c.toString(), e);
			c.setFitnessValue(0);
		}
	}

	private int singleTrial(Activator activator) {
		// TODO
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
			for (int i = 0; i < 4; ++i) {
				if (networkOutput[i] > maxV) {
					maxI = i;
					maxV = networkOutput[i];
				}
			}
			switch (maxI) {
			case 0:
				// System.out.println("CASE LEFT");
				// Player.setSingleTurnXVel(test);
				break;
			case 1:
				// System.out.println("CASE RIGHT");
				// Controller.testValue += 1;
				break;
			case 2:
				// System.out.println("CASE UP");
				// Controller.testValue += 2;
				break;
			case 3:
				// System.out.println("CASE DOWN");
				// Controller.testValue += 4;
				break;
			case 4:
				// System.out.println("CASE DON'T MOVE");
				break;
			default:
				throw new RuntimeException("This shouldn't happen");
			}
		}
		logger.debug("Trial took " + currentTimestep + " steps");
		return fitness;
	}

	private double[] getNetworkInput() {
		// TODO Change PH value
		double[] input = new double[100];
		// System.out.println(GameController.getActiveElements());
		Player player = GameController.getCurrentPlayer();
		input[0] = player.getXPosition();
		input[1] = player.getYPosition();
		input[2] = player.getHealth();
		ArrayList<Element> enemies = new ArrayList<Element>();
		ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

		for (Element current : GameController.getActiveElements()) {
			if (current instanceof Enemy) {
				enemies.add(current);
			}
			if (current instanceof Projectile) {
				Projectile currentProjectile = (Projectile) current;
				if (currentProjectile.getShooter() instanceof Enemy) {
					projectiles.add(currentProjectile);
				}
			}
		}
		int sizePerEnemy = 3;
		for (int i = 0; i < enemies.size(); i ++) {
			input[(i + 1) * sizePerEnemy] = enemies.get(i).getXPosition();
			input[((i + 1) * sizePerEnemy) + 1] = enemies.get(i).getYPosition();
			input[((i + 1) * sizePerEnemy) + 2] = ((Enemy) enemies.get(i)).getHealth();
		}
		int sizePerProjectile = 4;
		int offset = (enemies.size()*3)+3;
		for(int i = 0; i < projectiles.size(); i++) {
			input[offset+(i*sizePerProjectile)] = projectiles.get(i).getXPosition();
			input[offset+(i*sizePerProjectile)+1] = projectiles.get(i).getYPosition();
			input[offset+(i*sizePerProjectile)+2] = projectiles.get(i).getXVel();
			input[offset+(i*sizePerProjectile)+3] = projectiles.get(i).getYVel();
			
		}
		// TODO TIDY
		System.out.println("TEST " + input);
		return input;
	}

	// TODO
	@Override
	public int getMaxFitnessValue() {
		return numTrials * maxTimesteps;
	}
}
