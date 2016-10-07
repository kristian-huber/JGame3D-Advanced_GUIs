package aurora.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import aurora.world.entities.Entity;
import aurora.world.sfx.Light;
import aurora.world.sfx.ParticleEmitter;
import aurora.world.terrain.Terrain;

public class Sector {

	private HashMap<String, List<Entity>> entities = new HashMap<String, List<Entity>>();
	private ArrayList<Light> lights = new ArrayList<Light>();
	private ArrayList<ParticleEmitter> pe = new ArrayList<ParticleEmitter>();
	private Terrain terrain;

	public Vector2f position;

	public Sector(int x, int y) {

		position = new Vector2f(x, y);
		lights.add(new Light(new Vector3f(100, 100, 100), new Vector3f(1, 1, 1)));
		
		if(x == 0 && y == 0){
		
			pe.add(new ParticleEmitter());
		}
	}

	/* Updates the Sector */
	public void tick() {

		for (String s : entities.keySet()) {

			for (Entity e : entities.get(s)) {

				e.tick();
			}
		}
		
		for(ParticleEmitter p : pe){
			
			p.update();
		}
	}

	public ArrayList<ParticleEmitter> getParticleEmitters(){
		
		return pe;
	}
	
	public void addLight(Light l) {

		lights.add(l);
	}

	public void removeLight(Light l) {

		lights.remove(l);
	}

	public ArrayList<Light> getLights() {

		return lights;
	}

	/* Returns the Terrain */
	public Terrain getTerrain() {

		return terrain;
	}

	/* Sets the Terrain */
	public void setTerrain(Terrain t) {

		this.terrain = t;
	}

	/* Returns the List of Entities in the World */
	public HashMap<String, List<Entity>> getEntityList() {

		return entities;
	}

	/* Adds and Entity to the List to Render */
	public void addEntity(Entity entity) {

		List<Entity> batch = entities.get(entity.getID());

		if (batch != null) {

			batch.add(entity);
		} else {

			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);

			entities.put(entity.getID(), newBatch);
		}
	}
}
