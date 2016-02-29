package SpaceInvaders;
import processing.core.*;

import java.util.ArrayList;
import java.util.Iterator;

//TODO: Add sound
//TODO: Better title and text

public class SpaceInvaders extends PApplet {

    // Run this project as Java application and this
    // method will launch the sketch
    public static void main(String[] args) {
    	PApplet.main(SpaceInvaders.class.getName());
    }

    // Game constants
    final int TITLE_SCREEN = 0;
    final int GAME_PLAYING = 1;
    final int GAME_OVER    = 2;

    // Game Vars
    Defender player;
    Bullet bullet;
    ArrayList<Bullet> bullets;
    Invader enemy;
    Shield[] shieldArray;
    int shieldAmount = 4;
    Invader[][] enemyArray;
    Button startButton, endButton;
    int enemiesPerRow = 10; // enemy amount per row
    int enemyRows = 5;
    int enemies; // total enemies
    int gameState;
    int highscore;
    int level = 1;
    Boolean enemyReachedEnd = false;

    PImage background;

    public void settings() {
    	  size(565, 600);
    	}

    public void setup() {

        gameState = TITLE_SCREEN;
        // create buttons
        startButton = new Button("START", width/2, height - 160,
            150, 80, color(0,153,51), 7, this);
        endButton = new Button("Return to title", width/2, height - 160,
            150, 80, color(0,153,51), 7, this);

        // Load background
        background = loadImage("Ship.png");
    }

    public void draw() {

        // Show title screen until game is started
        switch (gameState) {

            case TITLE_SCREEN :
                drawScreen("Space Invaders", startButton);
            break;

            case GAME_PLAYING :
                playGame();
            break;

            case GAME_OVER :
                drawScreen("Game Over", endButton);
            break;
        }

    }

    // Game setup
    public void gameInit(){
        // create bullet list
        bullets = new ArrayList<Bullet>();
    	
        // create shields
    	shieldArray = new Shield[shieldAmount];
    	initShields();
    	
    	// create enemies
        enemyArray = new Invader[enemyRows][enemiesPerRow];
        initEnemies();
        
        // create player if not already initialised
        if (player == null) {
        	player = new Defender(250, 532, 5, "Defender.png", this);
        }
        enemies = enemyRows * enemiesPerRow;
    }

	// Main game loop
    public void playGame() {
        drawBackground();

        showUI(player);

        player.update();

        updateBullets();
        
        boolean changeDir = false;

        changeDir = checkEnemyBoundaries();

        updateEnemies(changeDir);
        
        updateShields();

        nextLevel();
        
        // check for game end conditions
        checkGameOver();
    }

	// initialises shields
    private void initShields() {
		
    	int xPos = 40;
    	int yPos = 480;
    	
    	// fill shieldArray
    	for (int i = 0; i < shieldArray.length; i++) {
			shieldArray[i] = new Shield(xPos , yPos, 0, "shield-green.png", this, "shield-amber.png", "shield-red.png");
			
			xPos += width/4;
		}
		
	}

    // Enemy creation
    public void initEnemies() {
        // row var
        int rowHeight = 60 + (20 * level);
        int points;
        String imageOne, imageTwo;

        // loop through array
        for (int row = 0; row < enemyArray.length; ++row)
        {
            for(int column = 0; column < enemyArray[0].length; column++)
            {

                // Assign enemy image based on row
                if(row < 1) {
                    imageOne = "squid1.png";
                    imageTwo = "squid2.png";
                    points = 30;

                } else if (row < 3) {
                    imageOne = "crab1.png";
                    imageTwo = "crab2.png";
                    points = 20;
                } else {
                    imageOne = "skull1.png";
                    imageTwo = "skull2.png";
                    points = 10;
                }


                // create enemies to fill array
                enemyArray[row][column] = new Invader(column * 35 + width/4, rowHeight, points, imageOne, imageTwo, this);
            }

            // change row height
            rowHeight += 55;
        }
    }


    // Controls

    public void keyReleased() {
        if (key == CODED)
        {
            if (keyCode == LEFT){
                player.setXDirection(0);
            } else if (keyCode == RIGHT){
            	player.setXDirection(0);
            }
        }
    }

    public void keyPressed() {
        if (key == CODED)
        {
            if (keyCode == LEFT){
            	player.setXDirection(-1);
            } else if (keyCode == RIGHT){
            	player.setXDirection(1);
            }
        }
        if (key == ' ' ){
        	if(player.canShoot())
        		bullets.add(player.shoot());
        }

    }

    // Get mouse clicks for button presses
    public void mouseClicked(){

        if(startButton.mouseOver() && gameState == TITLE_SCREEN){
            gameState = GAME_PLAYING;
            gameInit();
        } else if(endButton.mouseOver() && gameState == GAME_OVER){
            gameState = TITLE_SCREEN;
        }
    }

    // Draws a screen with a title and a button
    public void drawScreen(String title, Button button){
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
    public void showPoints(Defender player) {
        int score = player.getScore();

        // check for highscore
        highscore = score > highscore ? score : highscore;

        textAlign(CENTER);
        textSize(32);
        fill(255);
        text("HIGHSCORE", width/2, height/4);

        textAlign(CENTER);
        textSize(32);
        fill(255);
        text(highscore, width/2, height/4 + 50);

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
    public void drawBackground() {
        background(0);
        image(background, 0, 560);
    }

    // Displays player lives and score
    public void showUI(Defender player) {

        // Show score
        textAlign(CENTER);
        textSize(18);
        fill(255);
        text("SCORE: " + player.getScore(), width/4 * 3, 30);

        // Show lives
        // Show score
        textAlign(CENTER);
        textSize(18);
        fill(255);
        text("LIVES: " + player.getLives(), width/4, 30);

    }

    public void nextLevel() {
    	if (enemies > 0 || player.getLives() <= 0) {
    		return;
    	}
    	
    	level++;
    	
    	gameInit();
    }
    
    // Checks for game ending changes
    public void checkGameOver() {
        if (enemies <= 0 || player.getLives() <= 0 || enemyReachedEnd){
            gameState = GAME_OVER;
        }
    }

    public boolean checkEnemyBoundaries() {
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

    public void updateEnemies(boolean changeDir) {
        // call update on all enemies in array
        for (int row = 0; row < enemyArray.length; ++row) {
            for (int column = 0; column < enemyArray[row].length; ++column) {

                if(enemyArray[row][column] == null){
                    continue;
                 }

                enemyArray[row][column].update(changeDir);

                // check for collision with player
                if (enemyArray[row][column].hasCollided(player)){
                	gameState = GAME_OVER;
                }
                
                bullet = enemyArray[row][column].shoot();
                if(bullet != null) {
                    bullets.add(bullet);
                }
            } // end inner for
        }
    }

    public void updateBullets(){
        // check there are bullets
        if(bullets.isEmpty()){
            return;
        }

        // update all bullets, using iterator to remove collided bullets
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();)
        {
            // Get the bullet
            Bullet bullet = iterator.next();

            // remove bullet if off the screen
            if(!bullet.onScreen()){
                iterator.remove();
            }

            // check player bullets for collision with enemy
            if(bullet.getBulletType() == 1)
            {

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
                            enemyArray[row][column].destroy();
                            enemyArray[row][column] = null;
                            enemies--;
                        }
                    } // end inner inner for
                } // end inner for
            } else if(bullet.getBulletType() == 0) {
                // check if player is hit
                if(bullet.hasCollided(player)) {
                    player.getHit();
                    iterator.remove();
                }
            }
            
            // check for any bullets hitting shields
            for (int i = 0; i < shieldArray.length; i++) {
				if(bullet.hasCollided(shieldArray[i])){
					shieldArray[i].reduceHealth();
					iterator.remove();
				}
			}

            bullet.update();
        }// end bullet for
    } // end method
    
    private void updateShields() {
		for (int i = 0; i < shieldArray.length; i++) {
			shieldArray[i].update();
		}
		
	}
    
}