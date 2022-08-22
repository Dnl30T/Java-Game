package com.dnlStudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.dnlStudios.enemies.MushroomEnemy;
import com.dnlStudios.enemies.SkullEnemy;
import com.dnlStudios.enemies.SlimeEnemy;
import com.dnlStudios.entities.*;
import com.dnlStudios.main.Main;

public class World {
	
	private boolean spawnEnemies = true;
	private static Tileset[] tiles;
	public static int worldWidth, worldHeight; 
	public static final int TILE_SIZE = 16;

	public World(String path) {
		try {
			BufferedImage map  = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			
			worldWidth = map.getWidth();
			worldHeight = map.getHeight();
			
			tiles = new Tileset[map.getWidth() * map.getHeight()];
			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx =  0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int currentPixel = pixels[xx+(yy*map.getWidth())];
					//change to switch case
					tiles[xx + (yy * worldWidth)] = new PhaseTile(xx * 16,yy * 16,Tileset.No_Tile);
					if(currentPixel == 0xFF000000) {
						//grass
						int grassType =Main.rand.nextInt(12);
						if(grassType < 9)
							tiles[xx + (yy * worldWidth)] = new PhaseTile(xx * 16,yy * 16,Tileset.Grass_Tile_Plain);
						else if(grassType < 11)
							tiles[xx + (yy * worldWidth)] = new PhaseTile(xx * 16,yy * 16,Tileset.Grass_Tile_Grass);
						else if(grassType == 11)
							tiles[xx + (yy * worldWidth)] = new PhaseTile(xx * 16,yy * 16,Tileset.Grass_Tile_Leaves);
					}
					else if(currentPixel == 0xFFFFFFFF) {
						//grass
						tiles[xx + (yy * worldWidth)] = new SolidTile(xx * 16,yy * 16,Tileset.Wall_Tile);
					}
					else if(currentPixel == 0xFFFF0000) {
						Main.player.setX(xx*16);
						Main.player.setY(yy*16);
						//Player
					}
					else if(currentPixel == 0xFFFF6A00) {
						//enemy
						SlimeEnemy enemy = new SlimeEnemy(xx*16,yy*16,16,16,null);
						Main.enemies.add(enemy);
						if(spawnEnemies) {
							Main.entities.add(enemy);
						}
					}
					else if(currentPixel == 0xFF7F3300) {
						//med kit
						Lifepack lp = new Lifepack(xx*16,yy*16,16,16,Entity.En_LifePack);
						lp.setCollisionMask(0, 0, 16, 16);
						Main.entities.add(lp);
					}
					else if(currentPixel == 0xFFFF00DC) {
						//arrow
						Main.entities.add(new Arrow(xx*16,yy*16,16,16,Entity.En_Arrow));
					}
					else if(currentPixel == 0xFFFFD800) {
						//bow
						Main.entities.add(new Bow(xx*16,yy*16,16,16,Entity.En_Bow));
					}
					else if(currentPixel == 0xFF7FC9FF) {
						SkullEnemy enemy = new SkullEnemy(xx*16,yy*16,16,16,null);
						Main.enemies.add(enemy);
						if(spawnEnemies) {
							Main.entities.add(enemy);	
						}
					}
					else if(currentPixel == 0xFF00FFFF) {
						MushroomEnemy enemy = new MushroomEnemy(xx*16,yy*16,16,16,null);
						Main.enemies.add(enemy);
						if(spawnEnemies) {
							Main.entities.add(enemy);	
						}
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int nextX, int nextY,int width, int height) {
		
		int x1 = nextX / TILE_SIZE;
		int y1 = nextY / TILE_SIZE;
		
		int x2 = (nextX+width-1) / TILE_SIZE;
		int y2 = (nextY+height-1) / TILE_SIZE;

		int x3 = (nextX+width-1) / TILE_SIZE;
		int y3 = nextY / TILE_SIZE;
		
		int x4 = nextX / TILE_SIZE;
		int y4 = (nextY+height-1) / TILE_SIZE;
		
		return !( tiles[x1 + (y1*worldWidth)] instanceof SolidTile 
				|| tiles[x2 + (y2*worldWidth)] instanceof SolidTile 
				|| tiles[x3 + (y3*worldWidth)] instanceof SolidTile 
				|| tiles[x4 + (y4*worldWidth)] instanceof SolidTile
				);
	}
	
	public void render(Graphics g) {
		int xStart = Camera.x >>4;
		int yStart = Camera.y >>4;
		int xFinal = xStart + (Main.width>>4);
		int yFinal = yStart + (Main.height>>4);
		
		for(int xx = xStart; xx <= xFinal; xx++) {
			for(int yy = yStart; yy <= yFinal; yy++) {
				if(xx < 0 || yy < 0 || xx>= worldWidth || yy >= worldHeight) {
					continue;
				}
				Tileset tile = tiles[xx + (yy*worldWidth)];
				tile.render(g);
			}
		}
	}

}
