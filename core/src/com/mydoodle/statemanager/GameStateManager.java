package com.mydoodle.statemanager;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.mydoodle.game.MyDoodle;

public class GameStateManager {
	private MyDoodle game;
	private Stack<GameState> gameStates;
	
	public GameStateManager(MyDoodle game) {
		Gdx.app.log("GameStateManager", "create");
		this.game = game;
		gameStates = new Stack<GameState>();		
	}
	
	public void switchState(GameState state) {
		Gdx.app.log("GameStateManager", "switch");
		while (gameStates.isEmpty() == false) {
			gameStates.pop().dispose();
		}
		gameStates.push(state);
		state.onCreate();
		//state.onShow();
	}
	
	public void pop() {
		Gdx.app.log("GameStateManager", "pop");
		GameState TopState = gameStates.pop();
		TopState.dispose();
		gameStates.peek().onShow();
	}
	
	public void push(GameState state) {
		Gdx.app.log("GameStateManager", "push");
		gameStates.push(state);
	}
	
	public void render(float delta) {
		gameStates.peek().render(delta);
	}
	
	public void update(float delta) {
		gameStates.peek().update(delta);
	}
	
	public MyDoodle getGame() {return game;}
}
