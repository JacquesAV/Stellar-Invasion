void backgroundDecoration()
{
  //Varied size hills
  fill(#285A21);
  arc(100, height-10, 800, 400, PI, TWO_PI);
  arc(350, height-10, 500, 350, PI, TWO_PI);
  arc(800, height-10, 700, 400, PI, TWO_PI);
  arc(550, height-10, 600, 400, PI, TWO_PI);
  arc(0, height-10, 450, 400, PI, TWO_PI);
  arc(1200, height-10, 800, 400, PI, TWO_PI);
  arc(1400, height-10, 600, 450, PI, TWO_PI);

  //Floor
  fill(#40503E);
  rect(0, height-50, width, 50);

  //Rails for the Tank
  fill(#503806);
  rect(0, height-12, width, 5);
  rect(0, height-40, width, 4);

  fill(#5B5F5A);
  for (int i=0; i<width; i+=40)
  {
    quad(0+i, height-5, 15+i, height-5, 50+i, height-45, 40+i, height-45);
  }
}
