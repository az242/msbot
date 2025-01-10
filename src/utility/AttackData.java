package utility;

public class AttackData {
	int delay;
	int key;
	int manaCost;
	public AttackData(int key, int delay, int manaCost) {
		this.delay = delay;
		this.key = key;
		this.manaCost = manaCost;
	}
	public AttackData(int key, int delay) {
		this.delay =delay;
		this.key = key;
	}
}