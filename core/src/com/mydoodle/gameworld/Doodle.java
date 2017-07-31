package com.mydoodle.gameworld;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Doodle {
	private TextureRegion rgDoodleLeft;
	private TextureRegion rgDoodleLeftJump;
	private TextureRegion rgDoodleRight;
	private TextureRegion rgDoodleRightJump;
	private TextureRegion rgDoodleUp;
	private TextureRegion rgDoodleUpJump;
	private TextureRegion rgDoodleNose;
	private TextureRegion rgStars;
	private TextureRegion rgStars1;
	private TextureRegion rgStars2;
	private Animation stars;
	private float elapsedTime = 0;
	private float scale = 1;
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	public enum DeathType {dtNone, dtFall, dtBit, dtEscape};
	private DeathType dType = DeathType.dtNone;
	private boolean isDead;
	private boolean isLeft;
	private boolean isFire;
	private boolean isJump;
	private boolean canFire;
	private float maxXSpeed;
	private final float width = 62;
	private final float height = 90;
	private Rectangle bounds;	
	
	public Doodle(float x, float y) {
		position = new Vector2(x, y);
		bounds = new Rectangle(x, y, width, height);
		acceleration = new Vector2(0, World.gravity);
		velocity = new Vector2(0, 0);
		rgDoodleLeft = new TextureRegion(World.assetManager.txtGame, 652, 656, 124, 120);
		rgDoodleLeft.flip(false, true);
		rgDoodleLeftJump = new TextureRegion(World.assetManager.txtGame, 652, 776, 124, 120);
		rgDoodleLeftJump.flip(false, true);
		rgDoodleRight = new TextureRegion(World.assetManager.txtGame, 900, 656, 124, 120);
		rgDoodleRight.flip(false, true);
		rgDoodleRightJump = new TextureRegion(World.assetManager.txtGame, 900, 776, 124, 120);
		rgDoodleRightJump.flip(false, true);
		rgDoodleUp = new TextureRegion(World.assetManager.txtGame, 776, 656, 124, 120);
		rgDoodleUp.flip(false, true);
		rgDoodleUpJump = new TextureRegion(World.assetManager.txtGame, 776, 776, 124, 120);
		rgDoodleUpJump.flip(false, true);
		rgDoodleNose = new TextureRegion(World.assetManager.txtGame, 226, 904, 28, 120);
		rgDoodleNose.flip(false, true);
		rgStars = new TextureRegion(World.assetManager.txtGame, 915, 429, 100, 66);
		rgStars.flip(false, true);
		rgStars1 = new TextureRegion(World.assetManager.txtGame, 915, 499, 100, 66);
		rgStars1.flip(false, true);
		rgStars2 = new TextureRegion(World.assetManager.txtGame, 915, 575, 100, 66);
		rgStars2.flip(false, true);
		stars = new Animation(1/20f, rgStars, rgStars1, rgStars2);
	}
	
	public void update(float delta) {
		if (dType == DeathType.dtNone || dType == DeathType.dtFall || dType == DeathType.dtBit) {
			velocity.add(acceleration.x * delta, acceleration.y * delta);

			if (velocity.y > World.maxYSpeed) velocity.y = World.maxYSpeed;
		
			if (velocity.x > 0) velocity.x -= World.friction * delta;
			if (velocity.x < 0) velocity.x += World.friction * delta;
		
			if (maxXSpeed > 0) {
				if (velocity.x > maxXSpeed) velocity.x -= acceleration.x * delta;
			} else {
				if (velocity.x < maxXSpeed) velocity.x -= acceleration.x * delta;
			}
		
			if (position.x < - width / 2) position.x = World.gameWidth - width / 2;
			if (position.x > World.gameWidth - width / 2) position.x = - width / 2;
		
			if (isLeft) {
				if (velocity.x > 15f) isLeft = false;
			} else {
				if (velocity.x < -15f) isLeft = true;
			}
			if (velocity.y < -470) isJump = true;
			else isJump = false;
		
			position.add(velocity.x * delta, velocity.y * delta);
		}
		if (dType == DeathType.dtBit) {
			elapsedTime += delta;
		}
		if (dType == DeathType.dtEscape && isDead == false) {
			scale -= delta / 3;
			if (scale < 0.4) isDead = true;
		}
		bounds.set(position.x, position.y, width, height);		
	}
	
	public void draw(SpriteBatch batch) {
		if (isFire) {
			if (isJump) batch.draw(rgDoodleUpJump, bounds.x, bounds.y - World.dispY, rgDoodleUpJump.getRegionWidth(), rgDoodleUpJump.getRegionHeight());
			else batch.draw(rgDoodleUp, bounds.x, bounds.y - World.dispY, 62f, 60f, rgDoodleUp.getRegionWidth(), rgDoodleUp.getRegionHeight(), scale, scale, 1.57f);
				//batch.draw(rgDoodleUp, bounds.x, bounds.y - World.dispY, rgDoodleUp.getRegionWidth(), rgDoodleUp.getRegionHeight());
		} else {
			if (isLeft) {
				if (isJump) batch.draw(rgDoodleLeftJump, bounds.x - 30, bounds.y - World.dispY - 38, rgDoodleLeftJump.getRegionWidth(), rgDoodleLeftJump.getRegionHeight());
				else batch.draw(rgDoodleLeft, bounds.x - 30, bounds.y - World.dispY - 30, 62f, 60f, rgDoodleLeft.getRegionWidth(), rgDoodleLeft.getRegionHeight(), scale, scale, 1.57f);
					//batch.draw(rgDoodleLeft, bounds.x - 30, bounds.y - World.dispY - 30, rgDoodleLeft.getRegionWidth(), rgDoodleLeft.getRegionHeight());
			} else {
				if (isJump) batch.draw(rgDoodleRightJump, bounds.x - 32, bounds.y - World.dispY - 38, rgDoodleRightJump.getRegionWidth(), rgDoodleRightJump.getRegionHeight());
				else batch.draw(rgDoodleRight, bounds.x - 32, bounds.y - World.dispY - 30, 62f, 60f, rgDoodleRight.getRegionWidth(), rgDoodleRight.getRegionHeight(), scale, scale, 1.57f);
					//batch.draw(rgDoodleRight, bounds.x - 32, bounds.y - World.dispY - 30, rgDoodleRight.getRegionWidth(), rgDoodleRight.getRegionHeight());
			}
		}
		if (dType == DeathType.dtBit) {
			batch.draw(stars.getKeyFrame(elapsedTime, true), bounds.x - 20, bounds.y - 45 - World.dispY);
		}
		
	}
	
	public void jump(float speed) {
		velocity.y = -speed;
	}
	
	public void move(float speed) {
		speed = -speed;
		if (speed > World.maxXSpeed) {speed = World.maxXSpeed;}
		if (speed < -World.maxXSpeed) {speed = -World.maxXSpeed;}
		if (speed > 0) {acceleration.x = (speed + World.friction);}
		else {acceleration.x = (speed - World.friction);}
		maxXSpeed = speed;
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y);		
		bounds.set(x, y, width, height);
	}
	
	public void setVelocity(float x, float y) {
		velocity.set(x, y);		
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public Vector2 getAcceleration() {
		return acceleration;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public float getMaxSpeedX() {
		return maxXSpeed;
	}
	
	public void dead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public void setDType(DeathType dType) {
		this.dType = dType;
	}
	
	public DeathType getDType() {
		return dType;
	}
}
