package ch.inf.usi.ubicomp.pillow;

public class SmartPillow {

	ArduinoSerialReader arduinoReader;
	TISerialReader 		tiReader;

	public SmartPillow() {
		arduinoReader = new ArduinoSerialReader();
		tiReader = new TISerialReader();
	}

	public void init() {
		arduinoReader.initialize();
//		tiReader.initialize();
	}

	public static void main(String[] args) {
		SmartPillow pillow = new SmartPillow();
		pillow.init();
	}
}
