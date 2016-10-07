package aurora.util.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

public class GuiShader extends BaseShader {

	private static final String FRAGMENT_FILE = "GuiFragmentShader.txt";
	private static final String VERTEX_FILE = "GuiVertexShader.txt";

	private int loc_transformMat;
	private int loc_color;
	private int loc_load;
	
	/* Constructor Method */
	public GuiShader() {

		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	/* Loads the Transformation Matrix */
	public void loadTransformation(Matrix4f matrix) {

		super.loadMatrix(loc_transformMat, matrix);
	}

	/* Gets the Location of the Transformation Matrix */
	protected void getAllUniformLocations() {

		loc_transformMat = super
				.getUniformLocation("transformationMatrix");
		loc_color = super.getUniformLocation("guiColor");
		loc_load = super.getUniformLocation("isPicture");
	}
	
	public void loadIsPicture(boolean b){
		
		super.loadBoolean(loc_load, b);
	}
	
	public void loadColor(Vector4f color){
		
		super.loadVector(loc_color, color);
	}

	/* Writes the Position Attribute to the Shader */
	protected void bindAttributes() {

		super.bindAttribute(0, "position");
	}
}
