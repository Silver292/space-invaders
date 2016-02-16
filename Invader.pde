class Invader extends Ship {

	int moveCounter = 0;

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
		// move down
		// change direction

		// move x

/*        // patterened movement
		if (moveCounter > 15) {
			moveY();
		} else {
			moveX();
		}

		moveCounter++;

        // change direction after amount
		if (moveCounter >=30) {
			moveCounter = 0;
			xDirection = -xDirection;
		}*/

	}

	void moveX() {
		int nextX = x + (xDirection * speed);

		// Stop on boundary
		if (!inXBounds(nextX)) {
			nextX = x;
		}
		x = nextX;
	} // end move

	// TODO: look at this
	void moveY() {
		int nextY = y + shipHeight;
		y = nextY;
	}

}
