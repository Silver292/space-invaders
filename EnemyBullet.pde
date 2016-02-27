class EnemyBullet extends Bullet {

    EnemyBullet(int x, int y, int speed) {
        super(x, y, speed);
        bulletType = ENEMY;
        colour = #EE0000;
    }

    void move() {
        y += speed;
    }

}