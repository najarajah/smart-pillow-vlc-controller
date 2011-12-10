/**
 * Smart fucking pillow - Arduinsky code
 * 
 * @author Masiar.Babazadeh@usi.ch
 * @author Roberto.Guidi@supsi.ch
 * @author Roberto.Minelli@usi.ch
 * */

int red 	= A0;
int blue 	= A1;
int green 	= A2;
int yellow 	= A3;
int pressure    = A4;

int TH = 100;
int TH_PRESSURE = 200;

String 	RED       = "RED";
String 	BLUE      = "BLUE";
String 	GREEN     = "GREEN";
String 	YELLOW    = "YELLOW";
String 	PRESSURE  = "PRESSURE";
String 	RESET     = "RESET";

void setup() {
	
	Serial.begin(115200);

  	// declare INPUTS and OUTPUTS:
	pinMode(red, INPUT);
  	pinMode(blue, INPUT);
 	pinMode(green, INPUT);
 	pinMode(yellow, INPUT);
 	pinMode(pressure, INPUT);
}

void loop() {
  
	int redValue = analogRead(red);
  	int blueValue = analogRead(blue);
 	int greenValue = analogRead(green);
 	int yellowValue = analogRead(yellow);
	int pressureValue = analogRead(pressure);
  
  	if(redValue > TH) {
          Serial.println(RED);
  	} else if(blueValue > TH) {
    	  Serial.println(BLUE);
  	} else if(greenValue > TH) {
   	  Serial.println(GREEN);
  	} else if(yellowValue > TH) {
    	  Serial.println(YELLOW);
  	} else if(pressureValue > TH_PRESSURE) {
    	  Serial.println(PRESSURE);
  	} else {
    	  Serial.println(RESET);
  	}
  
  	delay(300);
}
