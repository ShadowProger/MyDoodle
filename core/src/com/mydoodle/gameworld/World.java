package com.mydoodle.gameworld;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mydoodle.assetmanager.AssetManager;
import com.mydoodle.game.MyDoodle;
import com.mydoodle.gameworld.Doodle.DeathType;

public class World {		
	private MyDoodle game;
	public static AssetManager assetManager;
	
	private Doodle doodle;
	private ArrayList<Platform> platforms;
	private ArrayList<Bonus> bonuses;
	private ArrayList<Monster> monsters;
	
	public static float gameWidth;
	public static float gameHeight;
	public static float gravity = 1800f;
	public static float friction = 1500f;
	public static float maxYSpeed = 1800f;
	public static float maxXSpeed = 1500f;
	public static float dispY = 0;
	public static float normalJump = 1150f;
	public static float midJump = 1550f;
	public static float highJump = 2000f;
	public float edge;	
	private boolean isGameOver;
	private boolean isFall;
	private boolean isFly;
	private float fallTime;
	private float fallDisp;	
	
	// for platforms
	private int[] PlatLevels = {0, -1000, -3000, -6000, -10000, -15000};
	private enum PlatformType {ptGreen, ptBlue, ptWhite, ptYellow};
	private boolean lastBroken;
	private float genLine;
	private int highestPlatform;
	private final int platformWidth = 114;
	private final int platformHeight = 30;
	private final int platformsCount = 50;
	private int curMaxPlatformDistance;
	private final int maxPlatformDistance = 300;	
	private final int minPlatformDistance = 40;
	
	// for bonuses
	private enum BonusType {btSpring, btShield, btHat, btJetpack, btNone};
	private int platCount;
	private int minPlatCount = 10;
	private int curPlatCount;
	
	// for monsters
	private enum MonsterType {mtRed, mtPurple, mtBlue, mtUFO, mtBlackHole};
	private int highestMonster;
	private int monsterDistance;
	private int minMonsterDistance = 1000;
	private int curMonsterDistance;
	
	// for death
	
	private int scores;
	private int startY;
	
	Random random = new Random();
	
	
	public World(MyDoodle game) {
		this.game = game;
		this.assetManager = game.getAssetManager();
		this.gameWidth = game.getGameWidth();
		this.gameHeight = game.getGameHeight();		
		platforms = new ArrayList<Platform>();
		bonuses = new ArrayList<Bonus>();
		monsters = new ArrayList<Monster>();
		doodle = new Doodle(0, 0);
		dispY = 0;
		isGameOver = false;
		isFall = false;
		edge = gameHeight - 533;
		//newGame();
	}
	
	public void update(float delta) {
		if (!isGameOver) {
			if (!isFall) {
				if (doodle.getPosition().y - dispY < edge) {
					dispY = doodle.getPosition().y - edge;
					int dist = startY - (int)doodle.getPosition().y;
					if (dist > 0) scores = dist / 3;
				}
				for (int i = 0; i < platforms.size(); i++)
					platforms.get(i).action();
				for (int i = 0; i < bonuses.size(); i++)
					bonuses.get(i).action();
				for (int i = 0; i < monsters.size(); i++)
					monsters.get(i).action();
				genLine = dispY - gameHeight;
				check();
				generate(); 
				doodle.update(delta); 
				switch (doodle.getDType()) {
					case dtNone:						
						checkCollision();						
						break;
					case dtBit:
						doodle.move(0);
						break;
					case dtEscape:
						break;
				}
				if (doodle.getPosition().y > dispY + gameHeight) {
					isFall = true;
					fallTime = 0.75f;
					fallDisp = gameHeight;
				}
			} else {
				if (doodle.getPosition().y - dispY > gameHeight / 2) {
					fallDisp -= 20;
					dispY = doodle.getPosition().y - fallDisp;					
				} else {
					dispY = doodle.getPosition().y - fallDisp;
					isFly = true;
				}					
				fallTime -= delta;
				if (fallTime <= 0) {
					isGameOver = true;
					isFly = true;
				}
				doodle.update(delta);
			}					
		} else {
			if (doodle.getPosition().y - dispY < gameHeight * 1.5) doodle.update(delta);
		}
	}
	
	public void draw(SpriteBatch batch) {
		for (int i = 0; i < platforms.size(); i++) {
			if (dispY - platforms.get(i).getBounds().height < platforms.get(i).getBounds().y) platforms.get(i).draw(batch);
		}
		for (int i = 0; i < bonuses.size(); i++) {
			if (dispY - bonuses.get(i).getBounds().height < bonuses.get(i).getBounds().y) bonuses.get(i).draw(batch);
		}
		for (int i = 0; i < monsters.size(); i++) {
			if (dispY - monsters.get(i).getBounds().height < monsters.get(i).getBounds().y) monsters.get(i).draw(batch);
		}
		doodle.draw(batch);
	}
	
	public void newGame() {
		platforms.clear();
		bonuses.clear();
		monsters.clear();
		dispY = 0;
		scores = 0;
		startY = (int)gameHeight / 2;
		genLine = dispY - gameHeight;
		highestPlatform = 0;
		curMaxPlatformDistance = 60;
		monsterDistance = random.nextInt(3000) + 3000;
		highestMonster = 0;
		isGameOver = false;
		isFall = false;
		isFly = false;
		generate();
		doodle.setPosition(gameWidth / 2 - doodle.getWidth() / 2, gameHeight - doodle.getHeight());
		doodle.setVelocity(0, 0);
		doodle.setDType(Doodle.DeathType.dtNone);
		doodle.dead(false);
		doodle.jump(normalJump);
	}
	
	private void generate() {
		if (platforms.isEmpty()) {
			int x = random.nextInt((int)gameWidth - platformWidth);
			int y = (int)gameHeight - minPlatformDistance;
			Platform newPlatform = new GreenPlatform(this, x, y, false);
			platforms.add(newPlatform);
			highestPlatform = y;
			platCount = random.nextInt(10) + 5;
		}		
		while (highestPlatform - genLine > curMaxPlatformDistance) {
			int i;
			for (i = 0; i < PlatLevels.length - 1; i++)
				if (highestPlatform > PlatLevels[i] * 3) break;
			int level = i;
			
			PlatformType pType = PlatformType.ptGreen;
			int brokenChance = 0;
			int r;
			int minSpeed = 0, maxSpeed = 0;
			switch (level) {
				case 0:
					curMaxPlatformDistance = 80;
					pType = PlatformType.ptGreen;
					if (lastBroken) {brokenChance = 0;}
					else {brokenChance = 20;}
					break;
				case 1:
					curMaxPlatformDistance = 120;
					pType = PlatformType.ptGreen;
					if (lastBroken) {brokenChance = 0;}
					else {brokenChance = 15;}
					break;
				case 2:
					curMaxPlatformDistance = 170;
					pType = PlatformType.ptGreen;
					r = random.nextInt(100);
					if (r < 20) {pType = PlatformType.ptBlue;}
					minSpeed = 100;
					maxSpeed = 150;
					if (lastBroken) {brokenChance = 0;}
					else {brokenChance = 10;}
					break;
				case 3:
					curMaxPlatformDistance = 200;
					pType = PlatformType.ptGreen;
					r = random.nextInt(100);
					if (r < 60) {pType = PlatformType.ptBlue;}
					minSpeed = 150;
					maxSpeed = 200;
					if (lastBroken) {brokenChance = 0;}
					else {brokenChance = 5;}
					break;
				case 4:
					curMaxPlatformDistance = 225;
					pType = PlatformType.ptGreen;
					r = random.nextInt(100);
					if (r < 70) {pType = PlatformType.ptBlue;}
					r = random.nextInt(100);
					if (r < 10) {pType = PlatformType.ptWhite;}
					r = random.nextInt(100);
					if (r < 10) {pType = PlatformType.ptYellow;}
					minSpeed = 200;
					maxSpeed = 250;
					if (lastBroken) {brokenChance = 0;}
					else {brokenChance = 5;}
					break;
				case 5:
					curMaxPlatformDistance = 250;
					pType = PlatformType.ptGreen;
					r = random.nextInt(100);
					if (r < 85) {pType = PlatformType.ptBlue;}
					r = random.nextInt(100);
					if (r < 15) {pType = PlatformType.ptWhite;}
					r = random.nextInt(100);
					if (r < 15) {pType = PlatformType.ptYellow;}
					minSpeed = 200;
					maxSpeed = 270;
					if (lastBroken) {brokenChance = 0;}
					else {brokenChance = 5;}
					break;
			}
			
			boolean isBroken;
			r = random.nextInt(100);
			if (r < brokenChance) {isBroken = true;}
			else {isBroken = false;}
			if (isBroken || lastBroken) {curMaxPlatformDistance = curMaxPlatformDistance / 2;}
			int x = random.nextInt((int)gameWidth - platformWidth);
			int a = highestPlatform - curMaxPlatformDistance;
			int b = highestPlatform - minPlatformDistance;
			int y = random.nextInt(b - a + 1) + a;
						
			Platform newPlatform;
			switch (pType) {
				case ptGreen:					
					newPlatform = new GreenPlatform(this, x, y, isBroken);
					platforms.add(newPlatform);
					break;
				case ptBlue:					
					int speed = random.nextInt(maxSpeed - minSpeed + 1) + minSpeed;
					newPlatform = new BluePlatform(this, x, y, speed, isBroken);
					platforms.add(newPlatform);
					break;
				case ptWhite:					
					newPlatform = new WhitePlatform(this, x, y);
					platforms.add(newPlatform);
					break;	
				case ptYellow:					
					int time = random.nextInt(4) + 4;
					newPlatform = new YellowPlatform(this, x, y, time);
					platforms.add(newPlatform);
					break;	
			}			
			platCount--;
			
			if (platCount <= 0) {
				if (pType == PlatformType.ptGreen || pType == PlatformType.ptBlue) {
					if (isBroken == false) {
						BonusType bType = BonusType.btSpring;
						switch (level) {
							case 0:
								r = random.nextInt(100);
								if (r < 40) bType = BonusType.btNone;
								curPlatCount = 20;
								break;
							case 1: 
								r = random.nextInt(100);
								if (r < 25) bType = BonusType.btNone;
								curPlatCount = 15;
								break;
						}
						Bonus newBonus;
						switch (bType) {
							case btNone:
								platCount = random.nextInt(curPlatCount - minPlatCount + 1) + minPlatCount;
								break;
							case btSpring:
								newBonus = new Spring(this, platforms.get(platforms.size() - 1));
								bonuses.add(newBonus);
								platCount = random.nextInt(curPlatCount - minPlatCount + 1) + minPlatCount;
								break;
						}
					}
				}					
			}
			
			lastBroken = isBroken;			
			highestPlatform = y;
		}
			
	if (highestMonster - monsterDistance >= genLine) {
		int a = 50;
		int b = (int)gameWidth - 200;
		int x = random.nextInt(b - a + 1) + a;
		int y = highestMonster - monsterDistance;
		Monster newMonster = new RedMonster(this, x, y);
		monsters.add(newMonster);
		highestMonster = y;
		curMonsterDistance = 5000;
		monsterDistance = random.nextInt(curMonsterDistance - minMonsterDistance + 1) + minMonsterDistance;
	}
}
	
	private void check() {
		if (!monsters.isEmpty()) {
			while (monsters.get(0).y > dispY + gameHeight) {
				monsters.remove(0);
				if (monsters.isEmpty()) break;
			}
			int i = 0;
			while (i < monsters.size()) {
				if (monsters.get(i).needDelete) {monsters.remove(i);}
				else {i++;}
			}
		}
		if (!bonuses.isEmpty()) {
			while (bonuses.get(0).y > dispY + gameHeight) {
				bonuses.remove(0);
				if (bonuses.isEmpty()) break;
			}
			int i = 0;
			while (i < bonuses.size()) {
				if (bonuses.get(i).needDelete) {bonuses.remove(i);}
				else {i++;}
			}
		}
		if (!platforms.isEmpty()) {
			while (platforms.get(0).y > dispY + gameHeight) {
				platforms.remove(0);
				if (platforms.isEmpty()) break;
			} 
			int i = 0;
			while (i < platforms.size()) {
				if (platforms.get(i).needDelete) {platforms.remove(i);}
				else {i++;}
			}
		}
	}
	
	private void checkCollision() {
		for (int i = 0; i < monsters.size(); i++) {
			if (monsters.get(i).collision(doodle)) monsters.get(i).collisionReaction();
		}
		
		for (int i = 0; i < bonuses.size(); i++) {
			if (bonuses.get(i).collision(doodle)) bonuses.get(i).collisionReaction();
		}
		float delta = Gdx.graphics.getDeltaTime();
		if (doodle.getVelocity().y > 0) { 
			for (int i = 0; i < platforms.size(); i++) {
				if (doodle.getPosition().y < platforms.get(i).y) {
					Rectangle d = doodle.getBounds();
					Rectangle p = platforms.get(i).getBounds();
					boolean f = ((d.x > p.x) && (d.x < p.x + p.width) && (d.y + d.height > p.y) && (d.y + d.height < p.y + p.height + doodle.getVelocity().y * delta)) ||
								((d.x + d.width > p.x) && (d.x + d.width < p.x + p.width) && (d.y + d.height > p.y) && (d.y + d.height < p.y + p.height + doodle.getVelocity().y * delta));
					if (f) {
						platforms.get(i).collisionReaction();
					}
				}
			}
		}
	}
	
	public Doodle getDoodle() {
		return doodle;
	}
	
	public boolean isGameOver() {
		return isFly;
	}
	
	public int getScores() {
		return scores;
	}
	
	public int getPlatCount() {
		return platforms.size();
	}
	
	public int getBonCount() {
		return bonuses.size();
	}
	
	public int getMonCount() {
		return monsters.size();
	}
}
