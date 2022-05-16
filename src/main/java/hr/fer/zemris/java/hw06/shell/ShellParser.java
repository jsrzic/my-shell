package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Object used for parsing user arguments from the shell.
 * @author Josip
 *
 */
public class ShellParser {
	/**
	 * Text data stored in an array of characters.
	 */
	private char[] text;
	
	/**
	 * Last generated token.
	 */
	private String token;
	
	/**
	 * Position of the next character to be analyzed.
	 */
	private int currentIndex;
	
	/**
	 * Creates a new ShellParser with initialized private fields.
	 * @param text input text which is being parsed
	 * @throws NullPointerException if input text is <code>null</code>
	 */
	public ShellParser(String text) {
		if (text == null)
			throw new NullPointerException("Input text can not be null.");
		
		this.text = text.toCharArray();
		this.currentIndex = 0;
	}
	
	/**
	 * Generates all tokens from user input and returns them in a list.
	 * @return list containing all tokens gathered from user input
	 */
	public List<String> parse() {
		List<String> tokens = new ArrayList<String>();
		
		nextToken();
		
		while(!this.token.equals("")) {
			tokens.add(token);
			nextToken();
		}
		
		return tokens;
	}
	
	/**
	 * Generates next token from the text, stores it in private field <code>token</code> and returns it.
	 * @return newly generated token
	 * @throws IllegalStateException if called after all tokens were generated, or if an error occurred during lexical analysis
	 */
	private String nextToken() {
		StringBuilder sb = new StringBuilder();
		if (this.token != null && this.token.equals("")) {
			throw new IllegalStateException("There are no more tokens to be generated.");
		}
		
		clearWhitespaces();
		
		if (currentIndex == text.length) {
			return this.token = "";
		}
		
		if (text[currentIndex] == '"') {
			currentIndex++;
			while(currentIndex < text.length && text[currentIndex] != '"') {
				if (text[currentIndex] == '\\' && currentIndex + 1 < text.length && (text[currentIndex + 1] == '\\' || text[currentIndex + 1] == '"')) {
					sb.append(text[currentIndex + 1]);
					currentIndex += 2;
				}
				else {
					sb.append(text[currentIndex]);
					currentIndex++;
				}
			}
			
			if (currentIndex >= text.length) {
				throw new IllegalStateException("End quotes missing.");
			}
			
			currentIndex++;
			
			if (currentIndex < text.length && !isWhitespace()) {
				throw new IllegalStateException("End quotes must be followed by a whitespace character or end of line.");
			}
			
			if (sb.isEmpty()) {
				throw new IllegalStateException("Empty string not allowed.");
			}
		}
		
		else {
			while(currentIndex < text.length && !isWhitespace()) {
				sb.append(text[currentIndex]);
				currentIndex++;
			}
		}
		
		return this.token = sb.toString();
	}
	
	/**
	 * Helper function for determining whether current index is pointing to a whitespace character.
	 * Characters recognized as whitespace: \n, \r, \t, ' '
	 * @return <code>true</code> if current index is pointing to a whitespace character, <code>false</code> otherwise
	 */
	private boolean isWhitespace() {
		return text[currentIndex] == '\r' || text[currentIndex] == '\n' || text[currentIndex] == '\t' || text[currentIndex] == ' ';
	}
	
	/**
	 * Helper function that increments current index as long as it is pointing to a whitespace character.
	 * Characters recognized as whitespace: \n, \r, \t, ' '
	 */
	private void clearWhitespaces() {
		while (this.currentIndex < this.text.length && isWhitespace()) {
			this.currentIndex++;
		}
	}
	
//	public static void main(String[] args) {
//		ShellParser parser = new ShellParser("copy	\" \"   test");
//		System.out.println(parser.parse());
//	}
}
