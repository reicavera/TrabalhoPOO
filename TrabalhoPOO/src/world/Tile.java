package world;

import java.awt.Graphics;
public abstract class Tile {
	/*
	 * Classe abstrata que abriga os objetos que s�o renderizados,mas n�o possuem nehuma a��o pr�pria.
	 */
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
		/*
		 * Verifica se esse tile � passivel ou n�o de ser atravessado pelo jogador.
		 */
		if(this instanceof WallTile)
			return true;
		return false;
	}
}
