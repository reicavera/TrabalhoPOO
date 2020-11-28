package com.reicavera.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.reicavera.main.Game;
import com.reicavera.world.Camera;
import com.reicavera.world.World;

public class Player extends Entity{
	public double maxLife=100,life=100;
	public int ammos=0;
	public int rightDir=0,leftDir=1;
	public int dir=rightDir;
	public boolean right,left,up,down;
	public double speed=2;
	private int frames=0,maxFrames=5,index=0,maxIndex=3;
	private int damageFrames=0;
	private boolean moved=false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage playerDamaged;
	public boolean isDamaged=false;
	public boolean weapon=false;
	public boolean shoot,mouseShoot;
	public int mx,my;	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x,y,width,height, sprite);
		rightPlayer=new BufferedImage[4];
		leftPlayer=new BufferedImage[4];
		for(int i=0;i<4;i++){
			rightPlayer[i]=Game.spritesheet.getSprite(32+i*16,0,16,16);
		}
		for(int i=0;i<4;i++){
			leftPlayer[i]=Game.spritesheet.getSprite(32+i*16,16,16,16);
		}
		playerDamaged=Game.spritesheet.getSprite(0,16,16,16);
		speed=2;
	}
	public void tick(){
		moved=false;
		if(right){
			if(World.isFree((int)(x+(speed)),(int)y)){
				moved=true;
				dir=rightDir;
				x+=speed;
			}
		}
		else if(left){
			if(World.isFree((int)(x-speed),(int)y)){
				moved=true;
				dir=leftDir;
				x-=speed;
			}
		}
		
		if(up){
			if(World.isFree((int)x,(int)(y-speed))){
				moved=true;
				y-=speed;
				}
		}
		else if(down){
			if(World.isFree((int)x,(int)(y+speed))){
				moved=true;
				y+=speed;
			}
		}
		if(moved){
			frames++;
			if(frames==maxFrames){
				frames=0;
				index++;
				if(index>maxIndex)
					index=0;
			}
		}
		collidingChests();
		collidingAmmos();
		collidingWeapon();
		shooting();
		if(isDamaged){
			damageFrames++;
			if(damageFrames==8){
				isDamaged=false;
				damageFrames=0;
			}
		}
		if(mouseShoot) {
			mouseShoot = false;
			if(weapon && ammos> 0) {
				ammos--;
				int px = 0,py = 8;
				double angle= Math.atan2(my - (this.getY()+py - Camera.y),mx - (this.getX()+px - Camera.x));
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				Bullet bullet = new Bullet(this.getX()+px,this.getY()+py,3,3,null,dx,dy);
				Game.entities.add(bullet);
				Game.bullets.add(bullet);
			}
		}
		Camera.x=Camera.clamp(this.getX()-Game.WIDTH/2,0,World.WIDTH*16-Game.WIDTH);
		Camera.y=Camera.clamp(this.getY()-Game.HEIGHT/2,0,World.HEIGHT*16-Game.HEIGHT);
	}
	public void collidingChests(){
		for(int i=0;i<Game.chests.size();i++){
			Chest chest=Game.chests.get(i);
			if(isColliding(this,chest)){
				life+=25;
				if(life>=100)
					life=100;
				Game.entities.remove(chest);
				Game.chests.remove(chest);
			}
		}
	}
	public void collidingAmmos(){
		for(int i=0;i<Game.ammos.size();i++){
			Ammo ammo=Game.ammos.get(i);
			if(isColliding(this,ammo)){
				ammos+=5;
				Game.entities.remove(ammo);
				Game.ammos.remove(ammo);
			}
		}
	}
	public void collidingWeapon(){
		for(int i=0;i<Game.weapon.size();i++){
			Weapon weapon=Game.weapon.get(i);
			if(isColliding(this,weapon)){
				this.weapon=true;
				Game.entities.remove(weapon);
				Game.weapon.remove(weapon);
			}
		}
	}
	public void shooting(){
		if(shoot){
			if(ammos>0&&weapon){
				ammos--;
				shoot=false;
				Bullet bullet;
				int dx=0;
				if(dir==rightDir){
					dx=1;
					bullet=new Bullet(this.getX()+20,this.getY()+4,3,3,null,dx,0);
				}
				else{
					dx=-1;
					bullet=new Bullet(this.getX()-7,this.getY()+4,3,3,null,dx,0);
				}
				Game.entities.add(bullet);
				Game.bullets.add(bullet);
			}
		}
	}
	public void render(Graphics g){
		if(!isDamaged){
			if(dir==rightDir){
			g.drawImage(rightPlayer[index],this.getX()-(int)Camera.x,this.getY()-(int)Camera.y,null);
			if(weapon){
				g.drawImage(Entity.WEAPON_RIGHT,(int)this.getX()-Camera.x+10,(int)this.getY()-Camera.y,null);
			}
			}
			else if(dir==leftDir){
				g.drawImage(leftPlayer[index],this.getX()-(int)Camera.x,this.getY()-(int)Camera.y,null);
				if(weapon){
					g.drawImage(Entity.WEAPON_LEFT,(int)this.getX()-Camera.x-10,(int)this.getY()-Camera.y,null);
				}
			}
		}
		else{
			g.drawImage(playerDamaged,this.getX()-(int)Camera.x,this.getY()-(int)Camera.y,null);
		}
	}

}
	