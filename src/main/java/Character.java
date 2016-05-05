package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import processing.core.PApplet;

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
	private Dimension currentLocation;
	
	private String name;
	private int color;

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
		this.currentLocation = location;
		edges = new Vector<>();
	}

	public void display() {
		parent.fill(color);
		parent.stroke(color);
		parent.tint(255, 175);
		parent.ellipse(currentLocation.width, currentLocation.height, CIRCLESIZE, CIRCLESIZE);
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
	 * get current location x-axis
	 * @return x-axis
	 */
	public int getX() {
		return currentLocation.width;
	}
	
	/**
	 * get current location y-axis
	 * @return y-axis
	 */
	public int getY() {
		return currentLocation.height;
	}
	
	/**
	 * reset location, make location to original location
	 */
	public void resetLocation() {
		currentLocation = originalLocation;
	}
	
}
