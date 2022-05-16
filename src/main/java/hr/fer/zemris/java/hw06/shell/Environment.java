package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Interface through which shell communicates with the user.
 * @author Josip
 *
 */
public interface Environment {
	/**
	 * Reads a line of user input from shell.
	 * @return the line received from user input
	 * @throws ShellIOException if an unexpected error occurs while reading the line from shell
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Displays given text to shell.
	 * @param text text which is to be displayed
	 * @throws ShellIOException if an unexpected error occurs while writing the line to shell
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Displays given text to shell and appends a new line character ('\n').
	 * @param text text which is to be displayed
	 * @throws ShellIOException if an unexpected error occurs while writing the line to shell
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns the map of all commands supported by this environment.
	 * Keys are command names and values are implementations of ShellCommand interface.
	 * @return map of all commands supported by this environment
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Returns the symbol used by the shell to signal user that his input is a part of multiline shell input.
	 * @return symbol used by the shell to signal user that his input is a part of multiline shell input
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets the symbol used by the shell to signal user that his input is a part of multiline shell input to the given value.
	 * @param symbol symbol used by the shell to signal user that his input is a part of multiline shell input
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Returns the symbol used by the shell to prompt user to enter some input.
	 * @return symbol used by the shell to prompt user to enter some input
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets the symbol used by the shell to prompt user to enter some input to the given value.
	 * @param symbol symbol used by the shell to prompt user to enter some input
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Returns the symbol used by the user to signal shell that his input is a part of multiline shell input.
	 * @return symbol used by the user to signal shell that his input is a part of multiline shell input
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets the symbol used by the user to signal shell that his input is a part of multiline shell input to the given value.
	 * @param symbol symbol used by the user to signal shell that his input is a part of multiline shell input
	 */
	void setMorelinesSymbol(Character symbol);
}
