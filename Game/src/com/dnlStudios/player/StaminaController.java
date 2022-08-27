package com.dnlStudios.player;

import com.dnlStudios.main.Main;

public class StaminaController {
	
	public static double stamina;
	public static double maxStamina;
	
	public boolean isSprinting;
	private boolean staminaFull=true;
	
	private double regenRatio;
	private double spendRatio;
	
	public StaminaController(double stamina,double maxStamina,double regenRatio,double spendRatio) {
		StaminaController.stamina = stamina;
		StaminaController.maxStamina = maxStamina;
		this.regenRatio = regenRatio;
		this.spendRatio = spendRatio;
	}
	
	public void update() {
		if(!isSprinting) {
			Main.player.speed =1.0;
			if(stamina <= maxStamina - 0.01) {
				stamina += regenRatio;
				staminaFull = false;
				if(stamina >= maxStamina) {
					staminaFull = true;
				}
			}
		}
	}
	public void sprinting() {
		//System.out.println(staminaFull);
		//System.out.println(isSprinting);
		if(isSprinting && staminaFull) {
			Main.player.speed =1.5;
			if(staminaFull) {
				stamina -= spendRatio;
				if(stamina < 0) {
					stamina = 0;
					Main.player.isRunning=false;
				}
			}
		}
	}

	public boolean getStaminaFull() {
		return staminaFull;
	}
}
