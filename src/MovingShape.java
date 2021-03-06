/*
 *	===============================================================================
 *	MovingShape.java : The superclass of all shapes.
 *	A shape has a point (top-left corner).
 *	A shape defines various properties, including selected, colour, width and height.
 *	===============================================================================
 */

import java.awt.*;

public abstract class MovingShape {

	public int marginWidth, marginHeight; // the margin of the animation panel
											// area
	protected Point p; // the top left coner of shapes
	protected int width; // the width of shapes
	protected int height; // the height of shapes
	protected MovingPath path; // the moving path of shapes
	protected boolean selected = false; // draw handles if selected
	protected Color currentFillColor = Color.green;
	protected Color currentBorderColor = Color.black;

	/**
	 * constuctor to create a shape with default values
	 */
	public MovingShape() {
		this(0, 0, 20, 20, 500, 500, 0, Color.green, Color.black); // the default properties
	}

	/**
	 * constuctor to create a shape
	 * 
	 * @param x
	 *            the x-coordinate of the new shape
	 * @param y
	 *            the y-coordinate of the new shape
	 * @param w
	 *            the width of the new shape
	 * @param h
	 *            the height of the new shape
	 * @param mw
	 *            the margin width of the animation panel
	 * @param mh
	 *            the margin height of the animation panel
	 * @param typeOfPath
	 *            the path of the new shape
	 */
	public MovingShape(int x, int y, int w, int h, int mw, int mh, int pathType, Color fillColor, Color borderColor) {
		p = new Point(x, y);
		marginWidth = mw;
		marginHeight = mh;
		width = w;
		height = h;
//		System.out.println("in MovingShape: this.currentFillColor: " + fillColor.toString());
		setCurrentFillColor(fillColor);
		setCurrentBorderColor(borderColor);
		setPath(pathType);
	}

	/**
	 * Return the x-coordinate of the shape.
	 * 
	 * @return the x coordinate
	 */
	public int getX() {
		return p.x;
	}

	/**
	 * Return the y-coordinate of the shape.
	 * 
	 * @return the y coordinate
	 */
	public int getY() {
		return p.y;
	}

	/**
	 * Return the selected property of the shape.
	 * 
	 * @return the selected property
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Set the selected property of the shape. When the shape is selected, its
	 * handles are shown.
	 * 
	 * @param s
	 *            the selected value
	 */
	public void setSelected(boolean s) {
		selected = s;
	}

	/**
	 * Set the width of the shape.
	 * 
	 * @param w
	 *            the width value
	 */
	public void setWidth(int w) {
		width = w;
	}

	/**
	 * Set the height of the shape.
	 * 
	 * @param h
	 *            the height value
	 */
	public void setHeight(int h) {
		height = h;
	}

	/**
	 * Return a string representation of the shape, containing the String
	 * representation of each element.
	 */
	public String toString() {
		return "[" + this.getClass().getName() + "," + p.x + "," + p.y + "]";
	}

	/**
	 * Draw the handles of the shape
	 * 
	 * @param g
	 *            the Graphics control
	 */
	public void drawHandles(Graphics g) {
		// if the shape is selected, then draw the handles
		if (isSelected()) {
			g.setColor(Color.black);
			g.fillRect(p.x - 2, p.y - 2, 4, 4);
			g.fillRect(p.x + width - 2, p.y + height - 2, 4, 4);
			g.fillRect(p.x - 2, p.y + height - 2, 4, 4);
			g.fillRect(p.x + width - 2, p.y - 2, 4, 4);
		}
	}

	/**
	 * Reset the margin for the shape
	 * 
	 * @param w
	 *            the margin width
	 * @param h
	 *            the margin height
	 */
	public void setMarginSize(int w, int h) {
		marginWidth = w;
		marginHeight = h;
	}

	/**
	 * abstract contains method Returns whether the point p is inside the shape
	 * or not.
	 * 
	 * @param p
	 *            the mouse point
	 */
	public abstract boolean contains(Point p);

	/**
	 * abstract draw method draw the shape
	 * 
	 * @param g
	 *            the Graphics control
	 */
	public abstract void draw(Graphics g);

	/**
	 * Set the path of the shape.
	 * 
	 * @param pathID
	 *            the integer value of the path MovingPath.BOUNDARY is the
	 *            boundary path MovingPath.FALLING is the falling path
	 */
	public void setPath(int pathID) {
		switch (pathID) {
		case MovingPath.BOUNDARY: {
			path = new BoundaryPath(10, 10);
			break;
		}
		case MovingPath.FALLING: {
			path = new FallingPath();
			break;
		}
		case MovingPath.MYPATH:{
			path = new MyPath();
			break;
		}
		}
	}

	/**
	 * move the shape by the path
	 */
	public void move() {
		path.move();
	}

	// Inner class
	// =====================================================================
	// Inner class

	/*
	 * ==========================================================================
	 * ===== MovingPath : The superclass of all paths. It is an inner class. A
	 * path can change the current position of the shape.
	 * ========================
	 * =======================================================
	 */

	public abstract class MovingPath {
		public static final int BOUNDARY = 0; // The Id of the moving path
		public static final int FALLING = 1; // The Id of the moving path
		public static final int MYPATH = 2; // The Id of the moving path
		protected int deltaX, deltaY; // moving distance

		/**
		 * constructor
		 */
		public MovingPath() {
		}

		/**
		 * abstract move method move the shape according to the path
		 */
		public abstract void move();
	}

	/*
	 * ==========================================================================
	 * ===== FallingPath : A falling path.
	 * ======================================
	 * =========================================
	 */
	public class MyPath extends MovingPath {
		// shhhh, i'm a lovely cat name copy
		private double am = 0, stx = 0, cosDeltax = 0;

		/**
		 * constructor to initialise values for a falling path
		 */
		public MyPath() {
			am = Math.random() * 10; // set amplitude variables
			stx = 0.9; // set step variables
			deltaY = 5;
			cosDeltax = 0;
		}

		/**
		 * move the shape
		 */
		public void move() {
			cosDeltax = cosDeltax + stx;
			p.x = (int) Math.round(p.x + am * Math.cos(cosDeltax));
			p.y = p.y + deltaY;
			if (p.y > marginHeight) // if it reaches the bottom of the frame,
									// start again from the top
				p.y = 0;
		}
	}
	
	/*
	 * ==========================================================================
	 * ===== FallingPath : A falling path.
	 * ======================================
	 * =========================================
	 */
	public class FallingPath extends MovingPath {
		private double am = 0, stx = 0, sinDeltax = 0;

		/**
		 * constructor to initialise values for a falling path
		 */
		public FallingPath() {
			am = Math.random() * 20; // set amplitude variables
			stx = 0.5; // set step variables
			deltaY = 5;
			sinDeltax = 0;
		}

		/**
		 * move the shape
		 */
		public void move() {
			sinDeltax = sinDeltax + stx;
			p.x = (int) Math.round(p.x + am * Math.sin(sinDeltax));
			p.y = p.y + deltaY;
			if (p.y > marginHeight) // if it reaches the bottom of the frame,
									// start again from the top
				p.y = 0;
		}
	}

	/*
	 * ==========================================================================
	 * ===== BoundaryPath : A boundary path which moves the shape around the
	 * boundary of the frame
	 * ====================================================
	 * ===========================
	 */
	public class BoundaryPath extends MovingPath {
		private int direction;

		/**
		 * constructor to initialise values for a boundary path
		 */
		public BoundaryPath(int speedx, int speedy) {
			deltaX = (int) (Math.random() * speedx) + 1;
			deltaY = (int) (Math.random() * speedy) + 1;
			direction = 0;
		}

		/**
		 * move the shape
		 */
		public void move() {
			int h = marginHeight - height;
			int w = marginWidth - width;
			switch (direction) {
			case 0: { // move downwards
				p.y += deltaY;
				if (p.y > h) {
					p.y = h - 1;
					direction = 90;
				}
				break;
			}
			case 90: { // move to the right
				p.x += deltaX;
				if (p.x > w) {
					p.x = w - 1;
					direction = 180;
				}
				break;
			}
			case 180: {
				p.y -= deltaY; // move upwards
				if (p.y < 0) {
					direction = 270;
					p.y = 0;
				}
				break;
			}
			case 270: { // move to the left
				p.x -= deltaX;
				if (p.x < 0) {
					direction = 0;
					p.x = 0;
				}
				break;
			}
			}
		}
	}
	// ========================================================================================

	public Color getCurrentFillColor() {
		return currentFillColor;
	}

	public void setCurrentFillColor(Color color) {
//		System.out.println("changing CurrentFillColor to: " + color.toString());
		this.currentFillColor = color;
	}

	public Color getCurrentBorderColor() {
		return currentBorderColor;
	}

	public void setCurrentBorderColor(Color color) {
		this.currentBorderColor = color;
	}
	
}