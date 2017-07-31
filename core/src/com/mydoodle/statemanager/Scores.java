package com.mydoodle.statemanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mydoodle.game.MyDoodle.Rec;
import com.mydoodle.ui.SimpleButton;

public class Scores extends GameState{
	private TextureRegion rgBackground;
	private TextureRegion rgButtonMenu;
	private TextureRegion rgButtonMenuOn;
	private TextureRegion rgDoodleRight;
	private TextureRegion rgPlatformGreen;
	private TextureRegion rgTitle;
	private TextureRegion rgBug1;
	private TextureRegion rgBug2;
	private TextureRegion rgBug3;
	private TextureRegion rgBug4;
	private TextureRegion rgBug5;
	
	private SimpleButton btnMenu;
	private Rec[] records;
	private GlyphLayout layout;

	public Scores(GameStateManager stateManager) {
		super(stateManager);
		rgBackground = new TextureRegion(assetManager.txtBackground, 0, 0, 640, (int)GameHeight);
		rgBackground.flip(false, true);
		rgButtonMenu = new TextureRegion(assetManager.txtButtons, 640, 0, 224, 88);
		rgButtonMenu.flip(false, true);
		rgButtonMenuOn = new TextureRegion(assetManager.txtButtons, 640, 88, 224, 88);
		rgButtonMenuOn.flip(false, true);
		rgDoodleRight = new TextureRegion(assetManager.txtGame, 900, 656, 124, 120);
		rgDoodleRight.flip(false, true);
		rgPlatformGreen = new TextureRegion(assetManager.txtGame, 2, 2, 114, 30);
		rgPlatformGreen.flip(false, true);
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
		
		records = game.getRecords();
		layout = new GlyphLayout();
		
		Gdx.input.setCatchBackKey(true);
		
		btnMenu = new SimpleButton(360, GameHeight - 130, rgButtonMenu.getRegionWidth(), rgButtonMenu.getRegionHeight(), rgButtonMenu, rgButtonMenuOn);
		Gdx.app.log("Scores", "create");
	}

	@Override
	public void render(float delta) {
		batch.disableBlending();
		batch.draw(rgBackground, 0, 0, 640, GameHeight);
		batch.enableBlending();
		batch.draw(rgTitle, 20, 20, rgTitle.getRegionWidth(), rgTitle.getRegionHeight());
		batch.draw(rgBug1, 90, 20, rgBug1.getRegionWidth(), rgBug1.getRegionHeight());
		batch.draw(rgBug2, 580, 400, rgBug2.getRegionWidth(), rgBug2.getRegionHeight());
		batch.draw(rgBug3, 25, 280, rgBug3.getRegionWidth(), rgBug3.getRegionHeight());
		batch.draw(rgBug4, 550, 120, rgBug4.getRegionWidth(), rgBug4.getRegionHeight());
		batch.draw(rgBug5, 15, 620, rgBug5.getRegionWidth(), rgBug5.getRegionHeight());
		btnMenu.draw(batch);
		batch.draw(rgPlatformGreen, 50, GameHeight - 70, rgPlatformGreen.getRegionWidth(), rgPlatformGreen.getRegionHeight());
		batch.draw(rgDoodleRight, 50, GameHeight - 190, rgDoodleRight.getRegionWidth(), rgDoodleRight.getRegionHeight());		

		for (int i = 0; i < 10; i++) {
			assetManager.font.draw(batch, Integer.toString(i + 1) + ". " + records[i].name, 110, i * 60 + 200);
			layout.setText(assetManager.font, Integer.toString(records[i].value));
			assetManager.font.draw(batch, Integer.toString(records[i].value), GameWidth - 100 - layout.width, i * 60 + 200);
		}
		
		//assetManager.font.draw(batch, "FPS= " + Gdx.graphics.getFramesPerSecond(), 0, 0);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void onCreate() {
		Gdx.app.log("Scores", "onCreate");
	}

	@Override
	public void onClose() {
		Gdx.app.log("Scores", "onClose");
	}

	@Override
	public void onHide() {
		Gdx.app.log("Scores", "onHide");
	}

	@Override
	public void onShow() {
		Gdx.app.log("Scores", "onShow");
	}

	@Override
	public void dispose() {
		Gdx.app.log("Scores", "dispose");
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		btnMenu.isTouchDown(x * Coef, y * Coef);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		if (btnMenu.isTouchUp(x * Coef, y * Coef) == true) {
			stateManager.pop();
		}
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK) {stateManager.pop();}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (btnMenu.isTouchUp(screenX * Coef, screenY * Coef) == true) {
			stateManager.pop();
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
