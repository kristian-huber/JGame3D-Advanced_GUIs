package aurora.util.resources;

public class ModelTexture {

	private boolean useFakeLighting = false;
	private boolean hasTransparency = false;

	private float shineDamper = 0F;
	private float reflectivity = 0F;

	private int textureID;

	/* Constructor Method */
	public ModelTexture(int id) {

		this.textureID = id;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	/* Returns if the Texture Uses Fake Lighting */
	public boolean isUseFakeLighting() {

		return useFakeLighting;
	}

	/* Sets the Use of Fake Lighting */
	public void setUseFakeLighting(boolean useFakeLighting) {

		this.useFakeLighting = useFakeLighting;
	}

	/* Returns if the Texture Has Transparency */
	public boolean isHasTransparency() {

		return hasTransparency;
	}

	/* Sets if the Texture Has Transparency */
	public void setHasTransparency(boolean hasTransparency) {

		this.hasTransparency = hasTransparency;
	}

	/* Returns the Texture ID */
	public int getTextureID() {

		return textureID;
	}
}
