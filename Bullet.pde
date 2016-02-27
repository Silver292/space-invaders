class Bullet extends GameObject{
	color colour = #1A64FF;
	boolean inGame = true;
	int bulletWidth = 5;
	int bulletHeight = 10;
	int bulletType;

	final int ENEMY = 0;
	final int PLAYER = 1;

	Bullet(int x, int y, int speed, int type) {
		super(x, y, speed);
		bulletType = type;
	} // end constructor

	// bullet movement
	void move() {

		if (bulletType == ENEMY){
			y += speed;
			return;
		}

		y -= speed;
	} // end move

	void render() {
		stroke(0);
		fill(colour);
		rect(x, y, bulletWidth, bulletHeight);
	} // end render

	boolean hasCollided(Ship ship)
	{
		int rightEdge = x + bulletWidth;

		if( (rightEdge >= ship.x && this.x <= ship.x + ship.shipWidth) &&
			(this.y >= ship.y &&  this.y <= ship.y + ship.shipHeight)){

			return true;
		} // end if

		return false;
	}// end hasCollided

	// returns true if bullet is on screen false if not
	boolean onScreen(){
		// check for the bottom of the bullet
		if (x + bulletHeight <= 0) {
			return false;
		}

		return true;
	}// end onScreen

	// returns bulletType
	int getBulletType(){
		return bulletType;
	}
}
