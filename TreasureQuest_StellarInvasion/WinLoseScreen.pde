//Used for the fading animantions
float opacityIncrease=0;
float restartBackgroundOpacity=0;
boolean restartAnimation=false;
void winLoseConditionCheck()
{
  //When the alien ship reaches the ground this is activated revealing the game over screen
  if (gameOver==true)
  {
    opacityIncrease+=3;
    fill(#290707, opacityIncrease);
    rect(0, 0, width, height);

    pushMatrix();
    textAlign(CENTER);
    fill(#FFFFFF, opacityIncrease);
    textSize(150);
    text("YOU FAILED EARTH!", width/2, height/2-100);
    textSize(75);
    text("MISSILES LEFT: " + int(missilesLeft), width/2, height/2-10);
    textSize(75);
    text("FINAL SCORE: " + int(scoreCounter), width/2, height/2+70);
    popMatrix();

    restartSelection();
  } else {
  }

  //Win screen that is activated when hitting the win condition score of 1000 points
  if (scoreCounter>=1000)
  {
    opacityIncrease+=0.5;
    fill(#0469BF, opacityIncrease);
    rect(0, 0, width, height);

    pushMatrix();
    textAlign(CENTER);
    fill(#FFFFFF, opacityIncrease);
    textSize(150);
    text("YOU SAVED EARTH!", width/2, height/2-100);
    textSize(75);
    text("MISSILES LEFT: " + int(missilesLeft), width/2, height/2-10);
    textSize(50);
    text("FINAL SCORE: " + int(scoreCounter), width/2, height/2+55);
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
void restartSelection()
{  
  textSize(50);
  //Reset instructions
  text("1.PLAY AGAIN?", width/2, height/2+320);
  text("2.RETURN TO MAIN MENU", width/2, height/2+400);

  //Restart Background
  fill(#0C050F, restartBackgroundOpacity);
  rect(0, 0, width, height);

  //Will restart only the current match
  if (key=='1')
  {
    restartBackgroundOpacity+=1.25;
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
