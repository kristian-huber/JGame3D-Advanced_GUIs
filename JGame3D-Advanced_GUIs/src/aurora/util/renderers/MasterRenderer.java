package aurora.util.renderers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import aurora.util.AuroraEngine;
import aurora.world.World;

public class MasterRenderer {
	public static Matrix4f projectionMatrix = MasterRenderer
			.createProjectionMatrix();

	/* Initializes the Shaders */
	public static void initialize() {

		EntityRenderer.loadProjectionMatrix();
		TerrainRenderer.loadProjectionMatrix();
		SkyboxRenderer.loadProjectionMatrix();
	}

	/* Enables Culling */
	public static void enableCulling() {

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	/* Disables Culling */
	public static void disableCulling() {

		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	public static void enablePolygonMode(boolean mode) {

		if (mode) {

			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		} else {

			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
	}

	/* Renders with all of the Renderers */
	public static void render() {
		World w = AuroraEngine.world;

		MasterRenderer.prepare();
		
		for (int i = -World.numSec; i <= World.numSec; i++) {
			for (int j = -World.numSec; j <= World.numSec; j++) {

				EntityRenderer.render(w.getSector(i, -j).getEntityList(), w
						.getSector(i, -j).getLights());
				TerrainRenderer.render(w.getSector(i, -j).getTerrain(), w
						.getSector(i, -j).getLights());
			}
		}

		SkyboxRenderer.render();
		GuiRenderer.render();
		TextRenderer.render();
	}

	/* Prepares the Display */
	private static void prepare() {

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	/* Cleans Up Shaders */
	public static void cleanUp() {

		EntityRenderer.cleanUp();
		TerrainRenderer.cleanUp();
		GuiRenderer.cleanUp();
	}

	/* Creates the Projection Matrix */
	private static Matrix4f createProjectionMatrix() {

		float near_plane = 0.1f;
		float far_plane = 1000;
		float FOV = 70;

		float aspectRatio = AuroraEngine.WIDTH / AuroraEngine.HEIGHT;
		float y = (float) (1f / Math.tan(Math.toRadians(FOV / 2f)))
				* aspectRatio;
		float x = y / aspectRatio;
		float f = far_plane - near_plane;

		projectionMatrix = new Matrix4f();

		projectionMatrix.m00 = x;
		projectionMatrix.m11 = y;
		projectionMatrix.m22 = -((far_plane + near_plane) / f);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * near_plane * far_plane) / f);
		projectionMatrix.m33 = 0;

		return projectionMatrix;
	}
}
