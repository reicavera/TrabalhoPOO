package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class FloorTile extends Tile {
	private static BufferedImage TILE_FLOOR=Game.getSpritesheet().getSprite(0,0,16,16);
	private BufferedImage sprite;
	public FloorTile(int x,int y){
		this(x,y,TILE_FLOOR);
	}
	public FloorTile(int x, int y, BufferedImage sprite) {
		super(x,y);
		this.sprite=sprite;
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(sprite,x-Camera.getX(),y-Camera.getY(),null);
	}

}
