package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class Enemy extends Entity implements Fighter{
	private static BufferedImage ENEMY_SPRITE=Game.getSpritesheet().getSprite(16*6,0,16,16);
	private BufferedImage sprite;
	private int life;
	private double speed;
	public Enemy(double x, double y, int width, int height){
		this(x,y,width,height,5,0.5,ENEMY_SPRITE);
	}
	public Enemy(double x, double y, int width, int height,int life,double speed,BufferedImage sprite) {
		super(x, y, width, height);
		this.sprite=sprite;
		this.life=life;
		this.speed=speed;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(sprite,(int)x-Camera.getX(),(int)y-Camera.getY(),null);
	}

	@Override
	public void tick() {
		move();
		attack();
	}
	@Override
	public void move() {
            if (x < Game.getPlayer().getX() ) {
                this.x += speed;
            }
            else if (x > Game.getPlayer().getX()) {
                this.x -= speed;
            }
            if (y > Game.getPlayer().getY()) {
                this.y -= speed;
            }
            else if (y < Game.getPlayer().getY()) {
                this.y += speed;
            }
        }
	@Override
	public void attack() {
		if(isColliding(this,Game.getPlayer())){
			Game.getPlayer().damage(1);
		}
		
	}
	public void damage(int value){
		this.life--;
		if(life<=0){
			Game.removeEntity(this);
			Game.addCount();
		}
	}
}
