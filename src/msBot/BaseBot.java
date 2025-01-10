package msBot;
import static java.time.temporal.ChronoUnit.MINUTES;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import servers.Server;
import utility.AttackData;
import utility.Client;
import utility.MaplePoint;
public abstract class BaseBot {
	Robot robot;
	Server server;
	HashMap<String, AttackData> attacks = new HashMap<String, AttackData>();
	Client[] clients;
	
	LocalTime startTime;
	public BaseBot(Client[] clients, Server server) {
		this.server = server;
		this.attacks.put("genesis", new AttackData(KeyEvent.VK_C, 2375, 2500));
		this.attacks.put("meteor", new AttackData(KeyEvent.VK_C, 3100, 1900));
		this.attacks.put("heal", new AttackData(KeyEvent.VK_V, 590));
		this.attacks.put("shining", new AttackData(KeyEvent.VK_N, 1050));
		this.robot = new Robot();
		this.clients = clients;
		startTime = LocalTime.now();
		botOutput("Initializing bot with " + clients.length + " screens.");
		try {
			this.server.searchArea = getMapleScreen();
//			mapleScreen = getMapleScreen();
			botOutput("Found Maple screen");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		chat = adjustRectangle(mapleScreen, chat);
		home= adjustRectangle(mapleScreen, new Rectangle(875, 723, 10, 12));
		insert = adjustRectangle(mapleScreen, new Rectangle(840, 723, 10, 12));
		pageUp = adjustRectangle(mapleScreen, new Rectangle(910, 723, 10, 12));
		buffZone = adjustRectangle(mapleScreen, new Rectangle(990, 24, 30, 30));
		
		try {
			initScreens(this.server);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.server.loadImages();
		try {
			startingMesos = getMesos();
			startingLevel = getLevel();
			botOutput("Starting level: " + startingLevel);
			botOutput("Starting mesos: " + startingMesos);
		} catch (IOException e) {
			botOutput("Error getting starting mesos/level");
		}
	}
	public MaplePoint findInventory() throws IOException {
		Rectangle inventorySearchArea = new Rectangle(850,50, 175, 650);
		inventorySearchArea = adjustRectangle(this.server.searchArea, inventorySearchArea);
		MaplePoint x = getOrigin(inventorySearchArea, "inventory/inventory.png");
		if(x.x < 0) {
			
		} else {
			//adjust
			x.x = inventorySearchArea.x + x.x;
			x.y = inventorySearchArea.y + x.y;
		}
		
		return x;
	}
	/**
	 * Abstract Method used for selling Equips at this continent
	 * @throws IOException
	 */
	public abstract void sellEquips() throws IOException;
	/**
	 * Searches for the maple client's icon in the given server's icon search area.
	 * Adjusts the mapleclient rectangle by the server's clientCaptureAdjustment
	 * @return Rectangle - returns the screen area of the Maple Client
	 */
	public Rectangle getMapleScreen() {
		MaplePoint upperLeft = getOrigin(this.server.clientIconSearchArea, this.server.taskBarIcon);
		return new Rectangle(upperLeft.x + this.server.clientCaptureAdjustment.x,upperLeft.y + this.server.clientCaptureAdjustment.y,this.server.clientCaptureAdjustment.width,this.server.clientCaptureAdjustment.height);
	}
	/**
	 * Searches for the maple client's icon in the given server's icon search area.
	 * @param saveImage Boolean - If true will save the image, else do nothing
	 * @return Rectangle - The screen area of the Maple Client
	 * @throws IOException
	 */
	public Rectangle getMapleScreen(boolean saveImage) throws IOException {
		Rectangle mapleClient = getMapleScreen();
		if(saveImage) {
			BufferedImage bi = robot.createScreenCapture(mapleClient);  // retrieve image
		    File outputfile = new File("saved.png");
		    ImageIO.write(bi, "png", outputfile);
		}
		return mapleClient;
	}
	/**
	 * Searches for the given BufferedImage image in the given Rectangle searchArea and returns the x,y where the image matched.
	 * @param searchArea - Rectangle - The area that will be searched
	 * @param image - BufferedImage - The image to be matched against
	 * @return MaplePoint - The x,y coordinate that matched the image
	 */
	public MaplePoint getOrigin(Rectangle searchArea, BufferedImage image) {
		BufferedImage searchAreaImage = robot.createScreenCapture(searchArea);
		for(int x1=0;x1<searchAreaImage.getWidth()-image.getWidth();x1++) {
			for(int y1=0;y1<searchAreaImage.getHeight()-image.getHeight();y1++) {
				boolean matches = true;
				ImageLoop:
				for(int x2=0;x2<image.getWidth();x2++) {
					for(int y2=0;y2<image.getHeight();y2++) {
						if(image.getRGB(x2, y2) != searchAreaImage.getRGB(x1+x2, y1+y2)) {
							matches = false;
							break ImageLoop;
						}
					}
				}
				if(matches) {
					return new MaplePoint(x1,y1);
				}
			}
		}
		return new MaplePoint(-1,-1);
	}
	/**
	 * Searches for the given BufferedImage image in the given Rectangle searchArea and returns the x,y where the image matched.
	 * @param searchArea - Rectangle - The area that will be searched
	 * @param image - BufferedImage - The image to be matched against
	 * @param saveImage - Boolean - True will save images of the searchArea
	 * @return MaplePoint - The x,y coordinate that matched the image
	 */
	public MaplePoint getOrigin(Rectangle searchArea, BufferedImage image, boolean saveImage) throws IOException {
		BufferedImage searchAreaImage = robot.createScreenCapture(searchArea);
		for(int x1=0;x1<searchAreaImage.getWidth()-image.getWidth();x1++) {
			for(int y1=0;y1<searchAreaImage.getHeight()-image.getHeight();y1++) {
				boolean matches = true;
				ImageLoop:
				for(int x2=0;x2<image.getWidth();x2++) {
					for(int y2=0;y2<image.getHeight();y2++) {
						if(image.getRGB(x2, y2) != searchAreaImage.getRGB(x1+x2, y1+y2)) {
							matches = false;
							break ImageLoop;
						}
					}
				}
				if(matches) {
					if(saveImage) {
						File outputfile = new File("couldFind.png");
					    ImageIO.write(image, "png", outputfile);
					}
					return new MaplePoint(x1,y1);
				}
			}
		}
		if(saveImage) {
			File outputfile = new File("couldntFind.png");
		    ImageIO.write(image, "png", outputfile);
		}
		return new MaplePoint(-1,-1);
	}
	public void botOutput(String output) {
		LocalTime myObj = LocalTime.now();
	    System.out.println("[" + myObj + "] " + output);
	}
	/**
	 * Clicks on the x,y coordinate given
	 * @param x
	 * @param y
	 */
	public void click(int x, int y) {
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	/**
	 * Clicks on the MaplePoint given
	 * @param point
	 */
	public void click(MaplePoint point) {
		robot.mouseMove(point.x, point.y);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	public void keyPress(int key) {
		robot.keyPress(key);
		robot.keyRelease(key);
	}
	public void keyHold(int key, int delay) {
		robot.keyPress(key);
		robot.delay(delay);
		robot.keyRelease(key);
	}
	public void moveRight(int amount) {
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.delay(amount);
		robot.keyRelease(KeyEvent.VK_RIGHT);
	}
	public void moveLeft(int amount) {
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.delay(amount);
		robot.keyRelease(KeyEvent.VK_LEFT);
	}
	public void teleportRight() {
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.delay(200);
		robot.keyRelease(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.delay(400);
	}
	public void teleportLeft() {
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.delay(200);
		robot.keyRelease(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.delay(400);
	}
	public void teleportUp() {
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.delay(200);
		robot.keyRelease(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.delay(100);
	}
	public void jumpDown() {
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_SPACE);
		robot.delay(100);
		robot.keyRelease(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_SPACE);
		robot.delay(500);
	}
	public static int randomNum(int min, int max) {
		return (int)(Math.random()*(max-min))+min;
	}
	public static int randomPosNeg(int input) {
		double random = Math.random();
		if(random > .5) {
			return input;
		} else {
			return input * -1;
		}
	}
	/**
	 * Adjusts the area by the maplescreen given
	 * @param mapleScreen - Rectangle - The rectangle that defines the play area of the Maple Client
	 * @param area - Rectangle - The area defined in the play area
	 * @return Rectangle - The adjusted area relative to the whole screen
	 */
	public static Rectangle adjustRectangle(Rectangle mapleScreen, Rectangle area) {
		area.setLocation((int)(mapleScreen.getX() + area.getX()),(int)( mapleScreen.getY() + area.getY()));
		return area;
	}
}
