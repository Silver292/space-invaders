class Invader extends Ship {

	public Invader (int x, int y, int speed,
		color colour)
	{
		super(x, y, speed, colour);
		xDirection = 1;
	} // end constructor

	//updates invader and changes direction based on boolean passed
	void update(boolean changeDir){
		move(changeDir);
		render();
	}

	void move(boolean changeDir) {
		// Slower movements speed
		if (frameCount % 40 != 0)
			return;

		// if changeDir
		if (changeDir) {
			moveY();
			xDirection = -xDirection;
		} else {
			moveX();
		}

	}

	// moves the invader across the screen
	void moveX() {
		x = x + (xDirection * speed);
	} // end move

	// moves in the invader down the screen
	void moveY() {
		y = y + shipHeight;
	}

}
