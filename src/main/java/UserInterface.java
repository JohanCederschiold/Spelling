
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class UserInterface extends JFrame  {
	
	
//	Components 
	private JButton quitGame;
	private JButton playWord;
	private JButton startOrSkip;
	private JLabel [] alphabet;
	private JLabel playerScore;
	private Border border = new LineBorder(Color.black, 2);
	private Border noBorder = new LineBorder(Color.LIGHT_GRAY, 2);
	private Border hintBorder = new LineBorder(Color.GREEN, 2);
	private Font font = new Font("Arial Black", Font.BOLD, 18);
	private JLabel progress;
	private JComboBox choosePlayer;
//	private Player [] players;
	private ImageIcon goldStar = new ImageIcon(getClass().getClassLoader().getResource("gold_star.png"));
	
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
		
		setFocusable(true);
		requestFocus();
		addKeyListener(kl);
		
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
		choosePlayer = new JComboBox(game.getPlayerNames());
		keysAndLabels.add(choosePlayer);
		playerScore = new JLabel();
		playerScore.setFont(font);
		upDatePoints();
		keysAndLabels.add(playerScore);
		playWord = new JButton("Spela upp ordet"); 
		keysAndLabels.add(playWord);
		playWord.setEnabled(false);
		startOrSkip = new JButton("Starta spelet");
		keysAndLabels.add(startOrSkip);
		progress = new JLabel(""); 
		progress.setFont(font);
		upDateProgress();
		keysAndLabels.add(progress);
		quitGame = new JButton("Avsluta spelet");
		quitGame.setBackground(Color.RED);
		keysAndLabels.add(quitGame);
		
		
//		Add listeners
		playWord.addActionListener(e -> sayWord());
		startOrSkip.addActionListener(e -> startGameorSkipWord()); 
		quitGame.addActionListener(e -> quitGame());
		choosePlayer.addActionListener(e -> changePlayer());
		
		
		
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
			requestFocus();
		}; 
	};
	

	
	KeyListener kl = new KeyAdapter () {
		
		
		public void keyReleased (KeyEvent e) {
//			
//			for (int i = 0 ; i < letters.length ; i++ ) {
//				alphabet[i].setBorder(noBorder);
//			}
//			
			if (e.getKeyCode() != KeyEvent.VK_ENTER) {
				String myGuess = "";
				myGuess += e.getKeyChar();
				game.checkLetter(myGuess);
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
		}
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
		
		game.givePlayerPoints();
		upDatePoints();
		
//		Change status of buttons
		playWord.setEnabled(false);
//		startOrSkip.setEnabled(true);
		game.playApplause();
		presentWord();
		
		if (!game.moreWords()) {
			progress.setText("No more words");
//			game.closeCurrentClip();
			startOrSkip.setEnabled(false);
		}

	}
	
	public void getNewWord () {
		
//		The player has asked to get a new word. 
		game.getNewWord();
		upDateProgress();
		playWord.setEnabled(true);
	}
	
	public void startGameorSkipWord() {
		if (game.getCurrentWord() == null ) { //No currentWord. This is the first round 
			startOrSkip.setText("Hoppa över");
			getNewWord();
		} else {
			game.markedAsSkipped();
			getNewWord();
		}
		requestFocus();
	}
	
	public void presentWord () {
//		The player spelled the word correctly and gets the word presented again. 
		String html = "<html><body><h1 style=\"font-size:300%;\">";
		html += game.getWordSoFar().toUpperCase();
		html += "</h1></body></html>";
		game.playWord();
		if (game.getWithoutFaults()) {
			JOptionPane.showMessageDialog(null, html, "Du klarade ordet", JOptionPane.PLAIN_MESSAGE, goldStar);
		} else {
			JOptionPane.showMessageDialog(null, html, "Du klarade ordet", JOptionPane.PLAIN_MESSAGE);
		}
		getNewWord();
	}
	
	public void quitGame() {
//		Player pressed quit game. Below the player is asked to confirm the choice to quit. 
		String [] customOptions = {"Ja tack", "Nej fortsätt spela"};
		int choice = JOptionPane.showOptionDialog(null, "Vill du avsluta?", "Avsluta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, customOptions, customOptions[1] );
		if (choice == 0 ) {
			System.exit(0);
		}
		requestFocus();
		
	}
	
	public void giveHint () {
//		This method will give the player at hint. 
		
//		TODO Fix borderprobem when using keyboard.
		String letterToHighlight = game.getCurrentWord().substring(game.getWordSoFar().length(), game.getWordSoFar().length() + 1).toUpperCase();
		for (int i = 0 ; i < letters.length ; i++) {
			if (letters[i].equals(letterToHighlight)) {
				alphabet[i].setBorder(hintBorder);
			}
		}

	}
	
	public void sayWord () {
		game.playWord();
		requestFocus();
	}
	
	public void changePlayer () {
		
		int choice = choosePlayer.getSelectedIndex();
		game.changePlayer(choice);
		upDatePoints();

	}
	
	public void upDatePoints () {
		playerScore.setText(String.format("%05d", game.getCurrentPlayerScore()));
	}
	
	
}
