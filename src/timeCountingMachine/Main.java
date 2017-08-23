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

class SerialPortUse {
	Enumeration portName = CommPortIdentifier.getPortIdentifiers();
}

class SimpleRead implements Runnable, SerialPortEventListener {
	static CommPortIdentifier portId;
	static Enumeration portList;
	InputStream inputStream;
	SerialPort serialPort;
	Thread readThread;
	public static void main(String[] args) {
		// getPortIdentifiers : 현재 사용중인 모든 port를 Enumeration으로 반환한다.
		portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				// if (portId.getName().equals("COM1")) {
				if (portId.getName().equals("/dev/term/a")) {
					SimpleRead reader = new SimpleRead();
				}
			}
		}
	}
	public SimpleRead() {
		try {
			serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
		} catch (PortInUseException e) {}
		try {
			inputStream = serialPort.getInputStream();
		} catch (IOException e) {}
		try {
			serialPort.addEventListener(this);
		} catch (TooManyListenersException e) {}
		serialPort.notifyOnDataAvailable(true);
		try {
			serialPort.setSerialPortParams(9600,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {}
		readThread = new Thread(this);
		readThread.start();
	}
	public void run() {
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {}
	}
	public void serialEvent(SerialPortEvent event) {
		switch(event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			byte[] readBuffer = new byte[20];
			try {
				while (inputStream.available() > 0) {
					int numBytes = inputStream.read(readBuffer);
				}
				System.out.print(new String(readBuffer));
			} catch (IOException e) {}
			break;
		}
	}
}
class SimpleWrite {
	static Enumeration portList;
	static CommPortIdentifier portId;
	static String messageString = "Hello, world!\n";
	static SerialPort serialPort;
	static OutputStream outputStream;
	public static void main(String[] args) {
		portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				// if (portId.getName().equals("COM1")) {
				if (portId.getName().equals("/dev/term/a")) {
					try {
						serialPort = (SerialPort)
								portId.open("SimpleWriteApp", 2000);
					} catch (PortInUseException e) {}
					try {
						outputStream = serialPort.getOutputStream();
					} catch (IOException e) {}
					try {
						serialPort.setSerialPortParams(9600,
								SerialPort.DATABITS_8,
								SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);
					} catch (UnsupportedCommOperationException e) {}
					try {
						outputStream.write(messageString.getBytes());
						} catch (IOException e) {}
				}
			}
		}
	}
}


class TwoWaySerialComm
{
    public TwoWaySerialComm()
    {
        super();
    }
    
    
    /** */
    public static class SerialReader implements Runnable 
    {
        InputStream in;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                    System.out.print(new String(buffer,0,len));
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }

    /** */
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }
        
        public void run ()
        {
            try
            {                
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                    this.out.write(c);
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
}

class Test{
	public void Test() {	
	System.out.println("start");
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
			while (ports.hasMoreElements()) {
				CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
				String type;
				switch (port.getPortType()) {
				case CommPortIdentifier.PORT_PARALLEL:
					type = "Parallel";
					break;
				case CommPortIdentifier.PORT_SERIAL:
					type = "Serial";
					break;
				default: /// Shouldn't happen
					type = "Unknown";
					break;
				}
				System.out.println(port.getName() + ": " + type);
			}
		System.out.println("end");
	}
}

class Test2 {
	private Object port_id;

	public Test2() {
		try{
			System.out.println("start");
			String port = "a";
			System.out.println(port);   
			this.port_id = CommPortIdentifier.getPortIdentifier(port);
			System.out.println(port);
		}
			  catch(NoSuchPortException e){
			   JOptionPane.showMessageDialog(null, "포트를 찾을수 없습니다.");
			  }

		System.out.println("please");
	}
}

public class Main {

	public static ShowFrame showFrame;
	public static ControlFrame controlFrame;
	
	public static void main(String[] arguis) {
		Test2 test = new Test2();
		SimpleRead a = new SimpleRead();
		System.out.println("1��");
		// File test and load data.
		FileManager.loadUserData();
		FileManager.saveData();
		// Show frames.
		showFrame = new ShowFrame();
		controlFrame = new ControlFrame();
	}
}