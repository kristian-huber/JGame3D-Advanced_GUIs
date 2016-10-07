package aurora.world;

import java.util.HashMap;

import org.lwjgl.util.Point;

import aurora.util.AuroraEngine;
import aurora.util.resources.TerrainTexturePack;
import aurora.world.terrain.Terrain;

public class World {
	private HashMap<Point, Sector> sectors;
	
	public static final int numSec = 2;
	
	/* Constructor Method */
	public World() {
		
		sectors = new HashMap<Point, Sector>();

		for (int i = -numSec; i <= numSec; i++) {
			for (int j = -numSec; j <= numSec; j++) {
				
				sectors.put(new Point(i, -j), new Sector(i, -j));
			}
		}
	}
	
	public void fill(){
		
		for (int i = -World.numSec; i <= World.numSec; i++) {
			for (int j = -World.numSec; j <= World.numSec; j++) {
				Sector s = AuroraEngine.world.getSector(i, -j);
				
				Terrain t = new Terrain(i, -j, new TerrainTexturePack(
						"grass"));
				t.getTexturePack().loadTexture("stone");
				s.setTerrain(t);
			}
		}
	}

	/* Updates the World */
	public void tick() {

		for (int i = -numSec; i <= numSec; i++) {
			for (int j = -numSec; j <= numSec; j++) {
				
				sectors.get(new Point(i, -j)).tick();
			}
		}
	}

	/* Gets the Sector at given Coordinates */
	public Sector getSector(int x, int z) {

		return sectors.get(new Point(x, -z));
	}
}
