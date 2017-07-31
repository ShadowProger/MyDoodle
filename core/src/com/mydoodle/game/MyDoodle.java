package com.mydoodle.game;

import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mydoodle.assetmanager.AssetManager;
import com.mydoodle.statemanager.GameStateManager;
import com.mydoodle.statemanager.Menu;

public class MyDoodle extends Game {
	public class Rec {
		public int value;
		public String name;
	}
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private GameStateManager gameStateManager;
	private AssetManager assetManager;
	private Preferences prefs;
	
	private float GameWidth;
	private float GameHeight;
	private float Coef;
	private Rec[] records = new Rec[10];
	
	@Override
	public void create () {
		float ScreenWidth = Gdx.graphics.getWidth();
		float ScreenHeight = Gdx.graphics.getHeight();
		
		GameWidth = 640;
		GameHeight = ScreenHeight / (ScreenWidth / GameWidth);
		
		Coef = GameWidth / ScreenWidth;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, GameWidth, GameHeight);
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		assetManager = new AssetManager(this);
		assetManager.Load();
		gameStateManager = new GameStateManager(this);				
		
		prefs = Gdx.app.getPreferences("Records");
		loadRecords();
		
		//gameStateManager.Switch(new Scores(gameStateManager));//(new Menu(gameStateManager));
		gameStateManager.switchState(new Menu(gameStateManager));
	}
	
	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		if (delta < 0.05) {
			gameStateManager.update(delta); 
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin(); 
		gameStateManager.render(delta);
		batch.end();
	}
	
	@Override
	public void dispose() {
		saveRecords();
		super.dispose();
		assetManager.dispose();
	}
	
	public SpriteBatch getSpriteBatch() {return batch;}	
	public AssetManager getAssetManager() {return assetManager;}
	public float getGameWidth() {return GameWidth;}
	public float getGameHeight() {return GameHeight;}
	public float getCoef() {return Coef;}
	public Rec[] getRecords() {return records;}
	
	public void setRecords(Rec[] records) {
		this.records = records;
	}
	
	public void saveRecords() {
		for (int i = 0; i < 10; i++) {			
			prefs.putString(Integer.toString(i + 1) + "Name", records[i].name);
			prefs.putInteger(Integer.toString(i + 1) + "Value", records[i].value);
		}
		prefs.flush();
	}
	
	public void loadRecords() {
		for (int i = 0; i < 10; i++) {			
			records[i] = new Rec();
			records[i].name = prefs.getString(Integer.toString(i + 1) + "Name", "");
			records[i].value = prefs.getInteger(Integer.toString(i + 1) + "Value", 0);
		}
	}	
}
