package main.java;

import java.awt.Dimension;
import java.util.Vector;

import de.looksgood.ani.Ani;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {

	/**
	 * a class to maintain edges
	 */
	private static class Edge {

		private Character node;
		private int weight;

		/**
		 * constructor
		 * @param node target Character
		 * @param weight edge weight
		 */
		private Edge(Character node, int weight) {
			this.node = node;
			this.weight = weight;
		}

	}

	public static int CIRCLESIZE = 45;

	private Vector<Edge> edges;

	private MainApplet parent;

	private Dimension originalLocation;
	private int currentX, currentY;

	private boolean inCircle;

	private String name;
	private int color;
	Ani resetXAni, resetYAni;

	/**
	 * constructor
	 * @param name character name
	 * @param color display color
	 * @param parent PApplet to draw at
	 */
	public Character(String name, int color, Dimension location, MainApplet parent) {
		this.name = name;
		this.color = color;
		this.parent = parent;
		this.originalLocation = location;
		this.currentX = location.width;
		this.currentY = location.height;
		this.inCircle = false;
		edges = new Vector<>();
		resetXAni = new Ani(this, 0.6f, "currentX", originalLocation.width, Ani.EXPO_OUT);
		resetYAni = new Ani(this, 0.6f, "currentY", originalLocation.height, Ani.EXPO_OUT);

	}

	/**
	 * draw this character
	 */
	public void display() {
		parent.fill(color);
		parent.stroke(color);
		parent.tint(255, 175);
		parent.ellipse(currentX, currentY, CIRCLESIZE, CIRCLESIZE);
	}

	/**
	 * draw link between another characters
	 */
	public void displayEdge() {
		for (Edge item : edges) {
			if (item.node.inCircle) {
				parent.noFill();
				parent.stroke(255, 102, 0);
				parent.strokeWeight(getStrokeWeight(item.weight));
				parent.curve(
							getX() - (MainApplet.getNetworkcenter().width - getX()) * 2,
							getY() - (MainApplet.getNetworkcenter().height - getY()) * 2,
							getX(),
							getY(),
							item.node.getX(),
							item.node.getY(),
							item.node.getX() - (MainApplet.getNetworkcenter().width - item.node.getX()) * 2,
							item.node.getY() - (MainApplet.getNetworkcenter().height - item.node.getY()) * 2
				);
				//parent.point(getX() - (MainApplet.getNetworkcenter().width - getX()) / 2, getY() - (MainApplet.getNetworkcenter().height - getY()) / 2);
			}
		}
	}

	/**
	 * get stroke weight according to weight of edge
	 * @param weight edge weight
	 * @return stroke weight
	 */
	private float getStrokeWeight(int weight) {
	    return 2.0f + weight / 3;
    }

    /**
	 * add an edge to destination, weight w
	 * @param dst destination character
	 * @param w	weight
	 */
	public void addEdge(Character dst, int w) {
		edges.add(new Edge(dst, w));
	}

	/**
	 * return character's name
	 * @return name of character
	 */
	public String getName() {
		return name;
	}

	/**
	 * set character\'s position
	 * @param position position to set
	 */
	public void setLocation(Dimension position) {
		if(resetXAni.isPlaying()) {
			resetXAni.end();
		}
		if(resetYAni.isPlaying()) {
			resetYAni.end();
		}
		currentX = position.width;
		currentY = position.height;
	}

	/**
	 * get current location x-axis
	 * @return x-axis
	 */
	public int getX() {
		return currentX;
	}

	/**
	 * get current location y-axis
	 * @return y-axis
	 */
	public int getY() {
		return currentY;
	}

	/**
	 * reset location, make location to original location
	 */
	public void resetLocation() {
		//currentLocation = originalLocation;
		//Ani.to(this, 2.0f, "currentLocation.width", originalLocation.width);
		//Ani.to(this, 2.0f, "currentLocation.height", originalLocation.height);
		//                    ^^^^^^^^^^^^^^^^^^^^^^
		// Ani.to can't get "currentLocation.width" or "currentLocation.height"

		//Ani.to(this, 2.0f, "color", color + 50);
		resetXAni.setBegin(currentX);
		resetYAni.setBegin(currentY);
		resetXAni.start();
		resetYAni.start();
	}

	/**
	 * check this character is in the middle circle
	 * @return true if in circle
	 */
	public boolean isInCircle() {
		return inCircle;
	}

	/**
	 * set in circle
	 * @param inCircle inCircle to set
	 */
	public void setInCircle(boolean inCircle) {
		this.inCircle = inCircle;
	}

}
