class Invader extends Ship {

    PImage sprite1, sprite2;
    int animCounter = 1;

	public Invader (int x, int y, int speed,
		color colour, String image1, String image2)
	{
		super(x, y, speed, colour, image1);

        sprite1 = sprite;
        sprite2 = loadImage(image2);
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

        if (animCounter >= 1) {
            sprite = sprite2;
            animCounter = 0;
            return;
        }

        sprite = sprite1;
        animCounter++;
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
