class Ship extends GameObject {
	int xDirection;
	int shipWidth = 25;
	int shipHeight = 25;
	// TODO: Remove once images are in
	color colour;

	public Ship (int x, int y, int speed,
			color colour) {
		super(x, y, speed);
		this.colour = colour;
	} // end constructor

	void update() {
		move();
		render();
	} // end update()

	// checks the next X location and returns true
	// if within bounds else returns false
	boolean inXBounds(int nextX) {
		// TODO: Will need updating with image sizes
		return (nextX > 0 && nextX + 50 < width);
	} // end inXBounds()

	void move() {
		// get next location
		int nextX = x + (xDirection * speed);

		// check if in bounds
		if(inXBounds(nextX)) {
			x = nextX;
		}
	} // end move()

	void render() {
		stroke(0);
		fill(colour);
		rect(x, y, shipWidth, shipHeight);
	} // end render
}