package msBot;

import DreamMS.Singapore;

public class Runner {
	public static void main(String args[]) {
		try {
			BaseBot bot = new Singapore();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
