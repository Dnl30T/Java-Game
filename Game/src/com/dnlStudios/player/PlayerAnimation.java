package com.dnlStudios.player;

import java.awt.image.BufferedImage;

import com.dnlStudios.main.Main;

public class PlayerAnimation {
	
protected static BufferedImage feedback = Main.spritesheet.getSprite(0, 80, 16, 16);
	
	public static BufferedImage[] playerRight;
	public static BufferedImage[] playerLeft;
	public static BufferedImage[] playerIdle;
	public static BufferedImage[] playerUp;
	public static BufferedImage[] playerDown;
	
	public static BufferedImage[] attack;
	public static BufferedImage[] bobTp;
	public static BufferedImage[] bobTpFade;
	
	public static BufferedImage[] SwordAttackRight;
	public static BufferedImage[] SwordAttackLeft;
	public static BufferedImage[] SwordAttackUp;
	public static BufferedImage[] SwordAttackDown;
	
	public static BufferedImage[] playerWeaponRight;
	public static BufferedImage[] playerWeaponLeft;
	public static BufferedImage[] playerWeaponIdle;
	public static BufferedImage[] playerWeaponUp;
	public static BufferedImage[] playerWeaponDown;
	
	public PlayerAnimation(){
		
		attack = new BufferedImage[4];
		bobTp = new BufferedImage[6];
		bobTpFade = new BufferedImage[6];
		
		playerRight = new BufferedImage[4];
		playerLeft = new BufferedImage[4];
		playerUp = new BufferedImage[4];
		playerDown = new BufferedImage[4];
		playerIdle = new BufferedImage[4];
		
		SwordAttackRight = new BufferedImage[4];
		SwordAttackLeft = new BufferedImage[4];
		SwordAttackUp = new BufferedImage[4];
		SwordAttackDown = new BufferedImage[4];
		
		playerWeaponRight = new BufferedImage[4];
		playerWeaponLeft = new BufferedImage[4];
		playerWeaponUp = new BufferedImage[4];
		playerWeaponDown = new BufferedImage[4];
		playerWeaponIdle = new BufferedImage[4];
		
		for(int i = 0; i < 4; i++) {
			attack[i] = Main.spritesheet.getSprite(96+(i*16), 80, 16, 32);
		}
		for(int i = 0; i < 6; i++) {
			bobTp[i] = Main.spritesheet.getSprite(0+(i*16), 272, 16, 16);
		}
		for(int i = 0; i < 6; i++) {
			bobTpFade[i] = Main.spritesheet.getSprite(0+(i*16), 288, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			SwordAttackDown[i] = Main.spritesheet.getSprite(0+(i*16), 176, 16, 32);
		}
		for(int i = 0; i < 4; i++) {
			SwordAttackRight[i] = Main.spritesheet.getSprite(0+(i*16), 208, 16, 20);
		}
		for(int i = 0; i < 4; i++) {
			SwordAttackLeft[i] = Main.spritesheet.getSprite(80+(i*16), 208, 16, 20);
		}
		for(int i = 0; i < 4; i++) {
			SwordAttackUp[i] = Main.spritesheet.getSprite(0+(i*24), 232, 24, 24);
		}
		
		for(int i = 0; i < 4; i++) {
			playerRight[i] = Main.spritesheet.getSprite(0+(i*16), 0, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			playerLeft[i] = Main.spritesheet.getSprite(0+(i*16), 16, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			playerIdle[i] = Main.spritesheet.getSprite(0+(i*16), 32, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			playerUp[i] = Main.spritesheet.getSprite(0+(i*16), 48, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			playerDown[i] = Main.spritesheet.getSprite(0+(i*16), 64, 16, 16);
		}
		
		for(int i = 0; i < 4; i++) {
			playerWeaponRight[i] = Main.spritesheet.getSprite(0+(i*16), 96, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			playerWeaponLeft[i] = Main.spritesheet.getSprite(0+(i*16), 112, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			playerWeaponIdle[i] = Main.spritesheet.getSprite(0+(i*16), 128, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			playerWeaponUp[i] = Main.spritesheet.getSprite(0+(i*16), 144, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			playerWeaponDown[i] = Main.spritesheet.getSprite(0+(i*16), 160, 16, 16);
		}
		
	}

}
