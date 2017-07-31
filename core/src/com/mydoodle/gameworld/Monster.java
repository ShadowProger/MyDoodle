package com.mydoodle.gameworld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Monster {
	protected World world;
	protected float x;
	protected float y;
	protected float width = 0;
	protected float height = 0;
	protected Rectangle bounds;
	protected boolean needDelete;
	protected float speedX = 0;
	protected float speedY = 0;
	protected float left;
	protected float right;
	protected float up;
	protected float down;
	public enum ReactionType {rtNone, rtDead, rtKill};
	protected ReactionType rType = ReactionType.rtNone;
	
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
	
	public ReactionType getRType () {
		return rType;
	}
}
