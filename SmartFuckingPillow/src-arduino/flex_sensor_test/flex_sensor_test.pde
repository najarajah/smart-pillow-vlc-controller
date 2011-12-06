/**
* Flex Sensor test
* 
* Authors:
*   Masiar Babazadeh
*   Roberto Guidi
*   Roberto Minelli
* */

int sensorPin = A0;     // select the input pin for the flex sensor
int flexRed = "";
int flexBlue = "";
int flexGreen = "";
int flexYellow = "";
int pressureSensor = "";

void setup() {
 	Serial.begin(9600);
 // declare INPUTS and OUTPUTS:
 	pinMode(sensorPin, INPUT);
 	pinMode(flexRed, INPUT);
 	pinMode(flexBlue, INPUT);
 	pinMode(flexGreen, INPUT);
 	pinMode(flexYellow, INPUT);
 	pinMode(pressureSensor, INPUT);
 
 }

void loop() {
 // read the value from the sensor:
 /*int flexValue = analogRead(sensorPin);
 if(flexValue > 14 || flexValue < 6) {
   Serial.println("bendato");
 } else {
   Serial.println("non-bendato");
 }*/
 
 // get the value from red flex sensor and sent message if bent
 int flexRedValue = analogRead(flexRed);
 if(flexRedValue > 14 || flexRedValue < 6) {
   Serial.println("red-bent");
 }
 
 //get the value from blue flex sensor and sent message if bent
 int flexBlueValue = analogRead(flexBlue);
 if(flexBlueValue > 14 || flexBlueValue < 6) {
   Serial.println("blue-bent");
 }
 
 //get the value from green flex sensor and sent message if bent
 int flexGreenValue = analogRead(flexGreen);
 if(flexGreenValue > 14 || flexGreenValue < 6) {
   Serial.println("green-bent");
 }
 
 //get the value from the yellow flex sensor and sent message if bent
 int flexYellowValue = analogRead(flexYellow);
 if(flexYellowValue > 14 || flexYellowValue < 6) {
   Serial.println("yellow-bent");
 }
 
 //get the pressure value from pressure sensor
 int pressureSensorValue = analogRead(pressureSensor);
 if(pressureSensorValue > 400){
 	String pressure = "pressure-triggered";
 }
 
 
 delay(500);
}
