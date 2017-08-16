package timeCountingMachine;

import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] arguis) {
		// File test and load data.
		FileManager.loadUserData();
		FileManager.saveData();
		
		// Show frames.
		ShowFrame showFrame = new ShowFrame();
		ControlFrame ctrlFrame = new ControlFrame();
		ctrlFrame.showFrame = showFrame;
		ctrlFrame.link();
	}
}