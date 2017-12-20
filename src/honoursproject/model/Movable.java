package honoursproject.model;

public interface Movable {
	
	public void move(Player player);
	
	public void moveSetUp(Player player);
	
	public void moveSetUp(Player player, double maxNoise);
}
