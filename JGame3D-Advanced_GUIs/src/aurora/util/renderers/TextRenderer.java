package aurora.util.renderers;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import aurora.util.Calculator;
import aurora.util.ModelManager;
import aurora.util.TextureManager;
import aurora.util.resources.Text;
import aurora.util.shaders.TextShader;

public class TextRenderer {
	private static final String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789:.,/?!%- ";
	private static final TextShader shader = new TextShader();

	private static List<Text> texts = new ArrayList<Text>();

	/* Renders the GUIs */
	public static void render() {

		shader.start();

		// Starts the Conditions
		GL30.glBindVertexArray(ModelManager.getModel("rectangle").getVaoID());
		GL20.glEnableVertexAttribArray(0);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		// Actually Draws the Textures
		for (Text t : texts) {

			if(t.visible){
			
				for (int i = 0; i < t.getText().length(); i++) {
					
					GL13.glActiveTexture(GL13.GL_TEXTURE0);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, TextureManager.getTextureID("Font"));

					Matrix4f matrix = Calculator.createTransformationMatrix(
							new Vector2f(t.getPosition().x + (0.025F * i), t
									.getPosition().y), new Vector2f(0.025F, 0.05F));

					shader.loadTransformation(matrix);
					shader.loadColor(t.getColor());
					
					Vector2f index = new Vector2f(0F, 0F);
					String s = t.getText().substring(i, i + 1);
					
					for(int j = 0; j < charSet.length(); j++){
						
						if(s.equals(charSet.substring(j,  j + 1))){
							
							index = new Vector2f((int)(j % 16), (int)(j / 16));
						}
					}

					shader.loadIndex(index);
					
					GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0,
							ModelManager.getModel("rectangle").getVertexCount());
				}
			}
		}

		// Disables Special Conditions
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);

		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);

		shader.stop();
	}

	/* Removes all the Texts */
	public static void clearList() {

		texts.clear();
	}

	/* Removes a Text from the Render List */
	public static void removeText(int t) {

		texts.remove(t);
	}

	/* Adds a Text to the Render List */
	public static void addText(Text t) {

		texts.add(t);
	}

	/* Gets the Last Index */
	public static int getLastIndex() {

		return texts.size() - 1;
	}

	/* Cleans Up the Shader */
	public static void cleanUp() {

		shader.cleanUp();
	}
}
