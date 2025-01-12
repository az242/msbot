package servers;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import msBot.BaseBot;
import utility.AttackData;
import utility.Client;
import utility.MaplePoint;

public class DreamBot extends BaseBot {
	public HashMap<String, AttackData> attacks = new HashMap<String, AttackData>();
	public DreamBot() throws AWTException {
		super(new Client[] {new Client("cleric", "test.png")});
		name = "dreamms";
		nameSearchArea = new Rectangle();
		levelSearchArea = new Rectangle();
		chatExpanderSearchArea = new Rectangle(605,705, 30, 30);
		inventorySearchArea = new Rectangle();
		minimapNameSearchArea = new Rectangle();
		
		clientIconSearchArea = new Rectangle(0,0,1920,1080);
		taskbarIconSearchArea = new Rectangle();
		clientCaptureAdjustment = new Rectangle(-3,21,1280,720);
		
		inventoryLabelSearchArea = new Rectangle(1100, 50, 175, 400);
		billionmesos = new Rectangle(36, 259, 20, 9);
		millionmesos = new Rectangle(60, 259, 20, 9);
		thousandmesos = new Rectangle(84, 259, 20, 9);
		hundredmesos = new Rectangle(108, 259, 20, 9);
		
		hpSearchArea = new Rectangle(262,739,35,7);
		mpSearchArea = new Rectangle(395,739,35,7);
		//each num 5x7
		//1 pixel inbetween numbers
		//starts at 353 739
		levelSearchArea = new Rectangle(38,744, 35, 13);
		this.attacks.put("genesis", new AttackData(KeyEvent.VK_C, 2375, 2500));
		this.attacks.put("meteor", new AttackData(KeyEvent.VK_C, 3100, 1900));
		this.attacks.put("heal", new AttackData(KeyEvent.VK_V, 615));
		this.attacks.put("shining", new AttackData(KeyEvent.VK_N, 1050));
		this.loadImages();
		try {
			this.searchArea = getMapleScreen(true);
			startingMesos = getMesos();
//			int startingLevel = getLevel();
//			botOutput("Starting level: " + startingLevel);
			botOutput("Starting mesos: " + startingMesos);
//			System.out.println("Search Area: " + this.searchArea.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadImages() {
		String path = name + "/";
		try {
			this.chatExpandedImage = ImageIO.read(new File(path + "chatOpen.png"));
			this.minimapPlayerImage = ImageIO.read(new File(path + "minimapLocation.png"));
			this.titleBarIcon = ImageIO.read(new File(path + "mapleIcon.png"));
			if(new File(path + "inventory/").exists()) {
				System.out.println("loaded custom numbers");
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
