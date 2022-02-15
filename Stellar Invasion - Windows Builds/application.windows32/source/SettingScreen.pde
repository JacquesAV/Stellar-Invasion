//Used for the fading animantions
int settingsOpacity=250;
int settingsBackgroundOpacity=275;

//Keeps track of which menu screen the player should be on
int currentOpening=1;
void openingScreen()
{
  //Each if statement corresponds to a different part of the main menu, which changes as options are selected

  pushMatrix();
  textAlign(CENTER);

  //Background
  fill(#0C050F, settingsBackgroundOpacity);
  rect(0, 0, width, height);

  //Title Screen
  if (currentOpening==1)
  {
    fill(#FFFFFF, settingsOpacity);
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
    fill(#FFFFFF, settingsOpacity);
    textSize(80);
    text("Choose a difficulty!", width/2, height/2-350);
    textSize(45);
    String dif = "Each difficulty setting changes the amount of points that can be earned from destroying one enemy, choose by pressing the number on your keyboard.";
    text(dif, width/8, height/2-300, width-width/4, height/2);
    fill(#B71212);
    text("1.Hard: Aliens worth 10 points", width/2, height/2);
    fill(#F7E219);
    text("2.Medium: Aliens worth 15 points", width/2, height/2+80);
    fill(#329B10);
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
    fill(#FFFFFF, settingsOpacity);
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
      settingsOpacity-=1.25;
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
    fill(#FFFFFF, settingsOpacity);
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
    fill(#FFFFFF, settingsOpacity);
    textSize(75);
    String mission = "YOUR MISSION: PROTECT EARTH";
    text(mission, width/12, height/2-50, width-width/6, height/2+100);
    settingsBackgroundOpacity--;
    settingsOpacity-=1.5;

    startGame=true;
  }
  popMatrix();
}
