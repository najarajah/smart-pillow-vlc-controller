package ch.inf.usi.ubicomp.pillow;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

public class TISerialReader implements SerialPortEventListener {
	private static boolean DEBUG = false;

	SerialPort serialPort;
	byte[] chunk = new byte[0];

	private static final String PORT_NAME = "/dev/tty.usbmodem001"; // Mac, ez430

	/** Buffered input stream from the port */
	private InputStream input;

	/** The output stream to the port */
	private OutputStream output;

	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;

	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void initialize() {
		CommPortIdentifier portId = null;
		@SuppressWarnings("rawtypes")
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

			if (currPortId.getName().equals(PORT_NAME)) {
				portId = currPortId;
				System.out.println("Selected port " + currPortId.getName());
				break;
			}
		}

		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// sending init data to ez430
			output.write(new byte[] {-1, 7, 3});
			output.write(new byte[] {-1, 8, 7, 0, 0, 0, 0});

			output.flush();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int available = input.available();

				chunk = new byte[available];
				input.read(chunk, 0, available);
				
				if(chunk[3] == 1) { 
					// Displaying the three values of the coordinates
					int x = chunk[4];
					int y = chunk[5];
					int z = chunk[6];

					if(DEBUG) System.out.println("x: " + x + " y: " + y + " z: " + z);
				}

				output.write(new byte[] {-1, 8, 7, 0, 0, 0, 0});


			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

	public static void main(String[] args) throws Exception {
		TISerialReader main = new TISerialReader();
		main.initialize();
		System.out.println("Started on " + main.serialPort);
	}
}