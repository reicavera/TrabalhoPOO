package com.reicavera.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.reicavera.main.Game;
import com.reicavera.world.Camera;

public class Bullet extends Entity{
	private double dx,dy,speed=12,life=10;

	public Bullet(int x, int y, int width, int height, BufferedImage sprite,double dx,double dy) {
		super(x, y, width, height, sprite);
		this.dx=dx;
		this.dy=dy;
	}
	public void tick(){
		if(life!=0){
			this.x+=(int)(dx*speed);
			this.y+=(int)(dy*speed);
			life--;
		}
		else{
			Game.bullets.remove(this);
			Game.entities.remove(this);
			return;
		}
		
	}
	public void render(Graphics g){
		g.setColor(Color.white);
		g.fillOval(this.getX()-Camera.x,this.getY()-Camera.y,(int)width,(int)height);
		
	}
}
