package aurora.world.gui;

import org.lwjgl.util.vector.Vector4f;

import aurora.util.InputManager;


public class Button extends Gui {

	public Button(String text, int x, int y, int length, int width) {

		super("Button_512x128", text, x, y, length,
				width);
		
		this.setClickable(true);
	}
	
	@Override
	public void update(){
		
		super.update();
		
		if(!this.area.contains(InputManager.mousePosition)){
			
			this.setColor(new Vector4f(0, 0, 0, 0));
		}
	}
	
	@Override
	public void onHover(){
		
		this.setColor(new Vector4f(0.1F, 0.1F, 0.1F, 0.0F));
	}
}
