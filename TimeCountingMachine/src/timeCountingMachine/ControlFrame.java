package timeCountingMachine;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import timeCountingMachine.MyTimer.reverseTimer;
import timeCountingMachine.MyTimer.sequenceTimer;
import timeCountingMachine.MyTimer.setBreakButton;
import timeCountingMachine.MyTimer.setDelayButton;
import timeCountingMachine.MyTimer.setDeleteButton;


public class ControlFrame extends JFrame {
	
	//public ShowFrame Main.showFrame;

	private JPanel mainButtonPanel = new JPanel(new GridLayout(4, 1, 0, 5));
	private JPanel subButtonPanel = new JPanel(new GridLayout(4, 1, 0, 5));
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
	private JLabel mainButtonLabel = new JLabel("total driving time");
	private JLabel subButtonLabel = new JLabel("lap time");

	private JTable userDataTable;
	
	private String currentName;
	private int row;
	
	private MyTimer timer = new MyTimer();
	
	public int getRow() {
		return row;
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
		
		for(Iterator<String> iterator = keys.iterator();iterator.hasNext();){
			data[i++][0] = (String) iterator.next();//key name
		}
		
		TableModel model = new DefaultTableModel(data, columnNames)
		{
            public boolean isCellEditable(int row, int column)
            {
            	return false;
            }
		};
		userDataTable = new JTable(model);
	}
	
	public String getmainButtonStatus() {
		return mainStartStopButton.getText();
	}
	
	private void Signal(char sign) {
		//start signal
		if(sign == 's') {
			subStartStopButton .doClick();		
		}
		//stop signal
		if(sign == 'p') {
			subStartStopButton.doClick();
			Main.showFrame.setRecordLabel(timer.getLapTime());
			System.out.println(timer.getLapTime());
		}
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
		subButtonPanel.add(deleteButton);
		
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
		reverseTimer mainActionListener = timer.new reverseTimer();
		sequenceTimer subActionLisener = timer.new sequenceTimer();
		
		mainStartStopButton.addActionListener(mainActionListener);
		subStartStopButton.addActionListener(subActionLisener);
		delayButton.addActionListener(delayButtonListener);
		breakButton.addActionListener(breakButtonListener);
		deleteButton.addActionListener(deleteButtonListener);
		
		
		userDataTable.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				JTable jt = (JTable) e.getSource();
				row = jt.getSelectedRow();
				currentName = (String)userDataTable.getValueAt(row, 0);
				Main.showFrame.setcurrentNamePanel(currentName);
				System.out.println(currentName);
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});

		mainPanel.setVisible(true);
		this.add(mainPanel);
		this.setVisible(true);
	}
}