package com.dnlStudios.entities;

import java.awt.image.BufferedImage;

import com.dnlStudios.main.Main;
import com.dnlStudios.player.Player;

public class Lifepack extends Entity{
	
	public Lifepack(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public static void healPlayer(int heal) {
		Player.health += heal;
		if(Player.health > Main.player.maxHealth) 
			Player.health = Main.player.maxHealth;
		
	}
}

