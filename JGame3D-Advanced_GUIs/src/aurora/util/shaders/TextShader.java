package aurora.util.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class TextShader extends BaseShader{
	private static final String FRAGMENT_FILE = "TextFragmentShader.txt";
	private static final String VERTEX_FILE = "TextVertexShader.txt";

	private int location_transformationMatrix;
	private int loc_color;
	private int loc_index;

	/* Constructor Method */
	public TextShader() {

		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	/* Loads the Transformation Matrix */
	public void loadTransformation(Matrix4f matrix) {

		super.loadMatrix(location_transformationMatrix, matrix);
	}

	/* Gets the Location of the Transformation Matrix */
	protected void getAllUniformLocations() {

		location_transformationMatrix = super
				.getUniformLocation("transformationMatrix");
		loc_color = super.getUniformLocation("givenColor");
		loc_index = super.getUniformLocation("index");
	}

	/* Writes the Position Attribute to the Shader */
	protected void bindAttributes() {

		super.bindAttribute(0, "position");
	}
	
	public void loadIndex(Vector2f index){
		
		super.loadVector(loc_index, index);
	}
	
	public void loadColor(Vector3f color){
		
		super.loadVector(loc_color, color);
	}
}
