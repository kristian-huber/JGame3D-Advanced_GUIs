package aurora.util.shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import aurora.util.Calculator;
import aurora.world.sfx.Light;
import aurora.world.terrain.Terrain;

public class TerrainShader extends BaseShader {

	private static final String FRAGMENT_FILE = "TerrainFragmentShader.txt";
	private static final String VERTEX_FILE = "TerrainVertexShader.txt";

	private static final int MAX_LIGHTS = 4;

	private int loc_transformMat;
	private int loc_projectMat;
	private int loc_lightCol[];
	private int loc_lightPos[];
	private int loc_viewMat;
	private int loc_backTex;
	private int loc_skyCol;
	private int loc_rTex;
	private int loc_gTex;
	private int loc_bTex;
	private int loc_act;
	private int loc_paint[];

	/* Constructor Method */
	public TerrainShader() {

		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	/* Writes Attributes to the Shader */
	protected void bindAttributes() {

		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	/* Gets the Variables' location in the Shader */
	protected void getAllUniformLocations() {

		loc_transformMat = super.getUniformLocation("transformationMatrix");
		loc_projectMat = super.getUniformLocation("projectionMatrix");
		loc_backTex = super.getUniformLocation("backgroundTexture");
		loc_viewMat = super.getUniformLocation("viewMatrix");
		loc_skyCol = super.getUniformLocation("skyColor");
		loc_rTex = super.getUniformLocation("rTexture");
		loc_gTex = super.getUniformLocation("gTexture");
		loc_bTex = super.getUniformLocation("bTexture");
		loc_act = super.getUniformLocation("active");

		loc_lightCol = new int[MAX_LIGHTS];
		loc_lightPos = new int[MAX_LIGHTS];

		for (int i = 0; i < MAX_LIGHTS; i++) {

			loc_lightCol[i] = super.getUniformLocation("lightColor[" + i + "]");
			loc_lightPos[i] = super.getUniformLocation("lightPosition[" + i
					+ "]");
		}
		
		loc_paint = new int[Terrain.VERTEX_COUNT * Terrain.VERTEX_COUNT];

		for (int i = 0; i < loc_paint.length; i++) {

			loc_paint[i] = super.getUniformLocation("paint[" + i + "]");
		}
	}

	/* Sets the Textures for each Number */
	public void connectTextureUnits(int activeTextures) {

		super.loadInt(loc_backTex, 0);

		if (activeTextures > 1)
			super.loadInt(loc_rTex, 1);

		if (activeTextures > 2)
			super.loadInt(loc_gTex, 2);

		if (activeTextures > 3)
			super.loadInt(loc_bTex, 3);

		super.loadInt(loc_act, activeTextures);
	}

	/* Loads the Transformation Matrix to the Shader */
	public void loadTransformationMatrix(Matrix4f matrix) {

		super.loadMatrix(loc_transformMat, matrix);
	}

	/* Loads the Projection Matrix to the Shader */
	public void loadProjectionMatrix(Matrix4f matrix) {

		super.loadMatrix(loc_projectMat, matrix);
	}

	/* Loads the View Matrix to the Shader */
	public void loadViewMatrix() {

		Matrix4f viewMatrix = Calculator.createViewMatrix();
		super.loadMatrix(loc_viewMat, viewMatrix);
	}

	/* Loads the Sky Color to the Shader */
	public void loadSkyColor(float r, float g, float b) {

		super.loadVector(loc_skyCol, new Vector3f(r, g, b));
	}

	public void loadPaint(Vector3f[][] paint) {
		
		for(int i = 0; i < Terrain.VERTEX_COUNT; i++){
			
			for(int j = 0; j < Terrain.VERTEX_COUNT; j++){
				
				super.loadVector(loc_paint[(i * Terrain.VERTEX_COUNT) + j], paint[i][j]);
			}
		}
	}

	/* Loads the Lights to the Shader */
	public void loadLights(List<Light> lights) {

		// Loops through each one
		for (int i = 0; i < MAX_LIGHTS; i++) {

			if (i < lights.size()) {

				super.loadVector(loc_lightPos[i], lights.get(i).getPosition());
				super.loadVector(loc_lightCol[i], lights.get(i).getColor());

				// Default Light
			} else {

				super.loadVector(loc_lightPos[i], new Vector3f(0, 0, 0));
				super.loadVector(loc_lightCol[i], new Vector3f(0, 0, 0));
			}

		}
	}
}
