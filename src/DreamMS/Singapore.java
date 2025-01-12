package DreamMS;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import servers.DreamBot;
import utility.Continent;
import utility.Map;
import utility.MaplePoint;
import utility.Zone;

public class Singapore extends DreamBot implements Continent{
	ArrayList<Map> maps;
	Zone topFloor = new Zone(new MaplePoint(2,20),new MaplePoint(140,33));
	Zone midFloor = new Zone(new MaplePoint(2,37),new MaplePoint(140,54));
	Zone botFloor = new Zone(new MaplePoint(2,58),new MaplePoint(140,75));
	public Singapore() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
		String path = name + "/";
		Map gs2 = new Map("gs2", new Rectangle(12,103,121,53), new Rectangle(54,77,75,15),path + "minimapNames/gs2mapName.png");
		Map gs1 = new Map("gs1", new Rectangle(6,72,115,53), new Rectangle(47,45,75,15),path + "minimapNames/gs1mapName.png");
		Map gs6 = new Map("gs6", new Rectangle(6,72,115,53), new Rectangle(47,45,75,15),path + "minimapNames/gs2mapName.png");
		
		Map ulu2 = new Map("ulu2", new Rectangle(6,72,132,81), new Rectangle(47,45,70,15),path + "minimapNames/ulu2Mapname.png");
		Map ulu1 = new Map("ulu1", new Rectangle(6,72,132,104), new Rectangle(47,45,70,15),path + "minimapNames/ulu2Mapname.png");
		
		Map cd = new Map("cds", new Rectangle(33,72,141,80), new Rectangle(47,45,115,15),path + "minimapNames/cdName.png");
		
		Map cityMap = new Map("singapore", new Rectangle(6,72,210,93), new Rectangle(48,46,70,15), path + "minimapNames/singapore.png");
		maps = new ArrayList<Map>();
		maps.add(gs6);
		maps.add(gs2);
		maps.add(gs1);
		maps.add(ulu2);
		maps.add(ulu1);
		maps.add(cd);
		maps.add(cityMap);
		adjustMinimapData(this.searchArea, maps);
	}

	@Override
	public void sellEquips() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bot(int hours, String mapName) {
		Map map = getMap(mapName, maps);
		MaplePoint cords = getMinimapPosition(map);
		System.out.println("<--------------->");
		System.out.println("Starting leech script");
		System.out.println("<--------------->");
		botOutput("Starting at position: " + cords.toString());
		int position = 0;
		int lootPosition = 0;
		long currTime = System.currentTimeMillis();
		long startTime = System.currentTimeMillis();
//		rebuff(.75);
		int minutes = 0;
//		checkEquipment();
		while(startTime + (hours * 60 * 60 * 1000) > currTime) {
//			checkPots();
			position = movement(position, map);
//			MaplePoint newCoords = getMinimapPosition(map);
//			botOutput("Moved to position index: " + newCoords.toString());
			robot.delay(200);
			attack(map);
//			feedPets();
			currTime = System.currentTimeMillis();
			LocalTime now = LocalTime.now();
			if(MINUTES.between(this.startTime, now) > minutes ) {
				minutes = (int) MINUTES.between(this.startTime, now);
				botOutput("Script ran for " + minutes + " minutes");
				
			}
			
		}
		exitScript();
	}
	public int movement(int position, Map map) throws IOException {
		switch(map.name) {
//		case "ulu2":
//			return ulu2(position, map);
//		case "ulu1":
//			return ulu1(position, map);
		case "gs2":
			return GSSafeMovement(position,map);
//		case "gs2dungeon":
//			return GS2Movement(position, map);
//		case "cds":
//		case "cdsdungeon":
//			return cdMovement(position, map);
//		case "gs1":
//			return GS1Movement(position, map);
//		case "gs6":
//			return GS6Movement(position, map);
		}
		return 0;
	}
	public void attack(Map map) throws IOException {
		switch(map.name) {
		case "ulu2":
		case "gs2":
		case "gs1":
		case "gs6":
		case "gs2dungeon":
		case "ulu1":
//			attack(1, KeyEvent.VK_C, 2750, 2000);
			attack(this.attacks.get("heal"), 100);
//			attack(1, KeyEvent.VK_V, 1115);
			
			break;
		case "cds":
		case "cdsdungeon":
			break;
		}
	}
	public int GSSafeMovement(int position, Map map) throws IOException {
		int leftBound = 96;
		int rightBound = 101;
		
//		long currTime = System.currentTimeMillis();
//		if(currTime > dropTimer + 120*1000 && position == 1) {
//			dropTimer = currTime;
//			swapPlatforms2(map);
//			moveToZoneX(leftSide, map);
//		}
		MaplePoint currPos = getMinimapPosition(map);
		MaplePoint currPos2= getMinimapPosition(map);
		double rand = Math.random();
		if(currPos.x >=39) {
			rand = 0;
		} else if(currPos.x <= 37) {
			rand = 1;
		}
		if(rand > .5) {
			//move right
			robot.keyPress(KeyEvent.VK_RIGHT);
			while(currPos2.x == currPos.x) {
				robot.delay(30);
				currPos2 = getMinimapPosition(map);
			}
			robot.keyRelease(KeyEvent.VK_RIGHT);
		} else {
			//move left
			robot.keyPress(KeyEvent.VK_LEFT);
			while(currPos2.x == currPos.x) {
				robot.delay(30);
				currPos2 = getMinimapPosition(map);
			}
			robot.keyRelease(KeyEvent.VK_LEFT);
		}
		return 0;
	}
}
