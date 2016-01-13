#include <Pixy.h>
#include <SoftwareSerial.h>


Pixy pixy;
short rx = 0;
short tx = 1;
SoftwareSerial mySerial(rx,tx);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(5600);
  while(!Serial)
  {
    
  }
  pixy.init();
}

mySerial.begin(4800);

void loop() 
{
  // put your main code here, to run repeatedly:
  short blockNum;
  short i = 0;
  if(i%50 == 0 && mySerial.available())
  {
    blockNum = pixy.getBlocks();
    for(int j=0; j<blockNum; i++)
    {
      mySerial.write(pixy.blocks[j].signature);
      pixy.blocks[j].print();
    }
  }
  
}
