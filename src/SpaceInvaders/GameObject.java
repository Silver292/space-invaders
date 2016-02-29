package SpaceInvaders;
import processing.core.PApplet;

public class GameObject  {
	int x, y, speed, width, height;
	boolean destroyed = false;
	PApplet parent;

	public GameObject (int x, int y, int speed, PApplet p) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		parent = p;
	}

	void update() {
		// check object is still in play
		if(destroyed){
			return;
		}
		move();
		render();
	} // end update()

	boolean hasCollided(Ship ship)
	{
	    return (x <= ship.x + ship.width && x + width >= ship.x &&
	            y <= ship.y + ship.height && y + height >= ship.y);
	}// end hasCollided
	
	public void destroy() {
		destroyed = true;
	}
	
	void move(){};

	void render(){};
}
