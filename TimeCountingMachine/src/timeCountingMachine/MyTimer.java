package timeCountingMachine;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class MyTimer {

	private Timer mainTimer = new Timer();
	private Timer subTimer = new Timer();

	private String mainTime;
	private int mainTimeMinute;
	private int mainTimeSecond;
	private int mainTimeMillisec;

	private String subTime;
	private int subTimeMinute;
	private int subTimeSecond;
	private int subTimeMillisec;

	/* GF */
	private long drivingStartTime;
	private long drivingEndTime;
	private long drivingTime;

	private long lapStartTime;
	private long lapEndTime;
	private long lapTime;

	//private JToggleButton mainStartStopButton = new JToggleButton("START");
	// private JToggleButton mainStopButton = new JToggleButton("STOP");
	//private JToggleButton subStartStopButton = new JToggleButton("START");

	class MainTimerTask extends TimerTask {
		@Override
		public void run() {
			ShowFrame.setTotalDrivingTimerLabel(mainTime);

			drivingEndTime = (long) System.currentTimeMillis();
			drivingTime = 300000 - (drivingEndTime - drivingStartTime);
			if (drivingTime < 0) {
				ControlFrame.mainStartStopButton.doClick();
				ControlFrame.subStartStopButton.doClick();
				mainTime = "05:00:00";
				
			} else {
				mainTimeMinute = (int) (drivingTime / 60000);
				mainTimeSecond = (int) ((drivingTime / 1000) % 60);
				mainTimeMillisec = (int) ((drivingTime / 10) % 100);

				mainTime = String.format("%02d:%02d:%02d", mainTimeMinute, mainTimeSecond, mainTimeMillisec);
			}
		}
	}

	class subTimerTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ShowFrame.setLapTimerLabel(subTime);

			lapEndTime = (long) System.currentTimeMillis();
			lapTime = lapEndTime - lapStartTime;
			subTimeMinute = (int) (lapTime / 60000);
			subTimeSecond = (int) ((lapTime / 1000) % 60);
			subTimeMillisec = (int) ((lapTime / 10) % 100);

			subTime = String.format("%02d:%02d:%02d", subTimeMinute, subTimeSecond, subTimeMillisec);
		}
	};

	class reverseTimer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JToggleButton b = (JToggleButton) e.getSource();
			if (b.getText().equals("START")) {
				b.setText("STOP");
				mainTimeMinute = 05;
				mainTimeSecond = 00;
				mainTimeMillisec = 00;
				drivingStartTime = (long) System.currentTimeMillis();
				mainTimer = new Timer();
				mainTimer.schedule(new MainTimerTask(), 0, 50);
				// drivingStartTime = System.currentTimeMillis();
			} else {
				b.setText("START");
				mainTimer.cancel();
				drivingEndTime = System.currentTimeMillis();
				drivingTime = drivingEndTime - drivingStartTime;
				System.out.println(String.valueOf(drivingTime));
			}
		}
	}
	
	class sequenceTimer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JToggleButton b = (JToggleButton) e.getSource();
				if (b.getText().equals("START")) {
					b.setText("STOP");
					subTimeMinute = 00;
					subTimeSecond = 00;
					subTimeMillisec = 00;
					lapStartTime = (long) System.currentTimeMillis();
					subTimer = new Timer();
					subTimer.schedule(new subTimerTask(), 0, 50);
				} else {
					b.setText("START");
					lapEndTime = System.currentTimeMillis();
					lapTime = lapEndTime - lapStartTime;
					subTimer.cancel();
				}
			}
		}
		
	}

	/*
	 * mainStartStopButton.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { // TODO
	 * Auto-generated method stub JToggleButton b = (JToggleButton)
	 * e.getSource(); if (b.getText().equals("START")) { b.setText("STOP");
	 * mainTimeMinute = 05; mainTimeSecond = 00; mainTimeMillisec = 00;
	 * drivingStartTime = (long) System.currentTimeMillis(); mainTimer = new
	 * Timer(); mainTimer.schedule(new MainTimerTask(), 0, 50); //
	 * drivingStartTime = System.currentTimeMillis(); } else {
	 * b.setText("START"); mainTimer.cancel(); drivingEndTime =
	 * System.currentTimeMillis(); drivingTime = drivingEndTime -
	 * drivingStartTime; System.out.println(String.valueOf(drivingTime)); } }
	 * });
	 * 
	 * subStartStopButton.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { // TODO
	 * Auto-generated method stub JToggleButton b = (JToggleButton)
	 * e.getSource(); if (b.getText().equals("START")) { b.setText("STOP");
	 * subTimeMinute = 00; subTimeSecond = 00; subTimeMillisec = 00;
	 * lapStartTime = (long) System.currentTimeMillis(); subTimer = new Timer();
	 * subTimer.schedule(new subTimerTask(), 0, 50); } else {
	 * b.setText("START"); lapEndTime = System.currentTimeMillis(); lapTime =
	 * lapEndTime - lapStartTime; subTimer.cancel(); } } });
	 */