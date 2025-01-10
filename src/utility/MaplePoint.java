package utility;
/**
 * Object used to store x and y coordinates in the maple world
 */
public class MaplePoint {
	public int x;
	public int y;
	/**
	 * @param x X coordinate from top left
	 * @param y Y coordinate from top left
	 */
	public MaplePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public String toString() {
		return x + ", " + y;
	}
}
