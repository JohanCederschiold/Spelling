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

public class Sounds implements Runnable {
	
	/*	This class creates sounds from the resources folder. The constructor takes the 
	 *  name of the file as a string. 
	 *  https://stackoverflow.com/questions/10144210/java-jar-file-use-resource-errors-uri-is-not-hierarchical
	 *  TODO: Look to merge this and the applause class. 
	 */
	
	protected URL url;
	protected AudioInputStream ais;
	protected Clip clip;
	Thread thread = new Thread(this);
	
	public Sounds (String word) {
		
		createUrl(word.toLowerCase());
		
		try {
			ais = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException lua) {
			lua.printStackTrace();
		}
		
	}
	
	private void createUrl(String wordToSound) {
		
		try {
			url = new URL(getClass().getClassLoader().getResource("audio/" + wordToSound + ".wav").toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run () {
		clip.start();
		long sleeptime = clip.getMicrosecondLength();
		try {
			Thread.sleep(sleeptime / 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		clip.stop();
	}
	
//	public void playClip () {
//		
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		clip.start();
//		
////		This method "resets" the clip to zero. 
//		clip.setMicrosecondPosition(0);
//
//	}
	
//	public void closeClip () {
////		The method is called when a resource is open to close it. 
//		if (clip.isOpen()) {
//			clip.close();
//		}
//	
//	}
	

}
