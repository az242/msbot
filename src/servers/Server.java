package servers;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Server {
	public String name;
	
	// area that is the play area of the client (minus the application title bar)
	public Rectangle searchArea;
	
	public Rectangle nameSearchArea;
	public Rectangle levelSearchArea;
	public Rectangle hpSearchArea;
	public Rectangle mpSearchArea;
	
	public Rectangle chatExpanderSearchArea;
	public Rectangle minimapNameSearchArea;
	
	//client icon search area (in application title bar)
	public Rectangle clientIconSearchArea;
	//taskbar icon search area
	public Rectangle taskbarIconSearchArea;
	//client adjustment to exclude the title bar
	public Rectangle clientCaptureAdjustment;
	//taskbar icon image
	public BufferedImage taskBarIcon;
	
	public BufferedImage[] numImages;
	public BufferedImage[] levelImages;
	public BufferedImage[] mesosNumImages;
	
	//search area for the inventory 
	public Rectangle inventorySearchArea;
	//inventory label image
	public BufferedImage inventoryLabel;
	public Rectangle inventoryLabelSearchArea; // do i need this?
	//search areas for mesos
	public Rectangle billionmesos;
	public Rectangle millionmesos;
	public Rectangle thousandmesos;
	public Rectangle hundredmesos;
	
	
	
	public abstract void loadImages();
	
}
