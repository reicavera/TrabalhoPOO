package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Spritesheet{
	/*
	 * Classe dedicada a ler um arquivo de imagem para poder ser usada na interface gráfica.
	 */
	private BufferedImage spritesheet;
	
	public Spritesheet(String path)
	/*
	 * Procura e lê o arquivo que se encontra na url dada em path.
	 */
	{
		try {
			spritesheet=ImageIO.read(getClass().getResource(path));
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	public BufferedImage getSprite(int x,int y,int width,int height){
	/*
	 * seleciona apenas uma parte especifica da imagem.
	 */
		return spritesheet.getSubimage(x,y,width,height);
	}
}
