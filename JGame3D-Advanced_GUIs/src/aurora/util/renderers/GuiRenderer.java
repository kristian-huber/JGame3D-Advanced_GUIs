package aurora.util.renderers;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import aurora.util.Calculator;
import aurora.util.ModelManager;
import aurora.util.TextureManager;
import aurora.util.resources.RawModel;
import aurora.util.shaders.GuiShader;
import aurora.world.gui.Gui;

public class GuiRenderer {
	private static HashMap<String, Gui> guis = new HashMap<String, Gui>();
	
	private static final RawModel rectangle = ModelManager.getModel("rectangle");
	private static final GuiShader shader = new GuiShader();

	/* Renders the GUIs */
	public static void render() {

		shader.start();

		// Starts the Conditions
		GL30.glBindVertexArray(rectangle.getVaoID());
		GL20.glEnableVertexAttribArray(0);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		
		// Actually Draws the Textures
		for (Gui gui : order()) {
			
			if(gui.isVisible()){
				gui.update();
				
				if(gui.hasPicture()){
					GL13.glActiveTexture(GL13.GL_TEXTURE0);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, TextureManager.getTextureID(gui.getID()));
				}
				
				Matrix4f matrix = Calculator.createTransformationMatrix(
						gui.getPosition(), gui.getScale());

				shader.loadTransformation(matrix);
				shader.loadColor(gui.getColor());
				shader.loadIsPicture(gui.hasPicture());
				
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, rectangle.getVertexCount());
			}
		}
		
		// Disables Special Conditions
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);

		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);

		shader.stop();
	}
	
	public static ArrayList<Gui> order(){
		ArrayList<Gui> g = new ArrayList<Gui>();
		
		for(int i = 0; i < 3; i++){
			
			for(Gui gui : guis.values()){
				
				if(gui.getLayer() == i){
					g.add(gui);
				}
			}
		}
		
		return g;
	}
	
	/* Removes all the Guis */
	public static void clearList() {

		guis.clear();
	}

	/* Removes a Gui from the Render List */
	public static void removeGui(int g) {

		guis.remove(g);
	}

	/* Adds a Gui to the Render List */
	public static void addGui(String value, Gui g) {

		guis.put(value, g);
	}

	/* Gets the Last Index */
	public static int getLastIndex() {

		return guis.size() - 1;
	}

	/* Cleans Up the Shader */
	public static void cleanUp() {

		shader.cleanUp();
	}
}
