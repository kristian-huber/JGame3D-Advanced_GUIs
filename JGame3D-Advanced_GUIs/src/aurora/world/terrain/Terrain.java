package aurora.world.terrain;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import aurora.util.Calculator;
import aurora.util.ModelManager;
import aurora.util.resources.TerrainTexturePack;

public class Terrain {

	// If your changing this make sure to update the shader too
	public static final int VERTEX_COUNT = 64;
	public static final int MAX_HEIGHT = 40;
	public static final int SIZE = 125;

	public Vector3f[][] paint = new Vector3f[VERTEX_COUNT][VERTEX_COUNT];
	public float[][] heights = new float[VERTEX_COUNT][VERTEX_COUNT];

	private TerrainTexturePack texturePack;
	private float x, z;

	public Terrain(int gridX, int gridZ, TerrainTexturePack texturePack) {

		this.texturePack = texturePack;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;

		for (int i = 0; i < VERTEX_COUNT; i++) {

			for (int j = 0; j < VERTEX_COUNT; j++) {

				paint[i][j] = new Vector3f(0, 0, 0);
			}
		}

		for (int i = 0; i < Terrain.VERTEX_COUNT - 1; i++) {
			int j = Terrain.VERTEX_COUNT - 1;

			heights[0][i] = 0;
			heights[i][0] = 0;
			heights[j][i] = 0;
			heights[i][j] = 0;
		}

		generateTerrain();
	}

	private Vector3f calculateNormal(int x, int z) {

		float heightL = getHeight(x - 1, z);
		float heightR = getHeight(x + 1, z);
		float heightD = getHeight(x, z - 1);
		float heightU = getHeight(x, z + 1);

		Vector3f normal = new Vector3f(heightL - heightR, 2F, heightD - heightU);
		normal.normalise();

		return normal;
	}

	public float getHeightOfTerrain(float wx, float wz) {
		float terrainX;
		float terrainZ;

		terrainX = wx - this.x;
		terrainZ = wz - this.z;

		float gridSquareSize = SIZE / ((float) heights.length - 1);

		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);

		if (gridX >= heights.length - 1 || gridZ >= heights.length - 1
				|| gridX < 0 || gridZ < 0) {
			return 0;
		}

		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;

		if (xCoord <= (1 - zCoord)) {

			answer = Calculator
					.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0),
							new Vector3f(1, heights[gridX + 1][gridZ], 0),
							new Vector3f(0, heights[gridX][gridZ + 1], 1),
							new Vector2f(xCoord, zCoord));
		} else {

			answer = Calculator
					.barryCentric(
							new Vector3f(1, heights[gridX + 1][gridZ], 0),
							new Vector3f(1, heights[gridX + 1][gridZ + 1], 1),
							new Vector3f(0, heights[gridX][gridZ + 1], 1),
							new Vector2f(xCoord, zCoord));
		}

		return answer;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}

	public void setTexturePack(TerrainTexturePack texturePack) {
		this.texturePack = texturePack;
	}

	public void generateTerrain() {

		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT * 1)];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int vertexPointer = 0;

		for (int i = 0; i < VERTEX_COUNT; i++) {

			for (int j = 0; j < VERTEX_COUNT; j++) {

				float height = getHeight(j, i);

				// Calculates Vertices
				vertices[vertexPointer * 3] = (float) j
						/ ((float) VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer * 3 + 1] = height;
				vertices[vertexPointer * 3 + 2] = (float) i
						/ ((float) VERTEX_COUNT - 1) * SIZE;

				// Calculates Normals
				Vector3f normal = calculateNormal(j, i);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;

				// Textures Coordinates
				textureCoords[vertexPointer * 2] = (float) j
						/ ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i
						/ ((float) VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}

		int pointer = 0;

		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {

			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {

				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;

				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}

		ModelManager.loadToVao("terrain(" + x + "," + z + ")", vertices,
				textureCoords, normals, indices);
	}

	private float getHeight(int x, int z) {

		if (x < 0 || x >= VERTEX_COUNT || z < 0 || z >= VERTEX_COUNT) {
			return 0;
		}

		return heights[x][z];
	}
}
