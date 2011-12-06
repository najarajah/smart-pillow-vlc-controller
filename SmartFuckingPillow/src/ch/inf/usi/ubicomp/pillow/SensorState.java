package ch.inf.usi.ubicomp.pillow;

public class SensorState implements Constants{
	
	public SensorState(){
		
	}
	
	
	public static String getSensor(String flexState){
		if(flexState.equals(Constants.FLEX_BLUE_BENT)){
			return "blue";
		}else if (flexState.equals(Constants.FLEX_GREEN_BENT)){
			return "green";
		}else if(flexState.equals(Constants.FLEX_RED_BENT)){
			return "red";
		}else if(flexState.equals(Constants.FLEX_YELLOW_BENT)){
			return "yellow";
		}else if(flexState.equals(Constants.PRESSURE_TRIGGERED)){
			return "pressure";
		}else{
			return "none";
		}
		
	}
	
	public static boolean isFlexRedBent(String flexState){
		if(flexState.equals(Constants.FLEX_RED_BENT)){
			return true;
		}else{
			return false;
		}
			
	}
	
	public static boolean isFlexBlueBent(String flexState){
		if(flexState.equals(Constants.FLEX_BLUE_BENT)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isFlexGreenBent(String flexState){
		if(flexState.equals(Constants.FLEX_GREEN_BENT)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isFlexYellowBent(String flexState){
		if(flexState.equals(Constants.FLEX_YELLOW_BENT)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isPressure(String pressureState){
		if(pressureState.equals(Constants.PRESSURE_TRIGGERED)){
			return true;
		}else {
			return false;
		}
	}
}
