package SpaceInvaders;

import processing.core.*;

import java.util.ArrayList;
import java.util.Iterator;

import kuusisto.tinysound.*;


public class SpaceInvaders extends PApplet {

    // Run this project as Java application and this
    // method will launch the sketch
    public static void main(String[] args) {
    	PApplet.main(SpaceInvaders.class.getName());
    }

    // Game variables
    private Defender player;
    private ArrayList<Bullet> bullets;
    private ShieldGroup shields;
    private Button startButton, endButton;
    private EnemyGroup enemies;
    private GameState gameState;
    private int highscore;
    private int level = 1;
    private Music bgMusic;
    private PFont smallFont, largeFont, buttonFont;

    private PImage background;

    public void settings() {
    	  size(565, 600);
    	}

    public void setup() {

        gameState = GameState.TITLE_SCREEN;
        
        // Load fonts
        largeFont = createFont("ca.ttf", 32);
        smallFont = createFont("ca.ttf", 18);
        buttonFont = createFont("ca.ttf", 16);
        
        // create buttons
        startButton = new Button("START", width/2, height - 160,
            150, 80, color(0,153,51), buttonFont, 7, this);
        endButton = new Button("Title screen", width/2, height - 160,
            150, 80, color(0,153,51), buttonFont, 7, this);

        // Load background
        background = loadImage("Ship.png");
        
        // Load background music
        TinySound.init();
        TinySound.setGlobalVolume(0.7);
        bgMusic = TinySound.loadMusic("bgMusic.wav");
        bgMusic.setVolume(0.5);
        bgMusic.play(true);
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

    public void stop() {
    	TinySound.shutdown();
    }
    
    // Game setup
    public void gameInit() {
        // create bullet list
        bullets = new ArrayList<Bullet>();
    	
        // create shields
    	shields = new ShieldGroup(4, this);
    	shields.init();
    	
    	// create enemies
    	enemies = new EnemyGroup(5, 10, this);
    	enemies.init(level);
        
        // create player on first level
        if (level == 1) {
        	player = new Defender(250, 532, 5, "Defender.png", this);
        }
    }

	// Main game loop
    public void playGame() {
        drawBackground();

        showUI(player);

        player.update();

        updateBullets();
        
        enemies.updateEnemies(player, bullets);
        
        shields.update();

        nextLevel();
        
        // check for game end conditions
        checkGameOver();
    }



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
        textFont(largeFont);
        
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
        textFont(largeFont);
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
        textFont(smallFont);
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
            level = 1;
        }
    }
    
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
            if(bullet.getBulletType() == BulletType.PLAYER) {

            	// remove bullet if it has hit an enemy
            	if (enemies.playerBulletHit(player, bullet)){
            		iterator.remove();
            	}
                
            } else if(bullet.getBulletType() == BulletType.ENEMY) {
            	
                // check if player is hit
                if(bullet.hasCollided(player)) {
                    player.getHit();
                    iterator.remove();
                }
            }
            
            // Checks for bullets hitting shields
            if (shields.hit(bullet)){
            	iterator.remove();
            }

            bullet.update();
        }// end bullet for
    } // end method
    
}