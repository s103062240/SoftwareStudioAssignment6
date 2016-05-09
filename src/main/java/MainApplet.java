package main.java;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Vector;

import controlP5.Button;
import controlP5.ControlFont;
import controlP5.ControlP5;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{

	private final static int width = 1200, height = 650;
	private final static int radius = 275;
	private final static Dimension networkCenter = new Dimension(600, 350);

	private final String pathPrefix = "res/";
	private String[] files;

	private Network graph;

	private Vector<Vector<Character>> networks;
	private int episode;

	private ControlP5 cp5;
	private Button bAddAll, bCleanAll;
	Minim minim;
	AudioPlayer song;

	private Character mouseTarget;

	/**
	 * setup
	 */
	@Override
    public void setup() {
		Ani.init(this);

		size(width, height);
		smooth();
		cp5 = new ControlP5(this);
		PFont pFont = createFont(getFont().getName(), 32, true);
		ControlFont cFont = new ControlFont(pFont, 32);
		bAddAll = cp5.addButton("buttonAddAll");
		bAddAll.setLabel("Add All");
		bAddAll.getCaptionLabel().setFont(cFont);
		bAddAll.setColorBackground(color(120,120,120));
		bAddAll.setColorForeground(color(255,140,0));
		bAddAll.setColorActive(color(200,90,0));
		bAddAll.setSize(200, 80);
		bAddAll.setPosition(950, 30);
		bCleanAll = cp5.addButton("buttonCleanAll");
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
		graph = new Network(networks, radius, networkCenter);
		episode = 0;
		loadData();
		
		minim = new Minim(this);
		song = minim.loadFile("bgm.mp3");
		song.play();
		song.loop();
	}

	/**
	 * draw
	 */
	@Override
    public void draw() {
		background(255);
		fill(255);
		stroke(255, 220, 0);
		strokeWeight(5.0f);
		ellipse(networkCenter.width, networkCenter.height, radius*2, radius*2);
		fill(0);
		textSize(40);
		text("Star Wars " + (episode + 1), 480, 10, 300, 100);
		graph.display(episode);
		for (Character character : networks.get(episode)) {
			character.display();
		}
		for (Character character : networks.get(episode)) {
			if (getDistance(character, mouseX, mouseY) <= Character.CIRCLESIZE / 2) {
				fill(0);
				textSize(28);
				text(character.getName(), mouseX + 20, mouseY + 20, 200, 50);
			}
		}
	}

	/**
	 * mouse pressed event, handle character position and buttons
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	    if (bAddAll.isPressed()) {
            graph.addAllCircleMember(episode);
	    }
	    if (bCleanAll.isPressed()) {
            graph.clearAllCircleMember(episode);
	    }
		for (Character character : networks.get(episode)) {
			if (getDistance(character, mouseX, mouseY) <= Character.CIRCLESIZE / 2) {
				mouseTarget = character;
				return;
			}
		}
	}

	/**
	 * mouse released event, handle character position
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (mouseTarget != null) {
			if (getDistanceToCircle(mouseTarget, e.getX(), e.getY()) <= radius) {
				graph.putIn(mouseTarget, episode);
			}
			else {
				graph.takeOut(mouseTarget, episode);
			}
		}
		mouseTarget = null;
	}

	/**
	 * mouse dragged event, handle character position
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (mouseTarget != null) {
			mouseTarget.setLocation(new Dimension(e.getX(), e.getY()));
		}
	}

	/**
	 * key pressed event, handle episode switch
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == PConstants.RIGHT) {
			episode = (episode + 1) % 7;
		}
		else if (e.getKeyCode() == PConstants.LEFT) {
			episode = (episode + 7 - 1) % 7;
		}
		else if (e.getKeyCode() == PConstants.UP) {
			graph.addAllCircleMember(episode);
		}
		else if (e.getKeyCode() == PConstants.DOWN) {
			graph.clearAllCircleMember(episode);
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

	/**
	 * get distance between character and mouse position
	 * @param character character to calculate distance
	 * @param x mouse x-axis
	 * @param y mouse y-axis
	 * @return distance
	 */
	private long getDistance(Character character, int x, int y) {
		int dis = (x - character.getX()) * (x - character.getX()) +
				  (y - character.getY()) * (y - character.getY());
		return Math.round(Math.sqrt(dis));
	}

	/**
	 * get distance between character and the big circle
	 * @param character character to calculate distance
	 * @param x mouse x-axis
	 * @param y mouse y-axis
	 * @return distance
	 */
	private long getDistanceToCircle(Character character, int x, int y) {
		int dis = (x - networkCenter.width) * (x - networkCenter.width) +
				  (y - networkCenter.height) * (y - networkCenter.height);
		return Math.round(Math.sqrt(dis));
	}

	/**
	 * get radius
	 * @return radius
	 */
	public static int getRadius() {
		return radius;
	}

	/**
	 * get network center
	 * @return network center
	 */
	public static Dimension getNetworkcenter() {
		return networkCenter;
	}
}
