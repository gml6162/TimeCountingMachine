package timeCountingMachine;

import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
	public static ShowFrame showFrame;
	public static ControlFrame controlFrame;
	public static void main(String[] arguis) {
		// File test and load data.
		FileManager.loadUserData();
		FileManager.saveData();
		
		// Show frames.
		showFrame = new ShowFrame();
		controlFrame = new ControlFrame();
	}
}