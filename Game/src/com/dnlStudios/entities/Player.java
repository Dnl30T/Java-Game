package com.dnlStudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.dnlStudios.main.Main;
import com.dnlStudios.projectiles.ArrowProjectile;
import com.dnlStudios.world.Camera;
import com.dnlStudios.world.World;

@SuppressWarnings("unused")
public class Player extends Entity{
	
	public int maskX = 4, maskY = 8,pW = 7, pH = 8;
	public boolean up,down,left,right;
	public int mouseX, mouseY;
	public int facingDirection = 2, shotType;
	public double speed = 1.0 ;
	
	public double maxHealth = 100;
	public static double health = 100;
	
	public boolean hasBow, isShooting, hasArrows;
	public boolean shot, mouseShot, offset;
	public static int arrows = 99;
	public boolean godMode = false;
	public boolean invincibleFrame = false;
	
	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 3;
	private int bframes = 0, bmaxFrames = 10, bindex = 0, bmaxIndex = 3;
	private int dframes = 0, dmaxFrames = 5, dindex = 0, dmaxIndex = 3;
	private boolean isMoving = false;
	private boolean isDamaged = false;
	
	protected BufferedImage feedback = Main.spritesheet.getSprite(0, 80, 16, 16);
	
	private BufferedImage[] playerRight;
	private BufferedImage[] playerLeft;
	private BufferedImage[] playerIdle;
	private BufferedImage[] playerUp;
	private BufferedImage[] playerDown;
	
	private BufferedImage[] playerWeaponRight;
	private BufferedImage[] playerWeaponLeft;
	private BufferedImage[] playerWeaponIdle;
	private BufferedImage[] playerWeaponUp;
	private BufferedImage[] playerWeaponDown;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		setCollisionMask(maskX,maskY,pW,pH);
		
		playerRight = new BufferedImage[4];
		playerLeft = new BufferedImage[4];
		playerUp = new BufferedImage[4];
		playerDown = new BufferedImage[4];
		playerIdle = new BufferedImage[4];
		
		playerWeaponRight = new BufferedImage[4];
		playerWeaponLeft = new BufferedImage[4];
		playerWeaponUp = new BufferedImage[4];
		playerWeaponDown = new BufferedImage[4];
		playerWeaponIdle = new BufferedImage[4];
		
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
	
	public void damagePlayer(double damageTaken) {
		if(invincibleFrame == false) {
			if(health > 0)
				health -= damageTaken;
			isDamaged=true;
			invincibleFrame = true;
			if(health <= 0) {
				//K.O
				//Main.startLevel("map1");
				Main.gameState="gameover";
			}
		}
	}
	
	public void collisionItems() {
		for(int i = 0; i < Main.entities.size(); i++) {
			Entity e = Main.entities.get(i);
			if(e instanceof Lifepack) {
				if(Entity.isColliding(this, e)) {
					if(health < maxHealth) {
						Lifepack.healPlayer(10);
						Main.entities.remove(i);
					}
				}
			}
			if(e instanceof Arrow) {
				if(Entity.isColliding(this, e)) {
					arrows+=10;
					Main.entities.remove(i);
				}
			}
			if(e instanceof Bow) {
				if(Entity.isColliding(this, e)) {
					this.hasBow = true;
					Main.entities.remove(i);
				}
			}
		}
	}
	
	public void tick() {
		if(godMode) {
			if(health < maxHealth) {
				health = maxHealth;
			}
		}
		if(arrows == 0) {
			hasArrows = false;
			isShooting = false;
		}else {
			hasArrows = true;
		}
		//System.out.println(hasArrows);
		collisionItems();
		maskX = 4;
		maskY = 8;
		pW = 7;
		pH = 8;
		isMoving = false;
			if(up && World.isFree((int)(x+ maskX), (int)(y + maskY -speed),pW,pH)) {
				isMoving = true;
				y-=speed;
				facingDirection = 1;
			}else if(down && World.isFree((int)(x+ maskX), (int)(y + maskY +speed),pW,pH)) {
				isMoving = true;
				y+=speed;
				facingDirection = 2;
			}
			if(right && World.isFree((int)(x + maskX + speed), (int)y + maskY,pW,pH)) {
				isMoving = true;
				x+=speed;
				facingDirection = 3;
			}else if(left && World.isFree((int)(x + maskX - speed), (int)y + maskY,pW,pH)) {
				isMoving = true;
				x-=speed;
				facingDirection = 4;
		}
		if(isMoving) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		if(isDamaged) {
			dframes++;
			if(dframes == dmaxFrames) {
				dframes = 0;
				dindex++;
				if(dindex > dmaxIndex) {
					dindex = 0;
					this.isDamaged=false;
					invincibleFrame=false;
				}
			}
		}
		//System.out.println(arrows);
		if(hasArrows) {
			if(isShooting) {
				double angle = (
						Math.atan2(mouseY-(this.getY()+8 - Camera.y), mouseX-(this.getX()+8 - Camera.x))
					);
				if(angle > -2.256 && angle < -0.785) {
					facingDirection = 1;
				}else if(angle < 2.236 && angle > 0.785) {
					facingDirection = 2;
				}else if(angle < 0.785 && angle > -0.785) {
					facingDirection = 3;
				}else {
					facingDirection = 4;
				}
				bframes++;
				if(bframes == bmaxFrames) {
					bframes = 0;
					bindex++;
					if(bindex > bmaxIndex) {
						isShooting = false;
						if(shotType == 2)
							mouseShot = true;
						bindex = 0;
					}
				}
			}
		}
		Camera.x = Camera.clamp(this.getX() - (Main.width/2), 0, World.worldWidth*16 - Main.width);
		Camera.y = Camera.clamp(this.getY() - (Main.height/2), 0, World.worldHeight*16 - Main.height);
		if(mouseShot) {
			mouseShot = false;
			double angle = (
				Math.atan2(mouseY-(this.getY()+8 - Camera.y), mouseX-(this.getX()+8 - Camera.x))
			);
				if(hasBow && arrows > 0) {
					double dx = Math.cos(angle);
					double dy = Math.sin(angle);
					Projectile projectile = new ArrowProjectile(this.getX(),this.getY()+3,8,8,En_Arrow,dx,dy);
					Main.projectile.add(projectile);
					arrows--;
			}
		}
	}
	public void renderBow(Graphics g, Graphics2D g2, int dir,BufferedImage[] bow) {
		if(dir==1) {
			g2.rotate(Math.toRadians(90), this.getX()+8 - Camera.x, this.getY()+8-Camera.y);
			g.drawImage(bow[bindex], this.getX() - Camera.x, this.getY()-Camera.y, null);
			g2.rotate(Math.toRadians(-90), this.getX()+8 - Camera.x, this.getY()+8-Camera.y);
		}else if(dir == 2) {
			g2.rotate(Math.toRadians(270), this.getX()+11 - Camera.x, this.getY()+11-Camera.y);
			g.drawImage(bow[bindex], this.getX() - Camera.x, this.getY()-Camera.y, null);
			g2.rotate(Math.toRadians(-270), this.getX()+11 - Camera.x, this.getY()+11-Camera.y);
		}else if(dir == 3) {
			g2.rotate(Math.toRadians(180), this.getX()+9 - Camera.x, this.getY()+9-Camera.y);
			g.drawImage(bow[bindex], this.getX() - Camera.x, this.getY()-Camera.y, null);
			g2.rotate(Math.toRadians(-180), this.getX()+9 - Camera.x, this.getY()+9-Camera.y);
		}else {
			g.drawImage(bow[bindex], this.getX()-2 - Camera.x, this.getY()+2-Camera.y, null);
		}
	}
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int relativeX = this.getX()-Camera.x;
		int relativeY = this.getY()-Camera.y;
		if(isDamaged == false) {
			if(hasBow == false) {
				if(facingDirection == 3 && isMoving) {
					g.drawImage(playerRight[index], relativeX,relativeY,null);
				}
				else if(facingDirection == 4 && isMoving) {
					g.drawImage(playerLeft[index], relativeX,relativeY,null);
				}
				else if(facingDirection == 1 && isMoving) {
					g.drawImage(playerDown[index], relativeX,relativeY,null);
				}
				else if(facingDirection == 2 && isMoving) {
					g.drawImage(playerUp[index], relativeX,relativeY,null);
				}else {
					g.drawImage(playerIdle[facingDirection-1],relativeX,relativeY,null);
				}
			}
			if(hasBow) {
				if(facingDirection == 3 && isMoving) {
					g.drawImage(playerWeaponRight[index], relativeX,relativeY,null);
					if(hasBow) {
						renderBow(g,g2,facingDirection,Bow.bowRightSpr);
					}
				}
				else if(facingDirection == 4 && isMoving) {
					g.drawImage(playerWeaponLeft[index],relativeX,relativeY,null);
					renderBow(g,g2,facingDirection,Bow.bowRightSpr);
				}
				else if(facingDirection == 1 && isMoving) {
					renderBow(g,g2,facingDirection,Bow.bowRightSpr);
					g.drawImage(playerWeaponDown[index], relativeX,relativeY,null);
				}
				else if(facingDirection == 2 && isMoving) {
					g.drawImage(playerWeaponUp[index],relativeX,relativeY,null);
					renderBow(g,g2,facingDirection,Bow.bowRightSpr);
				}
				else {
					if(facingDirection == 1) {
						renderBow(g,g2,facingDirection,Bow.bowRightSpr);
					}
					g.drawImage(playerWeaponIdle[facingDirection-1],relativeX,relativeY,null);
					if(facingDirection != 1) {
						renderBow(g,g2,facingDirection,Bow.bowRightSpr);
					}
				}
			}
		}else {
			g.drawImage(feedback,relativeX,relativeY,null);
			if(hasBow) {
				g2.rotate(Math.toRadians(270), this.getX()+9 - Camera.x, this.getY()+9-Camera.y);
				g.drawImage(Bow.bowDamageSpr, this.getX() - Camera.x, this.getY()-Camera.y, null);
				g2.rotate(Math.toRadians(-270), this.getX()+9 - Camera.x, this.getY()+9-Camera.y);
			}
		}
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskX - Camera.x, this.getY()  + maskY - Camera.y, pW, pH);
	}

}
