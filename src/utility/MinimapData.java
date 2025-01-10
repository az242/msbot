package utility;

import java.awt.Rectangle;

/**
 * Minimap data that is used to match the minimap name as well as the search area for character location
 */
public class MinimapData {
	Rectangle minimapName;
	Rectangle minimap;
	String name;
	String minimapNameFileName;
	/**
	 * 
	 * @param name String minimap identifier (Must be unique)
	 * @param minimap Rectangle minimap search area
	 * @param minimapName Rectangle minimap name search area
	 * @param minimapNameFileName String minimap path to minimap name image file
	 */
	public MinimapData(String name, Rectangle minimap, Rectangle minimapName, String minimapNameFileName) {
		this.name = name;
		this.minimap = minimap;
		this.minimapName = minimapName;
		this.minimapNameFileName = minimapNameFileName;
	}
	public void adjustPoint(int x, int y) {
		minimapName.setLocation(x + (int)minimapName.getX(), y + (int)minimapName.getY());
		minimap.setLocation(x + (int)minimap.getX(), y + (int)minimap.getY());
	}
}