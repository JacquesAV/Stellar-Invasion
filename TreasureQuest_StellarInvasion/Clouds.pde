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

  void cloudRun()
  {
    cloudDraw();
    cloudMove();
  }
  void cloudDraw()
  {
    pushMatrix();
    translate(200/scaleX, 200/scaleY);
    scale(scaleX, scaleY);
    //Cloud darkens over time
    cloudColor=sameRGB-scoreCounter/7.5;
    fill(cloudColor); //#E5E5E5

    //Filling of cloud
    noStroke();
    rect(xAxis, yAxis-20, 150, 35);
    rect(xAxis, yAxis-5, 140, 35);
    rect(xAxis+5, yAxis-35, 135, 35, 20);

    stroke(#000000);
    //Top of cloud
    arc(xAxis, yAxis, 50, 50, PI/2, PI+2);
    arc(xAxis+30, yAxis-20, 55, 35, PI, PI+2.5);
    arc(xAxis+70, yAxis-25, 60, 35, PI+0.3, PI+2.5);
    arc(xAxis+110, yAxis-25, 55, 35, PI+0.5, PI+3);

    //Bottom of cloud
    arc(xAxis+35, yAxis+20, 80, 35, 0.3, PI);
    arc(xAxis+140, yAxis-10, 55, 55, PI+1, PI*2+1.5);
    arc(xAxis+70, yAxis+15, 80, 55, 0.7, PI-0.7);
    arc(xAxis+110, yAxis+17, 80, 35, -0.3, PI-1.1);
    popMatrix();
  }

  void cloudMove()
  {      
    xAxis+=speed;
    //Randomized the cloud size and speed when leaving the border of the map
    if (xAxis>width)
    {
      speed=random(0.5, 1);
      scaleX=random(1, 2);
      scaleY=random(1.5, 2);
      xAxis=random(-400, -300);
      yAxis=random(0, 200);
    }
  }
}
