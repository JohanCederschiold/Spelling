
public class Spellword {
	
	private String wordToSpell;
//	private char[] wordLetterByLetter;
	
	
	
	public Spellword (String word) {
		wordToSpell = word;
//		wordAsArray(word);
	}
//	
//	public void wordAsArray (String convertThis) {
//		wordLetterByLetter = convertThis.toCharArray();
//	}
//	
	public int getWordLength () {
		return wordToSpell.length();
	}
	
	public String getWord () {
		return wordToSpell;
	}
	
//	public char [] getWordLetterByLetter() {
//		return wordLetterByLetter;
//	}
	
	
	
	
	

}
