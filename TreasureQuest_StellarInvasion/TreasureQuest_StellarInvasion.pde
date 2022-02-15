//Defines the number of aliens and clouds that exist, as well as how many missiles the player can have
int maximumMissileCount=150;
int numberOfAlienShips=20;
int numberOfClouds=10;

//Keeps track of which missile the player is currently on
int currentMissile=-1;
int missilesLeft=maximumMissileCount;

//Number of points the player will get per alien kill, is chosen in the difficulty menu for the player
int difficultyPoints;

//Statements
boolean gameOver=false;
boolean startGame=false;
boolean settingsChosen=false;

//Colors that can change during the game
color alienDecorationColor = #AF0B0B;
color orangeDecoration = #F08800;
color startingBackground = #7B92A7;

void setup()
{
  size(1400, 900, FX2D);
  strokeWeight(1.5);
  strokeJoin(ROUND);
  stroke(#000000);

  tank = new playerTank(-45+width/2, height-300);
  //For loop to declare maximum amount of instances of the missiles that can exist
  for (int i=0; i<missiles.length; i++)
  {
    missiles[i] = new playerMissile(tank.xAxis+45, height-80);
  }
  //For loop to declare amount of instances of the alien ships
  for (int i=0; i<alien.length; i++)
  {
    alien[i] = new alienShips(random(45, width-45), random(-300, -10));
  }
  //For loop to declare amount of instances for the clouds
  for (int i=0; i<clouds.length; i++)
  {
    clouds[i] = new cloudClass(random(-500, width), random(-20, 250), random(1, 2), random(1.5, 2), random(0.6, 0.9));
  }
}

//Creates moving entities as a valid object as well as declaring its relevant floats
playerMissile missiles[]=new playerMissile[maximumMissileCount];
alienShips alien[]=new alienShips[numberOfAlienShips];
cloudClass clouds[]=new cloudClass[numberOfClouds];
playerTank tank;

void draw()
{
  //When the settings are set, the game will start here
  if (startGame==true)
  {
    runGame();
  } else
  {
  }

  //Starts the game off with the settings/introduction screen, and will disable when done
  if (settingsChosen==false)
  {
    openingScreen();
  } else
  {
  }

  //Reset command
  if (key=='p')
  {
    restartGame();
  }

  if (restartAnimation==true)
  {
    restartAnimation();
  }
}

//Runs the game once settings are chosen
void runGame()
{
  //Ambience Functions and Decoration
  background(startingBackground);

  //Darkens the sky background as score increases
  fill(#000A1C, scoreCounter/5); //#3B0101
  rect(0, 0, width, height);

  backgroundDecoration();

  //Allows for missiles to be created at different times, with dependancy on a variable in the player tank class
  for (int i=0; i<=currentMissile; i++)
  {
    missiles[i].missileRun();
  }

  //Runs the created aliens and player tank object
  tank.tankRun();
  for (int i=0; i<numberOfAlienShips; i++)
  {
    alien[i].alienRun();
  }

  //Runs the created aliens and player tank object
  for (int i=0; i<numberOfClouds; i++)
  {
    clouds[i].cloudRun();
  }
  stroke(#000000);
  //Creates the score counter and fire counter
  counters();

  //Side Barriers (Must be in front of every other entity, so it is drawn last)
  fill(#376431);
  triangle(width, height, width, height-300, width-85, height);
  triangle(0, height, 0, height-300, 85, height);

  //Checks for if the game is won or lost, and will execute an action if either is true
  winLoseConditionCheck();
}

//Sets all relevant variables back to their original states and reruns the setup, allowing for a full game restart without having to close the application
void restartGame()
{
  maximumMissileCount=150;
  numberOfAlienShips=20;
  numberOfClouds=10;
  currentMissile=-1;
  missilesLeft=maximumMissileCount;
  scoreCounter=0;
  gameOver=false;
  startGame=false;
  settingsChosen=false;
  alienDecorationColor = #AF0B0B;
  orangeDecoration = #F08800;
  startingBackground = #7B92A7;
  opacityIncrease=0;

  setup();
}

//Creates the fade in for either the menu or restarted game
void restartAnimation()
{
  fill(#0C050F, restartBackgroundOpacity);
  rect(0, 0, width, height);

  restartBackgroundOpacity--;
  if (restartBackgroundOpacity<0)
  {
    restartBackgroundOpacity=0;
    restartAnimation=false;
  }
}
