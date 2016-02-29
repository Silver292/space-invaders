package SpaceInvaders;
import processing.core.PApplet;

public class GameObject  {
	protected int x, y, speed;
	protected int width;
	protected int height;
	protected boolean destroyed = false;
	protected PApplet parent;

	public GameObject (int x, int y, int speed, PApplet p) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		parent = p;
	}

	public void update() {
		// check object is still in play
		if(destroyed){
			return;
		}
		move();
		render();
	} // end update()

	// Checks for collisions with ships,
	// returns true if collision has occurred
	boolean hasCollided(Ship ship) {
	    return (x <= ship.x + ship.width && x + width >= ship.x &&
	            y <= ship.y + ship.height && y + height >= ship.y);
	}// end hasCollided
	
	public void destroy() {
		destroyed = true;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public void move(){};

	public void render(){};
}
