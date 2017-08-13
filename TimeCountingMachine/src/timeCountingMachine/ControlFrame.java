package timeCountingMachine;

import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.Timer;

import java.util.TimerTask;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToggleButton;

import javax.swing.SwingConstants;

import timeCountingMachine.MyTimer.reverseTimer;

import timeCountingMachine.MyTimer.sequenceTimer;

public class ControlFrame extends JFrame {

	private JPanel mainButtonPanel = new JPanel(new GridLayout(3, 1, 0, 5));
	private JPanel subButtonPanel = new JPanel(new GridLayout(3, 1, 0, 5));
	private JPanel switchPersonPanel = new JPanel(new GridLayout(2, 1, 0, 5));
	private JPanel mainPanel = new JPanel(new GridLayout(1, 3, 5, 5));
	private JPanel userDataPanel = new JPanel();

	public static JToggleButton mainStartStopButton = new JToggleButton("START");

	// private JToggleButton mainStopButton = new JToggleButton("STOP");

	public static JToggleButton subStartStopButton = new JToggleButton("START");

	// private JToggleButton subStopButton = new JToggleButton("STOP");

	private JButton upButton = new JButton("up");
	private JButton downButton = new JButton("down");
	private JLabel mainButtonLabel = new JLabel("total driving time");
	private JLabel subButtonLabel = new JLabel("lap time");

	public ControlFrame() {
		super("Control");
		this.setDefaultCloseOperation(ControlFrame.EXIT_ON_CLOSE);
		this.setBounds(400, 200, 300, 300);
		this.initialize();
	}

	private void setUserDataPanel() {
		String[] columnNames = { "Name", "Time" };
		String[][] data = new String[Main.userData.size()][2];
		
		data[0][0] = "1";
		data[0][1] = "success";

		JTable userDataTable = new JTable(data, columnNames);

		userDataPanel.add(userDataTable);
	}

	private void initialize() {

		this.setVisible(false);
		this.setUserDataPanel();

		// this.add(mainPanel);

		// Container mainPanelcontainer = new Container();
		mainPanel.add(mainButtonPanel);
		mainPanel.add(subButtonPanel);
		mainPanel.add(userDataPanel);
		
		mainButtonPanel.add(mainButtonLabel);
		subButtonPanel.add(subButtonLabel);
		mainButtonPanel.add(mainStartStopButton);
		// mainButtonPanel.add(mainStopButton);
		subButtonPanel.add(subStartStopButton);
		

		// subButtonPanel.add(subStopButton);
		switchPersonPanel.add(upButton);
		switchPersonPanel.add(downButton);

		// Property
		mainButtonLabel.setHorizontalAlignment(SwingConstants.CENTER); // label

		// //
		subButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// event

		reverseTimer mainActionListener = new MyTimer().new reverseTimer();
		sequenceTimer subActionLisener = new MyTimer().new sequenceTimer();

		mainStartStopButton.addActionListener(mainActionListener);
		subStartStopButton.addActionListener(subActionLisener);

		// this.setLayout(new BoxLayout(this.getContentPane(),
		// BoxLayout.X_AXIS));
		// mainPanel.setLayout(new BoxLayout(this.getContentPane(),
		// BoxLayout.X_AXIS));

		mainPanel.setVisible(true);
		this.add(mainPanel);
		this.setVisible(true);

	}

}