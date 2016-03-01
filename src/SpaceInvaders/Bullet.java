package SpaceInvaders;
import processing.core.PApplet;

public class Bullet extends GameObject {
	protected int colour;
	protected BulletType bulletType;

	public Bullet(int x, int y, int speed, PApplet p) {
		super(x, y, speed, p);
		colour = parent.color(26, 100, 255);
		bulletType = BulletType.PLAYER;
		width = 5;
		height = 10;
	} // end constructor

	// bullet movement
	public void move() {
		y -= speed;
	} // end move

	public void render() {
		parent.stroke(0);
		parent.fill(colour);
		parent.rect(x, y, width, height);
	} // end render

	// returns true if bullet is on screen false if not
	boolean onScreen() {
		// check for the bottom of the bullet
		return (x + height >= 0 || x < parent.height);
	}// end onScreen

	// returns bulletType
	public BulletType getBulletType() {
		return bulletType;
	}
}
