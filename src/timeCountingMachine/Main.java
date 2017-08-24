package timeCountingMachine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TooManyListenersException;

import javax.swing.JOptionPane;

import java.io.*;
import java.util.*;
import gnu.io.*;

public class Main {

	public static ShowFrame showFrame;
	public static ControlFrame controlFrame;

	public static void main(String[] arguis) {
		try {
			Enumeration e = CommPortIdentifier.getPortIdentifiers();

			System.out.println("Enumeration get()............... " + e.hasMoreElements());

			while (e.hasMoreElements()) {
				CommPortIdentifier first = (CommPortIdentifier) e.nextElement();
				System.out.println("COM name : " + first.getName());
				
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("1��");
		// File test and load data.
		FileManager.loadUserData();
		FileManager.saveData();
		// Show frames.
		showFrame = new ShowFrame();
		controlFrame = new ControlFrame();
		try {
			controlFrame.connect("COM3");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}