package com.mydoodle.assetmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mydoodle.game.MyDoodle;

public class AssetManager {
	private MyDoodle game;
	
	public Texture txtBackground;
	public Texture txtGame;
	public Texture txtButtons;	
	
	public BitmapFont font;
	private float GameWidth;
	private float GameHeight;
	
	public AssetManager(MyDoodle game) {
		this.game = game;
		GameWidth = game.getGameWidth();
		GameHeight = game.getGameHeight();
	}
	
	public void Load() {
		txtBackground = new Texture("BackGround.png");
		txtBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		txtButtons = new Texture("Buttons.png");
		txtButtons.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		txtGame = new Texture("Game.png");
		txtGame.setFilter(TextureFilter.Linear, TextureFilter.Linear);		

		//font = new 
		font = new BitmapFont(Gdx.files.internal("font.fnt"), true);
		//font.getData().setScale(0.6f, 0.6f);
	}
	
	public void dispose() {
		txtBackground.dispose();
		txtGame.dispose();
		txtButtons.dispose();
		font.dispose();
	}
}
