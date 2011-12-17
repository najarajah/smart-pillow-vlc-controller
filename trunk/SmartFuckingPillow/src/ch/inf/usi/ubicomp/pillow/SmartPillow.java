package ch.inf.usi.ubicomp.pillow;

import ch.inf.usi.ubicomp.pillow.util.Constants;
import ch.inf.usi.ubicomp.pillow.util.SensorState;
import ch.inf.usi.ubicomp.vlc.VLCController;

public class SmartPillow implements Constants{

	static boolean DEBUG = false;
	
	ArduinoSerialReader arduinoReader;
	TISerialReader 		tiReader;

	static VLCController 	vlcController;

	//unused 
	static boolean 		isRed 		= false;
	static long 		lastBlueTime 	= 0;
	static long 		lastGreenTime 	= 0;
	static long 		lastYellowTime 	= 0;
	
	static boolean 		isBlue 		= false;
	static boolean 		isGreen 	= false;
	static boolean 		isYellow	= false;
	static boolean 		isCenter	= false;

	static long 		lastRedTime 	= 0;
	static long 		lastCenterTime 	= 0;
	static long 		lastTexasTime 	= 0;	
	
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

		String arduinoData = null;
		byte[] texasData;

		// Entering loop to process input
		while(true) {

			// CONTROLLING VOLUME
			if(isGreen) {
				
				if((texasData = SmartPillow.getTexasData()) != null &&
						texasData.length != 0) {
					
					int[] texasValues = parseTiData(texasData);
					
					if(isValidTexasRead(texasValues) &&
						(System.currentTimeMillis() - lastTexasTime  > Constants.TEXAS_READ_TIMEOUT)) {

						lastTexasTime = System.currentTimeMillis();
						
						if (DEBUG) System.out.println("x: " + texasValues[0] + " y: " + texasValues[1] + " z: " + texasValues[2]);
						
						int x = texasValues[0];
						
						if(x < - 40) {
							vlcController.increaseVolumeCommand(100);
						} else if (x > 30){
							vlcController.decreaseVolumeCommand(100);
						}
					}
				}
			}
			
			// CONTROLLING SEEK
			if(isYellow) {
				
				if((texasData = SmartPillow.getTexasData()) != null &&
						texasData.length != 0) {
					
					int[] texasValues = parseTiData(texasData);
					
					if(isValidTexasRead(texasValues) &&
						(System.currentTimeMillis() - lastTexasTime  > Constants.TEXAS_READ_TIMEOUT)) {

						lastTexasTime = System.currentTimeMillis();
												
						int y = texasValues[1];
						
						if(y < -30) {
							vlcController.decreaseSeekCommand();
						} else if (y > 30){
							vlcController.increaseSeekCommand();
						}
					}
				}
			}
			
			// CONTROLLING NEXT/PREV
			if(isBlue) {
				
				if((texasData = SmartPillow.getTexasData()) != null &&
						texasData.length != 0) {
					
					int[] texasValues = parseTiData(texasData);
					
					if(isValidTexasRead(texasValues) &&
						(System.currentTimeMillis() - lastTexasTime  > Constants.TEXAS_READ_TIMEOUT)) {

						lastTexasTime = System.currentTimeMillis();
						
						if (DEBUG) System.out.println("x: " + texasValues[0] + " y: " + texasValues[1] + " z: " + texasValues[2]);
						
						int y = texasValues[1];
						
						if(y < -30) {
							vlcController.prevCommand();
						} else if (y > 30){
							vlcController.nextCommand();
						}
					}
				}
			}
			
			if((arduinoData = SmartPillow.getArduinoData()) != null 
					&& !arduinoData.equals("")) {
				
				if(DEBUG) System.out.println(arduinoData);

				// FULLSCREEN
				 if(SensorState.isRed(arduinoData) &&
						(System.currentTimeMillis() - lastRedTime  > Constants.ANGLE_READ_TIMEOUT)) {

					if (DEBUG) System.out.println(RED);

					lastRedTime = System.currentTimeMillis();
					
					vlcController.fullScreenCommand();

					// PLAY / PAUSE
				} else if(SensorState.isCenter(arduinoData) &&
						(System.currentTimeMillis() - lastCenterTime  > Constants.CENTER_READ_TIMEOUT)) {

					if (DEBUG) System.out.println(CENTER);

					lastCenterTime = System.currentTimeMillis();
					
					vlcController.playOrPauseCommand();

					// NEXT / PREV
				} else if(!isBlue && SensorState.isBlue(arduinoData)) {

					if (DEBUG) System.out.println(BLUE);

					isBlue = true;

					// VOLUME
				} else if(!isGreen && SensorState.isGreen(arduinoData)) { 

					if (DEBUG) System.out.println(GREEN);
					
					isGreen = true;

					// SEEK
				} else if(!isYellow && SensorState.isYellow(arduinoData)) {
					
					if (DEBUG) System.out.println(YELLOW);
					
					isYellow = true;

				} else if(SensorState.isReset(arduinoData)) {
					resetState();
				}
			}
		}
	}

	private static void resetState() {
		if(isGreen) 	isGreen = false;
		if(isYellow) 	isYellow = false;
		if(isBlue) 		isBlue = false;
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
