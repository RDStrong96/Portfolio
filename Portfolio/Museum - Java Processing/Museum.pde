/*
AUTHOR: Ryan Strong
STUDENT-NUM: 7764341
COURSE: COMP3490
SECTION: A01
*/

void setup()
{
  size(800,600,P3D);
  frustum(-1,1,1,-1,0.8,100);
  textureMode(NORMAL);
  textureWrap(REPEAT);
  wallTex = loadImage("assets/WallTexture.jpg");
  glassTex = loadImage("assets/GlassTexture.jpg");
  floor1Tex = loadImage("assets/Floor1Texture.jpg");
  floor2Tex = loadImage("assets/Floor2Texture.jpg");
  woodTex = loadImage("assets/WoodTexture.jpg");
  grassTex = loadImage("assets/GrassTexture.jpg");
  waterTex = loadImage("assets/WaterTexture.jpg");
  //ALL TEXTURES FROM textures.com because there's a good chance I'll forget to add that to the readme
}

float tarRot = 0;
float curRot = 0;

float tarX = 0;
float curX = 0;
float prevX = 0;
float tarZ = 0;
float curZ = 0;
float prevZ = 0;

int side = 0;
float yaw = 0;

PImage wallTex;
PImage glassTex;
PImage floor1Tex;
PImage floor2Tex;
PImage woodTex;
PImage grassTex;
PImage waterTex;

float t = 0.00;
float dt = 0.01;

boolean third = false;
boolean moving = false;
boolean still = true;

float legLRot = 0;
float legRRot = 0;
float legLTar = 0;
float legRTar = 0;

void draw()
{
  clear();
  background(100,100,100);
  if (third)
  {
    camera(0, 0, 0.75,    0, -2, -3,   0, 1, 0);
  }
  else{
  camera(0, 0, 0,    0, 0, -3,   0, 1, 0);  //Dont move the camera, move the world AROUND the camera
  }
  pushMatrix();
  
  
  
  checkTar();
  prevX = tarX;
  prevZ = tarZ;
  
  if ( (curRot < tarRot - 0.01) )//Floats can be weird
  {
    moving = true;
    still = false;
    curRot += 0.015;
  }else if ( (curRot > tarRot + 0.01) )
  { 
    moving = true;
    still=false;
    curRot -= 0.015;
  }else{
    curRot = tarRot; //Keeps from stuttering
  }
  
  if ( (curX < tarX - 0.01) )//Floats can be weird
  {
    moving = true;
    still = false;
    curX += 0.026;
  }else if ( (curX > tarX + 0.01) )
  {
    moving = true;
    still = false;
    curX -= 0.026;
  }else{
    curX = tarX; //Keeps from stuttering
  }
  
  if ( (curZ < tarZ - 0.01) )//Floats can be weird
  {
    moving = true;
    still = false;
    curZ += 0.026;
  }else if ( (curZ > tarZ + 0.01) )
  {
    moving = true;
    still = false;
    curZ -= 0.026;
  }else{
    curZ = tarZ; //Keeps from stuttering
  }
  if(!((curRot < tarRot - 0.01) || (curRot > tarRot + 0.01) || (curX < tarX - 0.01) || (curX > tarX + 0.01) || (curZ < tarZ - 0.01) || (curZ > tarZ + 0.01)) )
  {
    still = true;
  }
  if (third)
  {
    drawCharacter();
  }
  rotateY(curRot);
  translate(-1*curX,8,-1*curZ); //default 8 Y for cam to be close to floor
  
  drawFloor();
  drawWalls();
  
  pushMatrix();
  translate(6,-10,-6);
  drawExhib1();
  popMatrix();
  
  pushMatrix();
  translate(6,-10,6);
  drawExhib2();
  popMatrix();
  
  pushMatrix();
  translate(-6,-10,-6);
  drawExhib3();
  popMatrix();
  
  pushMatrix();
  translate(-6,-10,6);
  drawExhib4();
  popMatrix();
  
  
  popMatrix();
  t += dt;
  if((t >= 1) || t <= 0)
  {
    dt *= -1;
  }
}

void checkTar()
{
  if (abs(tarX)==10)
  {
    tarX = prevX;
  }
  if (abs(tarZ)==10)
  {
    tarZ = prevZ;
  }
  
  if (abs(tarZ)==6 && (abs(tarX)==6))
  {
    tarX = prevX;
    tarZ = prevZ;
  }
}

void drawFloor()
{
  boolean checked = false;
  //Tiles are 2 by 2, meaning that 0,0 is in the middle of the center square
  for (float x = -11; x <= 11; x+= 2)
  {
    checked = !(checked);
    for(float z = -11; z <= 11; z+= 2)
    {
      beginShape(QUADS);
      if (checked)
      {
        texture(floor1Tex);
      }else
      {
        texture(floor2Tex);
      }
      vertex(x,-10,z-2,0,0);
      vertex(x-2,-10,z-2,0,1);
      vertex(x-2,-10,z,1,1);
      vertex(x,-10,z,1,0);
      endShape();
      checked = !(checked);
    }
  }
}

void drawWalls()
{
  beginShape(QUADS);
  
  texture(wallTex);  
  vertex(-10.5,-10,10.5,0,0);
  vertex(-10.5,-10,-10.5,5,0);
  vertex(-10.5,10,-10.5,5,5);
  vertex(-10.5,10,10.5,0,5);
  
  vertex(-10.5,-10,-10.5,0,0);
  vertex(10.5,-10,-10.5,5,0);
  vertex(10.5,10,-10.5,5,5);
  vertex(-10.5,10,-10.5,0,5);
  
  vertex(10.5,-10,-10.5,0,0);
  vertex(10.5,-10,10.5,5,0);
  vertex(10.5,10,10.5,5,5);
  vertex(10.5,10,-10.5,0,5);
  
  vertex(10.5,-10,10.5,0,0);
  vertex(-10.5,-10,10.5,5,0);
  vertex(-10.5,10,10.5,5,5);
  vertex(10.5,10,10.5,0,5);
  endShape();
}

void keyPressed()
{
  
  switch(key)
  {
    case 'w':
      if (side == 0)
        tarZ -= 2;
      if (side == 1)
        tarX += 2;
      if (side == 2)
        tarZ += 2;
      if (side == 3)
        tarX -= 2;
      break;
    case 's':
      if (side == 0)
        tarZ += 2;
      if (side == 1)
        tarX -= 2;
      if (side == 2)
        tarZ -= 2;
      if (side == 3)
        tarX += 2;
      break;
    case 'a':
      if (side == 0)
        tarX -= 2;
      if (side == 1)
        tarZ -= 2;
      if (side == 2)
        tarX += 2;
      if (side == 3)
        tarZ += 2;
      break;
    case 'd':
      if (side == 0)
        tarX += 2;
      if (side == 1)
        tarZ += 2;
      if (side == 2)
        tarX -= 2;
      if (side == 3)
        tarZ -= 2;
      break;
    case 'q':
      tarRot -= radians(90);
      side --;
      side += 4;
      side %= 4;
      break;
    case 'e':
      tarRot += radians(90);
      side ++;
      side %= 4;
      break;
    case ' ':
      third = !third;
  }
}

void drawExhib1()
{
  pushMatrix();
  fill(255,255,0);
  translate(0,0.5,0);
  box(0.5,3,0.5);
  
  translate(lerp(-0.25,0.25,t),1.5,0);
  beginShape(TRIANGLES);
  texture(glassTex);
  vertex(0,0.35,0,0.5,1);
  vertex(0,0,-0.35,0,0);
  vertex(-0.35,0,0,1,0);
  
  vertex(0,0.35,0,0.5,1);
  vertex(-0.35,0,0,0,0);
  vertex(0,0,0.35,1,0);
  
  vertex(0,0.35,0,0.5,1);
  vertex(0,0,0.35,0,0);
  vertex(0.35,0,0,1,0);
  
  vertex(0,0.35,0,0.5,1);
  vertex(0.35,0,0,0,0);
  vertex(0,0,-0.35,1,0);
  endShape();
  beginShape(QUADS);
  texture(glassTex);
  vertex(0,0,-0.35,0,0);
  vertex(-0.35,0,0,1,0);
  vertex(0,0,0.35,1,1);
  vertex(0.35,0,0,0,1);
  endShape();
  noFill();
  popMatrix();
  drawExhibWalls();
}

void drawExhib2()
{
  pushMatrix();
  fill(0,255,255);
  translate(0,0.5,0);
  box(0.5,3,0.5);
  translate(0,2,0);
  rotateZ(lerp(0,30,t));
  
  beginShape(QUADS);
  texture(woodTex);
  
  vertex(0.4,-0.4,0.4,0,0);
  vertex(-0.4,-0.4,0.4,1,0);
  vertex(-0.4,-0.4,-0.4,1,1);
  vertex(0.4,-0.4,-0.4,0,1);
  
  vertex(0.4,-0.4,0.4,0,0);
  vertex(-0.4,-0.4,0.4,0,1);
  vertex(-0.4,0.4,0.4,1,1);
  vertex(0.4,0.4,0.4,1,0);
  
  vertex(0.4,-0.4,-0.4,0,0);
  vertex(-0.4,-0.4,-0.4,0,1);
  vertex(-0.4,0.4,-0.4,1,1);
  vertex(0.4,0.4,-0.4,1,0);
  
  vertex(-0.4,-0.4,0.4,0,0);
  vertex(-0.4,-0.4,-0.4,1,0);
  vertex(-0.4,0.4,-0.4,1,1);
  vertex(-0.4,0.4,0.4,0,1);
  
  vertex(0.4,-0.4,0.4,0,0);
  vertex(0.4,-0.4,-0.4,1,0);
  vertex(0.4,0.4,-0.4,1,1);
  vertex(0.4,0.4,0.4,0,1);
  
  vertex(0.4,0.4,0.4,0,0);
  vertex(-0.4,0.4,0.4,1,0);
  vertex(-0.4,0.4,-0.4,1,1);
  vertex(0.4,0.4,-0.4,0,1);
  
  endShape();
  noFill();
  popMatrix();
  drawExhibWalls();
}

void drawExhib3()
{
  pushMatrix();
  fill(255,0,255);
  translate(0,0.5,0);
  box(0.5,3,0.5);
  translate(0,lerp(1.9,2.7,t),0);
  
  beginShape(QUADS);
  texture(grassTex);
  
  vertex(0.4,-0.4,0.4,0,0);
  vertex(-0.4,-0.4,0.4,1,0);
  vertex(-0.4,-0.4,-0.4,1,1);
  vertex(0.4,-0.4,-0.4,0,1);
  
  vertex(0.4,-0.4,0.4,0,0);
  vertex(-0.4,-0.4,0.4,0,1);
  vertex(-0.4,0.4,0.4,1,1);
  vertex(0.4,0.4,0.4,1,0);
  
  vertex(0.4,-0.4,-0.4,0,0);
  vertex(-0.4,-0.4,-0.4,0,1);
  vertex(-0.4,0.4,-0.4,1,1);
  vertex(0.4,0.4,-0.4,1,0);
  
  vertex(-0.4,-0.4,0.4,0,0);
  vertex(-0.4,-0.4,-0.4,1,0);
  vertex(-0.4,0.4,-0.4,1,1);
  vertex(-0.4,0.4,0.4,0,1);
  
  vertex(0.4,-0.4,0.4,0,0);
  vertex(0.4,-0.4,-0.4,1,0);
  vertex(0.4,0.4,-0.4,1,1);
  vertex(0.4,0.4,0.4,0,1);
  
  vertex(0.4,0.4,0.4,0,0);
  vertex(-0.4,0.4,0.4,1,0);
  vertex(-0.4,0.4,-0.4,1,1);
  vertex(0.4,0.4,-0.4,0,1);
  
  endShape();
  popMatrix();
  noFill();
  drawExhibWalls();
}

void drawExhib4()
{
  pushMatrix();
  fill(0,0,255);
  translate(0,0.5,0);
  box(0.5,3,0.5);
  
  translate(0,1.5,0);
  scale(lerp(0,2,t));
  beginShape(TRIANGLES);
  texture(waterTex);
  vertex(0,0.35,0,0.5,1);
  vertex(0,0,-0.35,0,0);
  vertex(-0.35,0,0,1,0);
  
  vertex(0,0.35,0,0.5,1);
  vertex(-0.35,0,0,0,0);
  vertex(0,0,0.35,1,0);
  
  vertex(0,0.35,0,0.5,1);
  vertex(0,0,0.35,0,0);
  vertex(0.35,0,0,1,0);
  
  vertex(0,0.35,0,0.5,1);
  vertex(0.35,0,0,0,0);
  vertex(0,0,-0.35,1,0);
  endShape();
  beginShape(QUADS);
  texture(glassTex);
  vertex(0,0,-0.35,0,0);
  vertex(-0.35,0,0,1,0);
  vertex(0,0,0.35,1,1);
  vertex(0.35,0,0,0,1);
  endShape();
  popMatrix();
  noFill();
  drawExhibWalls();
}

void drawExhibWalls()
{
  fill(0,200,255,100);
  beginShape(QUADS);
  vertex(-0.75,0,-0.75);
  vertex(-0.75,0,0.75);
  vertex(-0.75,2.5,0.75);
  vertex(-0.75,2.5,-0.75);
  
  vertex(0.75,0,-0.75);
  vertex(0.75,0,0.75);
  vertex(0.75,2.5,0.75);
  vertex(0.75,2.5,-0.75);
  
  vertex(-0.75,0,-0.75);
  vertex(0.75,0,-0.75);
  vertex(0.75,2.5,-0.75);
  vertex(-0.75,2.5,-0.75);
  
  vertex(-0.75,0,0.75);
  vertex(0.75,0,0.75);
  vertex(0.75,2.5,0.75);
  vertex(-0.75,2.5,0.75);  
  endShape();
  noFill();
  noTint();
}

void drawCharacter()
{
  if (moving && ((legLTar==0)&&(legRTar==0)))
  {
    legLTar = radians(45);
    legRTar = radians(-45);
  }else if(still)
  {
    legLTar = 0;
    legRTar = 0;
    moving = false;
  }
  pushMatrix();
  translate(0,-0.55,-0.1);
  fill(255,255,255);
  box(0.15,0.15,0.10);
  
  translate(0,-0.38,0);
  fill(255,0,255);
  box(0.5,0.75,0.08);
  
  translate(0,-0.51,0);
  fill(50,240,50);
  
  pushMatrix();  
  translate(-0.2,0,0);
  if ( (legLRot < legLTar-0.1) )
  {
    legLRot += 0.05;
  }else if ( (legLRot > legLTar+0.1) )
  { 
    legLRot -= 0.05;
  }else{
    legLTar *= -1;
  }
  rotateX(legLRot);
  box(0.1,0.35,0.1);
  popMatrix();
  
  pushMatrix();
  translate(0.2,0,0);
  if ( (legRRot < legRTar-0.1) )
  {
    legRRot += 0.05;
  }else if ( (legRRot > legRTar+0.1) )
  { 
    legRRot -= 0.05;
  }else{
    legRTar *= -1;
  }
  rotateX(legRRot);
  box(0.1,0.35,0.1);
  popMatrix();
  
  popMatrix();
}
