package aurora.world.gui;

import java.awt.Rectangle;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import aurora.util.AuroraEngine;
import aurora.util.Calculator;
import aurora.util.InputManager;
import aurora.util.resources.Text;

public class Gui {
	public static Gui BACKGROUND = new Gui(new Vector4f(0, 0, 0, 0.65F), -5, -5,
			AuroraEngine.WIDTH, AuroraEngine.HEIGHT);
	public static final int BACK = 0;
	public static final int MIDDLE = 1;
	public static final int FRONT = 2;

	private boolean clickable;
	private Vector2f position;
	private boolean visible;
	private Vector2f scale;
	private Vector4f color;
	protected Rectangle area;
	private String ID;
	private Text text;
	private boolean hasPicture;
	private int layer = 2;

	/* Constructor Method */
	public Gui(String ID, int x, int y, int length, int width) {

		this.scale = Calculator.convertToOpenGLCoords(length, width);

		this.position = Calculator.convertToOpenGLCoords(-AuroraEngine.WIDTH
				/ 2 + (x + length - 1), AuroraEngine.HEIGHT / 2
				+ -(y + width + 4));

		this.area = new Rectangle(x, y, length * 2, width * 2);

		this.color = new Vector4f(0, 0, 0, 0);
		this.clickable = false;
		this.visible = true;
		this.ID = ID;
		this.hasPicture = true;
	}

	/* Constructor Method */
	public Gui(String ID, String text, int x, int y, int length, int width) {

		this.scale = Calculator.convertToOpenGLCoords(length, width);

		this.position = Calculator.convertToOpenGLCoords(-AuroraEngine.WIDTH
				/ 2 + (x + length - 1), AuroraEngine.HEIGHT / 2
				+ -(y + width + 4));

		this.area = new Rectangle(x, y, length * 2, width * 2);

		this.text = new Text(text, x, y, MainMenu.FOREGROUND_COLOR);
		
		this.color = new Vector4f(0, 0, 0, 0);
		this.clickable = false;
		this.visible = true;
		this.ID = ID;
		this.hasPicture = true;
	}

	/* Main Constructor Method */
	public Gui(Vector4f color, int x, int y, int length, int width) {

		this.scale = Calculator.convertToOpenGLCoords(length, width);

		this.position = Calculator.convertToOpenGLCoords(-AuroraEngine.WIDTH
				/ 2 + (x + length - 1), AuroraEngine.HEIGHT / 2
				+ -(y + width + 4));

		this.area = new Rectangle(x, y, length * 2, width * 2);
		this.clickable = false;
		this.visible = true;
		this.color = color;
		this.hasPicture = false;
	}

	// To Test this, make the mouse get unlocked
	public void update() {

		if (clickable && area.contains(InputManager.mousePosition)
				&& InputManager.isRMousePressed) {

			this.onClick();
		}

		if (area.contains(InputManager.mousePosition)) {

			this.onHover();
		}
	}

	public void setLayer(int layer) {

		this.layer = layer;
	}

	public int getLayer() {

		return layer;
	}

	public void onClick() {

	}

	public void onHover() {

	}

	public void setText(String arg0) {

		this.text.setText(arg0);
	}

	public void setText(String arg0, Vector3f color) {

		this.text.setText(arg0);
		this.text.setColor(color);
	}

	public void setText(Text t) {

		this.text = t;
	}

	public Text getText() {

		return text;
	}

	public void setClickable(boolean arg0) {

		this.clickable = arg0;
	}

	public String getID() {

		return ID;
	}

	public boolean hasPicture() {

		return hasPicture;
	}

	public void setVisible(boolean visible) {

		this.visible = visible;
		if (text != null) {
			text.visible = visible;
		}
	}

	public boolean isVisible() {

		return visible;
	}

	/* Sets the Color of the Gui */
	public void setColor(Vector4f color) {

		this.color = color;
	}

	/* Gets the Color of the Gui */
	public Vector4f getColor() {

		return color;
	}

	/* Gets the Position On Screen */
	public Vector2f getPosition() {

		return position;
	}

	/* Sets the Position on Screen */
	public void setPosition(Vector2f position) {

		this.position = position;
	}

	/* Gets the Scale */
	public Vector2f getScale() {

		return scale;
	}

	/* Sets the Scale */
	public void setScale(float width, float height) {

		this.scale = new Vector2f(width, height);
	}

}
