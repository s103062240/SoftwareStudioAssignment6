package main.java;

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
	
	public Network(PApplet parent, Vector<Vector<Character>> networks) {
		this.parent = parent;
		this.networks = networks;
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
	
	public void addSize() {
		this.size++;
	}
	
	public void substractSize() {
		this.size--;
	}
	
}
