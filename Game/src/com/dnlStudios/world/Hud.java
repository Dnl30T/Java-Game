package com.dnlStudios.world;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dnlStudios.entities.Entity;
import com.dnlStudios.main.Main;
import com.dnlStudios.player.Player;
import com.dnlStudios.player.PlayerInventory;
import com.dnlStudios.player.StaminaController;

public class Hud {
	
	private BufferedImage uiPortrait = Main.spritesheet.getSprite(0, 320, 80, 32);
	private BufferedImage holder = Main.spritesheet.getSprite(0, 352, 80, 32);
	private BufferedImage healthbar = Main.spritesheet.getSprite(80, 320, 6, 3);
	private BufferedImage staminabar = Main.spritesheet.getSprite(80, 323, 6, 3);
	private BufferedImage itemFrame = Main.spritesheet.getSprite(0, 384, 32, 32);
	private BufferedImage startSword = Main.spritesheet.getSprite(144, 32, 16, 16);
	
	public boolean visibility = false;
	public static boolean advancedHud = false;
	public boolean AHSwitch = true;
	public static int highScore, score;
	public boolean gameover = false;
	
	public void tick() {
		if(Main.gameState == "gameover") {
			this.gameover = true;
		}
	}
	public void render(Graphics g) {
		g.drawImage(uiPortrait, 15, 10, null);
		g.drawImage(holder, 128, 10, null);
		for(int i = 0; i < (Player.health/2)-4; i++) {
			g.drawImage(healthbar, 40+i, 12, null);
		}
		for(int i = 0; i < (StaminaController.stamina/2)-11; i++) {
			g.drawImage(staminabar, 40+i, 18, null);
		}
		g.drawImage(itemFrame, 15, 205, null);
		
		if(Main.player.changeWeapon == 0) {
			//g.drawString("Dmg:"+SwordHitBox.damage, 90, 90);
			g.drawImage(startSword, 22, 212, null);
		}else if(Main.player.changeWeapon == 1) {
			//g.drawString("Dmg:"+ArrowProjectile.damage, 90, 90);
			g.drawImage(Entity.En_Bow, 22, 212, null);
		}
		for(int i = 0; i<PlayerInventory.potions;i++) {
			g.drawImage(Entity.En_LifePack, 13+(10*i), 48, null);
		}
		if(Main.enemies.size() == 0) {
			g.drawString("Pressione enter para avançar", 90, 230);
		}
		g.setFont(new Font("consolas",Font.PLAIN,9));
		g.setColor(Color.white);
		g.drawString(Integer.toString(PlayerInventory.coins), 48, 31);
	}
	public void renderInfo(Graphics g) {
		if(advancedHud == true) {
			g.drawString("Fps: "+Main.frames, 20, 30);
			g.drawString("x: "+Main.player.getX(), 20, 60);
			g.drawString("y: "+Main.player.getY(), 20, 90);
			g.drawString("camera x: "+Camera.x, 20, 120);
			g.drawString("camera y: "+Camera.y, 20, 150);
			g.drawString("camera x2: "+(Camera.x + Main.width), 20, 180);
			g.drawString("camera y2: "+(Camera.y + Main.height), 20, 210);
			g.drawString("has bow: "+Boolean.toString(Main.player.hasBow), 20, 240);
			g.drawString("projectiles: "+Integer.toString(Main.projectile.size()), 20, 270);
			g.drawString("enemies: "+Integer.toString(Main.enemies.size()), 20, 300);
			g.drawString("entities: "+Integer.toString(Main.entities.size()), 20, 330);
			g.drawString("weapon state: "+Main.player.changeWeapon, 20, 360);
			g.drawString("Speed: "+Main.player.speed, 20, 390);
		}
		g.setColor(Color.black);
		g.setColor(Color.white);
		g.drawString("Arrows: "+PlayerInventory.arrows,50, 130);
		g.setFont(new Font("canterbury",Font.PLAIN,60));
		g.drawString("Stage: "+(Main.currentLevel+1),135*3, 45*2-8);
		//g.drawString("HighScore: "+highScore,135*3-55, 25*3);
		g.setColor(Color.red);
		g.setFont(new Font("canterbury",Font.PLAIN,40));
	}

}
