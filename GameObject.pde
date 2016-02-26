class GameObject  {
	int x, y, speed;
	boolean destroyed = false;

	public GameObject (int x, int y, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	void update() {
		// check object is still in play
		if(destroyed){
			return;
		}
		move();
		render();
	} // end update()

	void move(){};

	void render(){};
}
