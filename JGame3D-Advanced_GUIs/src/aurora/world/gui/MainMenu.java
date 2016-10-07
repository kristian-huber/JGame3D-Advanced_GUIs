package aurora.world.gui;

import org.lwjgl.util.vector.Vector3f;

import aurora.util.AuroraEngine;
import aurora.util.renderers.GuiRenderer;
import aurora.util.renderers.TextRenderer;
import aurora.util.resources.Text;

public class MainMenu {
	public static final Vector3f FOREGROUND_COLOR = new Vector3f(0.381F,
			0.185F, 0.00F);

	private Button achievements, campaign, singleplayer, multiplayer, modding,
			exit;
	private Gui backgroundImage, scroll;
	private Text title;

	public MainMenu() {

		title = new Text("Main Menu: ", AuroraEngine.WIDTH - 375,
				(AuroraEngine.HEIGHT / 2) - 290, FOREGROUND_COLOR);
		TextRenderer.addText(title);

		backgroundImage = new Gui("background", -5, -5, 730, 725);
		GuiRenderer.addGui("background_image", backgroundImage);
		backgroundImage.setLayer(Gui.BACK);

		scroll = new Gui("Scroll", AuroraEngine.WIDTH - 375,
				(AuroraEngine.HEIGHT / 2) - 335, 175, 335);
		GuiRenderer.addGui("background_scroll", scroll);
		scroll.setLayer(Gui.MIDDLE);

		// Campaign Button
		campaign = new Button("Campaign", AuroraEngine.WIDTH - 350, 180, 150,
				35) {
			@Override
			public void onClick() {
				/*
				 * Generate Code
				 */
			}
		};
		GuiRenderer.addGui("campaign_button", campaign);

		// Singleplayer Button
		singleplayer = new Button("Singleplayer", AuroraEngine.WIDTH - 350,
				260, 150, 35) {
			@Override
			public void onClick() {
				AuroraEngine.setRenderMode(AuroraEngine.GAME);
			}
		};
		GuiRenderer.addGui("singleplayer_button", singleplayer);

		// Multiplayer Button
		multiplayer = new Button("Multiplayer", AuroraEngine.WIDTH - 350, 340,
				150, 35) {
			@Override
			public void onClick() {
				/*
				 * Generate Code
				 */
			}
		};
		GuiRenderer.addGui("multiplayer_button", multiplayer);

		// Modding Button
		modding = new Button("Modding", AuroraEngine.WIDTH - 350, 420, 150, 35) {
			@Override
			public void onClick() {
				/*
				 * Generate Code
				 */
			}
		};
		GuiRenderer.addGui("modding_button", modding);

		// achievements Button
		achievements = new Button("Achievements", AuroraEngine.WIDTH - 350,
				500, 150, 35) {
			@Override
			public void onClick() {

			}
		};
		GuiRenderer.addGui("achievements_button", achievements);

		// Exit Button
		exit = new Button("Exit", AuroraEngine.WIDTH - 350, 580, 150, 35) {
			@Override
			public void onClick() {
				AuroraEngine.cleanUp();
			}
		};
		GuiRenderer.addGui("exit_button", exit);
	}

	/* Shows All of the Components */
	public void showMainMenu() {

		backgroundImage.setVisible(true);
		singleplayer.setVisible(true);
		achievements.setVisible(true);
		multiplayer.setVisible(true);
		campaign.setVisible(true);
		modding.setVisible(true);
		scroll.setVisible(true);
		exit.setVisible(true);
		title.visible = true;
	}

	/* Hides All the Components */
	public void closeMainMenu() {

		backgroundImage.setVisible(false);
		singleplayer.setVisible(false);
		achievements.setVisible(false);
		multiplayer.setVisible(false);
		campaign.setVisible(false);
		modding.setVisible(false);
		scroll.setVisible(false);
		exit.setVisible(false);
		title.visible = false;
	}
}
