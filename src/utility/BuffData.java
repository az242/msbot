package utility;

public class BuffData {
	int buffLength;
	int buffKey;
	String buffName;
	Client client;
	int delay;
	String image;
	boolean enabled = false;
	public BuffData(String buffName, int buffLength, int buffKey, int buffDelay) {
		this.buffKey = buffKey;
		this.buffLength = buffLength;
		this.buffName = buffName;
		this.delay = buffDelay;
	}
	public BuffData(String buffName, int buffLength, int buffKey, int buffDelay, Client client) {
		this.buffKey = buffKey;
		this.buffLength = buffLength;
		this.buffName = buffName;
		this.client = client;
		this.delay = buffDelay;
	}
	public BuffData(String buffName, int buffLength, int buffKey, int buffDelay, Client client, String image) {
		this.buffKey = buffKey;
		this.buffLength = buffLength;
		this.buffName = buffName;
		this.client = client;
		this.delay = buffDelay;
		this.image = image;
	}
	public void enable(){
		this.enabled = true;
	}
	public void disable(){
		this.enabled = false;
	}
}