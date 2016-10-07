package aurora.util.shaders;

import org.lwjgl.util.vector.Matrix4f;

import aurora.util.Calculator;

public class SkyboxShader extends BaseShader{

	private static final String VERTEX_FILE = "SkyboxVertexShader.txt";
    private static final String FRAGMENT_FILE = "SkyboxFragmentShader.txt";
     
    private int location_projectionMatrix;
    private int location_viewMatrix;
     
    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }
 
    public void loadViewMatrix(){
        Matrix4f matrix = Calculator.createViewMatrix();
        
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        
        super.loadMatrix(location_viewMatrix, matrix);
    }
     
    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
