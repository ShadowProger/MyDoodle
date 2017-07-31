package com.mydoodle.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class YellowPlatform extends Platform {
	private TextureRegion rgPlatformYellow;
	private TextureRegion rgPlatformYellow1;
	private TextureRegion rgPlatformYellow2;
	private TextureRegion rgPlatformYellow3;
	private TextureRegion rgPlatformYellow4;
	private TextureRegion rgPlatformYellow5;
	private TextureRegion rgPlatformYellow6;
	private TextureRegion rgPlatformYellow7;
	private Animation toRed;
	private Animation explosion;
	private float elapsedTime = 0;
	private float redTime;
	private float time;
	private int stage;
	
	public YellowPlatform(World world, float x, float y, float time) {
		super(world, x, y);
		this.time = time;
		redTime = time * 0.4f;
		stage = 0;
		rgPlatformYellow = new TextureRegion(World.assetManager.txtGame, 2, 329, 114, 30);
		rgPlatformYellow.flip(false, true);
		rgPlatformYellow1 = new TextureRegion(World.assetManager.txtGame, 2, 365, 114, 30);
		rgPlatformYellow1.flip(false, true);
		rgPlatformYellow2 = new TextureRegion(World.assetManager.txtGame, 2, 402, 114, 30);
		rgPlatformYellow2.flip(false, true);
		rgPlatformYellow3 = new TextureRegion(World.assetManager.txtGame, 2, 438, 114, 30);
		rgPlatformYellow3.flip(false, true);
		rgPlatformYellow4 = new TextureRegion(World.assetManager.txtGame, 2, 474, 114, 30);
		rgPlatformYellow4.flip(false, true);
		rgPlatformYellow5 = new TextureRegion(World.assetManager.txtGame, 2, 507, 114, 35);
		rgPlatformYellow5.flip(false, true);
		rgPlatformYellow6 = new TextureRegion(World.assetManager.txtGame, 2, 548, 121, 50);
		rgPlatformYellow6.flip(false, true);
		rgPlatformYellow7 = new TextureRegion(World.assetManager.txtGame, 1, 602, 123, 57);
		rgPlatformYellow7.flip(false, true);
		toRed = new Animation(1/30f, rgPlatformYellow, rgPlatformYellow1, rgPlatformYellow2, rgPlatformYellow3, rgPlatformYellow4);
		explosion = new Animation(1/35f, rgPlatformYellow4, rgPlatformYellow5, rgPlatformYellow6, rgPlatformYellow7);
	}

	@Override
	public void draw(SpriteBatch batch) {
		if (stage == 0) batch.draw(rgPlatformYellow, x, y - World.dispY, rgPlatformYellow.getRegionWidth(), rgPlatformYellow.getRegionHeight());
		if (stage == 1) batch.draw(toRed.getKeyFrame(elapsedTime, false), x, y - World.dispY);
		if (stage == 2) batch.draw(explosion.getKeyFrame(elapsedTime, false), x, y - World.dispY);
	}

	@Override
	public void action() {
		if (World.dispY < y - height) {
			time -= Gdx.graphics.getDeltaTime();
			if (stage == 0) {
				if (time < redTime) {
					stage = 1;
					elapsedTime = 0;
				}
			}		
			if (stage == 1) {
				elapsedTime += Gdx.graphics.getDeltaTime();
				if (time <= 0.133) {
					stage = 2;
					elapsedTime = 0;
				}
			}
			if (stage == 2) {
				elapsedTime += Gdx.graphics.getDeltaTime();
				if (time < 0) needDelete = true;
			}
		}
	}

	@Override
	public void collisionReaction() {
		world.getDoodle().setPosition(world.getDoodle().getPosition().x, y - world.getDoodle().getHeight());
		world.getDoodle().jump(World.normalJump);
	}

}
