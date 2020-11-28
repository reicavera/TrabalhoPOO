package com.reicavera.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;

public class Menu {
	public String options[]={"Novo Jogo","Carregar Jogo","Sair"};
	public int currentOption=0,maxOptions=options.length-1;
	public boolean up,down,enter;
	public static boolean saveExists,saveGame;
	File file=null;
	public void tick(){
		file=new File("save.txt");
		if(file.exists())
			saveExists=true;
		else
			saveExists=false;
		if(up){
			up=false;
			currentOption--;
			if(currentOption<0){
				currentOption=maxOptions;
			}
		}
		else if(down){
			down=false;
			currentOption++;
			if(currentOption>maxOptions){
				currentOption=0;
			}
		}
	}
	public void render(Graphics g){
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(new Color(0,0,0,100));
		g.fillRect(0,0,Game.WIDTH*Game.SCALE,Game.HEIGHT*Game.SCALE);
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,36));
		g.drawString("Jogo",(Game.WIDTH*Game.SCALE)/2-50,(Game.HEIGHT*Game.SCALE)/2-160);
		
		g.setFont(new Font("arial",Font.BOLD,24));
		g.drawString("Novo Jogo",(Game.WIDTH*Game.SCALE)/2-50,(Game.HEIGHT*Game.SCALE)/2-60);
		g.drawString("Carregar Jogo",(Game.WIDTH*Game.SCALE)/2-50,(Game.HEIGHT*Game.SCALE)/2);
		g.drawString("Sair",(Game.WIDTH*Game.SCALE)/2-50,(Game.HEIGHT*Game.SCALE)/2+60);
		
		switch(options[currentOption]){
			case "Novo Jogo":
				g.drawString(">",(Game.WIDTH*Game.SCALE)/2-80,(Game.HEIGHT*Game.SCALE)/2-60);
				if(enter){
					enter=false;
					Game.restart(0);
					Game.gameState="JOGO";
				}
				break;
			case "Carregar Jogo":
				g.drawString(">",(Game.WIDTH*Game.SCALE)/2-80,(Game.HEIGHT*Game.SCALE)/2);
				if(enter){
					if(file.exists()){
						String saver=Game.loadGame(15);
						Game.applySave(saver);
					}
					enter=false;
				}
				break;
			case "Sair":
				g.drawString(">",(Game.WIDTH*Game.SCALE)/2-80,(Game.HEIGHT*Game.SCALE)/2+60);
				if(enter){
					enter=false;
					System.exit(1);
				}
				
				break;
		}
	}
}
