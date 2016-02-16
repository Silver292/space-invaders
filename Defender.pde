import java.util.Iterator;

class Defender extends Ship {

	ArrayList<Bullet> bullets;
    Invader[][] enemyArray; // Check all this this is new
    int score = 0;
    int lives = 3;

	public Defender (int x, int y, int speed,
	 color colour, Invader[][] enemyArray) //check enemyArray
	{
		super(x, y, speed, colour);
		bullets = new ArrayList<Bullet>();
        this.enemyArray = enemyArray;

	} // end constructor


	void update(){
		super.update();
		updateBullets();
	}

	void shoot()
	{
		Bullet bullet = new Bullet(x + shipWidth/2, y, 5);
		bullets.add(bullet);
	}


	// updates all bullets the defender has fired
	void updateBullets(){
		// check there are bullets
		if(!bullets.isEmpty()){
			// update all bullets, using iterator to remove collided bullets
			for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();)
			{
				// Get the bullet
				Bullet bullet = iterator.next();
				// iterate over enemies to check collision
				for (int row = 0; row < enemyArray.length; ++row)
				{
					for(int column = 0; column < enemyArray[row].length; ++column)
					{
						// skip dead enemies
						if(enemyArray[row][column] == null){
							continue;
						}

						// check for collision and remove bullet and enemy if there is one
						if(bullet.hasCollided(enemyArray[row][column])){
							iterator.remove();
							enemyArray[row][column] = null;
							SpaceInvaders.enemies--; // TODO: Find a better way to do this
							score += 10;
						}
					} // end inner inner for
				} // end inner for

				// remove bullet if off the screen
				if(!bullet.onScreen()){
					iterator.remove();
				}

				bullet.update();
			}// end bullet for
		} //end if
	} // end method
}// end class
