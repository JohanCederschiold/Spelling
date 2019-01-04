import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Dictionary {
	
//	Instancevariables
	private List<String> words;
	
	
//	Constructor - Converts file, reads words from file and adds to arraylist.
	public Dictionary () {
		words = readWords();

	}
	
	
//	Choose a random word from the ArrayList
	public String getRandomWord () {
		Random slump = new Random();
		String fetchedWord = words.get(slump.nextInt(words.size()));
		removeWord(fetchedWord);
		return fetchedWord;
				
	}
	
//	Are there any words left
	public boolean isWordsLeft () {
		return !words.isEmpty();
	}
 	
	
//	Remove word from the list
	public void removeWord (String word ) {
		words.remove(word);
	}
	

	
	/*		Reads words from textfile and adds them to array
	 * 		This is only done once (when instantiating the Dictionary).
	 * 		The textfile needs to be handled as a stream due to jar packaging. 
	 * 		See: https://stackoverflow.com/questions/20389255/reading-a-resource-file-from-within-jar
	 */
	private List<String> readWords () {
		
		List<String> readWords = new ArrayList<>();
			
		InputStream in = getClass().getClassLoader().getResourceAsStream("words.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "UTF8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		while (true) {
			String textWord = "";
			try {
				textWord = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (textWord == null ) {
				break;
			} else {
				readWords.add(textWord);
			}
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return readWords;

	}
	


}
