//I2C Code
//BadRobots, Team 1014
//@Author Luke Johnson(Original), Ryan Turk
//@Date 4/19/2016

//Adafruit Dot Star RGB LED strip.


#include <Adafruit_DotStar.h>
#include <SPI.h>
#include <math.h>

#define NUMPIXELS 50 // Number of LEDs in strip

// Control Pins
#define DATAPIN    4 
#define CLOCKPIN   5

Adafruit_DotStar strip = Adafruit_DotStar(
  NUMPIXELS, DATAPIN, CLOCKPIN, DOTSTAR_BRG);
// The last parameter is optional -- this is the color data order of the
// DotStar strip, which has changed over time in different production runs.
// Your code just uses R,G,B colors, the library then reassigns as needed.
// Default is DOTSTAR_BRG, so change this if you have an earlier strip.

// Hardware SPI is a little faster, but must be wired to specific pins
// (Arduino Uno = pin 11 for data, 13 for clock, other boards are different).
//Adafruit_DotStar strip = Adafruit_DotStar(NUMPIXELS, DOTSTAR_BRG);

int pin = 3;
unsigned long duration;

void setup()
{
  Serial.begin(9600);
  strip.begin(); // Initialize pins for output
  strip.show();  // Turn all LEDs off ASAP
  pinMode(pin, INPUT);
}

// Runs 10 LEDs at a time along strip, cycling through red, green and blue.
// This requires about 200 mA for all the 'on' pixels + 1 mA per 'off' pixel.

short displayID = 0;  // Stores the last displayedID to increase some effeciency
byte teamID = 0; // Saves the last inputted team color
short scrollState = 0; // Values 0-9 representing which led's to turn on to simulate motion
uint32_t color = 0xFF0000;      // 'On' color (starts red)

void loop()
{
  duration = pulseIn(pin, HIGH);

  boolean newID =  displayID == round(duration/20.0);
  displayID = round(duration/20.0);
  
  if(displayID == 0 && newID)
  {
    teamID = 0;
    color = 0xA5FF00;
    for(int i = 0; i < NUMPIXELS; i++)
      strip.setPixelColor(i, color);
  }
  else if(displayID == 1 && newID)
  {
    teamID = 0;
    color = 0x00FF00;
    for(int i = 0; i < NUMPIXELS; i++)
      strip.setPixelColor(i, color);
  }
  else if(displayID == 2 && newID)
  {
    teamID = 1;
    color = 0x0000FF;
    for(int i = 0; i < NUMPIXELS; i++)
      strip.setPixelColor(i, color);
  }
  else if(displayID == 3)
  {
    color = 0xFF0000;
    short nextState = scrollState + 1;
    if(nextState > 9)
      nextState = 0;
    for(int i = 0; i < NUMPIXELS; i++)
    {
      strip.setPixelColor(i, 0);
      if(i < 20 && i % 10 == 9 - nextState)
          strip.setPixelColor(i, color);
      else if(i > 31 && i % 10 == nextState)
          strip.setPixelColor(i, color);
      else if(i >= 20 && i <= 31)
         strip.setPixelColor(i, color);
    }
    scrollState = scrollState + 1;
    if(scrollState > 9)
      scrollState = 0;
  }
  else if(displayID == 4)
  {
    color = 0x25FF00;
    short nextState = scrollState - 1;
    if(nextState < 0)
      nextState = 9;
    for(int i = 0; i < NUMPIXELS; i++)
    {
      strip.setPixelColor(i, 0);
      if(i < 20 && i % 10 == 9 - nextState)
          strip.setPixelColor(i, color);
      else if(i > 31 && i % 10 == nextState)
          strip.setPixelColor(i, color);
      else if(i >= 20 && i <= 31)
          strip.setPixelColor(i, color);
    }
    
    scrollState = scrollState - 1;
    if(scrollState < 0)
      scrollState = 9;
  }
  else if(displayID == 5)
  {
    color = 0x00FF00;
    scrollState = scrollState + 1;
    if(scrollState > 1)
      scrollState = 0;
    for(int i = 0; i < NUMPIXELS; i++)
    {
       if(i % 2 == 1 - scrollState)
          strip.setPixelColor(i, color);
       else
          strip.setPixelColor(i, 0);
    }
  }
  strip.show();
  delay(20);
}
