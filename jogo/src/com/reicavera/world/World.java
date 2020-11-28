package com.reicavera.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.reicavera.entities.Ammo;
import com.reicavera.entities.Chest;
import com.reicavera.entities.Enemy;
import com.reicavera.entities.Entity;
import com.reicavera.entities.Weapon;
import com.reicavera.main.Game;

public class World {
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE=16;
	
	public World(int playerX,int playerY,int width,int height,int interaction){
		Game.player.setX(playerX*16);
		Game.player.setY(playerY*16);
		WIDTH=width;
		HEIGHT=height;
		tiles=new Tile[WIDTH*HEIGHT];
		int dir=0,x=playerX,y=playerY;
		for(int i=0;i<WIDTH;i++){
			for(int j=0;j<HEIGHT;j++){
				tiles[i+j*WIDTH]=new WallTile(i*16,j*16,Tile.TILE_WALL);
			}
		}
		tiles[x+y*WIDTH]=new FloorTile(x*16,y*16,Tile.TILE_FLOOR);
		for(int i=0;i<interaction;i++){
			if(Game.random.nextInt(100)<40)
				dir=Game.random.nextInt(4);
			switch(dir){
				case 0:
					if(x<WIDTH-2)
						x++;
					break;
				case 1:
					if(x>1)
						x--;
					break;
				case 2:
					if(y<HEIGHT-2)
						y++;
					break;
				case 3:
					if(y>1)
						y--;
					break;
			}
			tiles[x+y*WIDTH]=new FloorTile(x*16,y*16,Tile.TILE_FLOOR);
		}
		
		for(int i=0;i<3;){
			FloorTile floor=new FloorTile(0,0,null);
			x=Game.random.nextInt(WIDTH);
			y=Game.random.nextInt(HEIGHT);
			if((tiles[x+y*WIDTH].getClass()==floor.getClass())&&Math.sqrt(Math.pow(Game.player.getX()-x*16,2)+Math.pow(Game.player.getY()-y*16,2))<64){
				Ammo ammo=new Ammo(x*16,y*16,16,16,Entity.BULLET_ENTITY);
				Game.entities.add(ammo);
				Game.ammos.add(ammo);
				i++;
			}
		}
		for(int i=0;i<1;){
			FloorTile floor=new FloorTile(0,0,null);
			x=Game.random.nextInt(WIDTH);
			y=Game.random.nextInt(HEIGHT);
			if((tiles[x+y*WIDTH].getClass()==floor.getClass())&&Math.sqrt(Math.pow(Game.player.getX()-x*16,2)+Math.pow(Game.player.getY()-y*16,2))<32){
				Weapon weapon=new Weapon(x*16,y*16,16,16,Entity.WEAPON_ENTITY);
				Game.entities.add(weapon);
				Game.weapon.add(weapon);
				i++;
			}
		}
		for(int i=0;i<5;){
			FloorTile floor=new FloorTile(0,0,null);
			x=Game.random.nextInt(WIDTH);
			y=Game.random.nextInt(HEIGHT);
			if((tiles[x+y*WIDTH].getClass()==floor.getClass())&&Math.sqrt(Math.pow(Game.player.getX()-x*16,2)+Math.pow(Game.player.getY()-y*16,2))>64){
				Enemy enemy=new Enemy(x*16,y*16,16,16,Entity.ENEMY_ENTITY);
				Game.entities.add(enemy);
				Game.enemies.add(enemy);
				i++;
			}
		}
		for(int i=0;i<3;){
			FloorTile floor=new FloorTile(0,0,null);
			x=Game.random.nextInt(WIDTH);
			y=Game.random.nextInt(HEIGHT);
			if((tiles[x+y*WIDTH].getClass()==floor.getClass())){
				Chest chest=new Chest(x*16,y*16,16,16,Entity.CHEST_ENTITY);
				Game.entities.add(chest);
				Game.chests.add(chest);
				i++;
			}
		}
	}
	public World(String path){
	try {
		BufferedImage map=ImageIO.read(getClass().getResource(path));	
		int[] pixels=new int[map.getWidth()*map.getHeight()];
		WIDTH=map.getWidth();
		HEIGHT=map.getHeight();
		tiles=new Tile[map.getWidth()*map.getHeight()];
		map.getRGB(0,0,map.getWidth(),map.getHeight(),pixels,0,map.getWidth());
		for(int i=0;i<map.getWidth();i++){
			for(int j=0;j<map.getHeight();j++){
				tiles[i+(j*WIDTH)]=new FloorTile(i*16,j*16,Tile.TILE_FLOOR);
				switch(pixels[i+(j*map.getWidth())]){
					case 0xFFFFFFFF:
						//parede
						tiles[i+(j*WIDTH)]=new WallTile(i*16,j*16,Tile.TILE_WALL);
						break;
					case 0xFF0026FF:
						//player
						Game.player.setX(i*16);
						Game.player.setY(j*16);
						break;
					case 0xFFFF0000:
						//enemy
						Enemy enemy=new Enemy(i*16,j*16,16,16,Entity.ENEMY_ENTITY);
						Game.entities.add(enemy);
						Game.enemies.add(enemy);
						break;
					case 0xFF4CFF00:
						//weapon
						Weapon weapon=new Weapon(i*16,j*16,16,16,Entity.WEAPON_ENTITY);
						Game.entities.add(weapon);
						Game.weapon.add(weapon);
						break;
					case 0xFF7F3300:
						//chest
						Chest chest=new Chest(i*16,j*16,16,16,Entity.CHEST_ENTITY);
						Game.entities.add(chest);
						Game.chests.add(chest);
						break;
					case 0xFFFFD800:
						//ammo
						Ammo ammo=new Ammo(i*16,j*16,16,16,Entity.BULLET_ENTITY);
						Game.entities.add(ammo);
						Game.ammos.add(ammo);
						break;
				}
				
			}
		}
	} catch (IOException e){
		e.printStackTrace();
	}
	}
	
	public static boolean isFree(int xNext,int yNext){
		int x1=xNext/TILE_SIZE;
		int y1=yNext/TILE_SIZE;
		
		int x2=(xNext+TILE_SIZE-1)/TILE_SIZE;
		int y2=yNext/TILE_SIZE;	
		
		int x3=xNext/TILE_SIZE;
		int y3=(yNext+TILE_SIZE-1)/TILE_SIZE;	
		
		int x4=(xNext+TILE_SIZE-1)/TILE_SIZE;
		int y4=(yNext+TILE_SIZE-1)/TILE_SIZE;	
		return !((tiles[x1+(y1*World.WIDTH)] instanceof WallTile)||
				(tiles[x2+(y2*World.WIDTH)] instanceof WallTile)||
				(tiles[x3+(y3*World.WIDTH)] instanceof WallTile)||
				(tiles[x4+(y4*World.WIDTH)] instanceof WallTile));
	}
	
	public void render(Graphics g){
		int xstart=Camera.x/16;
		int ystart=Camera.y/16;
		int xfinal=xstart+(Game.WIDTH/16);
		int yfinal=ystart+(Game.HEIGHT/16);
		for(int i=xstart;i<=xfinal+5;i++){
			for(int j=ystart;j<=yfinal+5 ;j++){
			if(i<0||j<0||i>=WIDTH||j>=HEIGHT)
				continue;
			Tile tile=tiles[i+(j*WIDTH)];
				tile.render(g);
			}
		}
	}
}
