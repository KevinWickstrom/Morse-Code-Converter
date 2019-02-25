import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
/*
 * Kevin Wickstrom
 * HW4
 * Created a morse code converter and an interactive GUI
 */
class Morse {
	char [] alpha;
	String [] morse;
	
	public Morse() throws IOException{			
		alpha = new char [26];
		morse = new String [26];
		Scanner infile = new Scanner(new File("test.txt"));
		int i = 0;
		while (infile.hasNext()){
	    	alpha[i] = infile.next().charAt(0);
	    	morse[i] = infile.next();
	    	i++;
		}
	    infile.close();
	}
		
		/**
		 * Returns morse code equivalent of an alphabet character
		 * @param target : Alphabet letter passed in as a String
		 * @return String of the alphabet character passed in
		 */
	public String getMorseCode(String target) {
		char key = target.toLowerCase().charAt(0);
		int ret = binarySearch(alpha, alpha.length, 0, key);
		if (ret != -1)
			return morse[ret];
		return "";
		}
		
	/**
	 * Returns the alphabet letter of a morse code String
	 * @param target : Morse code for a letter in passed in as a String
	 * @return char of the morse code passed in
	 */
	public char getCharCode(String target) {
		for (int i = 0; i < morse.length; i++)
			if (morse[i].equals(target))
				return alpha[i];
		return ' ';
	}
		
	/**
	 * BinarySearch for a char target in the alpha array
	 * @param arr : char array of the current portion of the alphabet being searched
	 * @param hi : index of the last element of the array being passed in
	 * @param lo : index of the first element of the array being passed in
	 * @param target : char being searched for
	 * @return int of index of the char being searched for
	 */
	private int binarySearch(char[] arr, int hi, int lo, char target) {
		if (hi < lo) 
			return -1;
		
		int mid = (hi + lo) / 2;
		
		if (arr[mid] == target)
			return mid;
				
		else if (arr[mid] > target)
			return binarySearch(arr, mid - 1, lo, target);
		
		else 
			return binarySearch(arr, hi, mid + 1, target);
	}
}

class MyWindow extends JFrame{
	private JPanel panel;
	private JLabel label;
	private JTextField inputText;
	private JTextField resultText;
	private JButton alphaButton, morseButton;
	private Morse morse;
	
	public MyWindow() throws IOException{
		setTitle("Morse Code Converter");
		setSize(350,  250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		label = new JLabel();
		inputText = new JTextField(10);
		resultText = new JTextField(10);
		alphaButton = new JButton("To Character");
		morseButton = new JButton("To Morse Code");
		morse = new Morse();
	
		alphaButton.addActionListener(new AlphaButtonListener());
		morseButton.addActionListener(new MorseButtonListener());
	
		panel.add(label);
		panel.add(inputText);
		panel.add(resultText);
		panel.add(alphaButton);
		panel.add(morseButton);
		panel.setBackground(Color.LIGHT_GRAY);

		add(panel);
		
		setVisible(true);
		
	}
	
	class AlphaButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String result = "";
			String [] code = inputText.getText().split(" ");
			
			for (int i = 0; i < code.length; i++)
				result += Character.toString(morse.getCharCode(code[i]));
			
			resultText.setText(result);
		}
	}
	
	class MorseButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String result = "";
			
			for (int i = 0; i < inputText.getText().length(); i++)
				result += morse.getMorseCode(Character.toString(inputText.getText().charAt(i))) + " ";
			
			resultText.setText(result);
		}
	}
}

public class HW4 {
	
	public static void main(String[] args) throws IOException{
		MyWindow win = new MyWindow();
	}

}
