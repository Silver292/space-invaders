package SpaceInvaders;

import processing.core.PApplet;
import processing.core.PImage;

public class Ship extends GameObject {
	int xDirection;
	int shipWidth = 25;
	int shipHeight = 25;
    PImage sprite;

    public Ship (int x, int y, int speed, String image, PApplet p) {
        super(x, y, speed, p);
        
        sprite = parent.loadImage(image);

        shipWidth = sprite.width;
        shipHeight = sprite.height;
    } // end constructor

	// checks the next X location and returns true
	// if within bounds else returns false
	boolean inXBounds(int nextX) {
		return (nextX > 0 && nextX + shipWidth < parent.width);
	} // end inXBounds()


	// returns the next x coord
	int getNextX() {
		return x + (xDirection * speed);
	}

	// returns true if the next x location will hot the edge of the screen
	boolean hitsEdge(){
		return !inXBounds(getNextX());
	}

	void move() {
		// get next location
		int nextX = getNextX();

		// check if in bounds
		if(inXBounds(nextX)) {
			x = nextX;
		}
	} // end move()

	void render() {
		parent.image(sprite, x, y);
	} // end render
}