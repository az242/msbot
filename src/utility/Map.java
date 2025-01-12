package utility;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Minimap data that is used to match the minimap name as well as the search area for character location
 */
public class Map {
	public Rectangle minimapName;
	public Rectangle minimap;
	public String name;
	public BufferedImage minimapNameImage;
	/**
	 * 
	 * @param name String minimap identifier (Must be unique)
	 * @param minimap Rectangle minimap search area
	 * @param minimapName Rectangle minimap name search area
	 * @param minimapNameFileName String minimap path to minimap name image file
	 * @throws IOException 
	 */
	public Map(String name, Rectangle minimap, Rectangle minimapName, String minimapNameFileName){
		this.name = name;
		this.minimap = minimap;
		this.minimapName = minimapName;
		try {
			minimapNameImage = ImageIO.read(new File(minimapNameFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void adjustPoint(int x, int y) {
		minimapName.setLocation(x + (int)minimapName.getX(), y + (int)minimapName.getY());
		minimap.setLocation(x + (int)minimap.getX(), y + (int)minimap.getY());
	}
	public void adjustPoint(Rectangle rect) {
		adjustPoint((int)rect.getX(),(int)rect.getY());
	}
	public void adjustPoint(MaplePoint point) {
		adjustPoint((int)point.getX(),(int)point.getY());
	}
}