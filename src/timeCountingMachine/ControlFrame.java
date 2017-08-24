package timeCountingMachine;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import gnu.io.*;

import java.util.Enumeration;

import timeCountingMachine.MyTimer.reverseTimer;
import timeCountingMachine.MyTimer.sequenceTimer;
import timeCountingMachine.MyTimer.setBreakButton;
import timeCountingMachine.MyTimer.setChangeButton;
import timeCountingMachine.MyTimer.setDCButton;
import timeCountingMachine.MyTimer.setDelayButton;
import timeCountingMachine.MyTimer.setDeleteButton;

public class ControlFrame extends JFrame {

	// public ShowFrame Main.showFrame;

	private JPanel mainButtonPanel = new JPanel(new GridLayout(6, 1, 0, 5));
	private JPanel subButtonPanel = new JPanel(new GridLayout(5, 1, 0, 5));
	private JPanel switchPersonPanel = new JPanel(new GridLayout(2, 1, 0, 5));
	private JPanel mainPanel = new JPanel(new GridLayout(1, 3, 5, 5));
	private JPanel userDataPanel = new JPanel();

	public static JToggleButton mainStartStopButton = new JToggleButton("START");
	public static JToggleButton subStartStopButton = new JToggleButton("START");

	private JButton upButton = new JButton("up");
	private JButton downButton = new JButton("down");
	private JButton delayButton = new JButton("delay");
	private JButton breakButton = new JButton("break");
	private JButton deleteButton = new JButton("delete");
	private JButton fixButton = new JButton("fix");
	private JButton changeButton = new JButton("change");
	private JButton DCButton = new JButton("DC");
	
	private JLabel mainButtonLabel = new JLabel("total driving time");
	private JLabel subButtonLabel = new JLabel("lap time");

	private JTable userDataTable;
	
	private JTextField timeField = new JTextField();
	
	private String currentName;
	private int row;
	private static long DCpenalty = 0;
	private static CommPortIdentifier portId;
	public boolean startCheck;

	private MyTimer timer = new MyTimer();

	public int getRow() {
		return row;
	}

	void connect(String portName) throws Exception, PortInUseException  {
		CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
		if (portId.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portId.open(this.getClass().getName(), 2000);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(288000, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();
				OutputStream out = serialPort.getOutputStream();

				(new Thread(new SerialReader(in))).start();
				(new Thread(new SerialWriter(out))).start();

			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	public class SerialReader implements Runnable {
		InputStream in;

		public SerialReader(InputStream in) {
			this.in = in;
		}

		public void run() {
			byte[] buffer = new byte[1024];
			int len = -1;
			try {
				while ((len = this.in.read(buffer)) > -1) {
					String data = new String(buffer, 0, len);
					/// µ¥ÀÌÅÍ ÀÐ¾î¿À´Â ºÎºÐ
					System.out.print(data);
					if (data.equals("S")) {
						System.out.println(startCheck);
						if (Main.showFrame.getTotalDrivingTimerLabel().equals("Time Over") == false) {
							if (startCheck) {
								timer.resetLapStartTime();
								
							} else {
								timer.breakable = true;
								subStartStopButton.doClick();
								startCheck = !startCheck;							

								// Thread rthread = new Thread(new RThreadtest());
								// rthread.start();
							}
						}
					} else if (data.equals("E")) {
						if (startCheck) {
							System.out.println("stopButton");
							subStartStopButton.doClick();
							startCheck = !startCheck;
						}
					} else if (len == 8) {
						System.out.println(DCpenalty);
						System.out.println("n");
						//System.out.println(longToString(Long.parseLong(data)));
						Main.showFrame.setRecordLabel((Long.parseLong(data)+DCpenalty)/10);
						Main.showFrame.setRankTable();
						Main.showFrame.setLapTimerLabel((Long.parseLong(data)+DCpenalty)/10);
					}
					
				}
			} catch (NoSuchElementException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class SerialWriter implements Runnable {
		OutputStream out;

		public SerialWriter(OutputStream out) {
			this.out = out;
		}

		public void run() {
			try {
				int c = 0;
				while ((c = System.in.read()) > -1) {
					this.out.write(c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ControlFrame() {
		super("Control");
		this.setDefaultCloseOperation(ControlFrame.EXIT_ON_CLOSE);
		this.setBounds(400, 200, 300, 300);
		this.initialize();
	}

	private void setUserDataPanel() {
		String[] columnNames = { "Name", "Time" };
		String[][] data = new String[FileManager.userData.size()][2];
		int i = 0;

		Set<String> keys = FileManager.userData.keySet();

		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			data[i++][0] = (String) iterator.next();// key name
		}

		TableModel model = new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		userDataTable = new JTable(model);
	}

	public String getmainButtonStatus() {
		return mainStartStopButton.getText();
	}


	private void initialize() {

		this.setVisible(false);
		this.setUserDataPanel();

		mainPanel.add(mainButtonPanel);
		mainPanel.add(subButtonPanel);
		mainPanel.add(userDataPanel);

		mainButtonPanel.add(mainButtonLabel);
		subButtonPanel.add(subButtonLabel);
		mainButtonPanel.add(mainStartStopButton);
		subButtonPanel.add(subStartStopButton);
		mainButtonPanel.add(delayButton);
		subButtonPanel.add(breakButton);
		userDataPanel.add(userDataTable);
		mainButtonPanel.add(deleteButton);
		subButtonPanel.add(fixButton);
		mainButtonPanel.add(DCButton);
		mainButtonPanel.add(timeField);
		subButtonPanel.add(changeButton);

		
		switchPersonPanel.add(upButton);
		switchPersonPanel.add(downButton);

		// Property
		mainButtonLabel.setHorizontalAlignment(SwingConstants.CENTER); // label

		// //
		subButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// event
		setBreakButton breakButtonListener = timer.new setBreakButton();
		setDelayButton delayButtonListener = timer.new setDelayButton();
		setDeleteButton deleteButtonListener = timer.new setDeleteButton();
		setChangeButton changeButtonListener = timer.new setChangeButton();
		setDCButton DCButtonListenwr = timer.new setDCButton();
		reverseTimer mainActionListener = timer.new reverseTimer();
		sequenceTimer subActionLisener = timer.new sequenceTimer();
		
		
		mainStartStopButton.addActionListener(mainActionListener);
		subStartStopButton.addActionListener(subActionLisener);
		delayButton.addActionListener(delayButtonListener);
		breakButton.addActionListener(breakButtonListener);
		deleteButton.addActionListener(deleteButtonListener);
		changeButton.addActionListener(changeButtonListener);
		DCButton.addActionListener(DCButtonListenwr);
		
		
		userDataTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JTable jt = (JTable) e.getSource();
				row = jt.getSelectedRow();
				currentName = (String) userDataTable.getValueAt(row, 0);
				timer.resetTotalTime();
				resetDCpenalty();
				Main.showFrame.setcurrentNamePanel(currentName);
				System.out.println(currentName);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		mainPanel.setVisible(true);
		this.add(mainPanel);
		this.setVisible(true);

	}

	private static String longToString(long time) {
		return String.format("%02d분%02d초%03d", time / 60000, (time / 1000) % 60, time % 1000);
	}

	private long stringToLong(String time) {
		return Long.parseLong(time.substring(0, 2)) * 60000 + Long.parseLong(time.substring(3, 5)) * 1000
				+ Long.parseLong(time.substring(6, 9));
	}

	public void setDCpenalty() {
		DCpenalty = 70000;
	}
	
	public void resetDCpenalty() {
		DCpenalty = 0;
	}
	
	public long getTimeField() {
		return Long.parseLong(timeField.getText());
	}
}