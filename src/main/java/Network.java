package main.java;

import java.awt.Dimension;
import java.util.Vector;

import processing.core.PApplet;

/**
* This class is used for the visualization of the network.
* Depending on your implementation, you might not need to use this class or create a class on your own.
* I used the class to draw the circle and re-arrange nodes and links.
* You will need to declare other variables.
*/
public class Network {
	
	private PApplet parent;
	
	private int size;
	
	private Vector<Vector<Character>> networks;
	
	private int radius;
	private Dimension center;
	
	public Network(PApplet parent, Vector<Vector<Character>> networks, int radius, Dimension center) {
		this.parent = parent;
		this.networks = networks;
		this.radius = radius;
		this.center = center;
	}

	public void display(int episode) {
		for (Character character : networks.get(episode)) {
			if (character.isInCircle()) {
				character.displayEdge();
			}
		}
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void putIn(Character character) {
		character.setInCircle(true);
		this.size++;
	}
	
	public void takeOut(Character character) {
		character.setInCircle(false);
		character.resetLocation();
		this.size--;
	}
	
}
