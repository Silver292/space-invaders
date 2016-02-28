package SpaceInvaders;
import processing.core.PApplet;

public class GameObject  {
	int x, y, speed;
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

	void move(){};

	void render(){};
}
