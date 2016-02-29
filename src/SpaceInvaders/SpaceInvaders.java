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

    // Game Vars
    private Defender player;
    private ArrayList<Bullet> bullets;
    private Shield[] shieldArray;
    private int shieldAmount = 4;
    private Button startButton, endButton;
//    private int enemiesPerRow = 10; // enemy amount per row
//    private int enemyRows = 5;
//    private int enemies; // total enemies
    private EnemyGroup enemies;
    private GameState gameState;
    private int highscore;
    private int level = 1;
    //private Boolean enemyReachedEnd = false;

    private PImage background;

    public void settings() {
    	  size(565, 600);
    	}

    public void setup() {

        gameState = GameState.TITLE_SCREEN;
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
        } // end switch
    }

    // Game setup
    public void gameInit() {
        // create bullet list
        bullets = new ArrayList<Bullet>();
    	
        // create shields
    	shieldArray = new Shield[shieldAmount];
    	initShields();
    	
    	// create enemies
//        enemyArray = new Invader[enemyRows][enemiesPerRow];
//        initEnemies();
    	enemies = new EnemyGroup(5, 10, this);
    	enemies.init(level);
        
        // create player if not already initialised
        if (player == null) {
        	player = new Defender(250, 532, 5, "Defender.png", this);
        }
//        enemies = enemyRows * enemiesPerRow;
    }

	// Main game loop
    public void playGame() {
        drawBackground();

        showUI(player);

        player.update();

        updateBullets();
        
        boolean changeDir = false;

//        changeDir = checkEnemyBoundaries();
//
//        updateEnemies(changeDir);
        
        enemies.updateEnemies(player, bullets);
        
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
		} // end for
	}

//    // Enemy creation
//    public void initEnemies() {
//        // row var
//        int rowHeight = 60 + (20 * level);
//        int points;
//        String imageOne, imageTwo;
//
//        // loop through array
//        for (int row = 0; row < enemyArray.length; ++row) {
//            for(int column = 0; column < enemyArray[0].length; column++) {
//
//                // Assign enemy image based on row
//                if(row < 1) {
//                    imageOne = "squid1.png";
//                    imageTwo = "squid2.png";
//                    points = 30;
//
//                } else if (row < 3) {
//                    imageOne = "crab1.png";
//                    imageTwo = "crab2.png";
//                    points = 20;
//                } else {
//                    imageOne = "skull1.png";
//                    imageTwo = "skull2.png";
//                    points = 10;
//                }
//
//                // create enemies to fill array
//                enemyArray[row][column] = new Invader(column * 35 + width/4, rowHeight, points, imageOne, imageTwo, this);
//            } // end inner for
//
//            // change row height
//            rowHeight += 55;
//        } // end for
//    }


    // Controls

    public void keyReleased() {
        if (key == CODED) {
            if (keyCode == LEFT) {
                player.setXDirection(0);
            } else if (keyCode == RIGHT) {
            	player.setXDirection(0);
            }
        } // end if
    }

    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == LEFT) {
            	player.setXDirection(-1);
            } else if (keyCode == RIGHT) {
            	player.setXDirection(1);
            }
        }
        
        if (key == ' ' ) {
        	if(player.canShoot())
        		bullets.add(player.shoot());
        }
    }

    // Get mouse clicks for button presses
    public void mouseClicked() {

        if(startButton.mouseOver() && gameState == GameState.TITLE_SCREEN) {
            gameState = GameState.GAME_PLAYING;
            gameInit();
        } else if(endButton.mouseOver() && gameState == GameState.GAME_OVER) {
            gameState = GameState.TITLE_SCREEN;
        }
    }

    // Draws a screen with a title and a button
    public void drawScreen(String title, Button button) {
        background(0);
        textAlign(CENTER);
        textSize(32);
        fill(255);
        text(title, width/2, 100);

        if (gameState == GameState.GAME_OVER) {
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

        text(highscore, width/2, height/4 + 50);
        
        text("SCORE", width/2, height/2);

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
        text("LIVES: " + player.getLives(), width/4, 30);

    }

    // Checks if all enemies are destroyed
    // if so, set up next level
    public void nextLevel() {
    	if (enemies.getAmount() > 0 || player.getLives() <= 0) {
    		return;
    	}
    	
    	level++;
    	
    	gameInit();
    }
    
    // Checks for game ending changes
    public void checkGameOver() {
        if (enemies.hasGameOverState() || player.getLives() <= 0 ) {
            gameState = GameState.GAME_OVER;
        }
    }

//    // Returns true if any enemy has hit the edge of the screen
//    public boolean checkEnemyBoundaries() {
//        // check if any enemies have hit the boundary
//        for (int row = 0; row < enemyArray.length; ++row) {
//            for(int column = 0; column < enemyArray[row].length; column++) {
//            	
//                // if enemy exists and will hit the edge next
//                if (enemyArray[row][column] != null && enemyArray[row][column].hitsEdge())
//                    return true;
//                
//            } // end inner for
//        } // end outer for
//        
//        return false;
//    }

//    public void updateEnemies(boolean changeDir) {
//        // call update on all enemies in array
//        for (int row = 0; row < enemyArray.length; ++row) {
//            for (int column = 0; column < enemyArray[row].length; ++column) {
//
//            	// skip dead enemies
//                if(enemyArray[row][column] == null){
//                    continue;
//                 }
//
//                enemyArray[row][column].update(changeDir);
//
//                // check for collision with player
//                if (enemyArray[row][column].hasCollided(player)){
//                	gameState = GameState.GAME_OVER;
//                }
//                
//                // Get bullets if shot
//                Bullet bullet = enemyArray[row][column].shoot();
//                if(bullet != null) {
//                    bullets.add(bullet);
//                }
//            } // end inner for
//        } // end outer for
//    }

    public void updateBullets() {
        // check there are bullets
        if(bullets.isEmpty()) {
            return;
        }

        // update all bullets, using iterator to remove collided bullets
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            // Get the bullet
            Bullet bullet = iterator.next();

            // remove bullet if off the screen
            if(!bullet.onScreen()) {
                iterator.remove();
            }

            // check player bullets for collision with enemy
            if(bullet.getBulletType() == 1) {

            	// remove bullet if it has hit an enemy
            	if (enemies.playerBulletHit(player, bullet)){
            		iterator.remove();
            	}
                
            } else if(bullet.getBulletType() == 0) {
            	
                // check if player is hit
                if(bullet.hasCollided(player)) {
                    player.getHit();
                    iterator.remove();
                }
            }
            
            // check for any bullets hitting shields
            for (int i = 0; i < shieldArray.length; i++) {
            	// skip destroyed shields
            	if(shieldArray[i] == null) {
            		continue;
            	}
            	
            	// handle bullet shield collision
				if(bullet.hasCollided(shieldArray[i])) {
					shieldArray[i].reduceHealth();
					iterator.remove();
				}
			}

            bullet.update();
        }// end bullet for
    } // end method
    
    private void updateShields() {
		for (int i = 0; i < shieldArray.length; i++) {
			// skip destroyed shields
			if (shieldArray[i] == null) {
				continue;
			}
					
			shieldArray[i].update();
			
			// remove destroyed shields
			if (shieldArray[i].isDestroyed()) {
				shieldArray[i] = null;
			}
		} // end for
	}
    
}