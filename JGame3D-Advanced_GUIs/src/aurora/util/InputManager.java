package aurora.util;

import java.awt.Point;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class InputManager {
	public static Point mousePosition = new Point(0, 0);
	public static boolean isRMousePressed = false;
	public static boolean gamePaused = false;
	
	public static int KEY_FORWARD = Keyboard.KEY_W;
	public static int KEY_BACKWARD = Keyboard.KEY_S;
	public static int KEY_LEFT = Keyboard.KEY_A;
	public static int KEY_RIGHT = Keyboard.KEY_D;
	public static int KEY_JUMP = Keyboard.KEY_SPACE;
	public static int KEY_PAUSE = Keyboard.KEY_ESCAPE;
	public static int KEY_INVENTORY = Keyboard.KEY_E;
	public static int KEY_RUN = Keyboard.KEY_LSHIFT;
	
	private static boolean mouseunlock = false;
	private static int count = 0;
	
	/* Updates the Keyboard and Mouse */
	public static void tick() {
		
		InputManager.isRMousePressed = Mouse.isButtonDown(0);
		InputManager.mousePosition = new Point(Mouse.getX(), AuroraEngine.HEIGHT - Mouse.getY());
		
		if(AuroraEngine.getRenderMode() == AuroraEngine.MENU){
		
		}else if(AuroraEngine.getRenderMode() == AuroraEngine.GAME){
		
			if (Keyboard.next() && Keyboard.getEventKey() == InputManager.KEY_PAUSE) {
				
				count++;
				
				if(count == 1){
					AuroraEngine.pmenu.showPauseMenu();
					InputManager.mouseunlock = true;
					InputManager.gamePaused = true;
				}else if(count == 3){
					AuroraEngine.pmenu.hidePauseMenu();
					InputManager.mouseunlock = false;
					InputManager.gamePaused = false;
				}else if(count >= 4){
					count = 0;
				}
			}

			//Sets the Mouse to the Center of the Screen
			if (!InputManager.mouseunlock && Display.isActive()) {
				Mouse.setCursorPosition(AuroraEngine.WIDTH / 2,
						AuroraEngine.HEIGHT / 2);
			}
		}
	}
	
	public static void unpause(){
		AuroraEngine.pmenu.hidePauseMenu();
		InputManager.mouseunlock = false;
		InputManager.gamePaused = false;
		InputManager.count = 4;
	}
	
	public static boolean isMouseUnlocked(){
		
		return InputManager.mouseunlock;
	}
	
	/* Toggles the Mouse Lock */
	public static void setMouseUnlock(boolean arg0) {

		InputManager.mouseunlock = arg0;
	}
}
