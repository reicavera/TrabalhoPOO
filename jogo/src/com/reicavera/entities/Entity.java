package com.reicavera.entities;

import java.awt.image.ImageObserver;
import java.awt.Image;
import com.reicavera.world.Camera;
import java.awt.Graphics;
import java.awt.Rectangle;
import com.reicavera.main.Game;
import java.awt.image.BufferedImage;

public class Entity
{
    public static BufferedImage CHEST_ENTITY;
    public static BufferedImage WEAPON_ENTITY;
    public static BufferedImage BULLET_ENTITY;
    public static BufferedImage ENEMY_ENTITY;
    public static BufferedImage WEAPON_RIGHT;
    public static BufferedImage WEAPON_LEFT;
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    private int xMask;
    private int yMask;
    private int widthMask;
    private int heightMask;
    private BufferedImage sprite;
    
    static {
        Entity.CHEST_ENTITY = Game.spritesheet.getSprite(96, 0, 16, 16);
        Entity.WEAPON_ENTITY = Game.spritesheet.getSprite(112, 0, 16, 16);
        Entity.BULLET_ENTITY = Game.spritesheet.getSprite(96, 16, 16, 16);
        Entity.ENEMY_ENTITY = Game.spritesheet.getSprite(112, 16, 16, 16);
        Entity.WEAPON_RIGHT = Game.spritesheet.getSprite(112, 0, 16, 16);
        Entity.WEAPON_LEFT = Game.spritesheet.getSprite(128, 0, 16, 16);
    }
    
    public Entity(final int x, final int y, final int width, final int height, final BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.xMask = 0;
        this.yMask = 0;
        this.widthMask = width;
        this.heightMask = height;
    }
    
    public int getX() {
        return (int)this.x;
    }
    
    public int getY() {
        return (int)this.y;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public void setMasks(final int xMask, final int yMask, final int widthMask, final int heightMask) {
        this.xMask = xMask;
        this.yMask = yMask;
        this.widthMask = widthMask;
        this.heightMask = heightMask;
    }
    
    public int getWidth() {
        return (int)this.width;
    }
    
    public int getHeight() {
        return (int)this.height;
    }
    
    public void tick() {
    }
    
    public void render() {
    }
    
    public boolean isColliding(final Entity e1, final Entity e2) {
        final Rectangle e1Mask = new Rectangle(e1.getX() + e1.xMask, e1.getY() + e1.yMask, e1.widthMask, e1.heightMask);
        final Rectangle e2Mask = new Rectangle(e2.getX() + e2.xMask, e2.getY() + e2.yMask, e2.widthMask, e2.heightMask);
        return e1Mask.intersects(e2Mask);
    }
    
    public void render(final Graphics g) {
        g.drawImage(this.sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
}