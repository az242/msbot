package servers;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class dream extends Server {
	String name = "dream";
	public dream() {
		this.nameSearchArea = new Rectangle();
		this.levelSearchArea = new Rectangle();
		this.chatExpanderSearchArea = new Rectangle();
		this.inventorySearchArea = new Rectangle();
		this.minimapNameSearchArea = new Rectangle();
		
		this.clientIconSearchArea = new Rectangle(0,0,1920,1080);
		this.taskbarIconSearchArea = new Rectangle();
		this.clientCaptureAdjustment = new Rectangle(-3,21,1025,768);
		
		this.inventoryLabelSearchArea = new Rectangle(850,50, 175, 650);
		
	}
	public void loadImages() {
		String path = name + "/";
		try {
			
			this.taskBarIcon = ImageIO.read(new File(path + "mapleIcon.png"));
			if(new File(path + "inventory/").exists()) {
				this.inventoryLabel = ImageIO.read(new File(path + "inventory/inventory.png"));
				this.mesosNumImages = new BufferedImage[]{
						ImageIO.read(new File(path + "inventory/0.png")),ImageIO.read(new File(path + "inventory/1.png")),ImageIO.read(new File(path + "inventory/2.png"))
						,ImageIO.read(new File(path + "inventory/3.png")),ImageIO.read(new File(path + "inventory/4.png")),ImageIO.read(new File(path + "inventory/5.png")),
						ImageIO.read(new File(path + "inventory/6.png")),ImageIO.read(new File(path + "inventory/7.png")),ImageIO.read(new File(path + "inventory/8.png")),ImageIO.read(new File(path + "inventory/9.png"))};
			} else {
				this.inventoryLabel = ImageIO.read(new File("inventory/inventory.png"));
				this.mesosNumImages = new BufferedImage[]{
						ImageIO.read(new File("inventory/0.png")),ImageIO.read(new File("inventory/1.png")),ImageIO.read(new File("inventory/2.png"))
						,ImageIO.read(new File("inventory/3.png")),ImageIO.read(new File("inventory/4.png")),ImageIO.read(new File("inventory/5.png")),
						ImageIO.read(new File("inventory/6.png")),ImageIO.read(new File("inventory/7.png")),ImageIO.read(new File("inventory/8.png")),ImageIO.read(new File("inventory/9.png"))};
			}
			if(new File(path + "numbers/").exists()) {
				this.numImages = new BufferedImage[]{
						ImageIO.read(new File(path + "numbers/0.png")),ImageIO.read(new File(path + "numbers/1.png")),ImageIO.read(new File(path + "numbers/2.png"))
						,ImageIO.read(new File(path + "numbers/3.png")),ImageIO.read(new File(path + "numbers/4.png")),ImageIO.read(new File(path + "numbers/5.png")),
						ImageIO.read(new File(path + "numbers/6.png")),ImageIO.read(new File(path + "numbers/7.png")),ImageIO.read(new File(path + "numbers/8.png")),ImageIO.read(new File(path + "numbers/9.png"))};
				this.levelImages = new BufferedImage[]{
						ImageIO.read(new File(path + "numbers/lvl0.png")),ImageIO.read(new File(path + "numbers/lvl1.png")),ImageIO.read(new File(path + "numbers/lvl2.png"))
						,ImageIO.read(new File(path + "numbers/lvl3.png")),ImageIO.read(new File(path + "numbers/lvl4.png")),ImageIO.read(new File(path + "numbers/lvl5.png")),
						ImageIO.read(new File(path + "numbers/lvl6.png")),ImageIO.read(new File(path + "numbers/lvl7.png")),ImageIO.read(new File(path + "numbers/lvl8.png")),ImageIO.read(new File(path + "numbers/lvl9.png"))};
			} else {
				this.numImages = new BufferedImage[]{
						ImageIO.read(new File("numbers/0.png")),ImageIO.read(new File("numbers/1.png")),ImageIO.read(new File("numbers/2.png"))
						,ImageIO.read(new File("numbers/3.png")),ImageIO.read(new File("numbers/4.png")),ImageIO.read(new File("numbers/5.png")),
						ImageIO.read(new File("numbers/6.png")),ImageIO.read(new File("numbers/7.png")),ImageIO.read(new File("numbers/8.png")),ImageIO.read(new File("numbers/9.png"))};
				this.levelImages = new BufferedImage[]{
						ImageIO.read(new File("numbers/lvl0.png")),ImageIO.read(new File("numbers/lvl1.png")),ImageIO.read(new File("numbers/lvl2.png"))
						,ImageIO.read(new File("numbers/lvl3.png")),ImageIO.read(new File("numbers/lvl4.png")),ImageIO.read(new File("numbers/lvl5.png")),
						ImageIO.read(new File("numbers/lvl6.png")),ImageIO.read(new File("numbers/lvl7.png")),ImageIO.read(new File("numbers/lvl8.png")),ImageIO.read(new File("numbers/lvl9.png"))};
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error loading images");
		}
	}
}