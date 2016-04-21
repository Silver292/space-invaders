package SpaceInvaders;
import processing.core.PApplet;
import processing.core.PImage;

public class Explosion extends GameObject{

    private PImage[] imageArray;
	private int imageCount = 0;
    
	public Explosion(int x, int y, PApplet parent) {
		super(x, y, parent);
		
		imageArray = new PImage[3];
		
        imageArray[0] = parent.loadImage("explosion1.png");
        imageArray[1] = parent.loadImage("explosion2.png");
        imageArray[2] = parent.loadImage("explosion3.png");
		
	}

	// update the animation
	public void update() {
		// if all the frames have played destroy
		if (imageCount > 2) {
			destroy();
			return;
		}
		
		render();
	}
	
	public void render() {
		parent.image(imageArray[imageCount], x, y);
		// next image
		imageCount++;
	}

}
