class Bullet extends GameObject{
  color colour = #1A64FF;
  boolean inGame = true;
  int bulletWidth = 5;
  int bulletHeight = 10;

  Bullet(int x, int y, int speed) {
    super(x, y, speed);
  }

  // bullet movement
  void move() {
    y -= speed;
  }

  void render() {
    stroke(0);
    fill(colour);
    rect(x, y, bulletWidth, bulletHeight);
  }

  boolean hasCollided(Invader enemy)
  {
    if((this.x >= enemy.x && this.x <= enemy.x + enemy.shipWidth) &&
      (this.y >= enemy.y &&  this.y <= enemy.y + enemy.shipHeight)){
     println("Hit!");
     return true;
    }

    return false;
  }// end method

  // returns true if bullet is on screen false if not
  boolean onScreen(){
    // check for the bottom of the bullet
    if (x + bulletHeight <= 0) {
      return false;
    }

    return true;
  }
}
