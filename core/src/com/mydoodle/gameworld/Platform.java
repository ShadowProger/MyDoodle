package com.mydoodle.gameworld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Platform {
	protected World world;
	protected float x;
	protected float y;
	protected final float width = 114;
	protected final float height = 30;
	protected Rectangle bounds;
	protected boolean needDelete;
	
	protected Platform(World world, float x, float y) {
		this.world = world;
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x, y, width, height);
		needDelete = false;
	}
	
	public abstract void draw(SpriteBatch batch);
	public abstract void action();
	public abstract void collisionReaction();
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public boolean needDelete() {
		return needDelete;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void delete() {
		needDelete = true;
	}
}
