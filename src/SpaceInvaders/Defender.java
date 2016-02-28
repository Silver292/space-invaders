package SpaceInvaders;

import processing.core.PApplet;

public class Defender extends Ship {

    int score = 0;
    int lives = 3;

	public Defender (int x, int y, int speed,
	 String image, PApplet p)
	{
		super(x, y, speed, image, p);
	} // end constructor

	Bullet shoot()
	{
		return new Bullet(x + shipWidth/2, y, 5, parent);
	}

	void addPoints(int points)
	{
		score += points;
	}

    // returns current score
    int getScore() {
        return score;
    }

    // returns lives
    int getLives() {
        return lives;
    }

    // reduces player lives
    void getHit() {
        --lives;
    }
}// end class
