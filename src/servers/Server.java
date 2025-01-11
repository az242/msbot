package servers;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Server {
	public String name;
	
	public Rectangle searchArea;
	
	public Rectangle nameSearchArea;
	public Rectangle levelSearchArea;
	public Rectangle hpSearchArea;
	public Rectangle mpSearchArea;
	
	public Rectangle chatExpanderSearchArea;
	public Rectangle minimapNameSearchArea;
	
	//for finding clients
	public Rectangle clientIconSearchArea;
	public Rectangle taskbarIconSearchArea;
	public Rectangle clientCaptureAdjustment;
	public BufferedImage taskBarIcon;
	
	public BufferedImage[] numImages;
	public BufferedImage[] levelImages;
	public BufferedImage[] mesosNumImages;
	
	public Rectangle inventorySearchArea;
	public BufferedImage inventoryLabel;
	public Rectangle inventoryLabelSearchArea;
	public Rectangle billionmesos;
	public Rectangle millionmesos;
	public Rectangle thousandmesos;
	public Rectangle hundredmesos;
	
	
	
	public abstract void loadImages();
	
}
