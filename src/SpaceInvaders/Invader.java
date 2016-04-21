package SpaceInvaders;

import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;

public class Invader extends Ship {

    private PImage sprite1, sprite2;
    private int animCounter = 1;
    private int points = 10;
    private int bulletTime = 0;
    private int bulletTimeMax = 200;
    private Random rand;

    public Invader (int x, int y, int points, String image1, 
    		String image2, PApplet p) {
        super(x, y, 8, image1, p);

        sprite1 = sprite;
        sprite2 = parent.loadImage(image2);
        xDirection = 1;
        this.points = points;
        rand = new Random();
        bulletTime = rand.nextInt(bulletTimeMax);
    } // end constructor

	public Invader (int x, int y, int speed, int points, String image1,
			String image2, PApplet p) {
		super(x, y, speed, image1, p);

        sprite1 = sprite;
        sprite2 = parent.loadImage(image2);
		xDirection = 1;
        this.points = points;
        rand = new Random();
        bulletTime = rand.nextInt(bulletTimeMax);
	} // end constructor

	//updates invader and changes direction based on boolean passed
	public void update(boolean changeDir) {
        // check object is still in play
        if(destroyed){
            return;
        }
		move(changeDir);
		render();
	}

	public void move(boolean changeDir) {
		// Slower movements speed
		if (parent.frameCount % 40 != 0)
			return;

		// Move down and change direction
		if (changeDir) {
			moveY();
			xDirection = -xDirection;
		} else {
			moveX();
		}

		// alternate images
        if (animCounter >= 1) {
            sprite = sprite2;
            animCounter = 0;
            return;
        }

        sprite = sprite1;
        animCounter++;
	}

	// Shoot bullets randomly
	// returns null if a bullet is not shot
	public Bullet shoot() {
		
		// random chance and set time period between shots
        if(rand.nextDouble() < 0.05 && ++bulletTime >= bulletTimeMax) {
            bulletTime = 0;
            shoot.play(0.3);
            return new EnemyBullet(x + width/2, y, 5, parent);
        } else {
            return null;
        }
    }

	// moves the invader across the screen
    public void moveX() {
		x = x + (xDirection * speed);
	} // end move

	// moves in the invader down the screen
    public void moveY() {
		y = y + height;
	}

    // returns the invaders points
    public int getPoints() {
        return points;
    }
    
    // used when enemy is hit by a bullet
    public void beenHit(AnimGroup explosions) {
    	destroy();
    	explosions.addExplosion(x,y);
    	hit.play(0.3);
    }

}
