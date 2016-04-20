package SpaceInvaders;

import kuusisto.tinysound.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Ship extends GameObject {
	protected int xDirection;
	protected PImage sprite;
	protected Sound shoot, hit;

    public Ship (int x, int y, int speed, String image, PApplet p) {
        super(x, y, speed, p);
        shoot = TinySound.loadSound("enemyShoot.wav");
        hit = TinySound.loadSound("enemyHit.wav");
        
        sprite = parent.loadImage(image);

        width = sprite.width;
        height = sprite.height;
    } // end constructor

	// checks the next X location and returns true
	// if within bounds else returns false
    public boolean inXBounds(int nextX) {
		return (nextX > 0 && nextX + width < parent.width);
	} // end inXBounds()


	// returns the next x coord
    public int getNextX() {
		return x + (xDirection * speed);
	}

	// returns true if the next x location will hit the edge of the screen
    public boolean hitsEdge() {
		return !inXBounds(getNextX());
	}

    // moves the ship
    public void move() {
		// get next location
		int nextX = getNextX();

		// check if in bounds
		if(inXBounds(nextX)) {
			x = nextX;
		}
	} // end move()

    // draws the sprite to the screen
    public void render() {
		parent.image(sprite, x, y);
	} // end render
    
    // unloads sound files from memory
	protected void unloadSounds() {
		shoot.unload();
		hit.unload();
	}
}