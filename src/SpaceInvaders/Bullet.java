package SpaceInvaders;
import processing.core.PApplet;

public class Bullet extends GameObject{
	int colour;
	boolean inGame = true;
	int bulletType;

	final int ENEMY = 0;
	final int PLAYER = 1;

	Bullet(int x, int y, int speed, PApplet p) {
		super(x, y, speed, p);
		colour = parent.color(26, 100, 255);
		bulletType = PLAYER;
		width = 5;
		height = 10;
	} // end constructor

	// bullet movement
	void move() {
		y -= speed;
	} // end move

	void render() {
		parent.stroke(0);
		parent.fill(colour);
		parent.rect(x, y, width, height);
	} // end render

	// returns true if bullet is on screen false if not
	boolean onScreen(){
		// check for the bottom of the bullet
		return (x + height >= 0 || x < parent.height);
	}// end onScreen

	// returns bulletType
	int getBulletType(){
		return bulletType;
	}
}
