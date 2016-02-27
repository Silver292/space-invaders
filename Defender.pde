import java.util.Iterator;

class Defender extends Ship {

    int score = 0;
    int lives = 3;

	public Defender (int x, int y, int speed,
	 String image)
	{
		super(x, y, speed, image);
	} // end constructor

	Bullet shoot()
	{
		return new Bullet(x + shipWidth/2, y, 5);
	}

	void addPoints(int points)
	{
		score += points;
	}

    // returns current score
    int getScore() {
        return score;
    }
}// end class
