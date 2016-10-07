package aurora.util.resources;

import aurora.util.TextureManager;

public class TerrainTexturePack {

	private TerrainTexture tex1;
	private TerrainTexture tex2;
	private TerrainTexture tex3;
	private TerrainTexture tex4;

	public int activeTextures = 0;

	public TerrainTexturePack(String tex1) {

		this(tex1, null, null, null);
	}

	public TerrainTexturePack(String tex1, String tex2) {

		this(tex1, tex2, null, null);
	}

	public TerrainTexturePack(String tex1, String tex2, String tex3) {

		this(tex1, tex2, tex3, null);
	}

	public TerrainTexturePack(String tex1, String tex2, String tex3, String tex4) {

		if (tex1 != null) {
			TextureManager.loadLocalTexture(tex1);
			this.tex1 = new TerrainTexture(TextureManager.getTextureID(tex1));
			activeTextures++;
		}
		if (tex2 != null) {
			TextureManager.loadLocalTexture(tex2);
			this.tex2 = new TerrainTexture(TextureManager.getTextureID(tex2));
			activeTextures++;
		}
		if (tex3 != null) {
			TextureManager.loadLocalTexture(tex3);
			this.tex3 = new TerrainTexture(TextureManager.getTextureID(tex3));
			activeTextures++;
		}
		if (tex4 != null) {
			TextureManager.loadLocalTexture(tex4);
			this.tex4 = new TerrainTexture(TextureManager.getTextureID(tex4));
			activeTextures++;
		}
	}

	public void setDefaultTexture(String tex) {

		this.tex1 = new TerrainTexture(TextureManager.getTextureID(tex));
	}

	public void loadTexture(String tex) {

		if (tex2 == null) {

			TextureManager.loadLocalTexture(tex);
			this.tex2 = new TerrainTexture(TextureManager.getTextureID(tex));
			activeTextures++;
		} else if (tex3 == null) {

			TextureManager.loadLocalTexture(tex);
			this.tex3 = new TerrainTexture(TextureManager.getTextureID(tex));
			activeTextures++;
		} else if (tex4 == null) {

			TextureManager.loadLocalTexture(tex);
			this.tex4 = new TerrainTexture(TextureManager.getTextureID(tex));
			activeTextures++;
		} else {

			System.err.println("Multi-Textured Queue Full");
		}
	}

	public TerrainTexture getBackgroundTexture() {
		return tex1;
	}

	public TerrainTexture getrTexture() {
		return tex2;
	}

	public TerrainTexture getgTexture() {
		return tex3;
	}

	public TerrainTexture getbTexture() {
		return tex4;
	}
}
