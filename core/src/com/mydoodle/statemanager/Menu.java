package com.mydoodle.statemanager;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mydoodle.gameworld.BluePlatform;
import com.mydoodle.gameworld.Bonus;
import com.mydoodle.gameworld.Doodle;
import com.mydoodle.gameworld.GreenPlatform;
import com.mydoodle.gameworld.Monster;
import com.mydoodle.gameworld.RedMonster;
import com.mydoodle.gameworld.Spring;
import com.mydoodle.gameworld.WhitePlatform;
import com.mydoodle.gameworld.World;
import com.mydoodle.gameworld.Platform;
import com.mydoodle.gameworld.YellowPlatform;
import com.mydoodle.ui.SimpleButton;

public class Menu extends GameState {	
	private TextureRegion rgBackground;
	private TextureRegion rgButtonPlay;
	private TextureRegion rgButtonPlayOn;
	private TextureRegion rgButtonScores;
	private TextureRegion rgButtonScoresOn;
	private TextureRegion rgBottom;
	private TextureRegion rgTitle;
	private TextureRegion rgBug1;
	private TextureRegion rgBug2;
	private TextureRegion rgBug3;
	private TextureRegion rgBug4;
	private TextureRegion rgBug5;
	private TextureRegion rgBlackHole;
	private TextureRegion rgUfo;
	private TextureRegion rgUfoOn;
	private TextureRegion rgDoodleRight;
	private TextureRegion rgPlatformGreen;
	
	private SimpleButton btnPlay;
	private SimpleButton btnScores;
			
	private World w;
	private Platform pl;
	private Doodle doodle;	
	
	//private Monster m;
		
	public Menu(GameStateManager stateManager) {
		super(stateManager);						
		rgBackground = new TextureRegion(assetManager.txtBackground, 0, 0, 640, (int)GameHeight);
		rgBackground.flip(false, true);
		rgButtonPlay = new TextureRegion(assetManager.txtButtons, 640, 176, 224, 88);
		rgButtonPlay.flip(false, true);
		rgButtonPlayOn = new TextureRegion(assetManager.txtButtons, 640, 264, 224, 88);
		rgButtonPlayOn.flip(false, true);
		rgButtonScores = new TextureRegion(assetManager.txtButtons, 640, 704, 100, 100);
		rgButtonScores.flip(false, true);
		rgButtonScoresOn = new TextureRegion(assetManager.txtButtons, 740, 704, 100, 100);
		rgButtonScoresOn.flip(false, true);
		rgBottom = new TextureRegion(assetManager.txtGame, 131, 231, 640, 138);
		rgBottom.flip(false, true);
		rgTitle = new TextureRegion(assetManager.txtGame, 133, 526, 406, 93);
		rgTitle.flip(false, true);
		rgBug1 = new TextureRegion(assetManager.txtGame, 714, 132, 43, 37);
		rgBug1.flip(false, true);
		rgBug2 = new TextureRegion(assetManager.txtGame, 941, 132, 41, 47);
		rgBug2.flip(false, true);
		rgBug3 = new TextureRegion(assetManager.txtGame, 836, 131, 44, 34);
		rgBug3.flip(false, true);
		rgBug4 = new TextureRegion(assetManager.txtGame, 881, 132, 58, 36);
		rgBug4.flip(false, true);
		rgBug5 = new TextureRegion(assetManager.txtGame, 761, 131, 74, 38);
		rgBug5.flip(false, true);
		rgBlackHole = new TextureRegion(assetManager.txtGame, 127, 92, 147, 135);
		rgBlackHole.flip(false, true);
		rgUfo = new TextureRegion(assetManager.txtGame, 4, 918, 163, 76);
		rgUfo.flip(false, true);
		rgUfoOn = new TextureRegion(assetManager.txtGame, 0, 668, 163, 250);
		rgUfoOn.flip(false, true);
		rgDoodleRight = new TextureRegion(assetManager.txtGame, 900, 656, 124, 120);
		rgDoodleRight.flip(false, true);
		rgPlatformGreen = new TextureRegion(assetManager.txtGame, 2, 2, 114, 30);
		rgPlatformGreen.flip(false, true);		
					
		btnPlay = new SimpleButton(200, GameHeight / 2 - rgButtonPlay.getRegionHeight() / 2, rgButtonPlay.getRegionWidth(), rgButtonPlay.getRegionHeight(), rgButtonPlay, rgButtonPlayOn);
		btnScores = new SimpleButton(420, GameHeight - 260, rgButtonScores.getRegionWidth(), rgButtonScores.getRegionHeight(), rgButtonScores, rgButtonScoresOn);
		
		w = new World(game);
		doodle = new Doodle(90, GameHeight - 300);		
		pl = new GreenPlatform(w, 70, GameHeight - 200, false);							

		Gdx.app.log("Menu", "create"); 
	}

	@Override
	public void render(float delta) {					
		batch.disableBlending();
		batch.draw(rgBackground, 0, 0, 640, GameHeight);
		batch.enableBlending();
		batch.draw(rgTitle, 20, 90, rgTitle.getRegionWidth(), rgTitle.getRegionHeight());
		batch.draw(rgBottom, 0, GameHeight - rgBottom.getRegionHeight(), 640, rgBottom.getRegionHeight());
		batch.draw(rgBug1, 80, 35, rgBug1.getRegionWidth(), rgBug1.getRegionHeight());
		batch.draw(rgBug2, 550, 600, rgBug2.getRegionWidth(), rgBug2.getRegionHeight());
		batch.draw(rgBug3, 65, 340, rgBug3.getRegionWidth(), rgBug3.getRegionHeight());
		batch.draw(rgBug4, 350, 280, rgBug4.getRegionWidth(), rgBug4.getRegionHeight());
		batch.draw(rgBug5, 300, GameHeight - 360, rgBug5.getRegionWidth(), rgBug5.getRegionHeight());			
		pl.draw(batch);		
		batch.draw(rgBlackHole, 470, 385, rgBlackHole.getRegionWidth(), rgBlackHole.getRegionHeight());
		Random random = new Random();
		int r = random.nextInt(4);
		if (r == 0)
			batch.draw(rgUfo, 450, 20, rgUfo.getRegionWidth(), rgUfo.getRegionHeight());
		else
			batch.draw(rgUfoOn, 450, 20, rgUfoOn.getRegionWidth(), rgUfoOn.getRegionHeight());
		btnPlay.draw(batch);
		btnScores.draw(batch);
		doodle.draw(batch);
		
		//m.draw(batch); // test
		
		//assetManager.font.draw(batch, "FPS= " + Gdx.graphics.getFramesPerSecond(), 0, 0);
		//assetManager.font.draw(batch, m.getRType().toString(), 0, 60);		
	}

	@Override
	public void update(float delta) {	
		pl.action();
		
		doodle.update(delta);
		
		//m.action(); // test
		//m.collision(doodle);
					
		if (doodle.getVelocity().y > 0) { 
			if (doodle.getPosition().y < pl.getY()) {
				Rectangle d = doodle.getBounds();
				Rectangle p = pl.getBounds();
				boolean f = ((d.x > p.x) && (d.x < p.x + p.width) && (d.y + d.height > p.y) && (d.y + d.height < p.y + p.height + doodle.getVelocity().y * delta)) ||
					        ((d.x + d.width > p.x) && (d.x + d.width < p.x + p.width) && (d.y + d.height > p.y) && (d.y + d.height < p.y + p.height + doodle.getVelocity().y * delta));
				if (f) {
					doodle.setPosition(doodle.getPosition().x, p.y - doodle.getHeight());
					//doodle.jump(World.normalJump);
					doodle.jump(900);
				}
			}
		}
	}

	@Override
	public void onCreate() {
		Gdx.input.setCatchBackKey(false);
		doodle.setPosition(90, GameHeight);
		doodle.jump(World.normalJump);
		Gdx.app.log("Menu", "onCreate");
	}

	@Override
	public void onClose() {
		Gdx.app.log("Menu", "onClose");		
	}

	@Override
	public void onHide() {
		Gdx.app.log("Menu", "onHide");		
	}

	@Override
	public void onShow() {
		Gdx.input.setInputProcessor(IM);
		Gdx.input.setCatchBackKey(false);
		doodle.setPosition(90, GameHeight);
		doodle.jump(World.normalJump);
		Gdx.app.log("Menu", "onShow");		
	}

	@Override
	public void dispose() {
		Gdx.app.log("Menu", "dispose");		
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		btnPlay.isTouchDown(x * Coef, y * Coef);
		btnScores.isTouchDown(x * Coef, y * Coef);
		
		//b.collisionReaction();
		//p2.delete();
		
		return true;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return true;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return true;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
        return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		if (btnPlay.isTouchUp(x * Coef, y * Coef) == true) {
			stateManager.switchState(new Game(stateManager));
		}		
		if (btnScores.isTouchUp(x * Coef, y * Coef) == true) {
			stateManager.push(new Scores(stateManager));
		}
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
        return true;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
        return true;
	}

	@Override
	public boolean keyDown(int keycode) {
        return true;
	}

	@Override
	public boolean keyUp(int keycode) {
        return true;
	}

	@Override
	public boolean keyTyped(char character) {
        return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) { //---		
        return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (btnPlay.isTouchUp(screenX * Coef, screenY * Coef) == true) {
			stateManager.switchState(new Game(stateManager));
		}
		if (btnScores.isTouchUp(screenX * Coef, screenY * Coef) == true) {
			stateManager.push(new Scores(stateManager));
		}
        return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
        return false;
	}

	@Override
	public boolean scrolled(int amount) {
        return false;
	}

}
