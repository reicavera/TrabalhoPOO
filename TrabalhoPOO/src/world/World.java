package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Enemy;
import main.Game;

public class World {
	/*
	 * Classe que agrupa os tiles para formar o mapa.Além disso define as posições iniciais das Entidades.
	 */
	public static Tile[] tiles;
	private static int width,height;
	public static final int TILE_SIZE=16;
	
	public World(String path){
		/*
		 * A partir de um arquivo de imagem,constrói o mapa de acordo com a cor de cada pixel. 
		 */
	try {
		BufferedImage map=ImageIO.read(getClass().getResource(path));	
		int[] pixels=new int[map.getWidth()*map.getHeight()];
		width=map.getWidth();
		height=map.getHeight();
		tiles=new Tile[map.getWidth()*map.getHeight()];
		map.getRGB(0,0,map.getWidth(),map.getHeight(),pixels,0,map.getWidth());
		for(int i=0;i<map.getWidth();i++){
			for(int j=0;j<map.getHeight();j++){
				//cobre o mapa com o chão
				tiles[i+(j*height)]=new FloorTile(i*16,j*16);
				switch(pixels[i+(j*map.getWidth())]){
					case 0xFFFFFFFF:
						//parede=pixel branco
						tiles[i+(j*width)]=new WallTile(i*16,j*16);
						break;
					case 0xFF0026FF:
						//player=pixel azul
						Game.getPlayer().setX(i*16);
						Game.getPlayer().setY(j*16);
						break;
					case 0xFFFF0000:
						//inimigo=pixel vermelho
						Enemy e=new Enemy(i*16,j*16,16,16);
						Game.addEntity(e);
						break;
				}
				
			}
		}
	} catch (IOException e){
		e.printStackTrace();
	}
	}
	
	public boolean isFree(int xNext,int yNext){
		/*
		 * verifica se o próximo movimeto do jogador é valido,uma vez que o jogador não pode atravessar a parede.
		 */
		int x1=xNext/TILE_SIZE;
		int y1=yNext/TILE_SIZE;
		
		int x2=(xNext+TILE_SIZE-1)/TILE_SIZE;
		int y2=yNext/TILE_SIZE;	
		
		int x3=xNext/TILE_SIZE;
		int y3=(yNext+TILE_SIZE-1)/TILE_SIZE;	
		
		int x4=(xNext+TILE_SIZE-1)/TILE_SIZE;
		int y4=(yNext+TILE_SIZE-1)/TILE_SIZE;	
		return !((tiles[x1+(y1*width)].hasCollision())||
				(tiles[x2+(y2*width)].hasCollision())||
				(tiles[x3+(y3*width)].hasCollision())||
				(tiles[x4+(y4*width)].hasCollision()));
	}
	
	public void render(Graphics g){
		int xstart=Camera.getX()/16;
		int ystart=Camera.getY()/16;
		int xfinal=xstart+(Game.WIDTH/16);
		int yfinal=ystart+(Game.HEIGHT/16);
		for(int i=xstart;i<=xfinal+5;i++){
			for(int j=ystart;j<=yfinal+5 ;j++){
			if(i<0||j<0||i>=height||j>=width)
				continue;
			Tile tile=tiles[i+(j*width)];
				tile.render(g);
			}
		}
	}
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
}
