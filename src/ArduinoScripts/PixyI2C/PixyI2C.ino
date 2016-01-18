#include <Pixy.h>
#include <Wire.h>

Pixy pixy;
void setup() 
{
  Wire.begin();
  Serial.begin(9600);
  pixy.init();
  Wire.begin(1);
}

static int i = 0;
void loop() 
{
  short blocks;
  if(Serial.available() &&  i%50==0)
  {
    blocks = pixy.getBlocks();
    if(blocks)
    {
      for(int j = 0; j<blocks; j++)
      {
        pixy.blocks[j].print();
        if(Wire.available())
        {
          Wire.write(pixy.blocks[j].signature);
        }
      }
    }
  }
}



