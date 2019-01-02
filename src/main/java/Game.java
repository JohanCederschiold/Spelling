import java.util.Scanner;

public class Game {
	
	private Dictionary dictionary;
	private String currentWord; //The word currently being guessed
	private String correctSoFar; //How much of the word that is guessed. 
	private Sounds appl; //Applauds.
	private Sounds sayWord; //The sound of the current word. 
	private boolean isWin; //Keeping track if the word is correctly guessed. 
	private int wrongGuesses; //Number of wrong guesses on current letter. 
	
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
		
//		Checks if letter is correct and (if so) if word is complete
		if (letter.equalsIgnoreCase(currentWord.substring(correctSoFar.length(), correctSoFar.length()+1))) {
//			Add correctly guessed letter to string with correct guess so far. 
			correctSoFar += currentWord.substring(correctSoFar.length(), correctSoFar.length()+1);
//			Reset wrongGuesses
			wrongGuesses = 0;
			if (correctSoFar.equalsIgnoreCase(currentWord)) {
				isWin = true;
			}
		} else {
			wrongGuesses++; //Add to wrong guesses
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
	
	public int getWrongGuesses () {
		return wrongGuesses;
	}
	
	public String getCurrentWord () {
		return currentWord;
	}


}
