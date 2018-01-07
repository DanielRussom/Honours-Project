package honoursproject;

import org.apache.log4j.Logger;
import org.jgap.BulkFitnessFunction;
import org.jgap.Chromosome;
import org.jgap.Genotype;
import org.jgap.event.GeneticEvent;

import com.anji.Copyright;
import com.anji.integration.LogEventListener;
import com.anji.integration.PersistenceEventListener;
import com.anji.integration.PresentationEventListener;
import com.anji.neat.NeatConfiguration;
import com.anji.persistence.Persistence;
import com.anji.run.Run;
import com.anji.util.Configurable;
import com.anji.util.Properties;
import com.anji.util.Reset;

public class Evolver implements Configurable {
	private static Logger logger = Logger.getLogger(Evolver.class);
	/**
	 * properties key, # generations in run
	 */
	public static final String NUM_GENERATIONS_KEY = "num.generations";
	public static final String FITNESS_FUNCTION_CLASS_KEY = "fitness_function";

	private static final String FITNESS_THRESHOLD_KEY = "fitness.threshold";
	private static final String RESET_KEY = "run.reset";
	public static final String FITNESS_TARGET_KEY = "fitness.target";

	private NeatConfiguration config = null;

	private Chromosome champ = null;

	private Genotype genotype = null;

	private int numEvolutions = 0;

	private double targetFitness = 0.0d;

	private double thresholdFitness = 0.0d;

	private int maxFitness = 0;

	private Persistence db = null;

	public Evolver() {
		super();
	}

	/**
	 * Main program to perform evolutionary test.
	 * 
	 * @param args
	 *            - Command line arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Try catch instead of throws
		System.out.println(Copyright.STRING);
		// Loads in properties file
		Properties props = new Properties("honoursproject/anji/properties.txt");
		Evolver evolver = new Evolver();
		evolver.init(props);
	}

	// WORKS WITH NO ERROR
	@Override
	public void init(Properties props) throws Exception {
		boolean doReset = props.getBooleanProperty(RESET_KEY, false);
		if (doReset) {
			logger.warn("Resetting previous run!");
			// TODO Try removing . from file paths in properties?
			Reset resetter = new Reset(props);
			resetter.setUserInteraction(false);
			resetter.reset();
		}
		config = new NeatConfiguration(props);// peristence
		db = (Persistence) props.singletonObjectProperty(Persistence.PERSISTENCE_CLASS_KEY);

		numEvolutions = props.getIntProperty(NUM_GENERATIONS_KEY);
		targetFitness = props.getDoubleProperty(FITNESS_TARGET_KEY, 1.0d);
		thresholdFitness = props.getDoubleProperty(FITNESS_THRESHOLD_KEY, targetFitness);
		Run run = (Run) props.singletonObjectProperty(Run.class);
		db.startRun(run.getName());
		config.getEventManager().addEventListener(GeneticEvent.GENOTYPE_EVALUATED_EVENT, run);

		// logging
		LogEventListener logListener = new LogEventListener(config);
		config.getEventManager().addEventListener(GeneticEvent.GENOTYPE_EVOLVED_EVENT, logListener);
		config.getEventManager().addEventListener(GeneticEvent.GENOTYPE_EVOLVED_EVENT, logListener);

		PersistenceEventListener dbListener = new PersistenceEventListener(config, run);
		dbListener.init(props);
		config.getEventManager().addEventListener(GeneticEvent.GENOTYPE_START_GENETIC_OPERATORS_EVENT, dbListener);
		config.getEventManager().addEventListener(GeneticEvent.GENOTYPE_FINISH_GENETIC_OPERATORS_EVENT, dbListener);
		config.getEventManager().addEventListener(GeneticEvent.GENOTYPE_EVALUATED_EVENT, dbListener);

		PresentationEventListener presListener = new PresentationEventListener(run);
		presListener.init(props);
		config.getEventManager().addEventListener(GeneticEvent.GENOTYPE_EVALUATED_EVENT, presListener);
		config.getEventManager().addEventListener(GeneticEvent.RUN_COMPLETED_EVENT, presListener);

		// fitness function
		BulkFitnessFunction fitnessFunc = (BulkFitnessFunction) props
				.singletonObjectProperty(FITNESS_FUNCTION_CLASS_KEY);
		config.setBulkFitnessFunction(fitnessFunc);
		maxFitness = fitnessFunc.getMaxFitnessValue();

		// load population, either from previous run or random
		genotype = db.loadGenotype(config);
		if (genotype != null) {
			logger.info("genotype from previous run");
		} else {
			genotype = Genotype.randomInitialGenotype(config);
			logger.info("random genotype");
		}
	}

}
