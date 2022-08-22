package com.dnlStudios.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dnlStudios.entities.Enemy;
import com.dnlStudios.main.Main;
import com.dnlStudios.world.Camera;
import com.dnlStudios.world.Hud;
import com.dnlStudios.world.World;

public class SkullEnemy extends Enemy{
	
	protected double speed = 0.2;
	protected double maxSpeed = 0.6;
	protected int tickDistance = 240;
	
	public int facingDirection = 1;
	
	protected int frames = 0, maxFrames = 10, index = 0, maxIndex = 2;
	//protected int enW,enH;
	//protected int maskX, maskY;
	
	protected BufferedImage[] enSprUp;
	protected BufferedImage[] enSprDown;
	protected BufferedImage[] enSprRight;
	protected BufferedImage[] enSprLeft;
	protected BufferedImage feedback = Main.spritesheet.getSprite(256, 56, 16, 24);

	public SkullEnemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.setCollisionMask(0, 0, 16, 16);
		setHealth(75);
		this.enH = 16;
		this.enW = 16;
		this.maskX = 0;
		this.maskY = 0;
		enSprUp = new BufferedImage[3];
		enSprDown = new BufferedImage[3];
		enSprRight = new BufferedImage[3];
		enSprLeft = new BufferedImage[3];
		for(int i = 0; i < 3; i++)
			enSprUp[i] = Main.spritesheet.getSprite(256 + (i*16), 0, 16, 23);
		for(int i = 0; i < 3; i++)
			enSprDown[i] = Main.spritesheet.getSprite(256 + (i*16), 23, 16, 23);
		for(int i = 0; i < 3; i++)
			enSprRight[i] = Main.spritesheet.getSprite(256 + (i*16), 80, 16, 25);
		for(int i = 0; i < 3; i++)
			enSprLeft[i] = Main.spritesheet.getSprite(256 + (i*16), 112, 16, 25);
	}
	public void tick() {
		speed = Main.rand.nextDouble(speed,maxSpeed);
		collidingEntity();
		if(this.x >= Camera.x - tickDistance
			&& this.y >= Camera.y - tickDistance
			&& this.x <= Camera.x + Main.width +tickDistance
			&& this.y <= Camera.y + Main.height + tickDistance
			) {
			if(!isDamaged) {
				if(checkPlayerCollision() ==  false) {
					if(Main.rand.nextInt(100) < 60) {
						if((int)y < Main.player.getY() && World.isFree((int)(x+maskX), (int)(y +maskY + speed),enW,enH)
								&& !this.isColliding((int)(x), (int)(y + speed))
								) {
							y	+= speed;
							facingDirection = 1;
						}else if((int)y > Main.player.getY() && World.isFree((int)(x+maskX), (int)(y +maskY - speed),enW,enH)
								&& !this.isColliding((int)(x), (int)(y - speed))
								) {
							y	-= speed;
							facingDirection = 2;
						}if((int)x < Main.player.getX() && World.isFree((int)(x+maskX+speed), (int)y+maskY,enW,enH)
								&& !this.isColliding((int)(x+speed), (int)y)
								) {
							x+= speed;
							facingDirection = 3;
						}else if((int)x > Main.player.getX() && World.isFree((int)(x+maskX-speed), (int)y+maskY,enW,enH)
								&& !this.isColliding((int)(x-speed), (int)y)
								) {
							x	-= speed;
							facingDirection = 4;
						}
						frames++;
						if(frames == maxFrames) {
							frames = 0;
							index++;
							if(index > maxIndex) {
								index = 0;
							}
						}
					}
				}else {
					if(Main.rand.nextInt(100) < 10) {
						Main.player.damagePlayer(30);
					}	
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
				}
			}
		}
	}
	public void render(Graphics g) {
		if(!isDamaged) {
			//System.out.println(facingDirection);
			if(facingDirection == 3)
				g.drawImage(enSprRight[index], this.getX() - Camera.x,this.getY()-Camera.y,null);
			else if(facingDirection == 4)
				g.drawImage(enSprLeft[index], this.getX() - Camera.x,this.getY()-Camera.y,null);
			else if(facingDirection == 1)
				g.drawImage(enSprUp[index], this.getX() - Camera.x,this.getY()-Camera.y,null);
			else if(facingDirection == 2)
				g.drawImage(enSprDown[index], this.getX() - Camera.x,this.getY()-Camera.y,null);
		}else {
			g.drawImage(feedback, this.getX() - Camera.x,this.getY()-Camera.y,null);
		}
		if(Hud.advancedHud == true) {
			g.setColor(Color.blue);
			g.fillRect(getX() + maskX - Camera.x, getY() + maskY - Camera.y, enW, enH);
			g.setColor(Color.red);
			g.fillRect(Main.player.getX() + Main.player.maskX - Camera.x, Main.player.getY() + Main.player.maskY - Camera.y, Main.player.pW, Main.player.pH);
		}
	}

}