import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class UserInterface extends JFrame {
	
	
//	Components 
	private JButton quitGame;
	private JButton playWord;
	private JButton getNextWord;
	private JLabel [] alphabet;
	private Border border = new LineBorder(Color.black, 2);
	private Border noBorder = new LineBorder(Color.LIGHT_GRAY, 2);
	private Border hintBorder = new LineBorder(Color.GREEN, 2);
	private Font font = new Font("Arial Black", Font.BOLD, 18);
	private JLabel progress;
	
//	Containers
	private JPanel letterPanel;
	private JPanel keysAndLabels;
	
//	Gamecomponents
	private String [] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Å", "Ä", "Ö"	};
	private Game game;

	
	

	public UserInterface () {
		
		game = new Game();
		setTitle("Stavnings-spel");

		setLayout(new BorderLayout());
		add(letterPanel = new JPanel(), BorderLayout.CENTER);
		add(keysAndLabels = new JPanel(), BorderLayout.NORTH);
		letterPanel.setPreferredSize(new Dimension(1700, 600));
		alphabet = new JLabel [letters.length];
		
//		Add letters
		for (int i = 0; i < letters.length ; i++ ) {
			alphabet[i] = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("letters/" + letters[i]+".jpg")));
			letterPanel.add(alphabet[i]);
			alphabet[i].setBorder(noBorder);
			alphabet[i].addMouseListener(l);
		}
		
		
//		Add buttons & components
		playWord = new JButton("Spela upp ordet"); 
		keysAndLabels.add(playWord);
		playWord.setEnabled(false);
		getNextWord = new JButton("Få nytt ord");
		keysAndLabels.add(getNextWord);
		progress = new JLabel(""); 
		progress.setFont(font);
		upDateProgress();
		keysAndLabels.add(progress);
		quitGame = new JButton("Avsluta spelet");
		quitGame.setBackground(Color.RED);
		keysAndLabels.add(quitGame);
		
		
//		Add listeners
		playWord.addActionListener(e -> game.playWord());
		getNextWord.addActionListener(e -> getNewWord());
		quitGame.addActionListener(e -> quitGame());
		
		
//		Final settings
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		
	}
	
	MouseListener l = new MouseAdapter () {
		
		public void mouseReleased(MouseEvent e) {
			
//			If the game is not already won: Neutralize all borders and highlight the chosen letter. 
			if (!game.getIsWin()) {
				for (int i = 0 ; i < letters.length ; i++ ) {
					alphabet[i].setBorder(noBorder);
					if (e.getSource() == alphabet[i] ) {
						alphabet[i].setBorder(border);
						game.checkLetter(letters[i]);
					}
				}
				upDateProgress();		
				
//				Check if the game is won.
				if (game.getIsWin()) {
					upDateProgress();
					gameWon();
				} else {
					if (game.getWrongGuesses() > 2 ) {
						giveHint();
					}
				}
			}	
		}; 
	};
	
	public void upDateProgress () {
		
		String result = "";
		
//		Check if word is null and only present whitespace in that case. 
		if (game.getWordSoFar() == null ) {
			result += fillOutWord();
		} else {
			result += game.getWordSoFar() + fillOutWord();
		}
		progress.setText(String.format("Ordet: %s", result ));
	}
	
	
	private String fillOutWord () {
		
//		Method fills out string with space to improve UI visuals
		
		int currentWordLength = 0;
		
		if (game.getWordSoFar() != null ) {
			currentWordLength = game.getWordSoFar().length();
		}

		int defaultLength = 30;
		String space = "";
		
		for (int i = 0 ; i < (defaultLength - currentWordLength ); i++ ) {
			space += " ";
		}
		
		return space;
	}
	
	public void gameWon () {
		
//		Change status of buttons
		playWord.setEnabled(false);
		getNextWord.setEnabled(true);
		game.playApplause();
		presentWord();
		
		if (!game.moreWords()) {
			progress.setText("No more words");
			game.closeCurrentClip();
			getNextWord.setEnabled(false);
		}

	}
	
	public void getNewWord () {
		
//		The player has asked to get a new word. 
		game.getNewWord();
		upDateProgress();
		playWord.setEnabled(true);
		getNextWord.setEnabled(false);
	}
	
	public void presentWord () {
//		The player spelled the word and gets the word presented again. 
		String html = "<html><body><h1 style=\"font-size:300%;\">";
		html += game.getWordSoFar().toUpperCase();
		html += "</h1></body></html>";
		game.playWord();
		JOptionPane.showMessageDialog(null, html, "Du klarade ordet", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void quitGame() {
//		Player pressed quit game. Below the player is asked to confirm the choice to quit. 
		String [] customOptions = {"Ja tack", "Nej fortsätt spela"};
		int choice = JOptionPane.showOptionDialog(null, "Vill du avsluta?", "Avsluta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, customOptions, customOptions[1] );
		if (choice == 0 ) {
			game.closeCurrentClip();
			System.exit(0);
		}
		
	}
	
	public void giveHint () {
//		This method will give the player at hint. 
		String letterToHighlight = game.getCurrentWord().substring(game.getWordSoFar().length(), game.getWordSoFar().length() + 1).toUpperCase();
		for (int i = 0 ; i < letters.length ; i++) {
			if (letters[i].equals(letterToHighlight)) {
				alphabet[i].setBorder(hintBorder);
			}
		}

	}
	
	
}
