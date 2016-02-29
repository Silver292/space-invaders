package SpaceInvaders;
import processing.core.PApplet;
import processing.core.PImage;

public class Shield extends Ship{
	
	PImage spriteHalfDamage, spriteFullDamage;
	int health = 10;
	
	public Shield(int x, int y, int speed, String image, PApplet p,
			String sprite2, String sprite3) {
		super(x, y, speed, image, p);
		spriteHalfDamage = parent.loadImage(sprite2);
		spriteFullDamage = parent.loadImage(sprite3);
	}
	
}
