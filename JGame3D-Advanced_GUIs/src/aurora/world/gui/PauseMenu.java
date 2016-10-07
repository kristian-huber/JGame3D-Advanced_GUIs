package aurora.world.gui;

import aurora.util.AuroraEngine;
import aurora.util.InputManager;
import aurora.util.renderers.GuiRenderer;
import aurora.util.resources.Text;

public class PauseMenu {
	private Text title;
	private Gui scroll;
	private Button back, menu, settings;

	public PauseMenu() {
		scroll = new Gui("Scroll", AuroraEngine.WIDTH / 2 - 225,
				AuroraEngine.HEIGHT / 2 - 175, 225, 175);
		GuiRenderer.addGui("Scroll2", scroll);
		scroll.setLayer(Gui.MIDDLE);
		scroll.setVisible(false);

		title = new Text("Pause Menu", AuroraEngine.WIDTH / 2 - 125,
				AuroraEngine.HEIGHT / 2 - 150, MainMenu.FOREGROUND_COLOR);
		title.visible = false;
		
		back = new Button("Return to Game", AuroraEngine.WIDTH / 2 - 200, AuroraEngine.HEIGHT / 2 - 75, 200, 35){
			@Override
			public void onClick(){
				InputManager.unpause();
			}
		};
		back.setVisible(false);
		back.setLayer(Gui.FRONT);
		GuiRenderer.addGui("returnB", back);
		
		settings = new Button("Settings", AuroraEngine.WIDTH / 2 - 200, AuroraEngine.HEIGHT / 2, 200, 35){
			@Override
			public void onClick(){
				
			}
		};
		settings.setVisible(false);
		settings.setLayer(Gui.FRONT);
		GuiRenderer.addGui("settings", settings);
		
		menu = new Button("Return to Main Menu", AuroraEngine.WIDTH / 2 - 200, AuroraEngine.HEIGHT / 2 + 75, 200, 35){
			@Override
			public void onClick(){
				AuroraEngine.setRenderMode(AuroraEngine.MENU);
			}
		};
		menu.setVisible(false);
		menu.setLayer(Gui.FRONT);
		GuiRenderer.addGui("menu2", menu);
	}

	public void showPauseMenu() {

		Gui.BACKGROUND.setVisible(true);
		title.visible = true;
		scroll.setVisible(true);
		back.setVisible(true);
		settings.setVisible(true);
		menu.setVisible(true);
	}

	public void hidePauseMenu() {

		Gui.BACKGROUND.setVisible(false);
		title.visible = false;
		scroll.setVisible(false);
		back.setVisible(false);
		settings.setVisible(false);
		menu.setVisible(false);
	}
}
