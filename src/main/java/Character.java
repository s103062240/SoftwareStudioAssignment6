package main.java;

import java.awt.Color;
import java.util.Vector;

import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	private static class Edge {
		
		private Character node;
		
		private int weight;
		
		private Edge(Character node, int weight) {
			this.node = node;
			this.weight = weight;
		}

	}
	
	private Vector<Edge> edges;
	
	private MainApplet parent;
	
	private String name;
	private int color;

	public Character(String name, int color, MainApplet parent) {
		this.name = name;
		this.color = color;
		this.parent = parent;
		edges = new Vector<>();
	}

	public void display() {
		
	}
	
	public void addEdge(Character dst, int w) {
		edges.add(new Edge(dst, w));
	}
	
}
