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
	
	public void putIn(Character character, int episode) {
		character.setInCircle(true);
		this.size++;
		reLocateCircleMember(episode);
	}
	
	public void takeOut(Character character, int episode) {
		character.setInCircle(false);
		character.resetLocation();
		this.size--;
		reLocateCircleMember(episode);
	}
	
	public void reLocateCircleMember(int episode) {
		int count = 0;
		double angle;
		for (Character character : networks.get(episode)) {
			if (character.isInCircle()) {
				angle =  2 * Math.PI * ((double)count / (double)this.size);
				character.setLocation(new Dimension(
							center.width + (int)(radius * Math.cos(angle)), 
							center.height + (int)(radius * Math.sin(angle))
							));
				count++;
			}
		}
	}
	
	public void clearAllCircleMember(int episode) {
		for (Character character : networks.get(episode)) {
			if(character.isInCircle()) {
				character.setInCircle(false);
				character.resetLocation();
			}
		}
		this.size = 0;
	}
	public void addAllCircleMember(int episode) {
		this.size = networks.get(episode).size();
		for(Character character : networks.get(episode)) {
			character.setInCircle(true);
		}
		this.reLocateCircleMember(episode);
	}
}
