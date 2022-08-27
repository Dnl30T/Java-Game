package com.dnlStudios.projectiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dnlStudios.entities.Projectile;
import com.dnlStudios.main.Main;
import com.dnlStudios.world.Camera;
import com.dnlStudios.world.Hud;

public class SwordHitBox extends Projectile{
	
	private double dx, dy;

	private int frames = 0, maxFrames = 1, index = 0, maxIndex = 1;

	public SwordHitBox(int x, int y, int width, int height, BufferedImage sprite, double dx2, double dy2) {
		super(x, y, width, height, sprite, dx2, dy2);
		this.setDamage(10);
		this.dx = dx2;
		this.dy = dy2;
	}
	public void tick() {
		//System.out.println(dx);
		//System.out.println(dy);
		x+=dx*14;
		y+=dy*14;
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
				Main.projectile.remove(this);
			}
		}
		
	}
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		if(Hud.advancedHud) {
			g.fillRect(this.getX()-Camera.x, this.getY()-Camera.y, this.width, this.height);
			g.setColor(Color.pink);
			g.fillRect(Main.player.getX()+4-Camera.x, Main.player.getY()+8-Camera.y, Main.player.pW, Main.player.pH);
		}
	}
}

