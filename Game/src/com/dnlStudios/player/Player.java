package com.dnlStudios.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.dnlStudios.entities.Arrow;
import com.dnlStudios.entities.Bow;
import com.dnlStudios.entities.Coin;
import com.dnlStudios.entities.Entity;
import com.dnlStudios.entities.Lifepack;
import com.dnlStudios.entities.Projectile;
import com.dnlStudios.main.Main;
import com.dnlStudios.projectiles.ArrowProjectile;
import com.dnlStudios.projectiles.SwordHitBox;
import com.dnlStudios.world.Camera;
import com.dnlStudios.world.Hud;
import com.dnlStudios.world.World;

@SuppressWarnings("unused")
public class Player extends Entity{
	
	public int maskX = 4, maskY = 8,pW = 7, pH = 8;
	public boolean up,down,left,right,tp;
	public int mouseX, mouseY;
	public int facingDirection = 2, shotType;
	public double speed = 1.0, angle;
	
	public double maxHealth = 100;
	public static double health = 100;
	
	public boolean hasBow, isShooting, isAtacking, hasArrows;
	public boolean shot, mouseShot,swordAttack, offset;
	public boolean godMode = false;
	public boolean invincibleFrame = false,fade;
	
	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 3;
	private int bframes = 0, bmaxFrames = 6, bindex = 0, bmaxIndex = 4;
	private int dframes = 0, dmaxFrames = 5, dindex = 0, dmaxIndex = 3;
	private int sframes = 0, smaxFrames = 6, sindex = 0, smaxIndex = 3;
	private int tframes = 0, tmaxFrames = 5, tindex = 0, tmaxIndex = 5;
	private int fframes = 0, fmaxFrames = 5, findex = 0, fmaxIndex = 5;
	public int changeWeapon;
	public int maxChange;
	private boolean isMoving = false;
	public boolean isRunning;
	private boolean isDamaged = false;
	
	public PlayerAnimation animate;
	public StaminaController stamina = new StaminaController(100,100,1,2);
	public PlayerInventory inventory;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		inventory = new PlayerInventory();
		animate = new PlayerAnimation();
		setCollisionMask(maskX,maskY,pW,pH);
	}
	
	public void damagePlayer(double damageTaken) {
		if(invincibleFrame == false) {
			if(health > 0)
				health -= damageTaken;
			isDamaged=true;
			invincibleFrame = true;
			if(health <= 0) {
				Main.gameState="gameover";
			}
		}
	}
	public void healPlayer(double healAmount) {
		health += healAmount;
		if(health > 100)
			health=100;
	}
	
	public void collisionItems() {
		for(int i = 0; i < Main.entities.size(); i++) {
			Entity e = Main.entities.get(i);
			if(e instanceof Lifepack) {
				if(Entity.isColliding(this, e)) {
					if(PlayerInventory.potions < inventory.potionSlot) {
						inventory.addPotion();
						Main.entities.remove(i);
					}
				}
			}
			if(e instanceof Arrow) {
				if(Entity.isColliding(this, e)) {
					if(PlayerInventory.arrows < inventory.quill) {
						Main.player.inventory.addArrow(10);
					}
					Main.entities.remove(i);
				}
			}
			if(e instanceof Bow) {
				if(Entity.isColliding(this, e)) {
					this.hasBow = true;
					this.maxChange++;
					Main.entities.remove(i);
				}
			}
			if(e instanceof Coin) {
				if(Entity.isColliding(this, e)) {
					inventory.addCoin(1);
					Main.entities.remove(i);
				}
			}
		}
	}
	public void tick() {
		System.out.println(mouseX+"-"+mouseY);
		stamina.update();
		if(isRunning) {
			stamina.isSprinting=true;
			stamina.sprinting();
		}else if(!isRunning) {
			stamina.isSprinting=false;
		}
		if(godMode) {
			if(health < maxHealth) {
				health = maxHealth;
			}
		}
		if(PlayerInventory.arrows == 0) {
			hasArrows = false;
			isShooting = false;
		}else {
			hasArrows = true;
		}
		//System.out.println(hasArrows);
		collisionItems();
		isMoving = false;
		if(tp) {
			isMoving = false;
			tframes++;
			if(tframes == tmaxFrames) {
				tframes = 0;
				tindex++;
				if(tindex > tmaxIndex) {
					tindex=0;
					fade=true;
					invincibleFrame = true;
					StaminaController.stamina-=100;
					if(right && World.isFree((int)(x + maskX + 48), (int)y + maskY,pW,pH))
						x+=48;
					if(left && World.isFree((int)(x + maskX - 48), (int)y + maskY,pW,pH))
						x-=48;
					if(down && World.isFree((int)(x)+ maskX, (int)(y + maskY +48),pW,pH))
						y+=48;
					if(up && World.isFree((int)(x)+ maskX, (int)(y + maskY -48),pW,pH))
						y-=48;
				}
			}
		}
		if(fade) {
			fframes++;
			if(fframes == fmaxFrames) {
				fframes = 0;
				findex++;
				if(findex > fmaxIndex) {
					findex = 0;
					tindex = 0;
					fade=false;
					tp=false;
					invincibleFrame = false;
				}
			}
		}
		if(!isAtacking && !tp) {
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
		if(changeWeapon == 0) {
			if(isAtacking) {
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
				sframes++;
				if(sframes == smaxFrames) {
					sframes = 0;
					sindex++;
					if(sindex > smaxIndex) {
						sindex = 0;
						isAtacking = false;
					}
					if(sindex == 1) {
						swordAttack = true;
					}
				}
			}
		}
		//System.out.println(arrows);
		if(changeWeapon == 1) {
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
		}
		Camera.x = Camera.clamp(this.getX() - (Main.width/2), 0, World.worldWidth*16 - Main.width);
		Camera.y = Camera.clamp(this.getY() - (Main.height/2), 0, World.worldHeight*16 - Main.height);
		if(mouseShot) {
			mouseShot = false;
			double angle = (
				Math.atan2(mouseY-(this.getY()+8 - Camera.y), mouseX-(this.getX()+8 - Camera.x))
			);
				if(hasBow && PlayerInventory.arrows > 0) {
					double dx = Math.cos(angle);
					double dy = Math.sin(angle);
					Projectile projectile = new ArrowProjectile(this.getX(),this.getY(),8,8,En_Arrow,dx,dy);
					Main.projectile.add(projectile);
					PlayerInventory.arrows--;
			}
		}
		if(swordAttack) {
			swordAttack = false;
			double angle = (
				Math.atan2(mouseY-(this.getY()+8 - Camera.y), mouseX-(this.getX()+8 - Camera.x))
			);
			this.angle = angle;
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				int x1=0,y1=0;
				int x2=0,y2=0;
				if(facingDirection == 1) {
					x1 = 32;
					y1 = 16;
					x2 = -6;
					y2 = -4;
				}else if(facingDirection == 2) {
					x1 = 32;
					y1 = 16;
					x2 =-10;
					y2 = +4;
				}
				else if(facingDirection == 3) {
					x1 = 16;
					y1 = 32;
					x2 = +4;
					y2 = -4;
				}
				else if(facingDirection == 4) {
					x1 = 16;
					y1 = 32;
					x2 = -4;
					y2 = -8;
				}
				SwordHitBox swordHitbox = new SwordHitBox(this.getX()+x2,this.getY()+y2,x1,y1,null,dx,dy);
				Main.projectile.add(swordHitbox);
				if(sindex > smaxIndex) {
					Main.projectile.remove(swordHitbox);
				}
		}
		if(changeWeapon == 0) {
			isShooting = false;
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
	public void renderArc(Graphics g, Graphics2D g2,double angle) {
		g.setColor(Color.red);
		//g.fillRect(this.getX()-Camera.x+8,this.getY()-Camera.y+8,2,2);
		g2.rotate(angle,this.getX()-Camera.x+8,this.getY()+8-Camera.y);
		g.drawImage(PlayerAnimation.attack[sindex], this.getX()-Camera.x+18, this.getY()-Camera.y-5, null);
		g2.rotate(-angle,this.getX()-Camera.x+8,this.getY()+8-Camera.y);	
	}
	public void playerAnimation(Graphics g, int dir,int x, int y, boolean atk) {
		//g.drawString(Boolean.toString(atk), x,y-20);
		Graphics2D g2 = (Graphics2D) g;
		if(atk) {
			if(facingDirection == 3) {
				g.drawImage(PlayerAnimation.SwordAttackRight[sindex], x,y,null);
				renderArc(g,g2,angle);
			}
			else if(facingDirection == 4) {
				g.drawImage(PlayerAnimation.SwordAttackLeft[sindex], x,y,null);
				renderArc(g,g2,angle);
			}
			else if(facingDirection == 2) {
				g.drawImage(PlayerAnimation.SwordAttackDown[sindex], x,y,null);
				renderArc(g,g2,angle);
			}
			else if(facingDirection == 1) {
				g.drawImage(PlayerAnimation.SwordAttackUp[sindex], x-6,y-4,null);
				renderArc(g,g2,angle);
			}
		}else {
			if(facingDirection == 3 && isMoving) {
				g.drawImage(PlayerAnimation.playerRight[index], x,y,null);
			}
			else if(facingDirection == 4 && isMoving) {
				g.drawImage(PlayerAnimation.playerLeft[index], x,y,null);
			}
			else if(facingDirection == 1 && isMoving) {
				g.drawImage(PlayerAnimation.playerDown[index], x,y,null);
			}
			else if(facingDirection == 2 && isMoving) {
				g.drawImage(PlayerAnimation.playerUp[index], x,y,null);
			}
		}
	}
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int relativeX = this.getX()-Camera.x;
		int relativeY = this.getY()-Camera.y;
		if(tp && fade == false) {
			g.drawImage(PlayerAnimation.bobTp[tindex],relativeX,relativeY,null);
		}else if(tp && fade) {
			g.drawImage(PlayerAnimation.bobTpFade[tindex],relativeX,relativeY,null);
		}
		else if(isDamaged == false) {
			if(changeWeapon == 0) {
				if(hasBow == true) {
					if(facingDirection == 3 && isMoving) {
						g.drawImage(En_Bow,relativeX,relativeY,null);
						playerAnimation(g,facingDirection,relativeX,relativeY,isAtacking);
					}
					else if(facingDirection == 4 && isMoving) {
						g.drawImage(En_Bow,relativeX,relativeY,null);
						playerAnimation(g,facingDirection,relativeX,relativeY,isAtacking);
					}
					else if(facingDirection == 1 && isMoving) {
						playerAnimation(g,facingDirection,relativeX,relativeY,isAtacking);
						g.drawImage(En_Bow, relativeX,relativeY,null);
					}
					else if(facingDirection == 2 && isMoving) {
						g.drawImage(En_Bow,relativeX,relativeY,null);
						playerAnimation(g,facingDirection,relativeX,relativeY,isAtacking);
					}else {
						if(!isAtacking) {
							if(facingDirection != 1)
								g.drawImage(En_Bow,relativeX,relativeY,null);
							g.drawImage(PlayerAnimation.playerIdle[facingDirection-1],relativeX,relativeY,null);
							if(facingDirection == 1)
								g.drawImage(En_Bow,relativeX,relativeY,null);
						}else {
							if(facingDirection != 1)
								g.drawImage(En_Bow,relativeX,relativeY,null);
							playerAnimation(g,facingDirection,relativeX,relativeY,isAtacking);
							//g.drawString(Boolean.toString(isAtacking), relativeX, relativeY);
							if(facingDirection == 1)
								g.drawImage(En_Bow,relativeX,relativeY,null);
						}
					}
				}else if(hasBow == false) {
					if(facingDirection == 3 && isMoving) {
						playerAnimation(g,facingDirection,relativeX,relativeY,isAtacking);
					}
					else if(facingDirection == 4 && isMoving) {
						playerAnimation(g,facingDirection,relativeX,relativeY,isAtacking);
					}
					else if(facingDirection == 1 && isMoving) {
						playerAnimation(g,facingDirection,relativeX,relativeY,isAtacking);
					}
					else if(facingDirection == 2 && isMoving) {
						playerAnimation(g,facingDirection,relativeX,relativeY,isAtacking);
					}else {
						if(!isAtacking)
							g.drawImage(PlayerAnimation.playerIdle[facingDirection-1],relativeX,relativeY,null);
						else {
							playerAnimation(g,facingDirection,relativeX,relativeY,isAtacking);
						}
					}
				}
			}
			if(hasBow && changeWeapon == 1) {
				if(facingDirection == 3 && isMoving) {
					g.drawImage(PlayerAnimation.playerWeaponRight[index], relativeX,relativeY,null);
					if(hasBow) {
						renderBow(g,g2,facingDirection,Bow.bowRightSpr);
					}
				}
				else if(facingDirection == 4 && isMoving) {
					g.drawImage(PlayerAnimation.playerWeaponLeft[index],relativeX,relativeY,null);
					renderBow(g,g2,facingDirection,Bow.bowRightSpr);
				}
				else if(facingDirection == 1 && isMoving) {
					renderBow(g,g2,facingDirection,Bow.bowRightSpr);
					g.drawImage(PlayerAnimation.playerWeaponDown[index], relativeX,relativeY,null);
				}
				else if(facingDirection == 2 && isMoving) {
					g.drawImage(PlayerAnimation.playerWeaponUp[index],relativeX,relativeY,null);
					renderBow(g,g2,facingDirection,Bow.bowRightSpr);
				}
				else {
					if(facingDirection == 1) {
						renderBow(g,g2,facingDirection,Bow.bowRightSpr);
					}
					g.drawImage(PlayerAnimation.playerWeaponIdle[facingDirection-1],relativeX,relativeY,null);
					if(facingDirection != 1) {
						renderBow(g,g2,facingDirection,Bow.bowRightSpr);
					}
				}
			}
		}else {
			g.drawImage(PlayerAnimation.feedback,relativeX,relativeY,null);
			if(hasBow && changeWeapon == 1) {
				g2.rotate(Math.toRadians(270), this.getX()+9 - Camera.x, this.getY()+9-Camera.y);
				g.drawImage(Bow.bowDamageSpr, this.getX() - Camera.x, this.getY()-Camera.y, null);
				g2.rotate(Math.toRadians(-270), this.getX()+9 - Camera.x, this.getY()+9-Camera.y);
			}
		}
		if(Hud.advancedHud == true) {
			g.setColor(Color.red);
			//g.fillRect(this.getX() + maskX - Camera.x, this.getY()  + maskY - Camera.y, pW, pH);
		}
	}

}
