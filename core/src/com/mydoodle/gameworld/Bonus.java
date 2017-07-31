package com.mydoodle.gameworld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Bonus {
	protected World world;
	protected Platform platform;
	protected float x;
	protected float y;
	protected float dispX = 0;
	protected float speedY = 0;
	protected int width = 0;
	protected int height = 0;
	protected float angle = 3.14f / 2;
	protected Rectangle bounds;
	protected boolean needDelete = false;
	protected boolean isFall = false;
	protected boolean isClockwise = false;
	
	public abstract void draw(SpriteBatch batch);
	public abstract void action();
	public abstract void collisionReaction();
	public abstract boolean collision(Doodle doodle);
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public boolean needDelete() {
		return needDelete;
	}	
	
	public Platform getPlatform() {
		return platform;
	}
}
