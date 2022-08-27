package com.dnlStudios.entities;

import java.awt.image.BufferedImage;

import com.dnlStudios.main.Main;

public class Bow extends Entity{
	
	public static BufferedImage bowRightSpr[];
	public static BufferedImage bowDamageSpr = Main.spritesheet.getSprite(16, 80, 16, 16);

	public Bow(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		bowRightSpr = new BufferedImage[5];

		for(int i = 0; i < 5; i++) {
			bowRightSpr[i] = Main.spritesheet.getSprite(96+(i*16), 64, 16, 16);
		}
	}

}
