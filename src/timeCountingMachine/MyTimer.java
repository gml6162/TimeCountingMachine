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
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class MyTimer {

	/* Timer */
	private Timer mainTimer = new Timer();
	private Timer subTimer = new Timer();

	/* Visual time */
	private String mainTime;
	private int mainTimeMinute;
	private int mainTimeSecond;
	private int mainTimeMillisec;
	private String subTime;
	private int subTimeMinute;
	private int subTimeSecond;
	private int subTimeMillisec;

	/* Actual time */
	private long drivingStartTime;
	private long drivingEndTime;
	private long drivingTime;
	private long lapStartTime;
	private long lapEndTime;
	private long lapTime;
	private static long totalTime = 240000;
	public static boolean breakable = false;

	class MainTimerTask extends TimerTask {
		@Override
		public void run() {
			Main.showFrame.setTotalDrivingTimerLabel(mainTime);
			drivingEndTime = (long) System.currentTimeMillis();
			drivingTime = totalTime - (drivingEndTime - getDrivingStartTime());
			if (drivingTime < 0) {
				ControlFrame.mainStartStopButton.doClick();
				drivingTime = 0;
				if(ControlFrame.subStartStopButton.getText().equals("STOP")) {
					ControlFrame.subStartStopButton.doClick();
				}
				Main.showFrame.setTotalDrivingTimerLabel("Time Out");
				Main.showFrame.setLapTimerLabel("00:00:00");
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
			Main.showFrame.setLapTimerLabel(subTime);

			lapEndTime = (long) System.currentTimeMillis();
			setLapTime(lapEndTime - lapStartTime);
			subTimeMinute = (int) (getLapTime() / 60000);
			subTimeSecond = (int) ((getLapTime() / 1000) % 60);
			subTimeMillisec = (int) ((getLapTime() / 10) % 100);
			subTime = String.format("%02d:%02d:%02d", subTimeMinute, subTimeSecond, subTimeMillisec);
		}
	};

	class reverseTimer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JToggleButton b = (JToggleButton) e.getSource();
			if (b.getText().equals("START")) {
				b.setText("STOP");
				mainTimeMinute = 04;
				mainTimeSecond = 00;
				mainTimeMillisec = 00;
				setDrivingStartTime((long) System.currentTimeMillis());
				mainTimer = new Timer();
				mainTimer.schedule(new MainTimerTask(), 0, 50);

				System.out.println(totalTime);
				// drivingStartTime = System.currentTimeMillis();
			} else {
				b.setText("START");
				mainTimer.cancel();
				drivingEndTime = System.currentTimeMillis();
				drivingTime = drivingEndTime - getDrivingStartTime();
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
				drivingEndTime = System.currentTimeMillis();
				drivingTime = drivingEndTime - getDrivingStartTime();
				//System.out.println(drivingTime);
				//System.out.println(Main.controlFrame.getmainButtonStatus());
				if(drivingTime <= 240000 && Main.controlFrame.getmainButtonStatus().equals("STOP"))
				{
					System.out.println("test");
					b.setText("STOP");
					subTimeMinute = 00;
					subTimeSecond = 00;
					subTimeMillisec = 00;
					lapStartTime = (long) System.currentTimeMillis();
					subTimer = new Timer();
					subTimer.schedule(new subTimerTask(), 0, 50);
				}
				else {
					b.setSelected(false);
				}
			} else {
				b.setText("START");
				lapEndTime = System.currentTimeMillis();
				lapTime = lapEndTime - lapStartTime;
				//setLapTime(lapTime);
				subTimer.cancel();
				
				//sign P//test
				/*Main.showFrame.setRecordLabel(lapTime);
				Main.showFrame.setRankTable();*/
			}
		}
	}
	
	class setChangeButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			totalTime = Main.controlFrame.getTimeField();
		}
		
	}
	
	class setDCButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Main.controlFrame.setDCpenalty();
		}
		
	}
	
	class setDelayButton implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//delayFrame a = new delayFrame();
			totalTime -= 30000;
			//System.out.println(totalTime);
			
		}
	}
	
	class delayFrame extends JFrame {
		
		JTextField time = new JTextField();
		JButton chkButton = new JButton();
		private void initalize() {
			this.setVisible(false);
			this.add(time);
			this.add(chkButton);
			
			chkButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					totalTime = Long.parseLong(time.getText());
					
				}
			});
			this.setVisible(true);
		}
		
		public delayFrame() {
			super("Control");
			this.dispose();
			this.setBounds(400, 200, 300, 300);
			this.initalize();
		}
		
	}
	
	class setBreakButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(breakable){
				totalTime -= 5000;//5 second	5*1000
				System.out.println("break");
				Main.controlFrame.startCheck = false;
			}

			ControlFrame.subStartStopButton.doClick();
		}
	}
	
	class setDeleteButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			Main.showFrame.deleteRecentRecord();
			Main.showFrame.setRankTable();
		}
		
	}
	
	class setFixButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(!breakable) {
				totalTime += 30000;
				setBreakable(true);
			}
		}
	}
	

	public long getLapTime() {
		return lapTime;
	}

	private void setLapTime(long lapTime) {
		this.lapTime = lapTime;
	}
	
	public void resetTotalTime(){
		totalTime = 240000;
	}
	
	public void setBreakable(boolean b){
		breakable =b;
	}

	private long getDrivingStartTime() {
		return drivingStartTime;
	}

	private void setDrivingStartTime(long drivingStartTime) {
		this.drivingStartTime = drivingStartTime;
	}

	public void resetLapStartTime() {
		this.lapStartTime = (long)System.currentTimeMillis();
	}
}