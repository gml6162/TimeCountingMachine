package timeCountingMachine;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class ShowFrame extends JFrame {

	//private static final Insets insets = new Insets(0, 0, 0, 0);

	// private JPanel mainPanel = new JPanel(new GridLayout(3, 1, 5, 5));
	private JPanel totalDrivingTimerPanel = new JPanel(new GridLayout(1,1));
	private JPanel lapTimerPanel = new JPanel();
	private JPanel personPanel = new JPanel();
	private JButton newButton = new JButton("test");
	private JPanel bestDataPanel = new JPanel(new GridLayout(1,2));
	private JPanel userDataPanel = new JPanel();

	private static JLabel totalDrivingTimerLabel = new JLabel("05:00:00");
	private static JLabel lapTimerLabel = new JLabel("00:00:00");

	// layout
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();

	public static void setTotalDrivingTimerLabel(String totalDrivingTimerLabel) {
		ShowFrame.totalDrivingTimerLabel.setText(totalDrivingTimerLabel);
	}

	public static void setLapTimerLabel(String lapTimerLabel) {
		ShowFrame.lapTimerLabel.setText(lapTimerLabel);
	}

	private void addGrid(GridBagLayout gbl, GridBagConstraints gbc, Component c, int gridx, int gridy, int gridwidth,
			int gridheight, int weightx, int weighty) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbl.setConstraints(c, gbc);
		this.add(c);
	}
	
	private void setUserDataPanel() {
		String[] columnNames = {"Name", "Time"};
		String[][] data = new String[Main.userData.size()][2];
		
		data[0][0]="1";
		data[0][1]="success";
		
		JTable userDataTable = new JTable(data, columnNames);
		
		
		userDataPanel.add(userDataTable);
	}

	public ShowFrame() {
		super("Show");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 200, 300, 300);
		this.initalize();
	}

	private void initalize() {
		this.setVisible(false);
		// layout
		gbc.fill = GridBagConstraints.BOTH;
		this.setUserDataPanel();
		this.setLayout(gbl);
		
		//boxlayout »ç¿ë
		addGrid(gbl, gbc, totalDrivingTimerPanel, 0, 0, 2, 1, 2, 1);
		addGrid(gbl, gbc, lapTimerPanel, 0, 1, 2, 1, 2, 1);
		addGrid(gbl, gbc, bestDataPanel, 2, 0, 2, 1, 1, 1);
		addGrid(gbl, gbc, userDataPanel, 2, 1, 2, 10, 1, 1);
		// mainPanel.add(totalDrivingTimerPanel);
		// mainPanel.add(lapTimerPanel);
		// mainPanel.add(personPanel);

		totalDrivingTimerPanel.add(totalDrivingTimerLabel);
		lapTimerPanel.add(lapTimerLabel);
		
		// Property
		totalDrivingTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lapTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);
 
		// this.add(mainPanel);k
		this.setVisible(true);
	}

}
