package com.mydoodle.gameworld;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Spring extends Bonus {
	private TextureRegion rgSpring;
	private TextureRegion rgSpringOn;
	private boolean active;
	Random random = new Random();
	
	public Spring(World world, Platform platform) {
		this.world = world;
		this.platform = platform;
		width = 34;
		height = 55;		
		dispX = random.nextInt((int)platform.width - width);
		x = platform.x + dispX;
		y = platform.y - height + 35;
		bounds = new Rectangle(x, y, width, height);
		active = false;
		rgSpring = new TextureRegion(World.assetManager.txtGame, 554, 96, 34, 23);
		rgSpring.flip(false, true);
		rgSpringOn = new TextureRegion(World.assetManager.txtGame, 554, 128, 34, 55);
		rgSpringOn.flip(false, true);
	}

	@Override
	public void draw(SpriteBatch batch) {
		if (active) {
			batch.draw(rgSpringOn, x, y - World.dispY - 32, 17, 28, rgSpringOn.getRegionWidth(), rgSpringOn.getRegionHeight(), 1, 1, angle);
		} else {
			batch.draw(rgSpring, x, y - World.dispY, 17, 12, rgSpring.getRegionWidth(), rgSpring.getRegionHeight(), 1, 1, angle);
		}
	}

	@Override
	public void action() {
		if (isFall) {
			angle -= 1.5f;
			speedY += World.gravity * Gdx.graphics.getDeltaTime();
			if (speedY > World.maxYSpeed) {speedY = World.maxYSpeed;}
			y += speedY  * Gdx.graphics.getDeltaTime();
		} else {
			x = platform.x + dispX;
			y = platform.y - height + 35;
			bounds.set(x, y, width, height);
			if (platform.needDelete) {
				isFall = true;
				platform = null;
				return;
			}			
		}
	}

	@Override
	public void collisionReaction() {
		active = true;
		world.getDoodle().setPosition(world.getDoodle().getPosition().x, y - world.getDoodle().getHeight());
		world.getDoodle().jump(World.highJump);
	}

	@Override
	public boolean collision(Doodle doodle) {
		boolean f = false;
		float delta = Gdx.graphics.getDeltaTime();
		if (doodle.getVelocity().y >= 0) { 
			if (doodle.getPosition().y < y) {
				Rectangle d = doodle.getBounds();
				Rectangle b = bounds;
				f = !((d.x > b.x + b.width) || 
					(d.x + d.width < b.x) ||
					(d.y > b.y + b.height) ||
					(d.y + d.height < b.y - doodle.getVelocity().y * delta));
			}
		}
		return f;
	}
	
}
