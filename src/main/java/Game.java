import java.util.Scanner;

public class Game {
	
	private Dictionary dictionary;
	private String currentWord;
	private String correctSoFar;
	private Sounds appl;
	private Sounds sayWord;
	private boolean isWin;
	
	public Game() {
		
		dictionary = new Dictionary();
		appl = new Sounds("applause");
		isWin = false;
		
	}
	
	
	public void getNewWord ()  {
		
//		If this is not the first word this will close the clip (of the previous word).
		if (sayWord != null) {
			closeCurrentClip();
		}
		
//		Reset win (i.e. the word is not correctly guessed). Also reset feedback string and get new random word. 
		isWin = false;
		correctSoFar = "";
		currentWord = dictionary.getRandomWord();
		
		sayWord = new Sounds(currentWord);
		sayWord.playClip();

	}
	
	public void checkLetter (String letter) {
		
		if (letter.equalsIgnoreCase(currentWord.substring(correctSoFar.length(), correctSoFar.length()+1))) {
			correctSoFar += currentWord.substring(correctSoFar.length(), correctSoFar.length()+1);
			if (correctSoFar.equalsIgnoreCase(currentWord)) {
				isWin = true;
			}
			
		}
		
	}
	
	public void playWord () {
		sayWord.playClip();		
	}
	
//	Get the current status of the guessed word. 
	public String getWordSoFar () {
		return correctSoFar;
	}
	
//	Is the word correctly guessed. 
	public boolean getIsWin () {
		return isWin;
	}
	
	public void playApplause () {
		Applause applause = new Applause();
		applause.thread.start();
	}
	
//	Are there more words
	public boolean moreWords () {
		return dictionary.isWordsLeft();
	}
	
//	Method that enables to close the current clip (if open).
	public void closeCurrentClip() {
		if ( sayWord != null) {
			sayWord.closeClip();
		}	
	}


}
