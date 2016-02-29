package SpaceInvaders;

import processing.core.PApplet;

public class Defender extends Ship {

    private int score = 0;
    private int lives = 3;
    private int shotDelay = 500;
    private int shotTime = 0;

	public Defender (int x, int y, int speed,
	 String image, PApplet p)
	{
		super(x, y, speed, image, p);
	} // end constructor

	public int getXDirection() {
		return xDirection;
	}

	public void setXDirection(int direction) {
		xDirection = direction;
	}

	public boolean canShoot() {
		return (parent.millis() - shotTime) > shotDelay;
	}

	public Bullet shoot()
	{
		shotTime = parent.millis();
		return new Bullet(x + width/2, y, 5, parent);
	}

	public void addPoints(int points)
	{
		score += points;
	}

    // returns current score
	public int getScore() {
        return score;
    }

    // returns lives
	public int getLives() {
        return lives;
    }

    // reduces player lives
	public void getHit() {
        --lives;
    }


}// end class
