Defender player;
Bullet bullet;
Invader enemy;
Invader[][] enemyArray;
int enemies = 10; // enemy amount

void setup() {
  size(565, 600);
  enemyArray = new Invader[2][enemies];
  initEnemies();
  player = new Defender(250, 530, 5, #CBCBCB, enemyArray);
}

void draw() {
  background(255);
  player.update();

  // loop through array
  for (int row = 0; row < enemyArray.length; ++row)
  {
    for(int column = 0; column < enemyArray[0].length; column++)
    {
        if(enemyArray[row][column] != null){
            enemyArray[row][column].update();
        }
    }

  }

}

// Enemy creation
void initEnemies() {
  // row var
  int rowHeight;

  // loop through array
  for (int row = 0; row < enemyArray.length; ++row)
  {
    for(int column = 0; column < enemyArray[0].length; column++)
    {

      // check which row
      rowHeight = row > 0 ? 65 : 10;

      // create enemies to fill array
      enemyArray[row][column] = new Invader(column * 55, rowHeight, 1, #FF0004);
    }

  }
}


// Controls

void keyReleased() {
  if (key == CODED)
  {
    if (keyCode == LEFT)
    {
      player.xDirection = 0;
    } else if (keyCode == RIGHT)
    {
      player.xDirection = 0;
    }
  }
}

void keyPressed() {
  if (key == CODED)
  {
    if (keyCode == LEFT)
    {
      player.xDirection = -1;
    } else if (keyCode == RIGHT)
    {
      player.xDirection = 1;
    }
  }
  if (key == ' ' )
  {
    player.shoot();
  }

}
