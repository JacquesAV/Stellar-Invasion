//The starting score
int scoreCounter=0;
void counters()
{
  scoreCounter();
  firingMonitor();
}

void scoreCounter()
{
  //Board
  fill(#939393);
  rect(width-170, 0, 170, 50);

  //Text
  textSize(30);
  fill(#000000);
  //fill(#005293);
  pushMatrix();
  textAlign(LEFT);
  text("Score: " + int(scoreCounter), width-160, 35);
  popMatrix();
}

void firingMonitor()
{
  //Circle fire
  fill(#939393);
  rect(150, 0, 50, 50);
  fill(#AF0B0B);
  ellipse(175, 25, 50, 50);

  //Reload viewer
  fill(tank.tankColor);
  arc(175, 25, 50, 50, 0, (2*PI)/tank.reloadStatus);

  //Missile Count
  fill(#939393);
  rect(0, 0, 150, 50);
  fill(#000000);
  pushMatrix();
  textAlign(LEFT);
  text("X " + int(missilesLeft), 60, 35);
  popMatrix();

  //Decorative missiles
  missileForCounter();
}

//Variables used for the decorative missiles
float yAxis=20;
void missileForCounter()
{
  for (int xAxis=5; xAxis<40; xAxis+=15)
  {
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
}
