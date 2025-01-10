package utility;

public class Zone {
	MaplePoint upperLeftBound;
	MaplePoint lowerRightBound;
	boolean ignoreY;
	public Zone(MaplePoint upperLeftBound, MaplePoint lowerRightBound, boolean ignoreY) {
		this.upperLeftBound = upperLeftBound;
		this.lowerRightBound = lowerRightBound;
		this.ignoreY = ignoreY;
	}
	public Zone(MaplePoint upperLeftBound, MaplePoint lowerRightBound) {
		this.upperLeftBound = upperLeftBound;
		this.lowerRightBound = lowerRightBound;
		this.ignoreY = false;
	}
	public Zone(int x1, int y1, int x2, int y2) {
		this.upperLeftBound = new MaplePoint(x1,y1);
		this.lowerRightBound = new MaplePoint(x2,y2);
	}
	public int getLeftBound(){
		return upperLeftBound.x;
	}
	public int getRightBound(){
		return lowerRightBound.x;
	}
	public int getTopBound() {
		return upperLeftBound.y;
	}
	public int getBottomBound() {
		return lowerRightBound.y;
	}
	public boolean isInZone(int x, int y) {
		if(ignoreY) {
			if(x>=upperLeftBound.x && x<=lowerRightBound.x){
				return true;
			}
		} else {
			if(x>=upperLeftBound.x && x<=lowerRightBound.x && y>=upperLeftBound.y && y<=lowerRightBound.y){
				return true;
			}
		}
		return false;
	}
	public boolean isInZone(MaplePoint point) {
		if(ignoreY) {
			if(point.x>=upperLeftBound.x && point.x<=lowerRightBound.x){
				return true;
			}
		} else {
			if(point.x>=upperLeftBound.x && point.x<=lowerRightBound.x && point.y>=upperLeftBound.y && point.y<=lowerRightBound.y){
				return true;
			}
		}
		return false;
	}
	public boolean isInYZone(int y) {
		if(y>=upperLeftBound.y && y<=lowerRightBound.y){
			return true;
		}
		return false;
	}
	public boolean isInYZone(MaplePoint point) {
		if(point.y>=upperLeftBound.y && point.y<=lowerRightBound.y){
			return true;
		}
		return false;
	}
	public boolean isInXZone(int x) {
		if(x>=upperLeftBound.x && x<=lowerRightBound.x){
			return true;
		}
		return false;
	}
	public boolean isInXZone(MaplePoint point) {
		if(point.x>=upperLeftBound.x && point.x<=lowerRightBound.x){
			return true;
		}
		return false;
	}
}