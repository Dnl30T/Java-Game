package com.dnlStudios.entities;

import java.awt.image.BufferedImage;

import com.dnlStudios.main.Main;

public class Coin extends Entity{
	
	private int direction;
	private int speed = 10;

	public Coin(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		direction = Main.rand.nextInt(4);
		System.out.println(direction);
	}
	public void tick() {
		switch(direction) {
		case 0:
			if(speed>0) {
				speed--;
				x--;
			}
		case 1:
			if(speed>0) {
				speed--;
				x++;
			}
		case 2:
			if(speed>0) {
				speed--;
				y++;
			}
		case 3:
			if(speed>0) {
				speed--;
				y--;
			}
	}
	}

}
