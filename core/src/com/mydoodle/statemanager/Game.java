package com.mydoodle.statemanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mydoodle.game.MyDoodle.Rec;
import com.mydoodle.gameworld.Doodle.DeathType;
import com.mydoodle.gameworld.World;
import com.mydoodle.ui.SimpleButton;

public class Game extends GameState {
	private TextureRegion rgBackground;
	private TextureRegion rgPause;		
	private TextureRegion rgKeyboard;
	private TextureRegion rgKeyboardOn;
	private TextureRegion rgButtonMenu;
	private TextureRegion rgButtonMenuOn;	
	private TextureRegion rgButtonResume;
	private TextureRegion rgButtonResumeOn;
	private TextureRegion rgButtonAgain;
	private TextureRegion rgButtonAgainOn;	
	private TextureRegion rgButtonPause;
	private TextureRegion rgButtonPauseOn;
	private TextureRegion rgBottom;
	private TextureRegion rgTop;		
	private TextureRegion rgGameOver;
	private TextureRegion rgScore;
	private TextureRegion rgHighScore;
	private TextureRegion rgName;
	private TextureRegion rgTapToChange;
	
	private SimpleButton btnAgain;
	private SimpleButton btnMenu;
	private SimpleButton btnResume;
	private SimpleButton btnPause;	
	
	private enum GameMode {gmGame, gmPause, gmFall, gmGameOver;}
	private GameMode gameMode;
	private float dispY;
	private float doodleAcc = 5f;
	private Rec[] records;
	private int highScore;
	
	private boolean isBackPressed = false;
	
	private World world = new World(game);
	
	public Game(GameStateManager stateManager) {
		super(stateManager);
		rgBackground = new TextureRegion(assetManager.txtBackground, 0, 0, 640, (int)GameHeight);
		rgBackground.flip(false, true);
		rgPause = new TextureRegion(assetManager.txtBackground, 640, 0, 640, (int)GameHeight);
		rgPause.flip(false, true);
		rgKeyboard = new TextureRegion(assetManager.txtButtons, 0, 0, 640, 416);
		rgKeyboard.flip(false, true);
		rgKeyboardOn = new TextureRegion(assetManager.txtButtons, 0, 416, 640, 416);
		rgKeyboardOn.flip(false, true);
		rgButtonMenu = new TextureRegion(assetManager.txtButtons, 640, 0, 224, 88);
		rgButtonMenu.flip(false, true);
		rgButtonMenuOn = new TextureRegion(assetManager.txtButtons, 640, 88, 224, 88);
		rgButtonMenuOn.flip(false, true);
		rgButtonResume = new TextureRegion(assetManager.txtButtons, 640, 352, 224, 88);
		rgButtonResume.flip(false, true);
		rgButtonResumeOn = new TextureRegion(assetManager.txtButtons, 640, 440, 224, 88);
		rgButtonResumeOn.flip(false, true);
		rgButtonAgain = new TextureRegion(assetManager.txtButtons, 640, 528, 224, 88);
		rgButtonAgain.flip(false, true);
		rgButtonAgainOn = new TextureRegion(assetManager.txtButtons, 640, 616, 224, 88);
		rgButtonAgainOn.flip(false, true);
		rgButtonPause = new TextureRegion(assetManager.txtGame, 851, 528, 32, 38);
		rgButtonPause.flip(false, true);
		rgButtonPauseOn = new TextureRegion(assetManager.txtGame, 882, 528, 32, 38);
		rgButtonPauseOn.flip(false, true);		
		rgBottom = new TextureRegion(assetManager.txtGame, 131, 231, 640, 138);
		rgBottom.flip(false, true);
		rgTop = new TextureRegion(assetManager.txtGame, 384, 922, 640, 102); //384, 896, 640, 128);
		rgTop.flip(false, true);				
		rgGameOver = new TextureRegion(assetManager.txtGame, 131, 369, 430, 156);
		rgGameOver.flip(false, true);
		rgScore = new TextureRegion(assetManager.txtGame, 782, 185, 230, 48);
		rgScore.flip(false, true);
		rgHighScore = new TextureRegion(assetManager.txtGame, 576, 416, 324, 64);
		rgHighScore.flip(false, true);
		rgName = new TextureRegion(assetManager.txtGame, 782, 234, 230, 48);
		rgName.flip(false, true);
		rgTapToChange = new TextureRegion(assetManager.txtGame, 778, 282, 240, 120);
		rgTapToChange.flip(false, true);
		
		btnAgain = new SimpleButton(360, GameHeight - 300, rgButtonAgain.getRegionWidth(), rgButtonAgain.getRegionHeight(), rgButtonAgain, rgButtonAgainOn);
		btnMenu = new SimpleButton(50, GameHeight - 300, rgButtonMenu.getRegionWidth(), rgButtonMenu.getRegionHeight(), rgButtonMenu, rgButtonMenuOn);
		btnResume = new SimpleButton(320, GameHeight - 300, rgButtonResume.getRegionWidth(), rgButtonResume.getRegionHeight(),rgButtonResume, rgButtonResumeOn);
		btnPause = new SimpleButton(574, 10, rgButtonPause.getRegionWidth(), rgButtonPause.getRegionHeight(), rgButtonPauseOn, rgButtonPauseOn);
		
		records = game.getRecords();		
		gameMode = GameMode.gmGame;
		
		Gdx.input.setCatchBackKey(true);
		
		newGame();
		
		Gdx.app.log("Game", "create");
	}

	@Override
	public void render(float delta) {
		batch.disableBlending();
		batch.draw(rgBackground, 0, 0, 640, GameHeight);
		switch (gameMode) {
			case gmGame: 
				batch.enableBlending();
				world.draw(batch);
				btnPause.draw(batch);
				break;
			case gmPause:
				batch.enableBlending();
				world.draw(batch);
				batch.draw(rgPause, 0, 0, 640, GameHeight);
				//batch.draw(rgButtonResume, 320, GameHeight - 300, rgButtonResume.getRegionWidth(), rgButtonResume.getRegionHeight());
				btnResume.draw(batch);
				break;
			case gmFall:								
			case gmGameOver:								
				batch.enableBlending();				
				batch.draw(rgGameOver, GameWidth / 2 - rgGameOver.getRegionWidth() / 2, GameHeight - 860 + dispY, rgGameOver.getRegionWidth(), rgGameOver.getRegionHeight());
				batch.draw(rgScore, 150, GameHeight - 600 + dispY, rgScore.getRegionWidth(), rgScore.getRegionHeight());
				batch.draw(rgHighScore, 35, GameHeight - 550 + dispY, rgHighScore.getRegionWidth(), rgHighScore.getRegionHeight());
				batch.draw(rgName, 75, GameHeight - 470 + dispY, rgName.getRegionWidth(), rgName.getRegionHeight());
				batch.draw(rgBottom, 0, GameHeight - rgBottom.getRegionHeight() + dispY, 640, rgBottom.getRegionHeight());
				assetManager.font.draw(batch, Integer.toString(world.getScores()), 400, GameHeight - 610 + dispY);
				assetManager.font.draw(batch, Integer.toString(highScore), 380, GameHeight - 548 + dispY);
				assetManager.font.draw(batch, "player", 310, GameHeight - 485 + dispY);
				btnMenu.setPos(50, GameHeight - 300 + dispY);
				btnAgain.setPos(360, GameHeight - 300 + dispY);
				btnMenu.draw(batch);
				btnAgain.draw(batch);
				world.draw(batch);
				break;
		}
		batch.enableBlending();
		batch.draw(rgTop, 0, 0, rgTop.getRegionWidth(), rgTop.getRegionHeight());
		
		assetManager.font.draw(batch, Integer.toString(world.getScores()), 20, 15);
		//assetManager.font.draw(batch, "Platforms: " + Integer.toString(world.getPlatCount()), 20, 65);
		//assetManager.font.draw(batch, "Bonuses:   " + Integer.toString(world.getBonCount()), 20, 115);
		//assetManager.font.draw(batch, "Monsters:  " + Integer.toString(world.getMonCount()), 20, 165);
		//assetManager.font.draw(batch, Float.toString(world.getDoodle().getVelocity().x), 20, 65);
	}
	
	@Override
	public void update(float delta) {
		switch (gameMode) {
			case gmGame:
				float speedX = Gdx.input.getAccelerometerX() * 10f;
				float st = 2 - Math.abs(speedX / 300);
				if (speedX < 0) {
					speedX = -(float)Math.pow(-speedX, st) * doodleAcc;
				} else {
					speedX = (float)Math.pow(speedX, st) * doodleAcc;
				}
				if (world.getDoodle().getDType() == DeathType.dtNone) {
					world.getDoodle().move(speedX);
				}
				
				//world.getDoodle().setVelocity(0, -300); // test
				
				world.update(delta);
				if (world.isGameOver()) {
					gameMode = GameMode.gmFall;
					if (world.getScores() > highScore) highScore = world.getScores();
				}
				break;
			case gmFall: 
				world.update(delta);
				dispY -= world.getDoodle().getVelocity().y * delta;
				if (dispY < 0) {
					dispY = 0;
					gameMode = GameMode.gmGameOver;
				}
				break;
			case gmGameOver:
				world.update(delta);
				break;
		}
	}

	@Override
	public void onCreate() {
		Gdx.app.log("Game", "onCreate");
	}

	@Override
	public void onClose() {
		Gdx.app.log("Game", "onClose");
	}

	@Override
	public void onHide() {
		Gdx.app.log("Game", "onHide");
	}

	@Override
	public void onShow() {
		Gdx.app.log("Game", "onShow");
	}

	@Override
	public void dispose() {
		Gdx.app.log("Game", "dispose");
	}

	private void newGame() {
		dispY = 860;
		highScore = records[0].value;
		gameMode = GameMode.gmGame;
		world.newGame();
	}
	
	private void checkRecords(String recName, int recValue) {
		int k = -1;
		for (int i = 0; i < 10; i++) {
			if (recValue > records[i].value) {
				k = i;
				break;
			}
		}
		if (k != -1) {
			for (int i = 8; i >= k; i--) {
				records[i+1].value = records[i].value;
				records[i+1].name = records[i].name;
			}		
			records[k].value = recValue;
			records[k].name = recName;
			game.setRecords(records);
			game.saveRecords();
		}
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		switch (gameMode) {
			case gmGame: 
				if (btnPause.isTouchDown(x * Coef, y * Coef) == true) {
					gameMode = GameMode.gmPause;
				}
				break;
			case gmPause: 
				btnResume.isTouchDown(x * Coef, y * Coef);
				break;
			case gmGameOver:	
				btnMenu.isTouchDown(x * Coef, y * Coef);
				btnAgain.isTouchDown(x * Coef, y * Coef);
				break;
		}
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
		switch (gameMode) {
			case gmGame:
				if (btnPause.isTouchUp(x * Coef, y * Coef) == true) {
					//gameMode = GameMode.gmPause;
				}
				break;
			case gmPause: 
				if (btnResume.isTouchUp(x * Coef, y * Coef) == true) {
					gameMode = GameMode.gmGame;
				}
				break;
			case gmGameOver:	
				if (btnMenu.isTouchUp(x * Coef, y * Coef) == true) {
					String recName = new String("player");
					checkRecords(recName, world.getScores());					
					stateManager.switchState(new Menu(stateManager));
				}
				if (btnAgain.isTouchUp(x * Coef, y * Coef) == true) {
					//NewGame
					String recName = new String("player");
					checkRecords(recName, world.getScores());					
					newGame();
					gameMode = GameMode.gmGame;
				}
				break;
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
		switch (gameMode) {
			case gmGame:
				if (keycode == Keys.BACK && isBackPressed == false) {
					gameMode = GameMode.gmPause;
					isBackPressed = true;
				}
				break;
			case gmGameOver:
				if (keycode == Keys.BACK && isBackPressed == false) {
					stateManager.switchState(new Menu(stateManager));
					isBackPressed = true;
				}
				break;
			case gmPause:
				if (keycode == Keys.BACK && isBackPressed == false) {
					gameMode = GameMode.gmGame;
					isBackPressed = true;
				}
				break;
		}				
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.BACK) {isBackPressed = false;}
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
		switch (gameMode) {
			case gmGame:
				if (btnPause.isTouchUp(screenX * Coef, screenY * Coef) == true) {
					//gameMode = GameMode.gmPause;
				}
				break;
			case gmPause: 
				if (btnResume.isTouchUp(screenX * Coef, screenY * Coef) == true) {
					gameMode = GameMode.gmGame;
				}
				break;
			case gmGameOver:	
				if (btnMenu.isTouchUp(screenX * Coef, screenY * Coef) == true) {
					String recName = new String("player");
					checkRecords(recName, world.getScores());					
					stateManager.switchState(new Menu(stateManager));
				}
				if (btnAgain.isTouchUp(screenX * Coef, screenY * Coef) == true) {
					//NewGame
					String recName = new String("player");
					checkRecords(recName, world.getScores());					
					newGame();
					gameMode = GameMode.gmGame;
				}
				break;
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
