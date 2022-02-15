class alienShips
{
  float xAxis;
  float yAxis;

  //Related to the explosion animation
  float explosionX=xAxis;
  float explosionY=yAxis;
  float explosionTimer=0;

  boolean collisionRemove=false;

  //Related to Alien Ship movement
  float angle = random(360);
  float verticalVelocity = 0.25; 
  float horizontalVelocity = 0.25; 

  //Constructor
  alienShips(float alienX, float alienY)
  {
    xAxis= alienX;
    yAxis= alienY;
  }

  //Runs all separate functions for the alien ship
  void alienRun()
  {
    alienShipCreation();
    alienShipMove();
    landingCheck();
    missileCollisionCheck();
  }

  //Draws the alien ship
  void alienShipCreation()
  {
    //Antena of alien ship 
    fill(#585858);
    rect(xAxis-3.5, yAxis-30, 7, 15);
    quad(xAxis, yAxis, xAxis+15, yAxis+15, xAxis+20, yAxis+15, xAxis+15, yAxis);

    //Feet of alien ship
    quad(xAxis+10, yAxis+10, xAxis+20, yAxis+10, xAxis+35, yAxis+25, xAxis+25, yAxis+25);
    triangle(xAxis+18, yAxis+28, xAxis+28, yAxis+18, xAxis+38, yAxis+28);
    quad(xAxis-10, yAxis+10, xAxis-20, yAxis+10, xAxis-35, yAxis+25, xAxis-25, yAxis+25);
    triangle(xAxis-18, yAxis+28, xAxis-28, yAxis+18, xAxis-38, yAxis+28);

    //Body of alien ship

    ellipse(xAxis+37, yAxis, 20, 20);
    ellipse(xAxis-37, yAxis, 20, 20);

    fill(#939393);
    ellipse(xAxis, yAxis, 75, 35);

    //Red decoration of ship
    fill(alienDecorationColor);
    rect(xAxis-37, yAxis-5, 75, 10, 50);
    ellipse(xAxis, yAxis-32, 15, 15);
  }

  void alienShipMove()
  { 
    //Moves the ships in a sine function, the velocity integers allow for changes in levels or difficulty, higher values make the ships faster and more more to the sides
    angle+=0.05;
    xAxis += sin(angle) * horizontalVelocity;
    yAxis += sin(90) * verticalVelocity;
  }

  //Checks if the coordinates of the missile are inside the alien ships hitbox, if yes it will make the ship spawn in a different random spot. It also calls the explosion animation
  void missileCollisionCheck() 
  {
    for (int i=0; i<=maximumMissileCount-1; i++)
    {
      if (dist(missiles[i].xAxis+2.5, missiles[i].yAxis, xAxis-5, yAxis-20) <= 42.5) 
      {          
        explosionAnimation();
        explosionTimer++;
        if (explosionTimer<60)
        {
          xAxis=random(45, width-45);
          yAxis=random(-150, -50);
          speedUpdate();
          explosionTimer=0;
        }
      }
    }
  }

  //Velocity changes only happen to new instances of the alien ship, after it has been destroyed once
  void speedUpdate()
  {
    //Checks for score intervals, increasing the speed of the ships    
    if (scoreCounter>100 && scoreCounter<250)
    {
      verticalVelocity=0.5;
      horizontalVelocity=0.5;
    }
    if (scoreCounter>250 && scoreCounter<400)
    {
      verticalVelocity=0.75;
      horizontalVelocity=0.75;
    }
    if (scoreCounter>400 && scoreCounter<550)
    {
      verticalVelocity=1;
      horizontalVelocity=1;
    }
    if (scoreCounter>550 && scoreCounter<600)
    {
      horizontalVelocity=1.25;
    }
    if (scoreCounter>600 && scoreCounter<750)
    {
      horizontalVelocity=1.5;
    }
    if (scoreCounter>750)
    {
      verticalVelocity=1.25;
      horizontalVelocity=1.5;
    }
  }

  //Stops alien ship if on ground and sends for the end condition
  void landingCheck()
  {
    if (yAxis>height-60 && scoreCounter<1000)
    {
      verticalVelocity=0;
      horizontalVelocity=0;
      gameOver=true;
    }
  }

  //Happens when the ship is hit by a missile
  void explosionAnimation()
  {
    fill(#E3C427);
    triangle(xAxis-30, yAxis-20, xAxis, yAxis+30, xAxis+30, yAxis-20);
    fill(#E50013);
    triangle(xAxis-30, yAxis+20, xAxis, yAxis-30, xAxis+30, yAxis+20);
    fill(#EAC400);
    triangle(xAxis-15, yAxis-10, xAxis, yAxis+20, xAxis+15, yAxis-10);
  }
}
