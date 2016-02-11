// Game constants
final int TITLE_SCREEN = 0;
final int GAME_PLAYING = 1;
final int GAME_OVER    = 2;

// Game Vars
Defender player;
Bullet bullet;
Invader enemy;
Invader[][] enemyArray;
int enemies = 10; // enemy amount
int gameState = TITLE_SCREEN;


void setup() {
	size(565, 600);
	enemyArray = new Invader[2][enemies];
	initEnemies();
	player = new Defender(250, 530, 5, #CBCBCB, enemyArray);
}

void draw() {

	// Show title screen until game is started
	switch (gameState) {

		case TITLE_SCREEN :
			gameState = titleScreen();
		break;

		case GAME_PLAYING :
			// TODO: take this to its own place
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
		break;

		case GAME_OVER :
		// TODO: Handle this, ensure that there is a win and lose screen
		break;
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
		if (keyCode == LEFT){
			player.xDirection = 0;
		} else if (keyCode == RIGHT){
			player.xDirection = 0;
		}
	}
}

void keyPressed() {
	if (key == CODED)
	{
		if (keyCode == LEFT){
			player.xDirection = -1;
		} else if (keyCode == RIGHT){
			player.xDirection = 1;
		}
	}
	if (key == ' ' ){
		player.shoot();
	}

}

int titleScreen(){
	background(0);
	textAlign(CENTER);
	textSize(32);
	fill(255);
	text("Space Invaders", width/2, 100);

	// button
	// TODO: take this button elsewhere, button class?
	int rectWidth    = 150;
	int rectHeight   = 80;
	int rectRounding = 7;
	int rectY        = height - rectHeight *2;
	int rectX        = width/2;

	rectMode(CENTER);
	fill(255);
	rect(rectX, rectY, rectWidth, rectHeight, rectRounding);

	textAlign(CENTER, CENTER);
	textSize(22);
	fill(0);
	text("START", rectX, rectY);

	return 0;
};