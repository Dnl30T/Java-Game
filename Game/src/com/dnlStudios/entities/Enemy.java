package com.dnlStudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.dnlStudios.main.Main;
import com.dnlStudios.projectiles.SwordHitBox;
import com.dnlStudios.world.Camera;
import com.dnlStudios.world.Hud;
import com.dnlStudios.world.Tileset;
import com.dnlStudios.world.World;

@SuppressWarnings("unused")
public class Enemy extends Entity{
	
	public int health;
	public int maxHealth;
	public double speed;
	protected double maxSpeed;
	protected int tickDistance;
	
	public boolean isFrozen, isPoisoned;
	
	protected int enW = 16,enH = 16;
	protected int maskX = 0, maskY = 0;
	
	protected boolean invincibleFrame; 
	protected boolean isDamaged = false;
	protected int dframes = 0, dmaxFrames = 5, dindex = 0, dmaxIndex = 3;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
	}
	
	protected void setHealth(int health){
		this.health = health;
		this.maxHealth = health;
	}
	public void damageEnemy(double damageTaken) {
		if(!invincibleFrame) {
			if(health > 0) {
				health -= damageTaken;
				isDamaged = true;
				invincibleFrame = true;
			}
		}
		if(health <= 0) {
			Main.entities.remove(this);
			Main.enemies.remove(this);
			for(int i = 0; i<Main.rand.nextInt(1,4);i++) {
				Main.entities.add(new Coin(this.getX(), this.getY(), 16, 16, Entity.En_Coin));
			}
			Hud.score++;
		}
		
	}
	
	public boolean checkPlayerCollision() {
		Rectangle currentEnemy = new Rectangle(this.getX() + maskX,this.getY() + maskY,enW,enH);
		Rectangle player = new Rectangle(Main.player.getX() + Main.player.maskX, Main.player.getY() + 0,7,15);
		
		return currentEnemy.intersects(player);
	}
	
	public boolean isColliding(int nextX, int nextY) {
		Rectangle currentEnemy = new Rectangle(nextX + maskX,nextY + maskY,enW,enH);
		for(int i = 0; i < Main.enemies.size(); i++) {
			Enemy en = Main.enemies.get(i);
			if(en == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(en.getX() + maskX,en.getY() + maskY, enW,enH);
			if(currentEnemy.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
	}
	public void collidingEntity() {
		for(int i = 0; i < Main.projectile.size(); i++) {
			Entity e = Main.projectile.get(i);
			if(e instanceof Projectile) {
				if(Entity.isColliding(this, e)) {
					Main.projectile.get(i);
					//this.isFrozen=true;
					damageEnemy(Projectile.damage);
					if(e instanceof SwordHitBox == false)
						Main.projectile.remove(i);
					return;
				}
			}
		}
		return;
	}
	public void tick() {
	}
	
	public void render(Graphics g) {
	}

}
