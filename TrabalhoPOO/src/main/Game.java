package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import entities.Enemy;
import entities.Entity;

import entities.Player;
import graphics.Spritesheet;
import world.World;

public class Game extends Canvas implements Runnable,KeyListener{
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private static JFrame frame;
	public static final int WIDTH=240;
	public static final int HEIGHT=180;
	public static final int SCALE=3;
	
	private BufferedImage image;
	private static Spritesheet spritesheet;
	private static World world;
	private static Player player;
	private static ArrayList<Entity> entities;
	
	private static int count;
	
	public Game(){
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		Sound.musicBackground.loop();
		this.image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		spritesheet=new Spritesheet("/spritesheet.png");
		entities=new ArrayList<Entity>();
		player=new Player(0,0,16,16);
		entities.add(player);
		world=new World("/map.png");
		count=0;
		addKeyListener(this);
	}
	
	public void initFrame(){
		frame=new JFrame();
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread=new Thread(this);
		thread.start();
	}

	public static void main(String[] args){
		Game game=new Game();
		game.start();
	}
	
	public void tick(){
		if(count!=6){
		if(player.getLife()<=0)
			Game.restart();
		if(count==4){
			Enemy e=new Enemy(0,0,32,32,20,1,spritesheet.getSprite(0,16,32,32));
			entities.add(e);
			count++;
		}
		for(int i=0;i<entities.size();i++){
			Entity e=entities.get(i);
			e.tick(); 
		}}
		else{
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
		if(count==6){
			g.setFont(new Font("arial",Font.BOLD,28));
			g.setColor(Color.white);
			g.drawString("Você Ganhou",35,100);
			}
		g.dispose();
		g=bs.getDrawGraphics();
		g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
		bs.show();
	}
	@Override
	public void run() {
		requestFocus();
		long lastTime=System.nanoTime();
		double amountOfTicks=60.0;
		double ns=1000000000/amountOfTicks;
		double delta=0;
		while(true){
			long now=System.nanoTime();
			delta+=(now-lastTime)/ns;
			lastTime=now;
			if(delta>=1){
				tick();
				render();
				delta--;
			}
		}
	}
	public static void restart(){
		entities=new ArrayList<Entity>();
		player=new Player(0,0,16,16);
		entities.add(player);
		world=new World("/map.png");
		count=0;
	}
	public static Spritesheet getSpritesheet(){
		return spritesheet;
	}
	public void setSpritesheet(Spritesheet spritesheet){
		Game.spritesheet=spritesheet;
	}
	public static Player getPlayer() {
		return player;
	}

	public static World getWorld() {
		return world;
	}
	public static ArrayList<Entity> getEntities(){
		return entities;
	}
	public static void addEntity(Entity e){
		entities.add(e);
	}
	public static void removeEntity(Entity e){
		entities.remove(e);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==e.VK_D){
			player.setRight(true);
		}
		else if(e.getKeyCode()==e.VK_A){
			player.setLeft(true);
		}
		if(e.getKeyCode()==e.VK_W){
			player.setUp(true);
		}
		else if(e.getKeyCode()==e.VK_S){
			player.setDown(true);
		}
		if(e.getKeyCode()==e.VK_SPACE){
			player.setShoot(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==e.VK_D){
			player.setRight(false);
		}
		else if(e.getKeyCode()==e.VK_A){
			player.setLeft(false);
		}
		if(e.getKeyCode()==e.VK_W){
			player.setUp(false);
		}
		else if(e.getKeyCode()==e.VK_S){
			player.setDown(false);
		}
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Game.count = count;
	}
	public static void addCount(){
		count++;
	}
}