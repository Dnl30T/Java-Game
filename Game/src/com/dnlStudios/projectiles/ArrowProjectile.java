package com.dnlStudios.projectiles;

import java.awt.image.BufferedImage;

import com.dnlStudios.entities.Projectile;

public class ArrowProjectile extends Projectile{

	public ArrowProjectile(int x, int y, int width, int height, BufferedImage sprite, double dx2, double dy2) {
		super(x, y, width, height, sprite, dx2, dy2);
		this.setDamage(25);
	}

}
