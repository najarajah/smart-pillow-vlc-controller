package ch.inf.usi.ubicomp.pillow;

import ch.inf.usi.ubicomp.vlc.VLCCommands;

public class SmartPillow implements Constants{

	ArduinoSerialReader arduinoReader;
	TISerialReader 		tiReader;
	static VLCCommands vlc;
	static boolean flexRedBent = false;
	static boolean flexBlueBent = false;
	static boolean flexGreenBent = false;
	static boolean flexYellowBent = false;

	private static String arduinoData 	= "";
	private static byte[] tiData 		= new byte[0];

	public SmartPillow() {
		arduinoReader 	= new ArduinoSerialReader();
		tiReader 		= new TISerialReader();
		vlc = new VLCCommands();
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

	public static byte[] getTiData() {
		synchronized (tiData) {
			return tiData;
		}
	}
	
	public static void setTiData(byte[] data) {
		synchronized (tiData) {
			tiData = data;
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
		
		while(true) {
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if((ardata = SmartPillow.getArduinoData()) != null) {
				//for test
				//ardata = "pressure-triggered";
			
				if(SensorState.isFlexBlueBent(ardata)){
					vlc.nextCommand();
				}else if(SensorState.isFlexGreenBent(ardata)){
					
				}else if(SensorState.isFlexRedBent(ardata)){
					vlc.prevCommand();
				}else if(SensorState.isFlexYellowBent(ardata)){
					
				}else if (SensorState.isPressure(ardata)){
					//pressure sensor is triggered toggles  VLC fullscreen mode
					System.out.println("pressure");
					vlc.fullScreenCommand();
					
				}else{
					
				}
				
				
				System.out.println(ardata);
				
				if(ardata.equals("bendato")) {
					
					if((tidata = SmartPillow.getTiData()) != null) {
						
						if(tidata.length >= 7 && tidata[3] == 1) { 
							// Displaying the three values of the coordinates
							int x = tidata[4];
							int y = tidata[5];
							int z = tidata[6];

							System.out.println("x: " + x + " y: " + y + " z: " + z);
						}
					}
				} else {
					// non fare un cazzo
				}
			}
			

				
			
		}
	}
}
