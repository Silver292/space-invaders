import java.util.Iterator;

class Defender extends Ship {

	ArrayList<Bullet> bullets;
    Invader[][] enemyArray; // Check all this this is new

	public Defender (int x, int y, int speed,
	 color colour, Invader[][] enemyArray) //check enemyArray
	{
		super(x, y, speed, colour);
		bullets = new ArrayList<Bullet>();
        this.enemyArray = enemyArray;

	} // end constructor


	void update(){
		super.update();
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
							iterator.remove(); // TODO: find a way to remove bullets from arraylist
							enemyArray[row][column] = null;
							SpaceInvaders.enemies--;
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
	}

	void shoot()
	{
		Bullet bullet = new Bullet(x + 25, y, 5);
		bullets.add(bullet);
	}
}
