package utility;

import java.io.IOException;

public abstract interface Continent {
	/**
	 * Abstract Method used for selling Equips at this continent
	 * @throws IOException
	 */
	public abstract void sellEquips();
	public abstract void bot(int hours, String map);
}
