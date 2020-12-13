package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


import javax.swing.JFrame;

import graphics.Spritesheet;
import world.World;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private static JFrame frame;
	public static final int WIDTH=160;
	public static final int HEIGHT=160;
	public static final int SCALE=3;
	
	private BufferedImage image;
	private static Spritesheet spritesheet;
	private World world;
	
	public Game(){
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		this.image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		spritesheet=new Spritesheet("/spritesheet.png");
		world=new World("/map.png");
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
	public static Spritesheet getSpritesheet(){
		return spritesheet;
	}
	public void setSpritesheet(Spritesheet spritesheet){
		Game.spritesheet=spritesheet;
	}
}