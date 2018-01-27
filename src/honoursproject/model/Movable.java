package honoursproject.model;

public interface Movable {

	/**
	 * Movement
	 * 
	 * @param player
	 *            - the player being moved
	 */
	public void move(Player player);

	/**
	 * Initializes the movement pattern
	 * 
	 * @param player
	 *            - the movable player
	 */
	public void moveSetUp(Player player);

	/**
	 * Initializes the movement pattern with shooting
	 * 
	 * @param player
	 *            - the movable player
	 * @param shootingType
	 *            - the shooting mode
	 */
	public void moveSetUp(Player player, int shootingType);

	/**
	 * Initializes the movement pattern with noise
	 * 
	 * @param player
	 *            - the movable player
	 * @param maxNoise
	 *            - the max amount of noise
	 */
	public void moveSetUp(Player player, double maxNoise);

	/**
	 * Initializes the movement pattern with noise and shooting
	 * 
	 * @param player
	 *            - the movable player
	 * @param maxNoise
	 *            - the max amount of noise
	 * @param shootingType
	 *            - the shooting mode
	 */
	void moveSetUp(Player player, double maxNoise, int shootingType);
}
