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
	 * Initialises the movement pattern
	 * 
	 * @param player
	 *            - the movable player
	 */
	public void moveSetUp(Player player);

	/**
	 * Initialises the movement pattern with noise
	 * 
	 * @param player
	 *            - the moveable pattern
	 * @param maxNoise
	 *            - the max amount of noise
	 */
	public void moveSetUp(Player player, double maxNoise);
}
