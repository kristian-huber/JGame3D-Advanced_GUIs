package aurora.world.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import aurora.util.AuroraEngine;
import aurora.util.InputManager;
import aurora.world.World;
import aurora.world.terrain.Terrain;

public class Player extends Entity {
	public static final float SCROLL_SPEED = 200F;
	public static final float SPEED = 20F;
	public static final float RUN_MODIFIER = 1.5F;

	public Player(Vector3f position) {

		super(position);
	}

	@Override
	public void tick() {
		super.tick();

		float tick = AuroraEngine.getDelta();

		if (!InputManager.isMouseUnlocked()) {

			if (!this.isInAir()) {

				if (velocity.x > 0.1) {
					this.velocity.x -= 1F * tick;
				} else if (velocity.x < -0.1) {
					this.velocity.x += 1F * tick;
				} else if (Math.abs(velocity.x) < 0.1) {
					this.velocity.x = 0;
				}

				if (velocity.z > 0.1) {
					this.velocity.z -= 1F * tick;
				} else if (velocity.z < -0.1) {
					this.velocity.z += 1F * tick;
				} else if (Math.abs(velocity.z) < 0.1) {
					this.velocity.z = 0;
				}

				float bx = 0;
				float bz = 0;
				float ax = 0;
				float az = 0;
				float dx;
				float dz;
				
				//Calculates Directions
				if(Keyboard.isKeyDown(InputManager.KEY_RUN)){
					dx = (float) (SPEED * RUN_MODIFIER * Math
							.sin(Math.toRadians(rotation.y)));
					dz = (float) (SPEED * RUN_MODIFIER * Math
							.cos(Math.toRadians(rotation.y)));
				}else{
					dx = (float) (SPEED * Math
							.sin(Math.toRadians(rotation.y)));
					dz = (float) (SPEED * Math
							.cos(Math.toRadians(rotation.y)));
				}
				
				// Handles the Forward and Back Directions
				if (Keyboard.isKeyDown(InputManager.KEY_FORWARD)) {

					bz = -dz * tick;
					bx = dx * tick;

				} else if (Keyboard.isKeyDown(InputManager.KEY_BACKWARD)) {

					bz = dz * tick;
					bx = -dx * tick;
				}

				if (Keyboard.isKeyDown(InputManager.KEY_LEFT)) {

					ax = -dz * tick;
					az = -dx * tick;
				} else if (Keyboard.isKeyDown(InputManager.KEY_RIGHT)) {

					ax = dz * tick;
					az = dx * tick;
				}

				if (ax + bx != 0) {
					velocity.x = ax + bx;
				}
				if (az + bz != 0) {
					velocity.z = az + bz;
				}
				
				// Handles the Up and Down Directions
				if (Keyboard.isKeyDown(InputManager.KEY_JUMP)) {

					this.velocity.y = 0.4F;
				}
			}
			
			// Increases the View Right or Left
			if (Mouse.getX() > AuroraEngine.WIDTH / 2) {

				rotation.y += SCROLL_SPEED * tick;
			} else if (Mouse.getX() < AuroraEngine.WIDTH / 2 - 5) {

				rotation.y -= SCROLL_SPEED * tick;
			}

			if (Mouse.getY() > AuroraEngine.HEIGHT / 2 + 5
					&& rotation.x > -80) {

				rotation.x -= SCROLL_SPEED * tick;
			} else if (Mouse.getY() < AuroraEngine.HEIGHT / 2 - 5
					&& rotation.x < 80) {

				rotation.x += SCROLL_SPEED * tick;
			}
		}

		// Keeps Rotation in check
		if (rotation.x < -80) {

			rotation.x = -80;
		}

		if (rotation.x > 80) {
			rotation.x = 80;
		}

		// Makes sure the player doesnt fall off the world
		if (position.x < -World.numSec * Terrain.SIZE + 3) {
			position.x = -World.numSec * Terrain.SIZE + 3;
		}

		if (position.x > World.numSec * Terrain.SIZE + Terrain.SIZE - 3) {
			position.x = World.numSec * Terrain.SIZE + Terrain.SIZE - 3;
		}

		if (position.z < -World.numSec * Terrain.SIZE + 3) {
			position.z = -World.numSec * Terrain.SIZE + 3;
		}

		if (position.z > World.numSec * Terrain.SIZE + Terrain.SIZE - 3) {
			position.z = World.numSec * Terrain.SIZE + Terrain.SIZE - 3;
		}
	}
}
