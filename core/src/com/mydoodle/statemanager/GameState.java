package com.mydoodle.statemanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.mydoodle.assetmanager.AssetManager;
import com.mydoodle.game.MyDoodle;

public abstract class GameState implements GestureListener, InputProcessor {
	protected GameStateManager stateManager;
	protected AssetManager assetManager;
	protected MyDoodle game;	
	protected SpriteBatch batch;
	protected float GameWidth;
	protected float GameHeight;
	protected float Coef;
	protected InputMultiplexer IM;
	
	protected GameState(GameStateManager stateManager) {
		this.stateManager = stateManager;
		game = stateManager.getGame();
		assetManager = game.getAssetManager();
		batch = game.getSpriteBatch();
		GameWidth = game.getGameWidth();
		GameHeight = game.getGameHeight();
		Coef = game.getCoef();
		
		IM = new InputMultiplexer();
		GestureDetector gd = new GestureDetector(this);
		IM.addProcessor(gd);
		IM.addProcessor(this);
		
		Gdx.input.setInputProcessor(IM);
	}
	
	public abstract void render(float delta);
	public abstract void update(float delta);
	public abstract void onCreate();
	public abstract void onClose();
	public abstract void onHide();
	public abstract void onShow();
	public abstract void dispose();	
}
