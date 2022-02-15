class playerMissile
{
  float xAxis;
  float yAxis;
  float missileSpeed=0;

  boolean collisionRemove=false;

  //Constructor
  playerMissile(float missileX, float missileY)
  {
    xAxis= missileX;
    yAxis= missileY;
  }

  //Runs all separate functions for the missile
  void missileRun()
  {
    //If missile is invalid it will stop functioning, boolean statement is so that it can dissapear when collision occurs with the alien ship
    if (collisionRemove==false && gameOver==false)
    {
      missileCreation();
      missileMove();
      missileCollisionCheck();
    } else
    {
      xAxis=width;
      yAxis=height;
    }
  }

  //Draws the missile
  void missileCreation()
  {
    //Missile flame
    fill(#EAD700);
    triangle(xAxis+2.5, yAxis+25, xAxis+5, yAxis+55, xAxis+7.5, yAxis+25);
    fill(#C61818);
    triangle(xAxis, yAxis+25, xAxis+2.5, yAxis+40, xAxis+5, yAxis+25);
    triangle(xAxis+5, yAxis+25, xAxis+7.5, yAxis+40, xAxis+10, yAxis+25);

    //Missile body
    fill(#818181);
    rect(xAxis, yAxis, 10, 25);
    triangle(xAxis, yAxis, xAxis+5, yAxis-15, xAxis+10, yAxis);
    fill(#363535);
    rect(xAxis, yAxis+22, 10, 3);

    //Missile decor
    fill(#0469BF);
    rect(xAxis, yAxis, 10, 5);
  }
  //Increases missile move speed
  void missileMove()
  {
    yAxis-=missileSpeed;
    missileSpeed+=0.1;

    //Checks if the missile is out of bounds, which 
    if (yAxis<=-10)
    {
      collisionRemove=true;
    }
  }

  //Checks if the coordinates of the missile are inside the alien ships hitbox
  void missileCollisionCheck() 
  {
    for (int i=0; i<numberOfAlienShips; i++)
    {
      if (dist(xAxis+2.5, yAxis, alien[i].xAxis-5, alien[i].yAxis-20) <= 42.5) 
      {
        //print("Hit!"); //<-- Testing purposes to see if collision occurs between alien ships and missile
        if (gameOver==false && scoreCounter<1000)
        {
          scoreCounter+=difficultyPoints;
        }
        collisionRemove=true;
      }
    }
  }
}
