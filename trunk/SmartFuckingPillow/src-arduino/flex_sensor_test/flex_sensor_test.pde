/**
* Flex Sensor test
* 
* Authors:
*   Masiar Babazadeh
*   Roberto Guidi
*   Roberto Minelli
* */

int sensorPin = A0;     // select the input pin for the flex sensor

void setup() {
 Serial.begin(9600);
 // declare INPUTS and OUTPUTS:
 pinMode(sensorPin, INPUT);
}

void loop() {
 // read the value from the sensor:
 int flexValue = analogRead(sensorPin);
 Serial.println(flexValue);               
}
