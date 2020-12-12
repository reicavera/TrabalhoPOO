package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
public abstract class Tile {
	
	protected int x,y;
	
	public Tile(int x,int y){
		this.x=x;
		this.y=y;
	}
	public int getX(){
		return x;
	}
	public void setX(int x){
		this.x=x;
	}
	public int getY(){
		return y;
	}
	public void setY(int y){
		this.y=y;
	}
	public abstract void render(Graphics g);
	
	public boolean hasCollision(){
		if(this instanceof WallTile)
			return true;
		return false;
	}
}
