package com.dnlStudios.entities;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.dnlStudios.main.Main;
import com.dnlStudios.world.Camera;
import com.dnlStudios.world.Hud;
import com.dnlStudios.world.World;

public class Projectile extends Entity{
	
	private double dx, dy;
	public double spd = 4, angle;
	private BufferedImage sprite;
	public static int damage;
	private boolean getAngle;
	public boolean killSelf;
	
	public boolean frostDamage;
	public boolean venomDamage;

	public Projectile(int x, int y, int width, int height, BufferedImage sprite,double dx2,double dy2) {
		super(x, y, width, height, sprite);
		this.sprite = sprite;
		this.dx = dx2;
		this.dy = dy2;
	}
	
	public void setDamage(int damage) {
		Projectile.damage = damage;
	}
	
	public void tick() {
		if(killSelf) {
			Main.projectile.remove(this);
		}
		//System.out.println(this.getX()+" "+this.getY()+" "+this.width+" "+this.height);
		if(World.isFree((int)x,(int)y+6,width,height)) {
			x+=dx*spd;
		}else {
			Main.projectile.remove(this);
		}
		if(World.isFree((int)x,(int)y+6,width,height)) {
			y+=dy*spd;
		}else {
			Main.projectile.remove(this);
		}
		if(!(this.x >= Camera.x - 16 
				&& this.y >= Camera.y - 16 
				&& this.x <= Camera.x + Main.width 
				&& this.y <= Camera.y + Main.height
				)) {
			Main.projectile.remove(this);
		}
		int mouseY=Main.player.mouseY;
		int mouseX=Main.player.mouseX;
		if(getAngle == false) {
			angle = (
					Math.atan2(mouseY-(this.getY()+8 - Camera.y), mouseX-(this.getX()+8 - Camera.x))
				);
			getAngle = true;
		}
	}
	public void rotateProjectile(Graphics g, Graphics2D g2, int angle, BufferedImage spr) {
		g2.rotate(Math.toRadians(angle),this.getX()-Camera.x+8,this.getY()+8-Camera.y);
		g.drawImage(sprite, this.getX()-Camera.x, this.getY()-Camera.y, null);
		g2.rotate(Math.toRadians(-angle),this.getX()-Camera.x+8,this.getY()+8-Camera.y);	
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.yellow);
		if(Hud.advancedHud)
			g.fillRect(this.getX()-Camera.x, this.getY()-Camera.y, this.width, this.height);
		if(dx == -1) {
			rotateProjectile(g,g2,180,sprite);
			//180
		}else if(dx == 1) {
			rotateProjectile(g,g2,0,sprite);
		}else if(dy == 1) {
			//270
			rotateProjectile(g,g2,90,sprite);
		}else if(dy == -1){
			rotateProjectile(g,g2,270,sprite);
		}else {
			if(getAngle) {
				g2.rotate(angle,this.getX()-Camera.x+8,this.getY()+8-Camera.y);
				g.drawImage(sprite, this.getX()-Camera.x, this.getY()-Camera.y, null);
				g2.rotate(-angle,this.getX()-Camera.x+8,this.getY()+8-Camera.y);	
			}
		}
	}

}
