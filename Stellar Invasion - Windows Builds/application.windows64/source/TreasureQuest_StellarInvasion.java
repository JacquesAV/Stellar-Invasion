import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TreasureQuest_StellarInvasion extends PApplet {

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
int alienDecorationColor = 0xffAF0B0B;
int orangeDecoration = 0xffF08800;
int startingBackground = 0xff7B92A7;

public void setup()
{
  
  strokeWeight(1.5f);
  strokeJoin(ROUND);
  stroke(0xff000000);

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
    clouds[i] = new cloudClass(random(-500, width), random(-20, 250), random(1, 2), random(1.5f, 2), random(0.6f, 0.9f));
  }
}

//Creates moving entities as a valid object as well as declaring its relevant floats
playerMissile missiles[]=new playerMissile[maximumMissileCount];
alienShips alien[]=new alienShips[numberOfAlienShips];
cloudClass clouds[]=new cloudClass[numberOfClouds];
playerTank tank;

public void draw()
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
public void runGame()
{
  //Ambience Functions and Decoration
  background(startingBackground);

  //Darkens the sky background as score increases
  fill(0xff000A1C, scoreCounter/5); //#3B0101
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
  stroke(0xff000000);
  //Creates the score counter and fire counter
  counters();

  //Side Barriers (Must be in front of every other entity, so it is drawn last)
  fill(0xff376431);
  triangle(width, height, width, height-300, width-85, height);
  triangle(0, height, 0, height-300, 85, height);

  //Checks for if the game is won or lost, and will execute an action if either is true
  winLoseConditionCheck();
}

//Sets all relevant variables back to their original states and reruns the setup, allowing for a full game restart without having to close the application
public void restartGame()
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
  alienDecorationColor = 0xffAF0B0B;
  orangeDecoration = 0xffF08800;
  startingBackground = 0xff7B92A7;
  opacityIncrease=0;

  setup();
}

//Creates the fade in for either the menu or restarted game
public void restartAnimation()
{
  fill(0xff0C050F, restartBackgroundOpacity);
  rect(0, 0, width, height);

  restartBackgroundOpacity--;
  if (restartBackgroundOpacity<0)
  {
    restartBackgroundOpacity=0;
    restartAnimation=false;
  }
}
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
  float verticalVelocity = 0.25f; 
  float horizontalVelocity = 0.25f; 

  //Constructor
  alienShips(float alienX, float alienY)
  {
    xAxis= alienX;
    yAxis= alienY;
  }

  //Runs all separate functions for the alien ship
  public void alienRun()
  {
    alienShipCreation();
    alienShipMove();
    landingCheck();
    missileCollisionCheck();
  }

  //Draws the alien ship
  public void alienShipCreation()
  {
    //Antena of alien ship 
    fill(0xff585858);
    rect(xAxis-3.5f, yAxis-30, 7, 15);
    quad(xAxis, yAxis, xAxis+15, yAxis+15, xAxis+20, yAxis+15, xAxis+15, yAxis);

    //Feet of alien ship
    quad(xAxis+10, yAxis+10, xAxis+20, yAxis+10, xAxis+35, yAxis+25, xAxis+25, yAxis+25);
    triangle(xAxis+18, yAxis+28, xAxis+28, yAxis+18, xAxis+38, yAxis+28);
    quad(xAxis-10, yAxis+10, xAxis-20, yAxis+10, xAxis-35, yAxis+25, xAxis-25, yAxis+25);
    triangle(xAxis-18, yAxis+28, xAxis-28, yAxis+18, xAxis-38, yAxis+28);

    //Body of alien ship

    ellipse(xAxis+37, yAxis, 20, 20);
    ellipse(xAxis-37, yAxis, 20, 20);

    fill(0xff939393);
    ellipse(xAxis, yAxis, 75, 35);

    //Red decoration of ship
    fill(alienDecorationColor);
    rect(xAxis-37, yAxis-5, 75, 10, 50);
    ellipse(xAxis, yAxis-32, 15, 15);
  }

  public void alienShipMove()
  { 
    //Moves the ships in a sine function, the velocity integers allow for changes in levels or difficulty, higher values make the ships faster and more more to the sides
    angle+=0.05f;
    xAxis += sin(angle) * horizontalVelocity;
    yAxis += sin(90) * verticalVelocity;
  }

  //Checks if the coordinates of the missile are inside the alien ships hitbox, if yes it will make the ship spawn in a different random spot. It also calls the explosion animation
  public void missileCollisionCheck() 
  {
    for (int i=0; i<=maximumMissileCount-1; i++)
    {
      if (dist(missiles[i].xAxis+2.5f, missiles[i].yAxis, xAxis-5, yAxis-20) <= 42.5f) 
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
  public void speedUpdate()
  {
    //Checks for score intervals, increasing the speed of the ships    
    if (scoreCounter>100 && scoreCounter<250)
    {
      verticalVelocity=0.5f;
      horizontalVelocity=0.5f;
    }
    if (scoreCounter>250 && scoreCounter<400)
    {
      verticalVelocity=0.75f;
      horizontalVelocity=0.75f;
    }
    if (scoreCounter>400 && scoreCounter<550)
    {
      verticalVelocity=1;
      horizontalVelocity=1;
    }
    if (scoreCounter>550 && scoreCounter<600)
    {
      horizontalVelocity=1.25f;
    }
    if (scoreCounter>600 && scoreCounter<750)
    {
      horizontalVelocity=1.5f;
    }
    if (scoreCounter>750)
    {
      verticalVelocity=1.25f;
      horizontalVelocity=1.5f;
    }
  }

  //Stops alien ship if on ground and sends for the end condition
  public void landingCheck()
  {
    if (yAxis>height-60 && scoreCounter<1000)
    {
      verticalVelocity=0;
      horizontalVelocity=0;
      gameOver=true;
    }
  }

  //Happens when the ship is hit by a missile
  public void explosionAnimation()
  {
    fill(0xffE3C427);
    triangle(xAxis-30, yAxis-20, xAxis, yAxis+30, xAxis+30, yAxis-20);
    fill(0xffE50013);
    triangle(xAxis-30, yAxis+20, xAxis, yAxis-30, xAxis+30, yAxis+20);
    fill(0xffEAC400);
    triangle(xAxis-15, yAxis-10, xAxis, yAxis+20, xAxis+15, yAxis-10);
  }
}
public void backgroundDecoration()
{
  //Varied size hills
  fill(0xff285A21);
  arc(100, height-10, 800, 400, PI, TWO_PI);
  arc(350, height-10, 500, 350, PI, TWO_PI);
  arc(800, height-10, 700, 400, PI, TWO_PI);
  arc(550, height-10, 600, 400, PI, TWO_PI);
  arc(0, height-10, 450, 400, PI, TWO_PI);
  arc(1200, height-10, 800, 400, PI, TWO_PI);
  arc(1400, height-10, 600, 450, PI, TWO_PI);

  //Floor
  fill(0xff40503E);
  rect(0, height-50, width, 50);

  //Rails for the Tank
  fill(0xff503806);
  rect(0, height-12, width, 5);
  rect(0, height-40, width, 4);

  fill(0xff5B5F5A);
  for (int i=0; i<width; i+=40)
  {
    quad(0+i, height-5, 15+i, height-5, 50+i, height-45, 40+i, height-45);
  }
}
class cloudClass
{  
  float xAxis;
  float yAxis;
  float scaleX;
  float scaleY;
  float speed;
  float sameRGB=190;
  float cloudColor=color(sameRGB, sameRGB, sameRGB);

  //Constructor
  cloudClass(float cloudX, float cloudY, float cloudScaleX, float cloudScaleY, float cloudSpeed)
  {
    xAxis= cloudX;
    yAxis= cloudY;
    scaleX=cloudScaleX;
    scaleY=cloudScaleY;
    speed=cloudSpeed;
  }

  public void cloudRun()
  {
    cloudDraw();
    cloudMove();
  }
  public void cloudDraw()
  {
    pushMatrix();
    translate(200/scaleX, 200/scaleY);
    scale(scaleX, scaleY);
    //Cloud darkens over time
    cloudColor=sameRGB-scoreCounter/7.5f;
    fill(cloudColor); //#E5E5E5

    //Filling of cloud
    noStroke();
    rect(xAxis, yAxis-20, 150, 35);
    rect(xAxis, yAxis-5, 140, 35);
    rect(xAxis+5, yAxis-35, 135, 35, 20);

    stroke(0xff000000);
    //Top of cloud
    arc(xAxis, yAxis, 50, 50, PI/2, PI+2);
    arc(xAxis+30, yAxis-20, 55, 35, PI, PI+2.5f);
    arc(xAxis+70, yAxis-25, 60, 35, PI+0.3f, PI+2.5f);
    arc(xAxis+110, yAxis-25, 55, 35, PI+0.5f, PI+3);

    //Bottom of cloud
    arc(xAxis+35, yAxis+20, 80, 35, 0.3f, PI);
    arc(xAxis+140, yAxis-10, 55, 55, PI+1, PI*2+1.5f);
    arc(xAxis+70, yAxis+15, 80, 55, 0.7f, PI-0.7f);
    arc(xAxis+110, yAxis+17, 80, 35, -0.3f, PI-1.1f);
    popMatrix();
  }

  public void cloudMove()
  {      
    xAxis+=speed;
    //Randomized the cloud size and speed when leaving the border of the map
    if (xAxis>width)
    {
      speed=random(0.5f, 1);
      scaleX=random(1, 2);
      scaleY=random(1.5f, 2);
      xAxis=random(-400, -300);
      yAxis=random(0, 200);
    }
  }
}
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
  public void missileRun()
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
  public void missileCreation()
  {
    //Missile flame
    fill(0xffEAD700);
    triangle(xAxis+2.5f, yAxis+25, xAxis+5, yAxis+55, xAxis+7.5f, yAxis+25);
    fill(0xffC61818);
    triangle(xAxis, yAxis+25, xAxis+2.5f, yAxis+40, xAxis+5, yAxis+25);
    triangle(xAxis+5, yAxis+25, xAxis+7.5f, yAxis+40, xAxis+10, yAxis+25);

    //Missile body
    fill(0xff818181);
    rect(xAxis, yAxis, 10, 25);
    triangle(xAxis, yAxis, xAxis+5, yAxis-15, xAxis+10, yAxis);
    fill(0xff363535);
    rect(xAxis, yAxis+22, 10, 3);

    //Missile decor
    fill(0xff0469BF);
    rect(xAxis, yAxis, 10, 5);
  }
  //Increases missile move speed
  public void missileMove()
  {
    yAxis-=missileSpeed;
    missileSpeed+=0.1f;

    //Checks if the missile is out of bounds, which 
    if (yAxis<=-10)
    {
      collisionRemove=true;
    }
  }

  //Checks if the coordinates of the missile are inside the alien ships hitbox
  public void missileCollisionCheck() 
  {
    for (int i=0; i<numberOfAlienShips; i++)
    {
      if (dist(xAxis+2.5f, yAxis, alien[i].xAxis-5, alien[i].yAxis-20) <= 42.5f) 
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
class playerTank
{
  float xAxis;
  float yAxis;
  int tankColor=0xff0061B4;
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
  public void tankRun()
  {
    tankCreation();
    tankMove();
    fireMissile();

    //Allows for delays to be made
    timePassed++;
  }

  //Draws the tank
  public void tankCreation()
  {
    //Top of tank
    fill(0xff585858);
    rect(xAxis+45, yAxis+215, 10, 20);
    arc(xAxis+50, yAxis+260, 80, 50, PI, TWO_PI);
    fill(0xff363535);
    quad(xAxis+45, yAxis+217, xAxis+55, yAxis+217, xAxis+60, yAxis+205, xAxis+40, yAxis+205);

    //Body of tank
    fill(0xff818181);
    rect(xAxis, yAxis+250, 100, 27);
    fill(0xff363535);
    rect(xAxis, yAxis+275, 100, 13, 50);
    fill(tankColor);
    rect(xAxis, yAxis+256, 100, 8);

    //Wheels of tank
    fill(0xff585858);
    ellipse(xAxis+5, yAxis+280, 20, 20);
    ellipse(xAxis+95, yAxis+280, 20, 20);

    ellipse(xAxis+24, yAxis+281, 15, 15);
    ellipse(xAxis+41, yAxis+281, 15, 15);
    ellipse(xAxis+59, yAxis+281, 15, 15);
    ellipse(xAxis+76, yAxis+281, 15, 15);

    fill(0xff747474);
    ellipse(xAxis+24, yAxis+281, 7, 7);
    ellipse(xAxis+41, yAxis+281, 7, 7);
    ellipse(xAxis+59, yAxis+281, 7, 7);
    ellipse(xAxis+76, yAxis+281, 7, 7);

    triangle(xAxis-1, yAxis+285, xAxis+5, yAxis+272, xAxis+11, yAxis+285);
    triangle(xAxis+89, yAxis+285, xAxis+95, yAxis+272, xAxis+102, yAxis+285);
  }

  //Related to player tank movement
  public void tankMove()
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
  public void fireMissile()
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
//The starting score
int scoreCounter=0;
public void counters()
{
  scoreCounter();
  firingMonitor();
}

public void scoreCounter()
{
  //Board
  fill(0xff939393);
  rect(width-170, 0, 170, 50);

  //Text
  textSize(30);
  fill(0xff000000);
  //fill(#005293);
  pushMatrix();
  textAlign(LEFT);
  text("Score: " + PApplet.parseInt(scoreCounter), width-160, 35);
  popMatrix();
}

public void firingMonitor()
{
  //Circle fire
  fill(0xff939393);
  rect(150, 0, 50, 50);
  fill(0xffAF0B0B);
  ellipse(175, 25, 50, 50);

  //Reload viewer
  fill(tank.tankColor);
  arc(175, 25, 50, 50, 0, (2*PI)/tank.reloadStatus);

  //Missile Count
  fill(0xff939393);
  rect(0, 0, 150, 50);
  fill(0xff000000);
  pushMatrix();
  textAlign(LEFT);
  text("X " + PApplet.parseInt(missilesLeft), 60, 35);
  popMatrix();

  //Decorative missiles
  missileForCounter();
}

//Variables used for the decorative missiles
float yAxis=20;
public void missileForCounter()
{
  for (int xAxis=5; xAxis<40; xAxis+=15)
  {
    //Missile body
    fill(0xff818181);
    rect(xAxis, yAxis, 10, 25);
    triangle(xAxis, yAxis, xAxis+5, yAxis-15, xAxis+10, yAxis);
    fill(0xff363535);
    rect(xAxis, yAxis+22, 10, 3);

    //Missile decor
    fill(0xff0469BF);
    rect(xAxis, yAxis, 10, 5);
  }
}
//Used for the fading animantions
int settingsOpacity=250;
int settingsBackgroundOpacity=275;

//Keeps track of which menu screen the player should be on
int currentOpening=1;
public void openingScreen()
{
  //Each if statement corresponds to a different part of the main menu, which changes as options are selected

  pushMatrix();
  textAlign(CENTER);

  //Background
  fill(0xff0C050F, settingsBackgroundOpacity);
  rect(0, 0, width, height);

  //Title Screen
  if (currentOpening==1)
  {
    fill(0xffFFFFFF, settingsOpacity);
    textSize(150);
    text("STELLAR INVASION", width/2, height/2-100);

    textSize(50);
    text("PRESS ENTER TO CONTINUE", width/2, height/2+50);

    //Changes to next screen
    if (key == ENTER)
    {
      currentOpening=2;
    }
  } else {
  }

  //Difficulty selection
  if (currentOpening==2)
  {
    fill(0xffFFFFFF, settingsOpacity);
    textSize(80);
    text("Choose a difficulty!", width/2, height/2-350);
    textSize(45);
    String dif = "Each difficulty setting changes the amount of points that can be earned from destroying one enemy, choose by pressing the number on your keyboard.";
    text(dif, width/8, height/2-300, width-width/4, height/2);
    fill(0xffB71212);
    text("1.Hard: Aliens worth 10 points", width/2, height/2);
    fill(0xffF7E219);
    text("2.Medium: Aliens worth 15 points", width/2, height/2+80);
    fill(0xff329B10);
    text("3.Easy: Aliens worth 20 points", width/2, height/2+160);

    //Changes to next screen
    if (key == '1')
    {
      difficultyPoints=10;
      currentOpening=3;
    }
    if (key == '2')
    {
      difficultyPoints=15;
      currentOpening=3;
    }
    if (key == '3')
    {
      difficultyPoints=20;
      currentOpening=3;
    }
  } else {
  }

  //Controls and game explanation
  if (currentOpening==3)
  {
    //Controls
    fill(0xffFFFFFF, settingsOpacity);
    textSize(80);
    text("Controls", width/2, height/2-350);
    textSize(45);
    text("The 'AD' keys are used to move left and right respectively.", width/2, height/2-275);
    text("Click the left mouse button to fire a missile.", width/2, height/2-200);

    //Game
    textSize(80);
    text("How does the game work?", width/2, height/2-75);
    textSize(40);
    String obj = "Alien ships will slowly move towards the Earth, fire missiles to destroy them before they reach the ground. Reach 1000 points and you win the game, if the alien ships reach the ground you lose. Keep in mind you have a limited amount of missiles, and that enemy ships speed up over time.";
    text(obj, width/12, height/2-45, width-width/6, height/2+25);
    textSize(50);
    text("PRESS ENTER TO BEGIN", width/2, height/2+400);

    //Changes to next screen
    if (key == ENTER)
    {
      settingsOpacity-=1.25f;
    }
    if (settingsOpacity<=0)
    {
      settingsOpacity=5;
      currentOpening=4;
    }
  } else {
  }

  //Fade in screen before game intro
  if (currentOpening==4)
  {
    fill(0xffFFFFFF, settingsOpacity);
    textSize(75);
    String mission = "YOUR MISSION: PROTECT EARTH";
    text(mission, width/12, height/2-50, width-width/6, height/2+100);

    settingsOpacity+=1;

    //When scene is ready for animated transition
    if (settingsOpacity>=275)
    {
      currentOpening=5;
    }
  } else {
  }

  //Fade out screen before game intro
  if (currentOpening==5)
  {
    fill(0xffFFFFFF, settingsOpacity);
    textSize(75);
    String mission = "YOUR MISSION: PROTECT EARTH";
    text(mission, width/12, height/2-50, width-width/6, height/2+100);
    settingsBackgroundOpacity--;
    settingsOpacity-=1.5f;

    startGame=true;
  }
  popMatrix();
}
//Used for the fading animantions
float opacityIncrease=0;
float restartBackgroundOpacity=0;
boolean restartAnimation=false;
public void winLoseConditionCheck()
{
  //When the alien ship reaches the ground this is activated revealing the game over screen
  if (gameOver==true)
  {
    opacityIncrease+=3;
    fill(0xff290707, opacityIncrease);
    rect(0, 0, width, height);

    pushMatrix();
    textAlign(CENTER);
    fill(0xffFFFFFF, opacityIncrease);
    textSize(150);
    text("YOU FAILED EARTH!", width/2, height/2-100);
    textSize(75);
    text("MISSILES LEFT: " + PApplet.parseInt(missilesLeft), width/2, height/2-10);
    textSize(75);
    text("FINAL SCORE: " + PApplet.parseInt(scoreCounter), width/2, height/2+70);
    popMatrix();

    restartSelection();
  } else {
  }

  //Win screen that is activated when hitting the win condition score of 1000 points
  if (scoreCounter>=1000)
  {
    opacityIncrease+=0.5f;
    fill(0xff0469BF, opacityIncrease);
    rect(0, 0, width, height);

    pushMatrix();
    textAlign(CENTER);
    fill(0xffFFFFFF, opacityIncrease);
    textSize(150);
    text("YOU SAVED EARTH!", width/2, height/2-100);
    textSize(75);
    text("MISSILES LEFT: " + PApplet.parseInt(missilesLeft), width/2, height/2-10);
    textSize(50);
    text("FINAL SCORE: " + PApplet.parseInt(scoreCounter), width/2, height/2+55);
    popMatrix();

    //Alien ships retreat when a win condition is met, inverting their velocities
    alienDecorationColor=orangeDecoration;
    for (int i=0; i<numberOfAlienShips; i++)
    {
      alien[i].verticalVelocity=-abs( alien[i].verticalVelocity);
      alien[i].horizontalVelocity=-abs( alien[i].horizontalVelocity);
    }

    restartSelection();
  }
} 

//Reset instructions for when the win or lose screen is available
public void restartSelection()
{  
  textSize(50);
  //Reset instructions
  text("1.PLAY AGAIN?", width/2, height/2+320);
  text("2.RETURN TO MAIN MENU", width/2, height/2+400);

  //Restart Background
  fill(0xff0C050F, restartBackgroundOpacity);
  rect(0, 0, width, height);

  //Will restart only the current match
  if (key=='1')
  {
    restartBackgroundOpacity+=1.25f;
    if (restartBackgroundOpacity>250)
    {
      restartAnimation=true;
      restartGame();
    }
  }

  //Will bring player back to main menu
  if (key=='2')
  {
    restartBackgroundOpacity++;
    if (restartBackgroundOpacity>250)
    {
      restartAnimation=true;
      settingsOpacity=250;
      settingsBackgroundOpacity=275;
      currentOpening=1;
      restartGame();
    }
  }
}
  public void settings() {  size(1400, 900, FX2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TreasureQuest_StellarInvasion" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
