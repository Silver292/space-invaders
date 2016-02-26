import java.util.Iterator;

class Defender extends Ship {

	ArrayList<Bullet> bullets;
    Invader[][] enemyArray; // Check all this this is new
    int score = 0;
    int lives = 3;

	public Defender (int x, int y, int speed,
	 String image, Invader[][] enemyArray) //check enemyArray
	{
		super(x, y, speed, image);
		bullets = new ArrayList<Bullet>();
        this.enemyArray = enemyArray;
	} // end constructor

	Bullet shoot()
	{
		return new Bullet(x + shipWidth/2, y, 5, 1);
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
