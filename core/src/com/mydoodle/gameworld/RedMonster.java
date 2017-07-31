package com.mydoodle.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class RedMonster extends Monster {
	private TextureRegion rgRedMonster;
	
	public RedMonster(World world, float x, float y) {
		this.world = world;
		this.x = x;
		this.y = y;
		width = 92;
		height = 71;
		bounds = new Rectangle(x, y, width, height);
		needDelete = false;
		left = x - 23;
		right = x + 23;
		up = y - 15;
		down = y + 15;
		speedX = 192;
		speedY = 15;
		rgRedMonster = new TextureRegion(World.assetManager.txtGame, 925, 4, 92, 71);
		rgRedMonster.flip(false, true);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(rgRedMonster, x, y - World.dispY, rgRedMonster.getRegionWidth(), rgRedMonster.getRegionHeight());
	}

	@Override
	public void action() {
		x += speedX * Gdx.graphics.getDeltaTime();
		y += speedY * Gdx.graphics.getDeltaTime();
		if (x < left) {
			x = left;
			speedX = -speedX;
		}
		if (x > right) {
			x = right;
			speedX = -speedX;
		}
		if (y < up) {
			y = up;
			speedY = -speedY;
		}
		if (y > down) {
			y = down;
			speedY = -speedY;
		}
	}

	@Override
	public void collisionReaction() {
		if (rType == ReactionType.rtDead) {
			world.getDoodle().setPosition(world.getDoodle().getPosition().x, y - world.getDoodle().getHeight());
			world.getDoodle().jump(World.midJump);
			needDelete = true;
		}
		if (rType == ReactionType.rtKill) {
			//world.getDoodle().dead(true);
			world.getDoodle().setDType(Doodle.DeathType.dtBit);
			world.getDoodle().setVelocity(0, 0);
		}
	}

	@Override
	public boolean collision(Doodle doodle) {
		boolean f = false;
		rType = ReactionType.rtNone;
		float delta = Gdx.graphics.getDeltaTime();		
		Rectangle d = doodle.getBounds();
		Rectangle b = bounds;
		f = !((d.x > b.x + b.width) || 
			(d.x + d.width < b.x) ||
			(d.y > b.y + b.height) ||
			(d.y + d.height < b.y - doodle.getVelocity().y * delta));
		if (f) {
			rType = ReactionType.rtKill;
			if (doodle.getVelocity().y >= 0) { 
				if (doodle.getPosition().y < y) {
					rType = ReactionType.rtDead;
				}
			}
		}
		return f;
	}
	
}
