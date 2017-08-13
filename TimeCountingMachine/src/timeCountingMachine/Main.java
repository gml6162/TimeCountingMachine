package timeCountingMachine;

import java.util.HashMap;

public class Main {
	public static HashMap<String, String[]> userData = new HashMap<String, String[]>();

	public static void main(String[] arguis) {
		FileManager.saveData(); // for output test
		userData = FileManager.loadUserData(); // for input test
		ShowFrame showFrame = new ShowFrame();
		ControlFrame ctrlFrame = new ControlFrame();
	}
}