package com.dnlStudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dnlStudios.main.Main;

public class Tileset {
	
	public static BufferedImage Grass_Tile_Plain = Main.spritesheet.getSprite(80, 0, 16, 16);
	public static BufferedImage Grass_Tile_Grass = Main.spritesheet.getSprite(80, 16, 16, 16);
	public static BufferedImage Grass_Tile_Leaves = Main.spritesheet.getSprite(80, 32, 16, 16);
	public static BufferedImage No_Tile = Main.spritesheet.getSprite(80, 0, 16, 16);
	public static BufferedImage Wall_Tile = Main.spritesheet.getSprite(96, 0, 16, 16);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tileset(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}

}
