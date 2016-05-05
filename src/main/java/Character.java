package main.java;

import java.awt.Color;
import java.awt.Dimension;
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
		
	}
	
	/**
	 * add an edge to destination, weight w
	 * @param dst destination character
	 * @param w	weight
	 */
	public void addEdge(Character dst, int w) {
		edges.add(new Edge(dst, w));
	}
	
}
