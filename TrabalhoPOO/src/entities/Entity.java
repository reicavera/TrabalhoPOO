package entities;


import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity{
	/*
	 * Classe abstrata que abriga os objetos que possuem ações próprias e são renderizadas na interface gráfica.
	 */
    protected double x;
    protected double y;
    protected int width;
    protected int height;
    private int xMask;
    private int yMask;
    private int widthMask;
    private int heightMask;
    
    
    public Entity(double x,double y,int width,int height) {
    	/*
    	 * construtor de Entidade.Note embora não alterado durante o projeto,a máscara de colisão de uma entidade pode ser
    	 * maior/menor do que seu tamanho.Isso geralmente é útil para projetos mais complexos.
    	 */
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xMask = 0;
        this.yMask = 0;
        this.widthMask = width;
        this.heightMask = height;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public void setMasks(int xMask,int yMask,int widthMask,int heightMask) {
        this.xMask = xMask;
        this.yMask = yMask;
        this.widthMask = widthMask;
        this.heightMask = heightMask;
    }
    
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public boolean isColliding( Entity e1,Entity e2) {
    	/*
    	 * verifica se duas entidades estão se colidindo,ou seja,há uma intersecsão entre as máscaras de colisão
    	 * das entidades.
    	 */
        Rectangle e1Mask = new Rectangle((int)e1.getX() + e1.xMask, (int)e1.getY() + e1.yMask, e1.widthMask, e1.heightMask);
        Rectangle e2Mask = new Rectangle((int)e2.getX() + e2.xMask, (int)e2.getY() + e2.yMask, e2.widthMask, e2.heightMask);
        return e1Mask.intersects(e2Mask);
    }
    
    public abstract void render(Graphics g);
    public abstract void tick();
}