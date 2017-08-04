package timeCountingMachine;

import java.util.HashMap;

public class Main {
	public static HashMap<String, Object> userData = new HashMap<String, Object>();
	public static void main(String[] arguis) {
		UserDataInputFrame userDataInputFrame = new UserDataInputFrame();
		FileManager.saveData(); // for output test
		FileManager.loadUserNames(); // for input test
	}
}
