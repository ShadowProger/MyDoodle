package com.mydoodle.gameworld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WhitePlatform extends Platform {
	private TextureRegion rgPlatformWhite;
	
	public WhitePlatform(World world, float x, float y) {
		super(world, x, y);
		rgPlatformWhite = new TextureRegion(World.assetManager.txtGame, 2, 70, 114, 30);
		rgPlatformWhite.flip(false, true);
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(rgPlatformWhite, x, y - World.dispY, rgPlatformWhite.getRegionWidth(), rgPlatformWhite.getRegionHeight());
	}

	@Override
	public void action() {}

	@Override
	public void collisionReaction() {
		world.getDoodle().setPosition(world.getDoodle().getPosition().x, y - world.getDoodle().getHeight());
		world.getDoodle().jump(World.normalJump);
		needDelete = true;
	}

}
