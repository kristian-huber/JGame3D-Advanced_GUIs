package aurora.world.entities;

import org.lwjgl.util.vector.Vector3f;

import aurora.util.AuroraEngine;
import aurora.util.Calculator;
import aurora.util.TextureManager;
import aurora.util.resources.ModelTexture;
import aurora.world.Sector;

public class Entity {

	private ModelTexture texture;

	public Vector3f velocity;
	public Vector3f position;
	public Vector3f rotation;

	private Sector currentSector;

	private float scale;
	private boolean inAir = false;

	private String ID;

	/* Constructor Method */
	public Entity(String ID, ModelTexture texture, Vector3f position,
			float scale) {

		this.currentSector = Calculator.getCurrentSector(position);
		this.velocity = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.position = position;
		this.texture = texture;
		this.scale = scale;
		this.ID = ID;
	}

	/* Constructor Method */
	public Entity(String ID, Vector3f position, float scale) {

		this.texture = new ModelTexture(TextureManager.getTextureID(ID));
		this.currentSector = Calculator.getCurrentSector(position);
		this.velocity = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.position = position;
		this.scale = scale;
		this.ID = ID;
	}

	public Entity(Vector3f position) {

		this.texture = new ModelTexture(
				TextureManager.getTextureID("Wood_Texture"));
		this.currentSector = Calculator.getCurrentSector(position);
		this.velocity = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.position = position;
		this.ID = "Wood_Texture";
		this.scale = 0.0F;
	}

	/* Updates the Entity */
	public void tick() {
		float height = Calculator.getCurrentTerrainHeight(position);
		float tick = AuroraEngine.getDelta();

		if (this.position.y > height + 8) {
			inAir = true;
		}

		if (inAir) {
			this.velocity.y -= 0.8F * tick;
		}

		if (position.y < height + 8) {

			inAir = false;
			position.y = height + 8;
		}

		this.position.x += velocity.x;
		this.position.y += velocity.y;
		this.position.z += velocity.z;
	}

	public boolean isInAir() {

		return inAir;
	}

	public void setInAir() {

		this.inAir = true;
	}

	/* Gets the Current Sector */
	public Sector getCurrentSector() {

		return currentSector;
	}

	/* Gets the Texture */
	public ModelTexture getTexture() {

		return texture;
	}

	/* Sets the Texture */
	public void setTexture(ModelTexture tex) {

		this.texture = tex;
	}

	/* Gets the Scale */
	public float getScale() {

		return scale;
	}

	/* Sets the Scale */
	public void setScale(float scale) {

		this.scale = scale;
	}

	/* Gets the Entity ID */
	public String getID() {

		return ID;
	}
}
