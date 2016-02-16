public class Button {

	String buttonText;
	int x;
	int y;
	int buttonWidth;
	int buttonHeight;
	color buttonColour;
	int rounding = 0;
	float leftEdge, rightEdge, topEdge, bottomEdge;

	public Button (String text, int x, int y, int bWidth, int bHeight, color colour)
	{
		buttonText   = text;
		this.x       = x;
		this.y       = y;
		buttonWidth  = bWidth;
		buttonHeight = bHeight;
		buttonColour = colour;

		setEdges();
	}

	public Button (String text, int x, int y, int bWidth, int bHeight, color colour, int rounding)
	{
		buttonText 	  = text;
		this.x        = x;
		this.y        = y;
		buttonWidth   = bWidth;
		buttonHeight  = bHeight;
		buttonColour  = colour;
		this.rounding = rounding;

		setEdges();
	}

	void render() {

		color fillColour;

		// lighten colour on mouse over
		if(overButton(mouseX, mouseY)){
			fillColour = buttonColour + 50;
		} else {
			fillColour = buttonColour;
		}

		// Button
		rectMode(CENTER);
		fill(fillColour);
		rect(x, y, buttonWidth, buttonHeight, rounding);

		// Button text
		textAlign(CENTER, CENTER);
		textSize(22);
		fill(0);
		text(buttonText, x, y);

	}; // end render

	// Return true if x,y passed are in the button
	// Returns false if otherwise
	boolean overButton(int x, int y)
	{
		return ((x >= leftEdge && x <= rightEdge) &&
				(y >= topEdge && y <= bottomEdge));
	} // end overButton

	// Returns true if mouse is over the button,
	// false if not.
	boolean mouseOver() {
		return overButton(mouseX, mouseY);
	}


	// Set the edges of the button
	void setEdges()
	{
		// Temp variables for calculation
		float halfWidth = buttonWidth / 2;
		float halfHeight = buttonHeight / 2;

		leftEdge   = x - halfWidth;
		rightEdge  = x + halfWidth;
		topEdge    = y - halfHeight;
		bottomEdge = y + halfHeight;
	} // end setEdges
}