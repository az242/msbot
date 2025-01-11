package msBot;
import static java.time.temporal.ChronoUnit.MINUTES;

import java.awt.AWTException;
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
	public BaseBot(Client[] clients, Server server) throws AWTException {
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
			this.server.searchArea = getMapleScreen(false);
//			mapleScreen = getMapleScreen();
			botOutput("Found Maple screen");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		chat = adjustRectangle(mapleScreen, chat);
//		home= adjustRectangle(mapleScreen, new Rectangle(875, 723, 10, 12));
//		insert = adjustRectangle(mapleScreen, new Rectangle(840, 723, 10, 12));
//		pageUp = adjustRectangle(mapleScreen, new Rectangle(910, 723, 10, 12));
//		buffZone = adjustRectangle(mapleScreen, new Rectangle(990, 24, 30, 30));
		
//		try {
//			initScreens(this.server);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		this.server.loadImages();
		try {
			int startingMesos = getMesos();
			int startingLevel = getLevel();
			botOutput("Starting level: " + startingLevel);
			botOutput("Starting mesos: " + startingMesos);
		} catch (IOException e) {
			botOutput("Error getting starting mesos/level");
		}
	}
	
	/**
	 * Abstract Method used for selling Equips at this continent
	 * @throws IOException
	 */
	public abstract void sellEquips() throws IOException;
	/**
	 * Searches for the maple client's icon in the given server's icon search area.
	 * Adjusts the mapleclient rectangle by the server's clientCaptureAdjustment
	 * @param saveImage Boolean - If true will save the image, else do nothing
	 * @return Rectangle - The screen area of the Maple Client
	 * @throws IOException
	 */
	public Rectangle getMapleScreen(boolean saveImage) throws IOException {
		MaplePoint upperLeft = getOrigin(this.server.clientIconSearchArea, this.server.taskBarIcon, false);
		Rectangle mapleClient = new Rectangle(upperLeft.x + this.server.clientCaptureAdjustment.x,upperLeft.y + this.server.clientCaptureAdjustment.y,this.server.clientCaptureAdjustment.width,this.server.clientCaptureAdjustment.height);
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
	/**
	 * finds a matching image in search area with pixels that are below 50 in RGB. (dark pixels)
	 * @param searchArea - Rectangle - The area to search in
	 * @param image - BufferedImage - image to match against
	 * @param saveImage - boolean - true to save images
	 * @return MaplePoint - The origin of the matched image, or -1,-1 if not found
	 * @throws IOException
	 */
	public MaplePoint getOriginByOutline(Rectangle searchArea, BufferedImage image, boolean saveImage) throws IOException {
		BufferedImage searchAreaImage = robot.createScreenCapture(searchArea);
		for(int x1=0;x1<searchAreaImage.getWidth()-image.getWidth();x1++) {
			for(int y1=0;y1<searchAreaImage.getHeight()-image.getHeight();y1++) {
				boolean matches = true;
				imageLoop:
				for(int x2=0;x2<image.getWidth();x2++) {
					for(int y2=0;y2<image.getHeight();y2++) {
						Color pic1 = new Color(image.getRGB(x2, y2));
						if(pic1.getRed() <= 50 && pic1.getBlue() <= 50 && pic1.getGreen() <= 50) {
							Color pic2 = new Color(searchAreaImage.getRGB(x1+x2, y1+y2));
							if(pic2.getRed() > 50 || pic2.getBlue() > 50 || pic2.getGreen() > 50) {
								matches = false;
								break imageLoop;
							}
						}
					}
				}
				if(matches) {
					if(saveImage) {
						File outputfile = new File("couldFindBuff.png");
					    ImageIO.write(image, "png", outputfile);
						System.out.println(x1 + ", " + y1);
					}
					return new MaplePoint(x1,y1);
				}
			}
		}
		if(saveImage) {
			File outputfile = new File("couldFindBuff.png");
		    ImageIO.write(image, "png", outputfile);
		}
		return new MaplePoint(-1,-1);
	}
	/**
	 * Find numbers in the given search area by their white pixels.
	 * @param searchArea - Rectangle - The area to search for numbers
	 * @param numbers - BufferedImage[] - the array of buffered images of numbers
	 * @param pixelOffset - int - Offset of each number in by pixels
	 * @param inverseCheck - boolean - If true will check against pixels NOT white, if false will only check black pixels
	 * @return ArrayList<Integer> - returns an array list of found digits
	 * @throws IOException
	 */
	public ArrayList<Integer> findNums(Rectangle searchArea, BufferedImage[] numbers, int pixelOffset, boolean inverseCheck) throws IOException {
		BufferedImage image = robot.createScreenCapture(searchArea);
		ArrayList<Integer> numbersFound = new ArrayList<Integer>();
		for(int k=0;k<5;k++) {
			for(int i=numbers.length-1;i>=0;i--) {
				boolean match = true;
				bp1:
				for(int x2=0;x2<numbers[i].getWidth();x2++) {
					for(int y2=0;y2<numbers[i].getHeight();y2++) {
						Color pic1 = new Color(numbers[i].getRGB(x2, y2));
						if(inverseCheck) {
							if(pic1.getRed() != 255 && pic1.getBlue() != 255 && pic1.getGreen() != 255) {
								if(numbers[i].getRGB(x2, y2) != image.getRGB(x2 + (k * pixelOffset), y2)) {
									match = false;
									break bp1;
								}
							}
						} else {
							if(pic1.getRed() == 255 && pic1.getBlue() == 255 && pic1.getGreen() == 255) {
								if(numbers[i].getRGB(x2, y2) != image.getRGB(x2 + (k * pixelOffset), y2)) {
									match = false;
									break bp1;
								}
							}
						}
					}
				}
				if(match) {
					numbersFound.add(i);
					break;
				}
			}
		}
		return numbersFound;
	}
	/**
	 * Find numbers in the given search area by their white pixels. ( ONLY CHECKS WHITE PIXELS)
	 * @param searchArea - Rectangle - The area to search for numbers
	 * @param numbers - BufferedImage[] - the array of buffered images of numbers
	 * @param pixelOffset - int - Offset of each number in by pixels
	 * @return ArrayList<Integer> - returns an array list of found digits
	 * @throws IOException
	 */
	public ArrayList<Integer> findNums(Rectangle searchArea, BufferedImage[] numbers, int pixelOffset) throws IOException {
		return findNums(searchArea, numbers, pixelOffset, false);
	}
	/**
	 * Finds the origin of the Inventory label in the search area adjusted by the inventoryLabelSearchArea
	 * @return MaplePoint - Origin of found inventory label, or -1,-1 if not found
	 * @throws IOException
	 */
	public MaplePoint findInventory() throws IOException {
		Rectangle adjustedSearchArea = adjustRectangle(this.server.searchArea, this.server.inventoryLabelSearchArea);
		MaplePoint x = getOrigin(adjustedSearchArea, this.server.inventoryLabel, false);
		if(x.x >= 0) {
			//adjust
			x.x = adjustedSearchArea.x + x.x;
			x.y = adjustedSearchArea.y + x.y;
		}
		return x;
	}
	public String formatMesos(int number) {
		return String.format("%,d", number);
	}
	public int getHp() throws IOException {
		int hp = 0;
		Rectangle hpRect = adjustRectangle(this.server.searchArea, new Rectangle(this.server.hpSearchArea));
		ArrayList<Integer> numbersFound = findNums(hpRect,this.server.numImages,6);
		for(int x=0;x<numbersFound.size();x++) {
			hp = hp + numbersFound.get(x)*10^(numbersFound.size()-x-1);
		}
		return hp;
	}
	public int getMp() throws IOException {
		int mp = 0;
		Rectangle MPRect = adjustRectangle(this.server.searchArea, new Rectangle(this.server.mpSearchArea));
		ArrayList<Integer> numbersFound = findNums(MPRect, this.server.numImages, 6);
		for(int x=0;x<numbersFound.size();x++) {
			mp = (int) (mp + numbersFound.get(x)*Math.pow(10, numbersFound.size()-x-1));
		}
		return mp;
	}
	public int getLevel() throws IOException {
		int level = 0;
		Rectangle LevelRect = adjustRectangle(this.server.searchArea, new Rectangle(this.server.levelSearchArea));
		ArrayList<Integer> findLevelNums = findNums(LevelRect, this.server.levelImages, 12);
		for(int x=0;x<findLevelNums.size();x++) {
			level = (int) (level + findLevelNums.get(x)*Math.pow(10, findLevelNums.size()-x-1));
		}
		return level;
	}
	/**
	 * gets the mesos in the inventory
	 * @return
	 * @throws IOException
	 */
	public int getMesos() throws IOException {
		MaplePoint invLoc = findInventory();
		ArrayList<Integer> list1 = findNums(new Rectangle(adjustRectangle(invLoc, this.server.hundredmesos)),this.server.mesosNumImages, 7, true);
		ArrayList<Integer> list2 = findNums(new Rectangle(adjustRectangle(invLoc, this.server.thousandmesos)),this.server.mesosNumImages, 7, true);
		ArrayList<Integer> list3 = findNums(new Rectangle(adjustRectangle(invLoc, this.server.millionmesos)),this.server.mesosNumImages, 7, true);
		ArrayList<Integer> list4 = findNums(new Rectangle(adjustRectangle(invLoc, this.server.billionmesos)),this.server.mesosNumImages, 7, true);
		ArrayList<Integer> combinedList = new ArrayList<>();
		combinedList.addAll(list4);
		combinedList.addAll(list3);
		combinedList.addAll(list2);
		combinedList.addAll(list1);
        
		int total = 0;
		for (Integer i : combinedList) {
            total = 10 * total + i;
        }
		return total;
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
	 * Adjusts the area's x and y coordinates by the given adjustment Rectangle
	 * @param adjustment - Rectangle - The rectangle that defines the adjustments
	 * @param area - Rectangle - The area defined in the play area
	 * @return Rectangle - The adjusted area relative to the whole screen
	 */
	public static Rectangle adjustRectangle(Rectangle adjustment, Rectangle area) {
		area.setLocation((int)(adjustment.getX() + area.getX()),(int)( adjustment.getY() + area.getY()));
		return area;
	}
	/**
	 * Adjusts the area's x and y coordinates by the given adjustment Rectangle
	 * @param adjustment - MaplePoint - The rectangle that defines the adjustments
	 * @param area - Rectangle - The area defined in the play area
	 * @return Rectangle - The adjusted area relative to the whole screen
	 */
	public static Rectangle adjustRectangle(MaplePoint adjustment, Rectangle area) {
		area.setLocation((int)(adjustment.getX() + area.getX()),(int)( adjustment.getY() + area.getY()));
		return area;
	}
}
