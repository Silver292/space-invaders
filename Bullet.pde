class Bullet extends GameObject{
	color colour = #1A64FF;
	boolean inGame = true;
	int bulletWidth = 5;
	int bulletHeight = 10;

	Bullet(int x, int y, int speed) {
		super(x, y, speed);
	} // end constructor

	// bullet movement
	void move() {
		y -= speed;
	} // end move

	void render() {
		stroke(0);
		fill(colour);
		rect(x, y, bulletWidth, bulletHeight);
	} // end render

	boolean hasCollided(Invader enemy)
	{
		if((this.x >= enemy.x && this.x <= enemy.x + enemy.shipWidth) &&
			(this.y >= enemy.y &&  this.y <= enemy.y + enemy.shipHeight)){
			println("Hit!"); //TODO: Debug code to be removec
			println("SpaceInvaders.enemies: "+SpaceInvaders.enemies);
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
}
