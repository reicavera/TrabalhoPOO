package main;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


import javax.swing.JFrame;


public class Game extends Canvas implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Thread thread;
	public static JFrame frame;
	public static final int WIDTH=240;
	public static final int HEIGHT=180;
	public static final int SCALE=3;
	
	private BufferedImage image;
	
	public Game(){
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

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
		g.dispose();
		g=bs.getDrawGraphics();
		g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
		bs.show();
		
	}
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
}