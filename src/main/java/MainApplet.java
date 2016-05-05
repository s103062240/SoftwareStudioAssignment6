package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import processing.core.PApplet;
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
	
	/**
	 * setup
	 */
	public void setup() {
		size(width, height);
		smooth();
		networks = new Vector<>();
		for (int i = 0; i < 7; ++i) {
			networks.add(new Vector<Character>());
		}
		files = new String[7];
		for (int i = 0; i < files.length; ++i) {
			files[i] = pathPrefix + "starwars-episode-" + (i + 1) + "-interactions.json";
		}
		loadData();
	}

	/**
	 * draw
	 */
	public void draw() {

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
				int x = (j / 4) * 20 + 10;
				int y = (j % 4) * 20 + 10;
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
