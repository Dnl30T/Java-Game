package com.dnlStudios.world;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dnlStudios.entities.Player;
import com.dnlStudios.main.Main;

public class Hud {
	
	private BufferedImage portrait = Main.spritesheet.getSprite(736, 0, 64, 64);
	
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
		g.setColor(Color.white);
		g.fillRect(263, 18, 54, 14);
		g.setColor(Color.green);
		g.fillRect(265, 20, (int)((Player.health/Main.player.maxHealth)*50), 10);
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
		}
		g.setColor(Color.black);
		g.drawString((int)(Player.health)+"/"+(int)(Main.player.maxHealth),274*3, 28*3);
		g.setColor(Color.white);
		g.drawString("Arrows: "+Player.arrows,274*3, 45*3);
		g.setFont(new Font("canterbury",Font.PLAIN,60));
		g.drawString("Score: "+score,135*3, 45*3);
		g.drawString("HighScore: "+highScore,135*3-55, 25*3);
		g.setColor(Color.red);
		g.setFont(new Font("canterbury",Font.PLAIN,40));
		g.drawString("Stage: "+(Main.currentLevel+1),20*3, 20*3);
	}
	public void renderPortrait(Graphics g) {
		g.drawImage(portrait, 0, 0, null);
	}

}
