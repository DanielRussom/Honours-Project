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
	 * @param isShootingEnabled
	 *            - the shooting enabled status
	 */
	public void moveSetUp(Player player, boolean isShootingEnabled);

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
	 * @param isShootingEnabled
	 *            - the shooting enabled status
	 */
	void moveSetUp(Player player, double maxNoise, boolean isShootingEnabled);
}
