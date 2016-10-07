package aurora.util;

import aurora.world.gui.MainMenu;
import aurora.world.gui.PauseMenu;

public class Main {

	public static void main(String[] args) {

		// Sets Up the World
		IOManager.loadAppdata();

		AuroraEngine.createDisplay();
		AuroraEngine.loadDefaults();

		// Loading External Things
		TextureManager.loadLocalTexture("background");
		TextureManager.loadLocalTexture("Button_1024x128");
		TextureManager.loadLocalTexture("Button_512x128");
		TextureManager.loadLocalTexture("Scroll");

		AuroraEngine.pmenu = new PauseMenu();
		AuroraEngine.menu = new MainMenu();

		// Starts the Game
		AuroraEngine.startRendering();
	}
}
