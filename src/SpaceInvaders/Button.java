package SpaceInvaders;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class Button {

	private String buttonText;
	private int x;
	private int y;
	private int buttonWidth;
	private int buttonHeight;
	private int buttonColour;
	private int rounding = 0;
	private float leftEdge, rightEdge, topEdge, bottomEdge;
	private PFont font;
	
	PApplet parent;

	public Button (String text, int x, int y, int bWidth, int bHeight, 
			int colour, PFont font, PApplet p) {
		buttonText   = text;
		this.x       = x;
		this.y       = y;
		buttonWidth  = bWidth;
		buttonHeight = bHeight;
		buttonColour = colour;
		this.font = font;
		
		parent = p;
		
		setEdges();
	}

	public Button (String text, int x, int y, int bWidth, int bHeight, 
			int colour, PFont font, int rounding, PApplet p) {
		buttonText 	  = text;
		this.x        = x;
		this.y        = y;
		buttonWidth   = bWidth;
		buttonHeight  = bHeight;
		buttonColour  = colour;
		this.rounding = rounding;
		this.font = font;

		parent = p;
		
		setEdges();
	}

	// Draws the button, also changes colour on mouse over
	public void render() {

		int fillColour;

		// lighten colour on mouse over
		if(overButton(parent.mouseX, parent.mouseY)){
			fillColour = buttonColour + 50;
		} else {
			fillColour = buttonColour;
		}

		// Button
		parent.rectMode(PConstants.CENTER);
		parent.fill(fillColour);
		parent.rect(x, y, buttonWidth, buttonHeight, rounding);

		// Button text
		parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.textFont(font);
		parent.fill(0);
		parent.text(buttonText, x, y);

	}; // end render

	// Return true if x,y passed are in the button
	// Returns false if otherwise
	public boolean overButton(int x, int y) {
		return ((x >= leftEdge && x <= rightEdge) &&
				(y >= topEdge && y <= bottomEdge));
	} // end overButton

	// Returns true if mouse is over the button,
	// false if not.
	public boolean mouseOver() {
		return overButton(parent.mouseX, parent.mouseY);
	}


	// Set the edges of the button
	public void setEdges() {
		// Temp variables for calculation
		float halfWidth = buttonWidth / 2;
		float halfHeight = buttonHeight / 2;

		leftEdge   = x - halfWidth;
		rightEdge  = x + halfWidth;
		topEdge    = y - halfHeight;
		bottomEdge = y + halfHeight;
	} // end setEdges
}