class EnemyBullet extends Bullet {

    EnemyBullet(int x, int y, int speed) {
        super(x, y, speed);
        bulletType = ENEMY;
    }

    void move() {
        y += speed;
    }

}