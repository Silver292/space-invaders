package SpaceInvaders;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;

public class AnimGroup {

	ArrayList<Explosion> animList;
	PApplet parent;
	
	AnimGroup(PApplet p){
		parent = p;
		animList = new ArrayList<Explosion>();
	}
	
	public void addExplosion(int x, int y){
		animList.add(new Explosion(x, y, parent));
	}
	
	public void update() {
        // check there are animations
        if(animList.isEmpty()) {
            return;
        }

        // update all bullets, using iterator to remove collided bullets
        for (Iterator<Explosion> iterator = animList.iterator(); iterator.hasNext();) {
            // Get the bullet
            Explosion e = iterator.next();
            if (e.isDestroyed()){
            	iterator.remove();
            }

            e.update();
        }
	}
	
	
	
}
