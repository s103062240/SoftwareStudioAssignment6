package main.java;

import java.awt.Dimension;
import java.util.Vector;

/**
* This class is used for the visualization of the network.
* Depending on your implementation, you might not need to use this class or create a class on your own.
* I used the class to draw the circle and re-arrange nodes and links.
* You will need to declare other variables.
*/
public class Network {

	private int size;

	private Vector<Vector<Character>> networks;

	private int radius;
	private Dimension center;

	/**
	 * constructor
	 * @param networks all data of characters
	 * @param radius circle radius
	 * @param center circle center position
	 */
	public Network(Vector<Vector<Character>> networks, int radius, Dimension center) {
		this.networks = networks;
		this.radius = radius;
		this.center = center;
		this.size = 0;
	}

	/**
	 * show edges between characters for all in circle
	 * @param episode episode
	 */
	public void display(int episode) {
		for (Character character : networks.get(episode)) {
			if (character.isInCircle()) {
				character.displayEdge();
			}
		}
	}

	/**
	 * get number of characters in circle
	 * @return number of characters in circle
	 */
	public int getSize() {
		return size;
	}

	/**
	 * set number of characters in circle
	 * @param size number of characters in circle to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * add character in circle
	 * @param character character to add
	 * @param episode episode of character is in
	 */
	public void putIn(Character character, int episode) {
		if(character.isInCircle() == true) {
			reLocateCircleMember(episode);
			return;
		}
		character.setInCircle(true);
		this.size++;
		reLocateCircleMember(episode);
	}

	/**
     * remove character from circle
     * @param character character to remove
     * @param episode episode of character is in
     */
	public void takeOut(Character character, int episode) {
		if(character.isInCircle() == false) {
			character.resetLocation();
			return;
		}
		character.setInCircle(false);
		character.resetLocation();
		this.size--;
		reLocateCircleMember(episode);
	}

	/**
	 * move character to its original location in circle
	 * @param episode episode of character is in
	 */
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

	/**
	 * remove all characters in circle
	 * @param episode episode of characters are in
	 */
	public void clearAllCircleMember(int episode) {
		for (Character character : networks.get(episode)) {
			if(character.isInCircle()) {
				character.setInCircle(false);
				character.resetLocation();
			}
		}
		this.size = 0;
	}

    /**
     * add all characters in circle
     * @param episode episode of characters are in
     */
	public void addAllCircleMember(int episode) {
		this.size = networks.get(episode).size();
		for(Character character : networks.get(episode)) {
			character.setInCircle(true);
		}
		this.reLocateCircleMember(episode);
	}
}
