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

short pin = 3;
unsigned long duration;

void setup()
{
  Serial.begin(9600);
  strip.begin(); // Initialize pins for output
  strip.show();  // Turn all LEDs off ASAP
  pinMode(pin, INPUT);
}

short displayID = 100;  // Stores the last displayedID to increase some effeciency
byte teamID = 0; // Saves the last inputted team color
short scrollState = 0; //Used by various funtions for animation
int delayTick = 0; //Used as a tick for delays.... don't touch this
int delayLength = 0; // Used to control speeds of animations. You can touch this

uint32_t scrollColors[6] = {0x00FF00, 0x0000FF, 0xFF0000, 0x00FFFF, 0xFFFF00, 0xFF00FF};
uint32_t rwbColors[4] = {0x00FF00, 0xFFFFFF, 0x0000FF,0x000000};

void loop()
{
  duration = pulseIn(pin, HIGH, 100000);

  // Checks if the display id is differen't from that of before
  boolean newID =  displayID != round(duration/20.0);
  // Sets the current display ID to what is being recieved
  displayID = round(duration/20.0);
  
  if(displayID == 0)
  {
    // Sets the whole strip to the BadRobot Yellow
    //setStripColor(0x77C700);
    delayLength = 0;
    fullScrollStrip(newID,scrollColors, 6,45);

    //sideScrollStripColor(0xFF0000, false);
  }
  else if(displayID == 1)
  {
    // Stores the teamID for use later
    teamID = 0;
    delayLength = 0;
    // Sets the whole strip to the red team color
    setStripColor(0x00FF00);
  }
  else if(displayID == 2)
  {
    // Stores the teamID for use later
    teamID = 1;
    delayLength = 0;
    // Sets the whole strip to the blue team color
    setStripColor(0x0000FF);
  }
  else if(displayID == 3)
  {
    delayLength = 0;
    // Scrolls the LED's along the side of the robot to simulate an outward motion
    sideScrollStripColor(0xFF0000, true);
  }
  else if(displayID == 4)
  {
    delayLength = 0;
    // Scrolls the LED's along the side of the robot to simulate an inward motion
    sideScrollStripColor(0x25FF00, false);
  }
  else if(displayID == 5)
  {
    delayLength = 5;
    // Blinks the LED's rapidly
    blinkColor(0x00FF00);
  }
  // Displays the new changes
  strip.show();
  // Small delay to make the display flow more smoothly
  delay(20);
}

//Sets the whole strip to one, given, color
void setStripColor(uint32_t color)
{
 if((delayTick = delayTick + 1) < delayLength)
    return;
  delayTick = 0;
  for(short i = 0; i < NUMPIXELS; i++)
      strip.setPixelColor(i, color);
}

//Scrolls the side LED's to simulate motion
void sideScrollStripColor(uint32_t color, boolean forward)
{
  delayTick = delayTick + 1;
  if(delayTick < delayLength)
    return;
  delayTick = 0;
  short nextState = scrollState + (forward ? 1 : -1);
  if(forward && nextState > 9)
      nextState = 0;
  else if(!forward && nextState < 0)
      nextState = 9;
      
  for(short i = 0; i < NUMPIXELS; i++)
  {
      strip.setPixelColor(i, 0);
      if(i < 20 && i % 10 == 9 - nextState)
          strip.setPixelColor(i, color);
      else if(i > 31 && i % 10 == nextState)
          strip.setPixelColor(i, color);
      else if(i >= 20 && i <= 31)
          strip.setPixelColor(i, color);
    }

    scrollState = scrollState + (forward ? 1 : -1);
    if(forward && scrollState > 9)
      scrollState = 0;
    else if(!forward && scrollState < 0)
      scrollState = 9;
}

//Scrolls the LED's along the strip
//ARGS:
//booolean - Is this the first sequential run of this method
//uint32_t array - List of colors to be displayed
//int - number of colors in list becuse sizeof doesn't seem to work
//int - block size for the colors to be displayed in
void fullScrollStrip(boolean firstRun, uint32_t colors[], int arrayLength, int groupLength)
{
  if((delayTick = delayTick + 1) < delayLength)
    return;
  delayTick = 0;
  boolean flag = false;
  for(int i = 0; i < arrayLength; i++)
  {
    if(strip.getPixelColor(0) == colors[i])
    {
      scrollState = i;
      flag = true;
    }
  }

  if(!flag)
    scrollState = 0;

  int runningLength = 1;
  if(firstRun)
  {
    for(short i = 0; i < NUMPIXELS; i++)
    {
      if(runningLength > groupLength)
      {
        runningLength = 1;
        if((scrollState = scrollState + 1) >= arrayLength)
          scrollState = 0;
      }
      runningLength = runningLength + 1;
      strip.setPixelColor(i, colors[scrollState]);
    }
  }
  else
  {
    if((scrollState = scrollState - 1) < 0)
      scrollState = arrayLength - 1;
    for(short i = NUMPIXELS; i > 0; i--)
      strip.setPixelColor(i, strip.getPixelColor(i-1));

    flag = true;
    for(int i = 2; i <= groupLength; i++)
      if(strip.getPixelColor(1) != strip.getPixelColor(i))
        flag = false;
        
    if(flag)
      strip.setPixelColor(0, colors[scrollState]);
    else
      strip.setPixelColor(0, strip.getPixelColor(1));
  }
}

// Blinks the LED's rapidly
void blinkColor(uint32_t color)
{
  if((delayTick = delayTick + 1) < delayLength)
    return;
  delayTick = 0;
   if((scrollState = scrollState + 1) > 1)
     scrollState = 0;
   for(short i = 0; i < NUMPIXELS; i++)
   {
      if(i % 2 == 1 - scrollState)
        strip.setPixelColor(i, color);
      else
        strip.setPixelColor(i, 0);
    }
}
