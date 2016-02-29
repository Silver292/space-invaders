package SpaceInvaders;

import processing.core.PApplet;

public class EnemyBullet extends Bullet {

	public EnemyBullet(int x, int y, int speed, PApplet p) {
        super(x, y, speed, p);
        bulletType = ENEMY;
        colour = parent.color(238, 0, 0);
    }

    public void move() {
        y += speed;
    }

}