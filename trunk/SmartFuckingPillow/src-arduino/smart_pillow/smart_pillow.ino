/**
 * Smart fucking pillow - Arduinsky code
 * 
 * @author Masiar.Babazadeh@usi.ch
 * @author Roberto.Guidi@supsi.ch
 * @author Roberto.Minelli@usi.ch
 * */

int red = A0;
int blue = A1;
// Il cazzo di ingresso A2 non va!
int green = A3;
int yellow = A4;
int center = A5;

int TH = 100;

String 	RED = "RED";
String 	BLUE = "BLUE";
String 	GREEN = "GREEN";
String 	YELLOW = "YELLOW";
String 	CENTER = "CENTER";
String 	RESET = "RESET";

void setup() {
  Serial.begin(115200);

  // declare INPUTS and OUTPUTS:
  pinMode(red, INPUT);
  pinMode(blue, INPUT);
  pinMode(green, INPUT);
  pinMode(yellow, INPUT);
  pinMode(center, INPUT);
}

void loop() {

  int redValue = analogRead(red);
  int blueValue = analogRead(blue);
  int greenValue = analogRead(green);
  int yellowValue = analogRead(yellow);
  int centerValue = analogRead(center);

//  Serial.println(redValue);
//  Serial.println(blueValue);
//  Serial.println(greenValue);
//  Serial.println(yellowValue);

  if(redValue > TH) {
    Serial.println(RED);
  } 
  else if(blueValue > TH) {
    Serial.println(BLUE);
  } 
  else if(greenValue > TH) {
    Serial.println(GREEN);
  } 
  else if(yellowValue > TH) {
    Serial.println(YELLOW);
  } 
  else if(centerValue > TH) {
    Serial.println(CENTER);
  } else {
    Serial.println(RESET);
  }

  delay(300);
}

