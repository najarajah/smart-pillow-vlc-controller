/**
 * Flex Sensor test
 * 
 * Authors:
 *   Masiar Babazadeh
 *   Roberto Guidi
 *   Roberto Minelli
 * */

//int sensorPin = A0;     // select the input pin for the flex sensor
int flexRed = A0;
int flexBlue = A1;
int flexGreen = A2;
int flexYellow = A3;
int pressureSensor = A4;

void setup() {
  Serial.begin(9600);
  // declare INPUTS and OUTPUTS:
  //pinMode(sensorPin, INPUT);
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
  int flexBlueValue = analogRead(flexBlue);
  int flexGreenValue = analogRead(flexGreen);
  int flexYellowValue = analogRead(flexYellow);

  int pressureSensorValue = analogRead(pressureSensor);
  //Serial.println(flexRedValue);
  if(flexRedValue > 100) {
    // Serial.println(flexRedValue);
    Serial.println("red-bent");
  }
  else if(flexBlueValue > 100) {
    Serial.println("blue-bent");
  }
  else if(flexGreenValue > 100) {
    Serial.println("green-bent");
  }
  else if(flexYellowValue > 100) {
    Serial.println("yellow-bent");
  }
  else if(pressureSensorValue > 400){
    Serial.println("pressure-triggered");
  }
  else{
    Serial.println("");
  }


  /*Serial.println(flexRedValue);
   Serial.println(flexGreenValue);
   Serial.println(flexBlueValue);
   Serial.println(flexYellowValue);*/

  delay(300);
}


