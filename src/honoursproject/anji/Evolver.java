package honoursproject.anji;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jgap.BulkFitnessFunction;
import org.jgap.Chromosome;
import org.jgap.Genotype;
import org.jgap.event.GeneticEvent;

import com.anji.Copyright;
import com.anji.integration.LogEventListener;
import com.anji.integration.PersistenceEventListener;
import com.anji.integration.PresentationEventListener;
import com.anji.neat.NeatChromosomeUtility;
import com.anji.neat.NeatConfiguration;
import com.anji.neat.NeuronType;
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

	public static BufferedWriter writer;

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
		System.out.println(Copyright.STRING);
		// Loads in properties file
		Properties props = new Properties("honoursproject/anji/properties.txt");
		Evolver evolver = new Evolver();
		evolver.init(props);
		evolver.run();
	}

	/**
	 * Sets up and starts the evolver
	 * 
	 * @throws Exception
	 */
	public static void setup() throws Exception {
		// writer = new BufferedWriter(new FileWriter("Fitness"));
		System.out.println(Copyright.STRING);
		// Loads in properties file
		Properties props = new Properties("honoursproject/anji/properties.txt");
		Evolver evolver = new Evolver();
		evolver.init(props);
		// Runs evolver
		evolver.run();
		writer.close();
	}

	/**
	 * Runs the evolver
	 */
	private void run() {
		// Stores the start time of the run
		Date runStartDate = Calendar.getInstance().getTime();
		DateFormat fmt = new SimpleDateFormat("HH:mm:ss");
		DateFormat fileFmt = new SimpleDateFormat("HH-mm-ss");
		try {
			writer = new BufferedWriter(new FileWriter("Fitness" +  fileFmt.format(runStartDate) + ".txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Run: start");
		// Initializes the results
		int generationOfFirstSolution = -1;
		champ = genotype.getFittestChromosome();
		double adjustedFitness = (maxFitness > 0 ? champ.getFitnessValue() / maxFitness : champ.getFitnessValue());
		// Evolves for the set number of generations
		for (int generation = 0; (generation < numEvolutions && adjustedFitness < targetFitness); ++generation) {
			// Stores the start time of the generation
			Date generationStartDate = Calendar.getInstance().getTime();
			logger.info("Generation " + generation + ": start");

			// Evolves the current generation of chromosomes
			genotype.evolve();
			// Gets the results from the evolution
			champ = genotype.getFittestChromosome();
			adjustedFitness = (maxFitness > 0 ? (double) champ.getFitnessValue() / maxFitness
					: champ.getFitnessValue());
			if (adjustedFitness >= thresholdFitness && generationOfFirstSolution == -1) {
				generationOfFirstSolution = generation;
			}
			// Writes the fittest champion value to a file
			try {
				writer.write(champ.getFitnessValue() + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Logs the time taken for this generation
			Date generationEndDate = Calendar.getInstance().getTime();
			long durationMillis = generationEndDate.getTime() - generationStartDate.getTime();
			logger.info("Generation " + generation + ": end [" + fmt.format(generationStartDate) + " - "
					+ fmt.format(generationEndDate) + "] [" + durationMillis + "]");
		}
		// Sends an event that the current evolution is finished
		config.getEventManager().fireGeneticEvent(new GeneticEvent(GeneticEvent.RUN_COMPLETED_EVENT, genotype));
		logConclusion(generationOfFirstSolution, champ);
		Date runEndDate = Calendar.getInstance().getTime();
		long durationMillis = runEndDate.getTime() - runStartDate.getTime();
		try {
			writer.write("Time: " + durationMillis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Run: end [" + fmt.format(runStartDate) + " - " + fmt.format(runEndDate) + "] [" + durationMillis
				+ "]");
	}

	/**
	 * Log summary data of run including generation in which the first solution
	 * occurred, and the champion of the final generation.
	 *
	 * @param generationOfFirstSolution
	 *            - Generation of the first solution
	 * @param champ
	 *            - Champion chromosome
	 */
	private static void logConclusion(int generationOfFirstSolution, Chromosome champ) {
		logger.info("generation of first solution == " + generationOfFirstSolution);
		logger.info("champ # connections == " + NeatChromosomeUtility.getConnectionList(champ.getAlleles()).size());
		logger.info("champ # hidden nodes == "
				+ NeatChromosomeUtility.getNeuronList(champ.getAlleles(), NeuronType.HIDDEN).size());
	}

	@Override
	public void init(Properties props) throws Exception {
		// Checks if the saved runs are to be reset
		boolean doReset = props.getBooleanProperty(RESET_KEY, false);
		// Resets the previous run
		if (doReset) {
			logger.warn("Resetting previous run!");
			Reset resetter = new Reset(props);
			resetter.setUserInteraction(false);
			resetter.reset();
		}
		// Loads parameters from properties
		config = new NeatConfiguration(props);
		db = (Persistence) props.singletonObjectProperty(Persistence.PERSISTENCE_CLASS_KEY);

		numEvolutions = props.getIntProperty(NUM_GENERATIONS_KEY);
		targetFitness = props.getDoubleProperty(FITNESS_TARGET_KEY, 1.0d);
		thresholdFitness = props.getDoubleProperty(FITNESS_THRESHOLD_KEY, targetFitness);
		Run run = (Run) props.singletonObjectProperty(Run.class);
		db.startRun(run.getName());
		config.getEventManager().addEventListener(GeneticEvent.GENOTYPE_EVALUATED_EVENT, run);

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

		BulkFitnessFunction fitnessFunc = (BulkFitnessFunction) props
				.singletonObjectProperty(FITNESS_FUNCTION_CLASS_KEY);
		config.setBulkFitnessFunction(fitnessFunc);
		maxFitness = fitnessFunc.getMaxFitnessValue();

		// Population is loaded
		genotype = db.loadGenotype(config);
		if (genotype != null) {
			logger.info("genotype from previous run");
		} else {
			genotype = Genotype.randomInitialGenotype(config);
			logger.info("random genotype");
		}
	}

	/**
	 * Gets the champion of the previous generation
	 * 
	 * @return champion of last generation
	 */
	public Chromosome getChamp() {
		return champ;
	}

	/**
	 * Fitness of current champ, 0 ... 1
	 *
	 * @return maximum fitness value
	 */
	public double getChampAdjustedFitness() {
		return (champ == null) ? 0d
				: (double) champ.getFitnessValue() / config.getBulkFitnessFunction().getMaxFitnessValue();
	}

	/**
	 * Gets the target fitness
	 * 
	 * @return target fitness value, 0 ... 1
	 */
	public double getTargetFitness() {
		return targetFitness;
	}

	/**
	 * Gets the threshold fitness
	 * 
	 * @return threshold fitness value, 0 ... 1
	 */
	public double getThresholdFitness() {
		return thresholdFitness;
	}
}
