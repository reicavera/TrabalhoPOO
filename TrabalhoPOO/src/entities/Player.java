package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class Player extends Entity implements Fighter{
	/*
	 * entidade controlada pelo jogador.
	 */
	private static final int LIFE_MAX=1;
	private	int life;
	private boolean up,down,left,right,shoot;
	private double speed;
	private int index;
	private static BufferedImage PLAYER_SPRITE[]={Game.getSpritesheet().getSprite(16*2,0,16,16),
			Game.getSpritesheet().getSprite(16*3,0,16,16),Game.getSpritesheet().getSprite(16*4,0,16,16),
			Game.getSpritesheet().getSprite(16*5,0,16,16)};
	private BufferedImage sprites[];
	public Player(double x, double y, int width, int height) {
		this(x,y,width,height,PLAYER_SPRITE);
	}
	public Player(double x, double y, int width, int height,BufferedImage sprites[]) {
		super(x, y, width, height);
		this.sprites=sprites;
		setLife(LIFE_MAX);
		this.index=0;
		this.speed=2;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(sprites[index],(int)x-Camera.getX(),(int)y-Camera.getY(),null);
	}

	@Override
	public void tick() {
		move();
		attack();
		Camera.setX(Camera.clamp((int)x-Game.WIDTH/2,0,Game.getWorld().getWidth()*16-Game.WIDTH));
		Camera.setY(Camera.clamp((int)y-Game.HEIGHT/2,0,Game.getWorld().getHeight()*16-Game.HEIGHT));
	}
	@Override
	public void move() {
		/*
		 * baseado nos inputs de KeyListener,o jogador se movimenta.
		 */
		if(right&&Game.getWorld().isFree((int)(x+speed),(int)(y))){
			index=1;
			x+=speed;
		}
		else if(left&&Game.getWorld().isFree((int)(x-speed),(int)(y))){
			index=0;
			x-=speed;
		}
		if(up&&Game.getWorld().isFree((int)(x),(int)(y-speed))){
			index=2;
			y-=speed;
		}
		else if(down&&Game.getWorld().isFree((int)(x),(int)(y+speed))){
			index=3;
			y+=speed;
		}
		
	}
	@Override
	public void attack() {
		/*
		 * ao apertar ESPAÇO,o jogador lança um Bullet na direção que estiver olhando.
		 */
		if(shoot){
			shoot=false;
			Bullet b;
			if(index==1){
				b=new Bullet(x,y,16,16,8,0,Game.getSpritesheet().getSprite(16*3,16*1,16,16));
				Game.addEntity(b);
			}
			else if(index==0){
				b=new Bullet(x,y,16,16,-8,0,Game.getSpritesheet().getSprite(16*3,16*1,16,16));
				Game.addEntity(b);
			}
			else if(index==2){
				b=new Bullet(x,y,16,16,0,-8,Game.getSpritesheet().getSprite(16*3,16*1,16,16));
				Game.addEntity(b);
			}
			else if(index==3){
				b=new Bullet(x,y,16,16,0,8,Game.getSpritesheet().getSprite(16*3,16*1,16,16));
				Game.addEntity(b);
			}
			
		}
		
	}
	public boolean getLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean getDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public boolean getRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean getUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public void damage(int value) {
		this.life-=value;
		
	}
	public boolean getShoot() {
		return shoot;
	}
	public void setShoot(boolean shoot) {
		this.shoot = shoot;
	}
}
