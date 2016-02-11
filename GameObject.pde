class GameObject  {
	int x, y, speed;

	public GameObject (int x, int y, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	void update() {
		move();
		render();
	} // end update()

	void move(){};

	void render(){};
}
