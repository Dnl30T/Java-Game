package com.dnlStudios.player;

import com.dnlStudios.main.Main;

public class PlayerInventory {
	
	public int headSlot = 1;
	public int bodySlot = 2;
	public int legSlot = 1;
	public int feetSlot = 2;
	
	public static int coins;
	
	public int potionSlot = 3;
	public int quill = 20;
	
	public static int arrows, potions;
	
	public void addArrow(int arrow) {
		if(arrows+arrow > quill)
			arrows = quill;
		else
			arrows+=arrow;
	}
	public void addPotion() {
		potions++;
	}
	public void addCoin(int amountOfCoins) {
		coins+=amountOfCoins;
	}
	public void usePotion() {
		if(potions > 0) {
			Main.player.healPlayer(10);
			potions--;
		}
	}

}
