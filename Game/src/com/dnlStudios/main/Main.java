package com.dnlStudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.dnlStudios.entities.Enemy;
import com.dnlStudios.entities.Entity;
import com.dnlStudios.entities.Player;
import com.dnlStudios.entities.Projectile;
import com.dnlStudios.graphics.Spritesheet;
import com.dnlStudios.world.Hud;
import com.dnlStudios.world.World;

public class Main extends Canvas implements Runnable,KeyListener,MouseListener{
	
	//Menu
	public Menu menu;
	
	//window and in game size rendering
	private static final long serialVersionUID = 1L;
	public static final int width = 320;
	public static final int height = 240;
	private final static int scale = 3;
	public static final int sWidth = width * scale, sHeight = height*scale;
	
	//levels
	public static String gameState = "menu";
	private boolean restart=false;
	private boolean showGOMessage = true;
	public int framesGO = 0;
	public static int currentLevel = 0;
	public int maxLevel = 2;
	
	//images and rendering
	private BufferedImage image;	
	public static Spritesheet spritesheet;
	public static Hud hud;
	
	//entities
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<Projectile> projectile;
	public static Player player;
	
	//foundation
	public static World world;
	public static JFrame window;
	private Thread thread;
	private boolean running;
	public static Random rand;
	
	//Debug
	public static int frames;
	
	public Main() {
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		this.setPreferredSize(new Dimension(sWidth,sHeight));
		window();
		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		startLevel("map0");
		menu = new Menu();
	}
	
	public static void startLevel(String level) {
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		projectile = new ArrayList<Projectile>();
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0,0,16,16,spritesheet.getSprite(0 , 0, 16, 16));
		entities.add(player);
		world = new World("/"+level+".png");
		hud = new Hud();
	}
	
	public static void main(String[] args) {
		Main game = new Main();
		game.start();
		game.requestFocus();
	}
	
	public void window() {
		window = new JFrame("Game");
		window.add(this);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop() {
		this.running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(running) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >=1000) {
				//System.out.println("Fps: "+ frames);
				Main.frames = frames;
				frames = 0;
				timer+=1000;
			}
		}
		stop();
	}
	
	public void tick() {
		hud.tick();
		if(gameState == "normal") {
			restart = false;
			for(int i = 0; i < entities.size(); i++) {
				entities.get(i).tick();
			}
			for(int i = 0; i < projectile.size(); i++) {
				projectile.get(i).tick();
			}
			if(enemies.size() == 0) {
				//next level
				//System.out.println("todos mortos");
				currentLevel++;
				if(currentLevel > maxLevel) {
					currentLevel = 0;
				}
				startLevel("map"+currentLevel);
			}
		}else if(gameState == "gameover") {
			//System.out.println("K.O");
			this.framesGO++;
			if(this.framesGO == 45) {
				this.framesGO = 0;
				if(this.showGOMessage)
					this.showGOMessage = false;
				else
					this.showGOMessage = true;
			}
			if(restart) {
				restart = false;
				if(Hud.score > Hud.highScore) {
					Hud.highScore = Hud.score;
				}
				Hud.score = 0;
				System.out.println("restart");
				Player.health = player.maxHealth;
				currentLevel = 0;
				startLevel("map"+currentLevel);
				gameState = "normal";
			}
		}
		else if(gameState == "menu") {
			menu.tick();
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0)); 
		g.fillRect(0, 0, sWidth, sHeight);
		
		//Render world
		
		world.render(g);
		//Render Objects
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g);
		}
		for(int i = 0; i < projectile.size(); i++) {
			projectile.get(i).render(g);
		}
		//Render HUD
		if(gameState == "normal") {
			hud.render(g);
			//hud.renderPortrait(g);
		}
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, sWidth, sHeight, null);
		g.setColor(new Color(255,255,255));
		g.setFont(new Font("CANTERBURY",Font.BOLD,30));
		if(gameState == "normal")
			hud.renderInfo(g);
		if(gameState == "gameover") {
			Graphics2D  g2 = (Graphics2D) g;	
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, sWidth, sHeight);
			g.setFont(new Font("consolas",Font.PLAIN,60));
			g.setColor(Color.green);
			g.drawString("K.O", (sWidth/2)-50,(sHeight/2)-60);
			if(showGOMessage) {
				g.setFont(new Font("consolas",Font.PLAIN,40));
				g.setColor(Color.white);
				g.drawString(">Press enter to continue", 210,(sHeight/2)+120);
			}
		}else if(gameState == "menu"){
			menu.render(g);
		}
		
		bs.show();
		}
	
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameState == "gameover")
				restart = true;
			if(gameState == "menu") 
				menu.enter = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = "menu";
		}
		//Advanced HUD switch
		if(e.getKeyCode() == KeyEvent.VK_P && hud.AHSwitch == true) {
			Hud.advancedHud = true;
			hud.AHSwitch = false;
		}else if(e.getKeyCode() == KeyEvent.VK_P && hud.AHSwitch == false) {
			Hud.advancedHud = false;
			hud.AHSwitch = true;
		}
		//left right
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		//up down
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			if(gameState == "menu") {
				menu.up = true;
			}
			else
				player.up = true;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if(gameState == "menu") {
				menu.down = true;
			}
			else
				player.down = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//left right
				if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					player.right = false;
				}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					player.left = false;
				}
				//up down
				if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
					player.up = false;
				}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
					player.down = false;
				}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.isShooting = true;
		player.shotType = 2;
		player.mouseX = (e.getX() / 3);
		player.mouseY = (e.getY() / 3);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
