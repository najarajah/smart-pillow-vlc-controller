/**
robot taken from http://wiki.processing.org/index.php/Robot_class
@author Ira Greenberg

Modified by Ivan Elhart for UbiComp Lab @ USI
Date: 09.11.2010
*/

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.*;

import processing.serial.*;
Serial chronos;
Robot robot;

//---robot coordinates
int xcor = 0;
int ycor = 0;
int xcorspeed = 0;
int ycorspeed = 0;

//---chronos coordinates
int cx = 0;
int cy = 0;

//codes are taken from: http://e2e.ti.com/support/microcontrollers/msp43016-bit_ultra-low_power_mcus/f/166/t/32714.aspx
//Background polling
//  PC to AP : ff 20 07 00 00 00 00
//  Response : ff 06 07 xx xx xx xx
//  xx xx xx xx: 4 byte watch address

//Start Access Point
//  PC to AP :  ff 07 03
//  Response:   ff 06 03
int accCode[] = {255, 7, 3}; 

//Request Acc. data
//  PC to AP: ff 08 07 00 00 00 00
//  Response: ff 06 07 tt xx yy zz
//  tt: data type (ff: no data, 01: valid acc data, 17: *, 49: up, 33: #)
int accDataRequestCode[] = {255, 8, 7, 0, 0, 0, 0}; //Request Acc. Data

byte accModeStart[] = byte(accCode);
byte accDataRequest[] = byte(accDataRequestCode);


void setup() {
  
  int i = 0;
  println("Length: " + accModeStart.length);
  for(; i < accModeStart.length; i ++) {
    println(accModeStart[i]);
  }
  
  println("Length: " + accDataRequest.length);
    for(i = 0; i < accDataRequest.length; i ++) {
      println(accDataRequest[i]);
  }
  
  //make a new serial connection
  String portName = Serial.list()[0];
  println("Cronos Serial Port" + portName);
  chronos = new Serial(this, portName, 115200);
  
  //start the connection with chronos in access point mode
  chronos.write(accModeStart);
  //request data from chronos
  chronos.write(accDataRequest);
  
  xcor = frame.getLocation().x;
  ycor = frame.getLocation().y;
  
}

void draw() {
   
  int[] buf = new int[7]; 
  
  //---robot----
  try { 
      robot = new Robot();
    } 
    catch (AWTException e) {
      e.printStackTrace(); 
    }
    
  //---chronos---
  if(chronos.available() >= 0){
  
    for (int i = 0; i < 7; i++)
      buf[i] = chronos.read();
      
    if (buf[3] == 1)  {
        //println ("x: " + str(buf[4]) + " y: " + str(buf[5]) + " z: " + str(buf[6]));
        cx = int(str(buf[4]));
        cy = int(str(buf[5]));
        
        //---y chronos coordinate -> x window coordinate 
        if(cy>5 && cy<50){
          xcorspeed = cy/2;
          xcor += xcorspeed;
          if(xcor> screen.width) xcor = screen.width;
        }
        if(cy>210 && cy<250){
          xcorspeed = -(255-cy)/2; 
          xcor += xcorspeed;
          if(xcor< 0) xcor = 0;
        }
        //---x chronos coordinate -> y window coordinate 
        if(cx>5 && cx<50){
          ycorspeed = -cx/2;
          ycor += ycorspeed;
          if(ycor< 0) ycor = 0;
        }
        if(cx>210 && cx<250){
          ycorspeed = (255-cx)/2; 
          ycor += ycorspeed;
          if(ycor> screen.height) ycor = screen.height;
        }
        
//        println("xcor: " + xcor + "   ycor: " + ycor);
        
       // chronos.write(xcor);
        
        robot.mouseMove(xcor, ycor);
        
    }if(buf[3] == 17){//*
      //println ("x: " + str(buf[4]) + " y: " + str(buf[5]) + " z: " + str(buf[6]) + " btn: *");
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }if(buf[3] == 49){//up
      //println ("x: " + str(buf[4]) + " y: " + str(buf[5]) + " z: " + str(buf[6]) + " btn: up");
      robot.mousePress(InputEvent.BUTTON3_MASK);
      robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }if(buf[3] == 33){//#
      //println ("x: " + str(buf[4]) + " y: " + str(buf[5]) + " z: " + str(buf[6]) + " btn: #");
    
    }else{
      chronos.write(accDataRequest);
    }
  }
}//draw
