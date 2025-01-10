package utility;
/**
 * Each client will have a search to a character name, mesos, and level
 */
public class Client {
	String pathToNameImage;
	boolean hasPet;
	long petFoodTime = 0;
	String name;
	int level;
	int mesos;
	public Client(String name, String pathToNameImage, boolean hasPet) {
		this.name = name;
		this.pathToNameImage = pathToNameImage;
		this.hasPet=hasPet;
	}
	public Client(String name, String pathToNameImage) {
		this.name = name;
		this.pathToNameImage = pathToNameImage;
	}
}
