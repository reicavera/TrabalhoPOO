package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class WallTile extends Tile{
	/*
	 * Tile que o jogador não pode atravessar.
	 */
	private static final BufferedImage WALL_TILE=Game.getSpritesheet().getSprite(16,0,16,16);
	private BufferedImage sprite;
	public WallTile(int x, int y) {
		this(x,y,WALL_TILE);
	}
	public WallTile(int x, int y,BufferedImage sprite) {
		super(x,y);
		this.sprite=sprite;
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(sprite,x-Camera.getX(),y-Camera.getY(),null);
	}
}
