import java.util.Random;

class Invader extends Ship {

    PImage sprite1, sprite2;
    int animCounter = 1;
    int points = 10;
    int bulletTime = 0;
    int bulletTimeMax = 250;
    Random rand;

    public Invader (int x, int y, int points, String image1, String image2)
    {
        super(x, y, 8, image1);

        sprite1 = sprite;
        sprite2 = loadImage(image2);
        xDirection = 1;
        this.points = points;
        rand = new Random();
        bulletTime = rand.nextInt(bulletTimeMax);
    } // end constructor

	public Invader (int x, int y, int speed, int points, String image1, String image2)
	{
		super(x, y, speed, image1);

        sprite1 = sprite;
        sprite2 = loadImage(image2);
		xDirection = 1;
        this.points = points;
        rand = new Random();
        bulletTime = rand.nextInt(bulletTimeMax);
	} // end constructor

	//updates invader and changes direction based on boolean passed
	void update(boolean changeDir){
        // check object is still in play
        if(destroyed){
            return;
        }
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

    Bullet shoot() {
        if(rand.nextDouble() < 0.05 && ++bulletTime >= bulletTimeMax){
            bulletTime = 0;
            return new EnemyBullet(x + shipWidth/2, y, 5);
        } else {
            return null;
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

    // returns the invaders points
    int getPoints() {
        return points;
    }
}
