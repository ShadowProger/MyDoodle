package com.mydoodle.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BluePlatform extends Platform {
	private TextureRegion rgPlatformBlue;
	private TextureRegion rgPlatformBroun;
	private TextureRegion rgPlatformBroun1;
	private TextureRegion rgPlatformBroun2;
	private TextureRegion rgPlatformBroun3;	
	private Animation anim;
	private float elapsedTime = 0;
	private float speedY = 0;
	private float speedX;
	private boolean isBroken;
	private boolean isDestroyed = false;
	private float left;
	private float right;
	
	public BluePlatform(World world, float x, float y, float speed, boolean isBroken) {
		super(world, x, y);
		this.isBroken = isBroken;
		speedX = speed;
		left = 0;
		right = World.gameWidth - width;
		rgPlatformBlue = new TextureRegion(World.assetManager.txtGame, 2, 36, 114, 30);
		rgPlatformBlue.flip(false, true);
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
			batch.draw(rgPlatformBlue, x, y - World.dispY, rgPlatformBlue.getRegionWidth(), rgPlatformBlue.getRegionHeight());
		}
	}

	@Override
	public void action() {		
		if (isDestroyed) {
			elapsedTime += Gdx.graphics.getDeltaTime();
			speedY += World.gravity * Gdx.graphics.getDeltaTime();
			if (speedY > World.maxYSpeed) {speedY = World.maxYSpeed;}
			y += speedY * Gdx.graphics.getDeltaTime();
		} else {
			x += speedX * Gdx.graphics.getDeltaTime();
			if (x > right) {
				x = right;
				speedX = -speedX;
			}
			if (x < left) {
				x = left;
				speedX = -speedX;
			}
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
