class playerTank
{
  float xAxis;
  float yAxis;
  color tankColor=#0061B4;
  int timePassed=0;
  int reloadTime=15; 
  float reloadStatus=0;

  //Constructor
  playerTank(float playerX, float playerY)
  {
    xAxis= playerX;
    yAxis= playerY;
  }

  //Runs all separate functions for the tank
  void tankRun()
  {
    tankCreation();
    tankMove();
    fireMissile();

    //Allows for delays to be made
    timePassed++;
  }

  //Draws the tank
  void tankCreation()
  {
    //Top of tank
    fill(#585858);
    rect(xAxis+45, yAxis+215, 10, 20);
    arc(xAxis+50, yAxis+260, 80, 50, PI, TWO_PI);
    fill(#363535);
    quad(xAxis+45, yAxis+217, xAxis+55, yAxis+217, xAxis+60, yAxis+205, xAxis+40, yAxis+205);

    //Body of tank
    fill(#818181);
    rect(xAxis, yAxis+250, 100, 27);
    fill(#363535);
    rect(xAxis, yAxis+275, 100, 13, 50);
    fill(tankColor);
    rect(xAxis, yAxis+256, 100, 8);

    //Wheels of tank
    fill(#585858);
    ellipse(xAxis+5, yAxis+280, 20, 20);
    ellipse(xAxis+95, yAxis+280, 20, 20);

    ellipse(xAxis+24, yAxis+281, 15, 15);
    ellipse(xAxis+41, yAxis+281, 15, 15);
    ellipse(xAxis+59, yAxis+281, 15, 15);
    ellipse(xAxis+76, yAxis+281, 15, 15);

    fill(#747474);
    ellipse(xAxis+24, yAxis+281, 7, 7);
    ellipse(xAxis+41, yAxis+281, 7, 7);
    ellipse(xAxis+59, yAxis+281, 7, 7);
    ellipse(xAxis+76, yAxis+281, 7, 7);

    triangle(xAxis-1, yAxis+285, xAxis+5, yAxis+272, xAxis+11, yAxis+285);
    triangle(xAxis+89, yAxis+285, xAxis+95, yAxis+272, xAxis+102, yAxis+285);
  }

  //Related to player tank movement
  void tankMove()
  {
    //Movement keys
    if (keyPressed)
    {
      if (key=='a')
      {
        xAxis-=7;
      }
      if (key=='d')
      {
        xAxis+=7;
      }
    }
    //Collision with edge check
    if (xAxis<10)
    {
      xAxis+=7;
    }
    if (xAxis>1290)
    {
      xAxis-=7;
    }
  }

  //When the player fires, this will allow for the creation of the newest instance of the missile, but there will be a maximum amount and there is a fire delay based on time between shots
  void fireMissile()
  {
    if (timePassed>reloadTime)
    {
      //If the score limit is reached or the game is lost, the player will be unable to fire more missiles 
      if (mousePressed && scoreCounter<1000 && gameOver==false)
      {
        if (currentMissile<missiles.length-1)
        {
          timePassed=0;
          currentMissile++;
          missilesLeft--;
          missiles[currentMissile].xAxis = xAxis + 45;
        } 
        if (currentMissile==maximumMissileCount-1)
        {
          tankColor=orangeDecoration;
        }
      }
    }

    if (timePassed>=reloadTime)
    {
      reloadStatus=1;
    } else
    {
      reloadStatus=0;
    }
  }
}
