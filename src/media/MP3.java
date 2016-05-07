package media;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javazoom.jl.player.Player;

public class MP3 {
	// esta el clase para reproducior MP3 (ponerla en un archivo solo)

	private InputStream filename;
	private Player player;

	// constructor que toma el nombre el arhivo MP3
	// public MP3(String filename) {
	// this.filename = filename;
	// }

	public MP3(InputStream filename) {
		this.filename = filename;
	}

	public void close() {
		if (player != null)
			player.close();
	}

	// reproduce le mp3 en la tarjeta de sonido predterminada
	public void play() {
		// try {

		// BufferedInputStream bis = new BufferedInputStream(fis);
		// player = new Player(bis);
		// } catch (Exception e) {
		// System.out.println("Problem playing file " + filename);
		// System.out.println(e);
		// }
		try {
			// FileInputStream fis = new FileInputStream(filename);
			BufferedInputStream bis = new BufferedInputStream(filename);
			player = new Player(bis);
		} catch (Exception e) {
			System.out.println("Problem playing file " + filename);
			System.out.println(e);
		}

		// correo el proceso en un nuevo hilo para deterner la ejecucion del
		// programa
		new Thread() {
			public void run() {
				try {
					player.play();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}.start();

	}
}
