package timeCountingMachine;

import java.util.HashMap;

public class Main {
	public static void main(String[] arguis) {
		FileManager.saveData();
		FileManager.loadUserData("userNames.txt");
		ShowFrame showFrame = new ShowFrame();
		ControlFrame ctrlFrame = new ControlFrame();
	}
}