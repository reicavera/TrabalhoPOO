package com.reicavera.main;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import com.reicavera.entities.*;
import com.reicavera.graphics.Spritesheet;
import com.reicavera.graphics.UI;
import com.reicavera.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener{
	
	private static final long serialVersionUID = 1L;
	public static boolean isRunning;
	private Thread thread;
	public static JFrame frame;
	public static final int WIDTH=240;
	public static final int HEIGHT=180;
	public static final int SCALE=3;
	public static String gameState="MENU";
	public boolean saveGame;
	public int saveFrame=0,maxSaveFrames=600;
	
	private BufferedImage image;
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<Ammo> ammos;
	public static List<Bullet> bullets;
	public static List<Weapon> weapon;
	public static List<Chest> chests;
	
	public static Spritesheet spritesheet;
	
	public static Player player;
	public static World world;
	public Menu menu;
	
	public static Random random;
	public UI ui;
	
	public Game(){
		Sound.musicBackground.loop();
		random=new Random();
		//this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		ui=new UI();
		image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities=new ArrayList<Entity>();
		enemies=new ArrayList<Enemy>();
		ammos=new ArrayList<Ammo>();
		bullets=new ArrayList<Bullet>();
		weapon=new ArrayList<Weapon>();
		chests=new ArrayList<Chest>();
		spritesheet=new Spritesheet("/spritesheet.png");
		player=new Player(0,0,16,16,spritesheet.getSprite(32,0,16,16));
		entities.add(player);
		world=new World("/map.png");
		menu=new Menu()	;
		addKeyListener(this);
		addMouseListener(this);

	}
	
	public void initFrame(){
		frame=new JFrame();
		frame.add(this);
		//frame.setUndecorated(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread=new Thread(this);
		isRunning=true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Game game=new Game();
		game.start();
	}
	
	public void tick(){
		restart((int)player.life);
		switch(gameState){
		case "JOGO":
			for(int i=0;i<entities.size();i++){
				Entity e=entities.get(i);
				e.tick();
			}
			
			break;
		case "MENU":
			menu.tick();
			break;
		}
	}
	public void render(){
		BufferStrategy bs=this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g=image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH,HEIGHT);
		world.render(g);
		for(int i=0;i<entities.size();i++){
			Entity e=entities.get(i);
			e.render(g); 
		}
		ui.render(g);
		g.dispose();
		g=bs.getDrawGraphics();
		g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
		g.setFont(new Font("arial",Font.BOLD,17));
		g.setColor(Color.white);
		g.drawString("Munição: "+player.ammos,28,56);
		if(gameState=="MENU"){
			menu.render(g);
		}
		bs.show();
		
	}
	
	public static void saveGame(String[] val1,int[] val2,int encode){
		BufferedWriter write=null;
		try{
			write=new BufferedWriter(new FileWriter("save.txt"));
		}catch(IOException e){}
		for(int i=0;i<val1.length;i++){
			String current=val1[i];
			current+=":";
			char[] value=Integer.toString(val2[i]).toCharArray();
			for(int j=0;j<value.length;j++){
				value[j]+=encode;
				current+=value[j];
			}
			try{
				write.write(current);
				if(i<val1.length-1)
					write.newLine();
			}catch(IOException e){}
		}
		try{
			write.flush();
			write.close();
		}catch(IOException e){}
	}
	public static String loadGame(int encode){
		String line="";
		File file=new File("save.txt");
		if(file.exists()){
			try{
				String singleLine=null;
				BufferedReader reader=new BufferedReader(new FileReader("save.txt"));
				try{
					while((singleLine=reader.readLine())!=null){
						String[] trans=singleLine.split(":");
						char[] val=trans[1].toCharArray();
						trans[1]="";
						for(int i=0;i<val.length;i++){
							val[i]-=encode;
							trans[1]+=val[i];
						}
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="/";
					}
				}catch(IOException e){}
			}catch(FileNotFoundException e){}
		}
		return line;
	}
	public static void applySave(String str){
		String[] spl=str.split("/");
		for(int i=0;i<spl.length;i++){
			String spl2[]=spl[i].split(":");
			switch(spl2[0]){
				case "level":
					restart(0);
					Game.gameState="JOGO";
			}
		}
	}

	
	public void run() {
		requestFocus();
		long lastTime=System.nanoTime();
		double amountOfTicks=60.0;
		double ns=1000000000/amountOfTicks;
		double delta=0;
		while(isRunning=true){
			long now=System.nanoTime();
			delta+=(now-lastTime)/ns;
			lastTime=now;
			if(delta>=1){
				tick();
				render();
				delta--;
			}
		}
		stop();
	}
	public static void restart(int status){
		if(status<=0){
			gameState="MENU";
			entities.clear();
			entities=new ArrayList<Entity>();
			spritesheet=new Spritesheet("/spritesheet.png");
			player=new Player(0,0,16,16,spritesheet.getSprite(32,0,16,16));
			player.life=player.maxLife;
			entities.add(player);
			world=new World("/map.png");
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_D){
			player.right=true;
		}
		else if(e.getKeyCode()==KeyEvent.VK_A){
			player.left=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_W){
			player.up=true;
			if(gameState=="MENU"){
				menu.up=true;
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_S){
			player.down=true;
			if(gameState=="MENU"){
				menu.down=true;
			}
		}
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			player.shoot=true;
			if(gameState=="MENU"){
				menu.enter=true;
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			switch(gameState){
			case "JOGO":
				gameState="MENU";
				break;
			case "MENU":
				gameState="JOGO";
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_D){
			player.right=false;
		}
		else if(e.getKeyCode()==KeyEvent.VK_A){
			player.left=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_W){
			player.up=false;
		}
		else if(e.getKeyCode()==KeyEvent.VK_S){
			player.down=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			player.shoot=false;
			if(gameState=="JOGO")
				this.saveGame=true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}	


	@Override
	public void mouseClicked(MouseEvent e) {
		player.mouseShoot=true;
		player.mx = (e.getX() / SCALE);
		player.my = (e.getY() / SCALE);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

