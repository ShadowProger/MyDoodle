package com.mydoodle.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mydoodle.gameworld.Platform;

public class GreenPlatform extends Platform {
	private TextureRegion rgPlatformGreen;
	private TextureRegion rgPlatformBroun;
	private TextureRegion rgPlatformBroun1;
	private TextureRegion rgPlatformBroun2;
	private TextureRegion rgPlatformBroun3;	
	private Animation anim;
	private float elapsedTime = 0;
	private float speedY = 0;
	//private float accY = World.gravity;
	private boolean isBroken;
	private boolean isDestroyed = false;

	public GreenPlatform(World world, float x, float y, boolean isBroken) {
		super(world, x, y);
		this.isBroken = isBroken;
		rgPlatformGreen = new TextureRegion(World.assetManager.txtGame, 2, 2, 114, 30);
		rgPlatformGreen.flip(false, true);
		rgPlatformBroun = new TextureRegion(World.assetManager.txtGame, 2, 107, 121, 30);
		rgPlatformBroun.flip(false, true);
		rgPlatformBroun1 = new TextureRegion(World.assetManager.txtGame, 2, 144, 121, 39);
		rgPlatformBroun1.flip(false, true);
		rgPlatformBroun2 = new TextureRegion(World.assetManager.txtGame, 2, 193, 121, 55);
		rgPlatformBroun2.flip(false, true);
		rgPlatformBroun3 = new TextureRegion(World.assetManager.txtGame, 2, 259, 121, 64);
		rgPlatformBroun3.flip(false, true);
		anim = new Animation(1/35f, rgPlatformBroun, rgPlatformBroun1, rgPlatformBroun2, rgPlatformBroun3);		
	}

	@Override
	public void draw(SpriteBatch batch) {
		if (isBroken) {
			if (isDestroyed) {
				batch.draw(anim.getKeyFrame(elapsedTime, false), x, y - World.dispY);
			} else {
				batch.draw(rgPlatformBroun, x, y - World.dispY, rgPlatformBroun.getRegionWidth(), rgPlatformBroun.getRegionHeight());
			}			
		} else {
			batch.draw(rgPlatformGreen, x, y - World.dispY, rgPlatformGreen.getRegionWidth(), rgPlatformGreen.getRegionHeight());
		}
	}

	@Override
	public void action() {		
		if (isDestroyed) {
			elapsedTime += Gdx.graphics.getDeltaTime();
			speedY += World.gravity * Gdx.graphics.getDeltaTime();
			if (speedY > World.maxYSpeed) {speedY = World.maxYSpeed;}
			y += speedY * Gdx.graphics.getDeltaTime();			
		}
		bounds.set(x, y, width, height);
		if (y > World.dispY + World.gameHeight) needDelete = true;
	}

	@Override
	public void collisionReaction() {
		if (isBroken) {isDestroyed = true;}
		else {
			world.getDoodle().setPosition(world.getDoodle().getPosition().x, y - world.getDoodle().getHeight());
			world.getDoodle().jump(World.normalJump);
		}
	}

}
