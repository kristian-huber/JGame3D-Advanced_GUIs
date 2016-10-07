package aurora.util;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import aurora.util.resources.TextureData;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class TextureManager {
	private static HashMap<String, Integer> TEXTUREIDS = new HashMap<String, Integer>();

	/* Registers A Texture Into Memory */
	public static void loadLocalTexture(String fileName) {

		// Tries to Load the Texture
		Texture texture = null;
		try {

			texture = TextureLoader.getTexture("PNG", TextureManager.class
					.getResourceAsStream("/aurora/resources/textures/"
							+ fileName + ".png"));

			// Registers the ID
			int textureID = texture.getTextureID();
			TEXTUREIDS.put(fileName, textureID);

		} catch (Exception e) {

			System.err.println("Could Not Load Texture for " + fileName);
			System.err.println("/aurora/resources/textures/" + fileName
					+ ".png");
		}
	}

	/* Loads a Cube Map, the Six Sides of the Skybox */
	public static int loadCubeMap(String[] files) {

		int texID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);

		for (int i = 0; i < files.length; i++) {

			TextureData data = decodeTextureFile(files[i] + ".png");
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0,
					GL11.GL_RGBA, data.getWidth(), data.getHeight(), 0,
					GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
		}

		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP,
				GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP,
				GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		TEXTUREIDS.put("Cube", texID);

		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		return texID;
	}

	/* Decodes the Textures */
	private static TextureData decodeTextureFile(String fileName) {

		int width = 0;
		int height = 0;
		ByteBuffer buffer = null;

		try {
			InputStream resource = TextureManager.class.getClassLoader().getResourceAsStream(
					"aurora/resources/textures/" + fileName);

			PNGDecoder decoder = new PNGDecoder(resource);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.RGBA);
			buffer.flip();
			resource.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Tried to load texture " + fileName
					+ ", didn't work");
			System.exit(-1);
		}

		return new TextureData(buffer, width, height);
	}

	/* Returns A Texture ID */
	public static int getTextureID(String fileName) {

		return TEXTUREIDS.get(fileName);
	}

	/* Deletes Old Textures */
	public static void cleanUp() {

		for (Integer i : TEXTUREIDS.values()) {

			GL11.glDeleteTextures(i);
		}
	}
}
