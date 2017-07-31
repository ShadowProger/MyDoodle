package com.mydoodle.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class SimpleButton {

	private float x, y, width, height;

	private TextureRegion buttonUp;
	private TextureRegion buttonDown;

	private Rectangle bounds;

	private boolean isPressed = false;

	public SimpleButton(float x, float y, float width, float height,
			TextureRegion buttonUp, TextureRegion buttonDown) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buttonUp = buttonUp;
		this.buttonDown = buttonDown;

		bounds = new Rectangle(x, y, width, height);
	}

	public boolean isClicked(float screenX, float screenY) {
		return bounds.contains(screenX, screenY);
	}

	public void draw(SpriteBatch batch) {
		if (isPressed) {
			batch.draw(buttonDown, x, y, width, height);
		} else {
			batch.draw(buttonUp, x, y, width, height);
		}
	}

	public boolean isTouchDown(float screenX, float screenY) {
		if (bounds.contains(screenX, screenY)) {
			isPressed = true;
			return true;
		}
		return false;
	}

	public boolean isTouchUp(float screenX, float screenY) {		
		// It only counts as a touchUp if the button is in a pressed state.
		if (bounds.contains(screenX, screenY) && isPressed) {
			isPressed = false;
			return true;
		}		
		// Whenever a finger is released, we will cancel any presses.
		isPressed = false;
		return false;
	}
	
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
		bounds.set(x, y, width, height);
	}
}
