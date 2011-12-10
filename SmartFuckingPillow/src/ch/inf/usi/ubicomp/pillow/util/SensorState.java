package ch.inf.usi.ubicomp.pillow.util;

public class SensorState implements Constants{
	
	public static boolean isRed(String state){
		return state.equals(Constants.RED);		
	}
	
	public static boolean isBlue(String state){
		return state.equals(Constants.BLUE);		
	}
	
	public static boolean isGreen(String state){
		return state.equals(Constants.GREEN);		
	}
	
	public static boolean isYellow(String state){
		return state.equals(Constants.YELLOW);		
	}
	
	public static boolean isCenter(String state){
		return state.equals(Constants.CENTER);		
	}

	public static boolean isReset(String state) {
		return state.equals(Constants.RESET);		
	}
}
