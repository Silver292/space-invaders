// Game constants
final int TITLE_SCREEN = 0;
final int GAME_PLAYING = 1;
final int GAME_OVER    = 2;

// Game Vars
Defender player;
Bullet bullet;
Invader enemy;
Invader[][] enemyArray;
Button startButton, endButton;
public static int enemiesPerRow = 10; // enemy amount per row
public static int enemyRows = 2;
public static int enemies; // total enemies
int gameState;


void setup() {
	size(565, 600);

	gameState = TITLE_SCREEN;
	// create buttons
	startButton = new Button("START", width/2, height - 160,
		150, 80, color(0,153,51), 7);
	endButton = new Button("Return to title", width/2, height - 240,
		150, 80, color(0,153,51), 7);

}

void draw() {

	// Show title screen until game is started
	switch (gameState) {

		case TITLE_SCREEN :
			drawScreen("Space Invaders", startButton);
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

			// check for game end conditions
			if (enemies <= 0){
				gameState = GAME_OVER;
			}
		break;

		case GAME_OVER :
		// TODO: Handle this, ensure that there is a win and lose screen
			String endMessage = enemies <= 0 ? "Congratulations!" : "Game Over";
			drawScreen(endMessage, endButton);
		break;
	}

}

// Game setup
void gameInit(){
	enemyArray = new Invader[enemyRows][enemiesPerRow];
	initEnemies();
	player = new Defender(250, 530, 5, #CBCBCB, enemyArray);
	enemies = enemyRows * enemiesPerRow;
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

// Draws a screen with a title and a button
void drawScreen(String title, Button button){
  background(0);
  textAlign(CENTER);
  textSize(32);
  fill(255);
  text(title, width/2, 100);

  // draw button
  button.render();
}


// Get mouse clicks for button presses
void mouseClicked(){
	if(startButton.mouseOver()){
		gameState = GAME_PLAYING;
		gameInit();
	} else if(endButton.mouseOver()){
		gameState = TITLE_SCREEN;
	}
}
