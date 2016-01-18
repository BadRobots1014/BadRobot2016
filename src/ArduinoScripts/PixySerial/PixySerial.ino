#include <Pixy.h>
#include <SoftwareSerial.h>


Pixy pixy;
short rx = 0;
short tx = 1;
SoftwareSerial mySerial(rx,tx);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(5600);
  pinMode(rx , INPUT);
  pinMode(tx , OUTPUT);
  pixy.init();
  mySerial.begin(4800);
  mySerial.listen();
}

short i = 0;

void loop() 
{
}

short i = 0;

void loop() 
{
  // put your main code here, to run repeatedly:
  if(mySerial.isListening() && mySerial.available()>0 && i%50 == 0)
  {
      if(mySerial.read() == "1")
      {
        mySerial.write(pixy.getBlocks());
        delay(50);
      }
  }
  i++;
}

