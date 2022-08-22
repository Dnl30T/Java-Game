package com.dnlStudios.main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {
	
	public String[] options = {"new game", "load game","exit"};
	
	public int currentOption = 0;
	public int maxOption = options.length-1;
	private int frames = 0, maxFrames = 60, index = 0, maxIndex = 2;

	public boolean up = false;
	public boolean down = false;
	public boolean enter = false;
	private boolean timer;
	
	public void tick() {
		if(up && timer == false) {
			up = false;
			currentOption--;
			if(currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if(down && timer == false) {
			down = false;
			currentOption++;
			if(currentOption > maxOption) {
				currentOption = 0;
			}
		}
		if(enter) {
			timer = true;
			if(currentOption == 0) {
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					System.out.println(index);
					if(index > maxIndex) {
						enter = false;
						timer = false;
						Main.gameState = "normal";
						index = 0;
					}
				}
			}
			else if(currentOption == 2) {
				System.exit(1);
			}
		}
	}
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(0, 0, Main.sWidth, Main.sHeight);
		if(timer) {
			g.setColor(Color.white);
			g.drawString(Integer.toString(((index)-3)*-1), Main.sWidth/2, Main.sHeight/2);
		}
		else if(timer == false) {
		g.setColor(Color.white);
		g.setFont(new Font("consolas", Font.BOLD,64));
		g.drawString("Bob's Quest", Main.sWidth/2-150, Main.sHeight/2-200);
		g.setFont(new Font("consolas", Font.BOLD,32));
		g.drawString("The assests in this game was token from itch.io", Main.sWidth/10-20, Main.sHeight/2+ 300);
		
		//menu options
		g.setFont(new Font("consolas", Font.BOLD,36));
		for(int i = 0; i < maxOption+1; i++) {
			g.drawString(options[i], 400, 300+(50*i));
		}
		switch(currentOption) {
			case 0:
				g.drawString(">", 380, 300+(50*currentOption));
			case 1:
				g.drawString(">", 380, 300+(50*currentOption));
			case 2:
				g.drawString(">", 380, 300+(50*currentOption));
		}
		}
	}

}
