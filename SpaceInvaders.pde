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
public static int enemyRows = 5;
public static int enemies; // total enemies
int gameState;


void setup() {
	size(565, 600);

	gameState = TITLE_SCREEN;
	// create buttons
	startButton = new Button("START", width/2, height - 160,
		150, 80, color(0,153,51), 7);
	endButton = new Button("Return to title", width/2, height - 160,
		150, 80, color(0,153,51), 7);

}

void draw() {

	// Show title screen until game is started
	switch (gameState) {

		case TITLE_SCREEN :
			drawScreen("Space Invaders", startButton);
		break;

		case GAME_PLAYING :
			playGame();
		break;

		case GAME_OVER :
			// If there are enemies left the player must have died
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


// Main game loop
void playGame() {
	// TODO: take this to its own place
	background(255);

	showUI(player.score);

	player.update();

	boolean changeDir = false;

	changeDir = checkEnemyBoundaries();

	updateEnemies(changeDir);

	// check for game end conditions
	checkGameOver();
}

// Enemy creation
void initEnemies() {
	// row var
	int rowHeight = 60;

	// loop through array
	for (int row = 0; row < enemyArray.length; ++row)
	{
		for(int column = 0; column < enemyArray[0].length; column++)
		{
			// create enemies to fill array TODO: Fix spacing number to use width of enemy
			enemyArray[row][column] = new Invader(column * 35 + width/4, rowHeight, 8, #FF0004);
		}

		// change row height
		rowHeight += 55;
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

// Get mouse clicks for button presses
void mouseClicked(){

	if(startButton.mouseOver() && gameState == TITLE_SCREEN){
		gameState = GAME_PLAYING;
		gameInit();
	} else if(endButton.mouseOver() && gameState == GAME_OVER){
		gameState = TITLE_SCREEN;
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

// Displays player lives and score
void showUI(int score) {

	// Show score
	textAlign(CENTER);
	textSize(18);
	fill(0);
	text("SCORE: " + score, width/4 * 3, 30);

	// TODO: Add lives
}

// Checks for game ending changes
void checkGameOver() {
	// TODO: Add death for player
	if (enemies <= 0 || player.lives <=0){
		gameState = GAME_OVER;
	}
}

boolean checkEnemyBoundaries() {
	// check if any enemies have hit the boundary
	for (int row = 0; row < enemyArray.length; ++row)
	{
		for(int column = 0; column < enemyArray[row].length; column++)
		{
			// if enemy exits and will hit the edge next
			if (enemyArray[row][column] != null && enemyArray[row][column].hitsEdge())
				return true;
		}
	}

	return false;
}

void updateEnemies(boolean changeDir) {
	// call update on all enemies in array
	for (int row = 0; row < enemyArray.length; ++row)
	{
		for(int column = 0; column < enemyArray[row].length; column++)
		{
			if(enemyArray[row][column] != null){
				enemyArray[row][column].update(changeDir);
			}
		}
	}
}