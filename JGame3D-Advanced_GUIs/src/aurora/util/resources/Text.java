package aurora.util.resources;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import aurora.util.AuroraEngine;
import aurora.util.Calculator;
import aurora.util.renderers.TextRenderer;

public class Text {
	
	private Vector2f position;
	private Vector3f color;
	private String text;
	public boolean visible = true;

	/* Constructor Method */
	public Text(String text, float x, float y, Vector3f color) {
		
		this.position = Calculator.convertToOpenGLCoords((int)(-AuroraEngine.WIDTH
				/ 2 + (x + 32 - 1)), (int)(AuroraEngine.HEIGHT / 2 + -(y + 32 + 4)));
		this.color = color;
		this.text = text;
		TextRenderer.addText(this);
	}

	public Vector3f getColor(){
		
		return color;
	}
	
	public void setColor(Vector3f color){
		
		this.color = color;
	}
	
	public String getText(){
		
		return text;
	}
	
	public void setText(String text){
		
		this.text = text;
	}
	
	/* Gets the Position On Screen */
	public Vector2f getPosition() {

		return position;
	}

	/* Sets the Position on Screen */
	public void setPosition(Vector2f position) {

		this.position = position;
	}
}
