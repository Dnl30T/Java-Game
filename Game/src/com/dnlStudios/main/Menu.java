package com.dnlStudios.main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Menu {
	
	public String[] options = {"new game", "load game","exit"};
	
	public int currentOption = 0;
	public int maxOption = options.length-1;
	private int frames = 0, maxFrames = 60, index = 0, maxIndex = 2;

	public boolean up = false;
	public boolean down = false;
	public boolean enter = false;
	private boolean timer;
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0]) {
			case "map":
				Main.startLevel("map"+spl[2]+".png");
				Main.gameState = "normal";
				break;
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for(int i = 0; i<val.length;i++) {
							val[i] -= encode;
							trans[1]+=val[i];
						}
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="/";
					}
				}catch(IOException e) {
					
				}
			}catch(FileNotFoundException e) {
				
			}
		}
		return line;
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < val1.length;i++) {
			String current = val1[i];
			current+=":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int j = 0; j < value.length; j++) {
				value[j]+=encode;
				current+=value[j];
			}
			try {
				write.write(current);
				if(i<val1.length-1)
					write.newLine();
			}catch(IOException e) {}
		}
		try {
			write.flush();
			write.close();
		}catch(IOException e) {}
	}
	
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
