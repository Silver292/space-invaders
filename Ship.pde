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

	// checks the next X location and returns true
	// if within bounds else returns false
	boolean inXBounds(int nextX) {
		// TODO: Will need updating with image sizes
		return (nextX > 0 && nextX + shipWidth < width);
	} // end inXBounds()


	// returns the next x coord
	int getNextX() {
		return x + (xDirection * speed);
	}

	// returns true if the next x location will hot the edge of the screen
	boolean hitsEdge(){
		return !inXBounds(getNextX());
	}

	void move() {
		// get next location
		int nextX = getNextX();

		// check if in bounds
		if(inXBounds(nextX)) {
			x = nextX;
		}
	} // end move()

	void render() {
		stroke(0);
		fill(colour);
		rectMode(CORNER);
		rect(x, y, shipWidth, shipHeight);
	} // end render
}