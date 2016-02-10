class Invader extends Ship {

	int moveCounter = 0;

	public Invader (int x, int y, int speed,
		color colour)
	{
		super(x, y, speed, colour);
		xDirection = 1;
	} // end constructor

	void move() {
                // patterened movement
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
		}

		render();
	}

	void moveX() {
		int nextX = x + (xDirection * speed);

		// Stop on boundary
		if (!inXBounds(nextX)) {
			nextX = x;
		}
		x = nextX;
	} // end move

	void moveY() {
		int nextY = y + speed;

		y = nextY;
	}

/*	Old bouncing move
	void move() {
		int nextX = x + (xDirection * speed);

		// Bounce on boundaries
		if (!inXBounds(nextX)) {

			xDirection = -xDirection;
		}

		x = nextX;
	} // end move*/
}
