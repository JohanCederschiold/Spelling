import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sounds {
	
	private URL url;
	private AudioInputStream ais;
	private Clip clip;
	
	public Sounds (String word) {
		
		createUrl(word);

	}
	
	private void createUrl(String wordToSound) {
		
		try {
			url = new URL(getClass().getClassLoader().getResource(wordToSound + ".wav").toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void playClip () {
		
		try {
			ais = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(ais);
			System.out.println("finished");
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException lua) {
			lua.printStackTrace();
		}
		
		System.out.println("called");
		clip.start();

		do  {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (clip.isActive());
		
		clip.close();

	}

}
