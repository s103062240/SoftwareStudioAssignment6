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
	
	private Vector<Character> characters;

	public Network(PApplet parent) {
		this.parent = parent;
		characters = new Vector<>();
	}

	public void display() {
		for (Character character : characters) {
			character.displayEdge();
		}
	}
	
	public void addCharacter(Character character) {
		characters.add(character);
	}
	
	public void removeCharacter(Character character) {
		int index;
		for (index = 0; index < characters.size(); ++index) {
			if (characters.get(index) == character) {
				characters.remove(index);
			}
		}
	}
	
}
