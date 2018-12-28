import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserInterface extends JFrame {
	
	
	private JButton startGame;
	private JButton playWord;
	private JLabel [] alphabet;
	private JPanel letterPanel;
	private String [] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Å", "Ä", "Ö"	};
	private Game game;
	

	public UserInterface () {
		
		game = new Game();
		
		
		
		
		setLayout(new BorderLayout());
		add(letterPanel = new JPanel(), BorderLayout.CENTER);
		letterPanel.setPreferredSize(new Dimension(1700, 900));
		alphabet = new JLabel [letters.length];
		
//		Add letters
		for (int i = 0; i < letters.length ; i++ ) {
			alphabet[i] = new JLabel(new ImageIcon(getClass().getClassLoader().getResource(letters[i]+".png")));
			letterPanel.add(alphabet[i]);
			alphabet[i].addMouseListener(l);
		}
		
//		Add buttons
		playWord = new JButton("Spela upp ordet"); add(playWord, BorderLayout.NORTH);
		playWord.addActionListener(e -> game.playWord());

		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		
	}
	
	MouseListener l = new MouseAdapter () {
		
		public void mouseReleased(MouseEvent e) {
			
			for (int i = 0 ; i < letters.length ; i++ ) {
				if (e.getSource() == alphabet[i] ) {
//					System.out.println(letters[i] + " pressed");
					game.checkLetter(letters[i]);
				}
			}
			
		}; 

	};
	
	
}
