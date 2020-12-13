package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import world.Camera;

public class Bullet extends Entity{
	private BufferedImage sprite;
	private double xSpeed,ySpeed;
	public Bullet(double x, double y, int width, int height,double xSpeed,double ySpeed,BufferedImage sprite) {
		super(x, y, width, height);
		this.sprite=sprite;
		this.xSpeed=xSpeed;
		this.ySpeed=ySpeed;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(sprite,(int)x-Camera.getX(),(int)y-Camera.getY(),null);
		
	}

	@Override
	public void tick() {
		this.x+=xSpeed;
		this.y+=ySpeed;
		ArrayList<Entity> e=Game.getEntities();
		for(int i=0;i<e.size();i++){
			if(e.get(i)instanceof Enemy){
				Enemy e2=(Enemy)e.get(i);
				if(isColliding(this, e2)){
					e2.damage(1);
					Game.removeEntity(this);
				}
			}
		}
		
	}
	
}
