package aurora.util;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import aurora.world.Sector;
import aurora.world.entities.Player;
import aurora.world.terrain.Terrain;

public class Calculator {

	/* Converts the Coordinates to OpenGL Coordinates */
	public static Vector2f convertToOpenGLCoords(int posX, int posY) {

		float x = 0, y = 0;

		x = posX * 0.0014641F;
		y = posY * 0.0026041F;

		return new Vector2f(x, y);
	}

	public static Sector getCurrentSector(Vector3f position) {
		float a = position.x / Terrain.SIZE;
		float b = position.z / Terrain.SIZE;

		if (a < 0)
			a--;

		if (b < 0)
			b--;

		return AuroraEngine.world.getSector((int) a, (int) b);
	}

	public static float getCurrentTerrainHeight(Vector3f position) {
		float a = position.x / Terrain.SIZE;
		float b = position.z / Terrain.SIZE;

		if (a < 0)
			a--;

		if (b < 0)
			b--;

		float height = AuroraEngine.world.getSector((int) a, (int) b)
				.getTerrain().getHeightOfTerrain(position.x, position.z);
		return height;
	}

	/* Determines Terrain Collision Detection */
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3,
			Vector2f pos) {

		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x)
				* (p1.z - p3.z);

		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x)
				* (pos.y - p3.z))
				/ det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x)
				* (pos.y - p3.z))
				/ det;
		float l3 = 1.0f - l1 - l2;

		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	/* Determines the Distance Between 2 Points */
	public static float distanceFormula(float x1, float y1, float x2, float y2) {

		float answer = 0;

		float x = (float) Math.pow(x1 - x2, 2);
		float y = (float) Math.pow(y1 - y2, 2);

		answer = (float) Math.sqrt(x + y);

		return answer;
	}

	/* Creates a Transformation Matrix for 2D Objects */
	public static Matrix4f createTransformationMatrix(Vector2f translation,
			Vector2f scale) {

		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();

		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);

		return matrix;
	}

	/* Creates a TransformationMatrix for 3D objects */
	public static Matrix4f createTransformationMatrix(Vector3f translation,
			float rx, float ry, float rz, float scale) {

		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();

		Matrix4f.translate(translation, matrix, matrix);

		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0),
				matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0),
				matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1),
				matrix, matrix);

		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);

		return matrix;
	}

	/* Creates a ViewMatrix for the Scene */
	public static Matrix4f createViewMatrix() {
		Player player = AuroraEngine.player;

		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();

		Matrix4f.rotate((float) Math.toRadians(player.rotation.x),
				new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(player.rotation.y),
				new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(player.rotation.z),
				new Vector3f(0, 0, 1), matrix, matrix);

		Vector3f negativeplayerPos = new Vector3f(-player.position.x,
				-player.position.y, -player.position.z);
		Matrix4f.translate(negativeplayerPos, matrix, matrix);

		return matrix;
	}
}
