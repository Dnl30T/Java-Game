package com.dnlStudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import com.dnlStudios.main.Main;
import com.dnlStudios.world.Camera;
import com.dnlStudios.world.Hud;

public class Entity {
	
	public static BufferedImage En_LifePack = Main.spritesheet.getSprite(128, 0, 16, 16);
	public static BufferedImage En_Arrow = Main.spritesheet.getSprite(128, 16, 16, 16);
	public static BufferedImage En_Bow = Main.spritesheet.getSprite(128, 32, 16, 16);
	public static BufferedImage En_Enemy = Main.spritesheet.getSprite(176, 0, 16, 16);
	public static BufferedImage En_Coin = Main.spritesheet.getSprite(176, 160, 9, 7);

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	private int maskX, maskY, mW, mH;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskX = 0;
		this.maskY = 0;
		this.mW = width;
		this.mH = height;
	}
	
	public void setCollisionMask(int maskX, int maskY, int mW, int mH) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.mW = mW;
		this.mH = mH;
	}
	public int getMasks(int index) {
		switch (index){
		case 1:
			return this.mW;
		case 2:
			return this.mH;
		case 3:
			return this.maskX;
		case 4:
			return this.maskY;
		default:
			return 0;
		}
		
	}
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1M = new Rectangle(e1.getX() + e1.maskX,e1.getY()+e1.maskY,e1.mW,e1.mH);
		Rectangle e2M = new Rectangle(e2.getX() + e2.maskX,e2.getY()+e2.maskY,e2.mW,e2.mH);
		
		return e1M.intersects(e2M);
	}
	
	public void tick() {
		
	}
	public void render(Graphics g) {
			if(Hud.advancedHud == true) {
				g.setColor(Color.yellow);
				g.fillRect(this.getX() + maskX - Camera.x, this.getY() + maskY - Camera.y, mW, mH);
			}	
			g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
	}
	
	//get - set X
	public int getX() {
		return (int)this.x;
	}
	public int setX(int X) {
		return (int) (this.x = X);
	}
	//get - set Y
	public int getY() {
		return (int)this.y;
	}
	public int setY(int Y) {
		return (int) (this.y = Y);
	}
	
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
}
