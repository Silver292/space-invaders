package SpaceInvaders;

import processing.core.PApplet;

public class ShieldGroup {
    private Shield[] shieldArray;
    private PApplet parent;
    
	public ShieldGroup(int shieldAmount, PApplet p) {
		parent = p;
		shieldArray = new Shield[shieldAmount];
	}

	// initialises shields
    public void init() {
		
    	int xPos = 40;
    	int yPos = 480;
    	
    	// fill shieldArray
    	for (int i = 0; i < shieldArray.length; i++) {
			shieldArray[i] = new Shield(xPos , yPos, 0, "shield-green.png", parent, "shield-amber.png", "shield-red.png");
			
			xPos += parent.width/4;
		} // end for
	}
    
    // updates shields
    public void update() {
		for (int i = 0; i < shieldArray.length; i++) {
			// skip destroyed shields
			if (shieldArray[i] == null) {
				continue;
			}
					
			shieldArray[i].update();
			
			// remove destroyed shields
			if (shieldArray[i].isDestroyed()) {
				shieldArray[i] = null;
			}
		} // end for
	}
    
    // returns true if a shield has been hit
    public boolean hit(Bullet bullet) {
    	
        // check for any bullets hitting shields
        for (int i = 0; i < shieldArray.length; i++) {
        	// skip destroyed shields
        	if(shieldArray[i] == null) {
        		continue;
        	}
        	
        	// handle bullet shield collision
    		if(bullet.hasCollided(shieldArray[i])) {
    			shieldArray[i].reduceHealth();
    			return true;
    		}
    	}
        
        return false;
    }

}
