package com.reicavera.graphics;

import java.awt.Color;
import java.awt.Graphics;

import com.reicavera.main.Game;

public class UI {
	public void render(Graphics g){
		g.setColor(Color.red);
		g.fillRect(8,8,50,4);
		g.setColor(Color.green);
		g.fillRect(8,8,(int)((Game.player.life/Game.player.maxLife)*50),4);
	}
}
