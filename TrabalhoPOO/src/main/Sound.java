
package main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	/*
	 * Classe dedicada a reproduzir sons.Note que o construtor é privado e o atributo musicBackground é final,o que
	 * significa que os audios a serem usados devem ser escolhidos previamente,já que não é possível adicionar novos audios
	 * ao jogo.
	 */
	private AudioClip clip;
	
	public static final Sound musicBackground = new Sound("/music.wav");
	
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e) {}
	}
	
	public void play() {
		/*
		 * Reproduz o audio uma única vez.
		 */
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		}catch(Throwable e) {}
	}
	
	public void loop() {
		/*
		 * Reproduz o audio indefinidas vezes seguidas.
		 */
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		}catch(Throwable e) {}
	}
}

