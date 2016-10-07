package aurora.util.shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import aurora.util.Calculator;
import aurora.world.sfx.Light;

public class EntityShader extends BaseShader {

	private static final String FRAGMENT_FILE = "EntityFragmentShader.txt";
	private static final String VERTEX_FILE = "EntityVertexShader.txt";
	private static final int MAX_LIGHTS = 4;

	private int loc_transformMat;
	private int loc_lightPos[];
	private int loc_lightCol[];
	private int loc_fakeLight;
	private int loc_projMat;
	private int loc_viewMat;
	private int loc_reflect;
	private int loc_skyCol;
	private int loc_damp;

	/* Constructor Method */
	public EntityShader() {

		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	/* Binds each VBO */
	protected void bindAttributes() {

		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	/* Gets the Shader Variables */
	protected void getAllUniformLocations() {

		loc_transformMat = super.getUniformLocation("transformationMatrix");
		loc_fakeLight = super.getUniformLocation("useFakeLighting");
		loc_projMat = super.getUniformLocation("projectionMatrix");
		loc_viewMat = super.getUniformLocation("viewMatrix");
		loc_skyCol = super.getUniformLocation("skyColor");
		loc_reflect = super.getUniformLocation("reflect");
		loc_damp = super.getUniformLocation("damp");
		
		loc_lightPos = new int[MAX_LIGHTS];
		loc_lightCol = new int[MAX_LIGHTS];

		for (int i = 0; i < MAX_LIGHTS; i++) {

			loc_lightPos[i] = super.getUniformLocation("lightPosition[" + i
					+ "]");

			loc_lightCol[i] = super.getUniformLocation("lightColor[" + i + "]");
		}
	}

	/* Sets the Transformation Matrix in the Shader */
	public void loadTransformationMatrix(Matrix4f matrix) {

		super.loadMatrix(loc_transformMat, matrix);
	}

	/* Sets the Projection Matrix in the Shader */
	public void loadProjectionMatrix(Matrix4f matrix) {

		super.loadMatrix(loc_projMat, matrix);
	}

	/* Sets the View Matrix in the Shader */
	public void loadViewMatrix() {

		Matrix4f viewMatrix = Calculator.createViewMatrix();

		super.loadMatrix(loc_viewMat, viewMatrix);
	}

	/* Tells the Shader to Use Fake Lighting */
	public void loadFakeLightingVariable(boolean useFake) {

		super.loadBoolean(loc_fakeLight, useFake);
	}

	/* Sets the Sky Color in the Sky Color to the Shader */
	public void loadSkyColor(float r, float g, float b) {

		super.loadVector(loc_skyCol, new Vector3f(r, g, b));
	}
	
	/* Loads Specular Light Settings */
	public void loadShineSettings(float reflect, float dampness){
		
		super.loadFloat(loc_damp, dampness);
		super.loadFloat(loc_reflect, reflect);
	}
	
	/* Loads the Light Variables to the Shader */
	public void loadLights(List<Light> lights) {

		for (int i = 0; i < MAX_LIGHTS; i++) {

			if (i < lights.size()) {
				
				super.loadVector(loc_lightPos[i], lights.get(i).getPosition());
				super.loadVector(loc_lightCol[i], lights.get(i).getColor());
			} else {

				super.loadVector(loc_lightPos[i], new Vector3f(0, 0, 0));
				super.loadVector(loc_lightCol[i], new Vector3f(0, 0, 0));
			}
		}
	}
}
