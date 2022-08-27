package com.dnlStudios.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dnlStudios.entities.Enemy;
import com.dnlStudios.main.Main;
import com.dnlStudios.world.Camera;
import com.dnlStudios.world.Hud;
import com.dnlStudios.world.World;

public class MiniMushroomEnemy extends Enemy{
	
	protected double speed = 0.2;
	protected double maxSpeed = 1.0;
	protected int tickDistance = 340;
	
	public int facingDirection = 1;
	
	protected int frames = 0, maxFrames = 10, index = 0, maxIndex = 2;
	
	protected BufferedImage[] enSpr;
	protected BufferedImage feedback = Main.spritesheet.getSprite(176, 64, 16, 16);
	
	public boolean isColliding(int nextX, int nextY) {
		return false;
	}

	public MiniMushroomEnemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.setCollisionMask(0, 0, 8, 8);
		setHealth(10);
		this.enH = 8;
		this.enW = 8;
		this.maskX = 0;
		this.maskY = 0;
		enSpr = new BufferedImage[3];
		for(int i = 0; i < 3; i++)
			enSpr[i] = Main.spritesheet.getSprite(176 + (i*16), 64, 16, 16);
	}
	public void tick() {
		speed = Main.rand.nextDouble(speed,maxSpeed);
		collidingEntity();
		if(this.x >= Camera.x - tickDistance
			&& this.y >= Camera.y - tickDistance
			&& this.x <= Camera.x + Main.width +tickDistance
			&& this.y <= Camera.y + Main.height + tickDistance
			) {
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
					Main.player.damagePlayer(5);
				}	
			}
		}
		if(isDamaged) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
					this.isDamaged=false;
					this.invincibleFrame=false;
				}
			}
		}
	}
	public void render(Graphics g) {
		if(!isDamaged) {
			//System.out.println(facingDirection);
			g.drawImage(enSpr[index], this.getX() - Camera.x,this.getY()-Camera.y,null);
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