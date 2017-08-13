package timeCountingMachine;

import java.util.HashMap;

public class Main {
	public static void main(String[] arguis) {
		// File test and load data.
		FileManager.saveData();
		FileManager.loadUserData("userNames.txt");
		
		// Show frames.
		ShowFrame showFrame = new ShowFrame();
		ControlFrame ctrlFrame = new ControlFrame();
	}
}