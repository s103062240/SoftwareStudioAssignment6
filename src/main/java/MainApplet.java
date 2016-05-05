package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle.Control;

import controlP5.Button;
import controlP5.ControlFont;
import controlP5.ControlP5;

import java.util.Vector;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	
	private final static int width = 1200, height = 650;
	
	private final String pathPrefix = "res/";
	private String[] files;

	private Vector<Vector<Character>> networks;
	private int episode;
	
	private ControlP5 cp5; 	
	
	/**
	 * setup
	 */
	public void setup() {
		size(width, height);
		smooth();
		cp5 = new ControlP5(this);
		PFont pFont = createFont(getFont().getName(), 32, true);
		ControlFont cFont = new ControlFont(pFont, 32);
		Button bAddAll = cp5.addButton("buttonAddAll");
		bAddAll.setLabel("Add All");
		bAddAll.getCaptionLabel().setFont(cFont);
		bAddAll.setColorBackground(color(120,120,120));
		bAddAll.setColorForeground(color(255,140,0));
		bAddAll.setColorActive(color(200,90,0));
		bAddAll.setSize(200, 80);
		bAddAll.setPosition(950, 30);
		
		Button bCleanAll = cp5.addButton("buttonCleanAll");
		bCleanAll.setLabel("Clean All");
		bCleanAll.getCaptionLabel().setFont(cFont);
		bCleanAll.setColorBackground(color(120,120,120));
		bCleanAll.setColorForeground(color(255,140,0));
		bCleanAll.setColorActive(color(200,90,0));
		bCleanAll.setSize(200, 80);
		bCleanAll.setPosition(950, 140);
		networks = new Vector<>();
		for (int i = 0; i < 7; ++i) {
			networks.add(new Vector<Character>());
		}
		files = new String[7];
		for (int i = 0; i < files.length; ++i) {
			files[i] = pathPrefix + "starwars-episode-" + (i + 1) + "-interactions.json";
		}
		episode = 0;
		loadData();
	}

	/**
	 * draw
	 */
	public void draw() {
		background(255);
		fill(255);
		stroke(255, 220, 0);
		strokeWeight(5.0f);
		ellipse(600, 350, 550, 550);
		fill(0);
		textSize(40);
		text("Star Wars " + (episode + 1), 480, 10, 300, 100);
		for (Character character : networks.get(episode)) {
			character.display();
		}
		for (Character character : networks.get(episode)) {
			if (mouseX >= character.getX() - Character.CIRCLESIZE / 2 &&
					mouseX <= character.getX() + Character.CIRCLESIZE / 2 &&
					mouseY >= character.getY() - Character.CIRCLESIZE / 2 &&
					mouseY <= character.getY() + Character.CIRCLESIZE / 2) {
				fill(0);
				textSize(20);
				text(character.getName(), mouseX, mouseY + 20, 200, 100);
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == PConstants.RIGHT) {
			episode = (episode + 1) % 7;
		}
		else if (e.getKeyCode() == PConstants.LEFT) {
			episode = (episode + 7 - 1) % 7;
		}
	}

	/**
	 * load json files
	 */
	private void loadData() {
		for (int i = 0; i < 7; ++i) {
			JSONObject jsonObject = loadJSONObject(files[i]);
			JSONArray nodes = jsonObject.getJSONArray("nodes");
			for (int j = 0; j < nodes.size(); ++j) {
				JSONObject item = nodes.getJSONObject(j);
				String name = item.getString("name");
				int color = unhex(item.getString("colour").substring(1));
				int x = (j % 4) * Character.CIRCLESIZE + Character.CIRCLESIZE + 20 + (j % 4) * 10;
				int y = (j / 4) * Character.CIRCLESIZE + Character.CIRCLESIZE + 20 + (j / 4) * 10;
				networks.get(i).add(new Character(name, color, new Dimension(x, y), this));
			}
			JSONArray links = jsonObject.getJSONArray("links");
			for (int j = 0; j < links.size(); ++j) {
				JSONObject item = links.getJSONObject(j);
				int src = item.getInt("source");
				int dst = item.getInt("target");
				int w = item.getInt("value");
				networks.get(i).get(src).addEdge(networks.get(i).get(dst), w);
			}
		}
	}

}
