package aurora.util.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import aurora.util.Calculator;
import aurora.util.ModelManager;
import aurora.util.resources.ModelTexture;
import aurora.util.shaders.EntityShader;
import aurora.world.entities.Entity;
import aurora.world.sfx.Light;

/**
 * @Author: Thin Matrix
 * @Description: Renders the Entities
 * 
 */

public class EntityRenderer{

	private static EntityShader shader = new EntityShader();

	/* Loads the ProjectionMatrix to the Shader */
	public static void loadProjectionMatrix() {

		shader.start();

		shader.loadProjectionMatrix(MasterRenderer.projectionMatrix);

		shader.stop();
	}

	/* Renders the Entites */
	public static void render(HashMap<String, List<Entity>> entities,
			ArrayList<Light> lights) {

		shader.start();
		shader.loadSkyColor(1.0F, 1.0F, 1.0F);

		shader.loadLights(lights);
		shader.loadViewMatrix();

		// Goes through Each ID
		for (String ID : entities.keySet()) {

			List<Entity> batch = entities.get(ID);

			EntityRenderer.prepareModel(ID);

			// Goes Through Each Entity
			for (Entity entity : batch) {

				EntityRenderer.prepareTexture(entity);

				EntityRenderer.prepareInstance(entity);

				GL11.glDrawElements(GL11.GL_TRIANGLES, ModelManager
						.getModel(ID).getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}

			// Cleans Up the Model and Texture
			EntityRenderer.unbindTexturedModel();
		}

		shader.stop();
	}

	/* Prepares the Model Vertices and Normals */
	private static void prepareModel(String ID) {

		GL30.glBindVertexArray(ModelManager.getModel(ID).getVaoID());

		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
	}

	/* Prepares the Textures */
	private static void prepareTexture(Entity e) {

		if (e.getTexture().isHasTransparency()) {

			MasterRenderer.disableCulling();
		}

		shader.loadFakeLightingVariable(e.getTexture().isUseFakeLighting());

		GL13.glActiveTexture(GL13.GL_TEXTURE0);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, e.getTexture().getTextureID());
	}

	/* Unbinds the Current Model */
	private static void unbindTexturedModel() {

		MasterRenderer.enableCulling();

		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);

		GL30.glBindVertexArray(0);
	}

	/* Prepares the Transformation Matrix */
	private static void prepareInstance(Entity entity) {

		Matrix4f transformationMatrix = Calculator.createTransformationMatrix(
				entity.position, entity.rotation.x, entity.rotation.y,
				entity.rotation.z, entity.getScale());

		shader.loadTransformationMatrix(transformationMatrix);
		
		ModelTexture t = entity.getTexture();
		shader.loadShineSettings(t.getReflectivity(), t.getShineDamper());
	}

	/* Cleans Up */
	public static void cleanUp() {

		shader.cleanUp();
	}
}
