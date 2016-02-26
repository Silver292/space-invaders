import java.util.ArrayList;

// Game constants
final int TITLE_SCREEN = 0;
final int GAME_PLAYING = 1;
final int GAME_OVER    = 2;

// Game Vars
Defender player;
Bullet bullet;
ArrayList<Bullet> bullets;
Invader enemy;
Invader[][] enemyArray;
Button startButton, endButton;
public static int enemiesPerRow = 10; // enemy amount per row
public static int enemyRows = 5;
public static int enemies; // total enemies
int gameState;
Boolean enemyReachedEnd = false;

PImage background;

void setup() {
	size(565, 600);

	gameState = TITLE_SCREEN;
	// create buttons
	startButton = new Button("START", width/2, height - 160,
		150, 80, color(0,153,51), 7);
	endButton = new Button("Return to title", width/2, height - 160,
		150, 80, color(0,153,51), 7);

    // Load background
    background = loadImage("assets/Ship.png");

    // create bullet list
    bullets = new ArrayList<Bullet>();
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
	player = new Defender(250, 532, 5, "assets/Defender.png", enemyArray);
	enemies = enemyRows * enemiesPerRow;
}


// Main game loop
void playGame() {
	drawBackground();

	showUI(player);

	player.update();

	updateBullets();

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
    int points;
    String imageOne, imageTwo;

	// loop through array
	for (int row = 0; row < enemyArray.length; ++row)
	{
		for(int column = 0; column < enemyArray[0].length; column++)
		{

            // Assign enemy image based on row
            if(row < 1) {
                imageOne = "assets/squid1.png";
                imageTwo = "assets/squid2.png";
                points = 30;

            } else if (row < 3) {
                imageOne = "assets/crab1.png";
                imageTwo = "assets/crab2.png";
                points = 20;
            } else {
                imageOne = "assets/skull1.png";
                imageTwo = "assets/skull2.png";
                points = 10;
            }


			// create enemies to fill array TODO: Fix spacing number to use width of enemy
			enemyArray[row][column] = new Invader(column * 35 + width/4, rowHeight, points, imageOne, imageTwo);
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
		bullets.add(player.shoot());
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

    if (gameState == GAME_OVER) {
        showPoints(player);
    }

	// draw button
	button.render();
}

// Shows the players points at the end of the game
void showPoints(Defender player) {
    int score = player.getScore();

    textAlign(CENTER);
    textSize(32);
    fill(255);
    text("SCORE", width/2, height/2);

    textAlign(CENTER);
    textSize(32);
    fill(255);
    text(score, width/2, height/2 + 50);
}

// draws the background
void drawBackground() {
    background(0);
    image(background, 0, 560);


}

// Displays player lives and score
void showUI(Defender player) {

	// Show score
	textAlign(CENTER);
	textSize(18);
	fill(255);
	text("SCORE: " + player.getScore(), width/4 * 3, 30);

	// TODO: Add lives
}

// Checks for game ending changes
void checkGameOver() {
	// TODO: Add death for player
	if (enemies <= 0 || player.lives <=0 || enemyReachedEnd){
		gameState = GAME_OVER;
	}
}

boolean checkEnemyBoundaries() {
	// check if any enemies have hit the boundary
	for (int row = 0; row < enemyArray.length; ++row)
	{
		for(int column = 0; column < enemyArray[row].length; column++)
		{
			// if enemy exists and will hit the edge next
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

void updateBullets(){
	// check there are bullets
	if(bullets.isEmpty()){
		return;
	}

    // update all bullets, using iterator to remove collided bullets
    for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();)
    {
        // Get the bullet
        Bullet bullet = iterator.next();

        // check player bullets for collision with enemy
        if(bullet.getBulletType() == 1)
        {
	        // remove bullet if off the screen
	        if(!bullet.onScreen()){
	            iterator.remove();
	        }

	        // iterate over enemies to check collision
	        for (int row = 0; row < enemyArray.length; ++row)
	        {
	            for(int column = 0; column < enemyArray[row].length; ++column)
	            {
	                // skip dead enemies
	                if(enemyArray[row][column] == null){
	                    continue;
	                }

	                // check for collision and remove bullet and enemy if there is one
	                if(bullet.hasCollided(enemyArray[row][column])){
	                    player.addPoints(enemyArray[row][column].getPoints());
	                    iterator.remove();
	                    enemyArray[row][column] = null;
	                    enemies--;
	                }
	            } // end inner inner for
	        } // end inner for
        } else if(bullet.getBulletType() == 0) {
        	// enemy bullet code goes here
        }



        bullet.update();
    }// end bullet for
} // end method