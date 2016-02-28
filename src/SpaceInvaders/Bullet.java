package SpaceInvaders;
import processing.core.PApplet;

public class Bullet extends GameObject{
	int colour;
	boolean inGame = true;
	int bulletWidth = 5;
	int bulletHeight = 10;
	int bulletType;

	final int ENEMY = 0;
	final int PLAYER = 1;

	Bullet(int x, int y, int speed, PApplet p) {
		super(x, y, speed, p);
		colour = parent.color(26, 100, 255);
		bulletType = PLAYER;
	} // end constructor

	// bullet movement
	void move() {
		y -= speed;
	} // end move

	void render() {
		parent.stroke(0);
		parent.fill(colour);
		parent.rect(x, y, bulletWidth, bulletHeight);
	} // end render

	boolean hasCollided(Ship ship)
	{
	    return (x <= ship.x + ship.shipWidth && x + bulletWidth >= ship.x &&
	            y <= ship.y + ship.shipHeight && y +bulletHeight >= ship.y);
	}// end hasCollided

	// returns true if bullet is on screen false if not
	boolean onScreen(){
		// check for the bottom of the bullet
		return (x + bulletHeight >= 0 || x < parent.height);
	}// end onScreen

	// returns bulletType
	int getBulletType(){
		return bulletType;
	}
}
