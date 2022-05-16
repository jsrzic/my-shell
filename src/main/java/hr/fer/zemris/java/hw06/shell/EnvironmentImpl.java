package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;
import java.util.SortedMap;

/**
 * Environment which uses System.in and System.out to communicate with the user.
 * @author Josip
 *
 */
public class EnvironmentImpl implements Environment {
	
	/**
	 * Symbol used by the shell to signal user that his input is a part of multiline shell input.
	 */
	private Character multilineSymbol;
	
	/**
	 * Symbol used by the shell to prompt user to enter some input.
	 */
	private Character promptSymbol;
	
	/**
	 * Symbol used by the user to signal shell that his input is a part of multiline shell input.
	 */
	private Character morelinesSymbol;
	
	/**
	 * Map of all commands supported by this environment
	 */
	private SortedMap<String, ShellCommand> commands;
	
	/**
	 * Scanner used to wait for and read user input.
	 */
	private Scanner sc;
	
	/**
	 * Creates a new environment instance with given commands.
	 * @param commands commands which are supported by this environment
	 */
	public EnvironmentImpl(SortedMap<String, ShellCommand> commands) {
		this.multilineSymbol = '|';
		this.promptSymbol = '>';
		this.morelinesSymbol = '\\';
		this.commands = commands;
		this.sc = new Scanner(System.in);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String readLine() throws ShellIOException {
		try {
			String line = sc.nextLine();
			return line;
		} catch (Exception e) {
			throw new ShellIOException("An error has occurred while reading input.\n" + e.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		} catch (Exception e) {
			throw new ShellIOException("An error has occurred while writing output.\n" + e.getMessage());
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			this.write(text + "\n");
		} catch (Exception e) {
			throw new ShellIOException("An error has occurred while writing output.\n" + e.getMessage());
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return this.commands;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getMultilineSymbol() {
		return this.multilineSymbol;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multilineSymbol = symbol;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getPromptSymbol() {
		return this.promptSymbol;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getMorelinesSymbol() {
		return this.morelinesSymbol;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.morelinesSymbol = symbol;
	}

}
