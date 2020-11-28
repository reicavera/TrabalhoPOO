package com.reicavera.entities;

import java.awt.image.ImageObserver;
import java.awt.Image;
import com.reicavera.world.Camera;
import java.awt.Graphics;
import java.awt.Rectangle;
import com.reicavera.world.World;
import com.reicavera.main.Game;
import java.awt.image.BufferedImage;

public class Enemy extends Entity
{
    public double speed;
    private int frames;
    private int maxFrames;
    private int index;
    private int maxIndex;
    private static BufferedImage[] sprites;
    private boolean hostile;
    
    public Enemy(final int x, final int y, final int width, final int height, final BufferedImage sprite) {
        super(x, y, width, height, null);
        this.speed = 0.5;
        this.frames = 0;
        this.maxFrames = 20;
        this.index = 0;
        this.maxIndex = 1;
        (Enemy.sprites = new BufferedImage[2])[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
        Enemy.sprites[1] = Game.spritesheet.getSprite(128, 16, 16, 16);
    }
    
    @Override
    public void tick() {
        if (Math.sqrt(Math.pow(Game.player.getX() - this.x, 2.0) + Math.pow(Game.player.getY() - this.y, 2.0)) < 128.0) {
            this.hostile = true;
        }
        else {
            this.hostile = false;
        }
        this.collidingBullet();
        if (!this.isCollidingPlayer()) {
            if (this.x < Game.player.getX() && World.isFree((int)(this.x + this.speed), (int)this.y) && !this.isCollidingEnemies((int)(this.x + this.speed), (int)this.y) && this.hostile) {
                this.x += this.speed;
            }
            else if (this.x > Game.player.getX() && World.isFree((int)(this.x - this.speed), (int)this.y) && !this.isCollidingEnemies((int)(this.x - this.speed), (int)this.y) && this.hostile) {
                this.x -= this.speed;
            }
            if (this.y > Game.player.getY() && World.isFree((int)this.x, (int)(this.y - this.speed)) && (!this.isCollidingEnemies((int)this.x, (int)(this.y - this.speed)) & this.hostile)) {
                this.y -= this.speed;
            }
            else if (this.y < Game.player.getY() && World.isFree((int)this.x, (int)(this.y + this.speed)) && !this.isCollidingEnemies((int)this.x, (int)(this.y + this.speed)) && this.hostile) {
                this.y += this.speed;
            }
        }
        else {
            final Player player = Game.player;
            --player.life;
            Game.player.isDamaged = true;
        }
        ++this.frames;
        if (this.frames == this.maxFrames) {
            this.frames = 0;
            ++this.index;
            if (this.index > this.maxIndex) {
                this.index = 0;
            }
        }
    }
    
    public boolean isCollidingPlayer() {
        return this.isColliding(this, Game.player);
    }
    
    public void collidingBullet() {
        for (int i = 0; i < Game.bullets.size(); ++i) {
            final Bullet bullet = Game.bullets.get(i);
            if (this.isColliding(this, bullet)) {
                Game.entities.remove(this);
                Game.entities.remove(bullet);
                Game.enemies.remove(this);
                Game.bullets.remove(bullet);
            }
        }
    }
    
    public boolean isCollidingEnemies(final int xNext, final int yNext) {
        final Rectangle enemyCurrent = new Rectangle(xNext, yNext, 16, 16);
        for (int i = 0; i < Game.enemies.size(); ++i) {
            final Enemy e = Game.enemies.get(i);
            if (e != this) {
                final Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), 16, 16);
                if (enemyCurrent.intersects(targetEnemy)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void render(final Graphics g) {
        g.drawImage(Enemy.sprites[this.index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
}