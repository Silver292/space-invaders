package SpaceInvaders;
import processing.core.PApplet;
import processing.core.PImage;

public class Shield extends Ship{
	
	private PImage spriteHalfDamage, spriteFullDamage;
	private int health = 10;
	
	public Shield(int x, int y, int speed, String image, PApplet p,
			String sprite2, String sprite3) {
		super(x, y, speed, image, p);
		spriteHalfDamage = parent.loadImage(sprite2);
		spriteFullDamage = parent.loadImage(sprite3);
	}
	
	public void update(){
		super.update();
		
		changeState();
		
		if (health < 1) {
			destroy();
		}
	}
	
	public void reduceHealth() {
		health--;
	}
	
	private void changeState(){
		switch(health) {
			case 7:
			case 6:
			case 5:
				sprite = spriteHalfDamage;
				break;
			case 4:
			case 3:
			case 2:
			case 1:
				sprite = spriteFullDamage;
				break;
				
		}
	}
	
}
