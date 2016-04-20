package SpaceInvaders;

import java.util.ArrayList;

import processing.core.PApplet;

public class EnemyGroup {
	private Invader[][] enemyArray;
	private int amount;
	private PApplet parent;
	private boolean gameOverState = false;
	
	public EnemyGroup(int rows, int enemiesPerRow, PApplet p) {
		enemyArray = new Invader[rows][enemiesPerRow];
		amount = rows * enemiesPerRow;
		parent = p;
	}
	
	// Updates all enemies in group
    public void updateEnemies(Ship player, ArrayList<Bullet> bullets) {
    	
    	// Check if any enemies have hit edge of screen
        boolean changeDir = false;
        changeDir = checkEnemyBoundaries();
    	
        // call update on all enemies in array
        for (int row = 0; row < enemyArray.length; ++row) {
            for (int column = 0; column < enemyArray[row].length; ++column) {

            	// skip dead enemies
                if(enemyArray[row][column] == null){
                    continue;
                 }

                enemyArray[row][column].update(changeDir);

                // check for collision with player
                if (enemyArray[row][column].hasCollided(player)){
                	gameOverState = true;
                }
                
                // Get bullets if shot
                Bullet bullet = enemyArray[row][column].shoot();
                if(bullet != null) {
                    bullets.add(bullet);
                }
                
            } // end inner for
        } // end outer for
    }
	
    // Returns true if any enemy has hit the edge of the screen
    public boolean checkEnemyBoundaries() {
        // check if any enemies have hit the boundary
        for (int row = 0; row < enemyArray.length; ++row) {
            for(int column = 0; column < enemyArray[row].length; column++) {
            	
                // if enemy exists and will hit the edge next
                if (enemyArray[row][column] != null && enemyArray[row][column].hitsEdge())
                    return true;
                
            } // end inner for
        } // end outer for
        
        return false;
    }
    
    // Fill array
    public void init(int level) {
        // row var
        int rowHeight = 60 + (20 * level);
        int points;
        String imageOne, imageTwo;

        // loop through array
        for (int row = 0; row < enemyArray.length; ++row) {
            for(int column = 0; column < enemyArray[0].length; column++) {

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
                enemyArray[row][column] = new Invader(column * 35 + parent.width/4, rowHeight, points, imageOne, imageTwo, parent);
            } // end inner for

            // change row height
            rowHeight += 55;
        } // end for
    }
    
    // Takes a player and a bullet and checks if the bullet
    // has hit any enemies. If there is a collision points are
    // added to player and returns true
    public boolean playerBulletHit(Defender player, Bullet bullet) {
    	
        // iterate over enemies to check collision
        for (int row = 0; row < enemyArray.length; ++row) {
            for(int column = 0; column < enemyArray[row].length; ++column) {
                // skip dead enemies
                if(enemyArray[row][column] == null) {
                    continue;
                }

                // check for collision and remove bullet and enemy if there is one
                if(bullet.hasCollided(enemyArray[row][column])) {
                	
                	// add points for enemy to player score
                    player.addPoints(enemyArray[row][column].getPoints());
                    
                    // remove collided objects
                    enemyArray[row][column].beenHit();
                    enemyArray[row][column] = null;
                    amount--;
                    return true;
                }
            } // end inner inner for
        } // end inner for
    	
    	return false;
    }
    
    // Returns amount of enemies in group
    public int getAmount() {
    	return amount;
    }
    
    // Checks for enemies left and updates gameOverState
    private void checkEnemiesLeft() {
    	// return if game has already ended used in case of collision with player
    	if (gameOverState){
    		return;
    	}
    	gameOverState = amount > 0 ? false : true;
    }
    
    // Returns game over state
    public boolean hasGameOverState() {
    	checkEnemiesLeft();
    	return gameOverState;
    }
}
