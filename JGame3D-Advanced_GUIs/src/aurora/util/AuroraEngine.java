package aurora.util;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import aurora.util.renderers.GuiRenderer;
import aurora.util.renderers.MasterRenderer;
import aurora.util.renderers.TextRenderer;
import aurora.world.World;
import aurora.world.entities.Player;
import aurora.world.gui.Gui;
import aurora.world.gui.MainMenu;
import aurora.world.gui.PauseMenu;

/**
 * @Author: Kristian Huber
 * @Description: Main Class for the Aurora Engine
 * 
 */

public class AuroraEngine {
	public static final String TITLE = "Aurora Engine Pre-Alpha";
	public static final int WIDTH = 1360, HEIGHT = 768;
	public static final int MENU = 0, GAME = 1;

	private static long lastFrameTime;
	private static int rendering;
	private static float delta;

	public static PauseMenu pmenu;
	public static MainMenu menu;
	public static Player player;
	public static World world;

	/* Creates the Display */
	public static void createDisplay() {

		// Tries to Make the Window
		try {
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setVSyncEnabled(true);
			Display.setTitle(TITLE);
			Display.sync(60);

			Display.create();

		} catch (LWJGLException e) {

			e.printStackTrace();
		}
	}

	/* Loads the Resources */
	public static void loadDefaults() {

		// Default Resources
		ModelManager.loadToVao("rectangle", new float[] { -1, 1, -1, -1, 1, 1,
				1, -1 }, 2);

		// Setting Variables
		AuroraEngine.rendering = AuroraEngine.MENU;

		TextureManager.loadLocalTexture("Wood_Texture");
		TextureManager.loadLocalTexture("Font");
		
		GuiRenderer.addGui("background", Gui.BACKGROUND);
		Gui.BACKGROUND.setVisible(false);
		Gui.BACKGROUND.setLayer(Gui.BACK);

		InputManager.setMouseUnlock(false);

		// OpenGL Calls
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);

		// Making New Objects
		AuroraEngine.world = new World();
		AuroraEngine.player = new Player(new Vector3f(0, 0, 0));

		MasterRenderer.initialize();
	}

	/* Starts to Render */
	public static void startRendering() {
		AuroraEngine.world.fill();

		// Looping
		while (!Display.isCloseRequested()) {

			// Decides What to Render
			switch (rendering) {

			// At the Main Menu
			case AuroraEngine.MENU:
				GuiRenderer.render();
				TextRenderer.render();
				InputManager.tick();
				
				break;

			// In the Actual Game
			case AuroraEngine.GAME:

				if(!InputManager.gamePaused){
				
					AuroraEngine.world.tick();
					AuroraEngine.player.tick();
				}
				MasterRenderer.render();
				
				InputManager.tick();
				break;
			// If this Happens, Something Went Wrong.
			default:
				AuroraEngine.cleanUp();
			}

			// Paces the Computer
			long currentFrameTime = getCurrentTime();
			AuroraEngine.delta = (currentFrameTime - lastFrameTime) / 1000F;
			AuroraEngine.lastFrameTime = currentFrameTime;

			Display.update();
		}

		AuroraEngine.cleanUp();
		System.exit(0);
	}

	/* Cleans Up Resources */
	public static void cleanUp() {

		TextureManager.cleanUp();
		MasterRenderer.cleanUp();
		ModelManager.cleanUp();

		AL.destroy();
		Display.destroy();
		
		System.exit(0);
	}
	
	/* Gets the Render Mode */
	public static int getRenderMode(){
		
		return rendering;
	}

	/* Sets the Render Mode */
	public static void setRenderMode(int type) {

		if (type == AuroraEngine.MENU) {

			AuroraEngine.menu.showMainMenu();
			AuroraEngine.pmenu.hidePauseMenu();
			InputManager.unpause();
		} else if (type == AuroraEngine.GAME) {

			AuroraEngine.menu.closeMainMenu();
		}

		AuroraEngine.rendering = type;
	}

	/* Gets the System Time */
	private static long getCurrentTime() {

		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	/* Gets the Delta */
	public static float getDelta() {

		return AuroraEngine.delta;
	}
}
