package ch.inf.usi.ubicomp.pillow;

import ch.inf.usi.ubicomp.pillow.util.Constants;
import ch.inf.usi.ubicomp.pillow.util.SensorState;
import ch.inf.usi.ubicomp.vlc.VLCController;

public class SmartPillow implements Constants{

	ArduinoSerialReader arduinoReader;
	TISerialReader 		tiReader;

	static VLCController 	vlcController;

	static boolean 		isRed 		= false;
	static boolean 		isBlue 		= false;
	static boolean 		isGreen 	= false;
	static boolean 		isYellow	= false;

	static long 		lastRedTime 	= 0;
	static long 		lastBlueTime 	= 0;
	static long 		lastGreenTime 	= 0;
	static long 		lastYellowTime 	= 0;


	private static String arduinoData 	= "";
	private static byte[] texasData 	= new byte[0];

	public SmartPillow() {
		arduinoReader 	= new ArduinoSerialReader();
		tiReader 		= new TISerialReader();
		vlcController 	= new VLCController();
	}

	public static void setArduinoData(String data) {
		synchronized (arduinoData) {
			arduinoData = data;
		}
	}

	public static String getArduinoData() {
		synchronized (arduinoData) {
			return arduinoData;
		}
	}

	public static byte[] getTexasData() {
		synchronized (texasData) {
			return texasData;
		}
	}

	public static void setTexasData(byte[] data) {
		synchronized (texasData) {
			texasData = data;
		}
	}

	public void init() {
		arduinoReader.initialize();
		tiReader.initialize();
	}

	public static void main(String[] args) {
		
		SmartPillow pillow = new SmartPillow();

		pillow.init();

		String ardata = null;
		byte[] tidata;

		// Entering loop to process input
		while(true) {

			if((ardata = SmartPillow.getArduinoData()) != null 
					&& !ardata.equals("")) {

				// NEXT
				if(SensorState.isBlue(ardata) && 
						(System.currentTimeMillis() - lastBlueTime  > Constants.ANGLE_READ_TIMEOUT)) {

					System.out.println(BLUE);
					
					lastBlueTime = System.currentTimeMillis();
					vlcController.nextCommand();

				// PREVIOUS
				} else if(SensorState.isRed(ardata) &&
						(System.currentTimeMillis() - lastRedTime  > Constants.ANGLE_READ_TIMEOUT)) {

					System.out.println(RED);
					
					lastRedTime = System.currentTimeMillis();
					vlcController.prevCommand();

				// VOLUME
				} else if(SensorState.isGreen(ardata)) {
					
					System.out.println(GREEN);
					
//					if((tidata = SmartPillow.getTexasData()) != null
//							&& tidata.length != 0) {
//
//						int[] tiValues = parseTiData(tidata);
//						
//						if(isValidTexasRead(tiValues)) {
//							System.out.println("x: " + tiValues[0] + " y: " + tiValues[1] + " z: " + tiValues[3]);
//						}
//
//					}

				} else if(SensorState.isYellow(ardata)) {

					System.out.println(YELLOW);
					
				} else if(SensorState.isCenter(ardata)) {
					//pressure sensor is triggered toggles  VLC fullscreen mode
					vlcController.fullScreenCommand();

				} else if(SensorState.isReset(ardata)){
					resetState();
				}
			}
		}
	}

	private static void resetState() {
		
	}
	
	private static int[] parseTiData(byte[] tidata) {
		int[] result = new int[] {-Integer.MAX_VALUE, -Integer.MAX_VALUE, -Integer.MAX_VALUE};

		if(tidata.length >= 7 && tidata[3] == 1) { 
			result[0] = tidata[4]; 	//x
			result[1] = tidata[5];	//y
			result[2] = tidata[6];	//z
		}

		return result;
	}

	private static boolean isValidTexasRead(int[] texasValue) {
		if(texasValue[0] == -Integer.MAX_VALUE &&
				texasValue[1] == -Integer.MAX_VALUE &&
				texasValue[2] == -Integer.MAX_VALUE) {
			return false;
		} return true;
	}
}
