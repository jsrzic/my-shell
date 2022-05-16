package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command used for displaying and modifying shell symbols.
 * Three symbols are supported: PROMPT, MULTILINE, MORELINES.
 * PROMPT is the symbol which is displayed at the beginning when shell is waiting for user input.
 * MULTILINE is the symbol which can be placed at the end of the line if user wants to enter input which spans across multiple lines.
 * MORELINES is the symbol which is displayed at the beginning when shell is waiting for additional lines of the input.
 * First argument is the shell symbol whose value is to be displayed.
 * Second argument is optional. If it is present, given shell symbol is changed to the symbol denoted by the second argument.
 * @author Josip
 *
 */
public class SymbolShellCommand implements ShellCommand {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ShellParser parser = new ShellParser(arguments);
		List<String> args;
		try {
			args = parser.parse();
		} catch (Exception e) {
			env.writeln("An error has occurred while reading arguments. " + e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if (args.size() < 1 || args.size() > 2) {
			env.writeln("Invalid number of arguments for command symbol. Check out help command for more info.");
			return ShellStatus.CONTINUE;
		}
		
		switch(args.get(0)) {
			case "PROMPT" -> {
				if (args.size() == 2) {
					String newPromptSymbol = args.get(1);
					if(newPromptSymbol.length() != 1) {
						env.writeln("New PROMPT symbol must be a single character, not: " + newPromptSymbol);
						return ShellStatus.CONTINUE;
					}
					Character oldPromptSymbol = env.getPromptSymbol();
					env.setPromptSymbol(newPromptSymbol.charAt(0));
					env.writeln(String.format("Symbol for PROMPT changed from '%c' to '%c'", oldPromptSymbol, env.getPromptSymbol()));
				}
				else {
					env.writeln(String.format("Symbol for PROMPT is '%c'", env.getPromptSymbol()));
				}
			}
			case "MORELINES" -> {
				if (args.size() == 2) {
					String newMorelinesSymbol = args.get(1);
					if(newMorelinesSymbol.length() != 1) {
						env.writeln("New MORELINES symbol must be a single character, not: " + newMorelinesSymbol);
						return ShellStatus.CONTINUE;
					}
					Character oldMorelinesSymbol = env.getMorelinesSymbol();
					env.setMorelinesSymbol(newMorelinesSymbol.charAt(0));
					env.writeln(String.format("Symbol for MORELINES changed from '%c' to '%c'", oldMorelinesSymbol, env.getMorelinesSymbol()));
				}
				else {
					env.writeln(String.format("Symbol for MORELINES is '%c'", env.getMorelinesSymbol()));
				}
			}
			case "MULTILINE" -> {
				if (args.size() == 2) {
					String newMultilineSymbol = args.get(1);
					if(newMultilineSymbol.length() != 1) {
						env.writeln("New MULTILINE symbol must be a single character, not: " + newMultilineSymbol);
						return ShellStatus.CONTINUE;
					}
					Character oldMultilineSymbol = env.getMultilineSymbol();
					env.setMultilineSymbol(newMultilineSymbol.charAt(0));
					env.writeln(String.format("Symbol for MULTILINE changed from '%c' to '%c'", oldMultilineSymbol, env.getMultilineSymbol()));
				}
				else {
					env.writeln(String.format("Symbol for MULTILINE is '%c'", env.getMultilineSymbol()));
				}
			}
			default -> {
				env.writeln("Unrecognized argument: " + args.get(0));
			}
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "symbol";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Command used for displaying and modifying shell symbols.");
		commandDescription.add("Three symbols are supported: PROMPT, MULTILINE, MORELINES.");
		commandDescription.add("PROMPT is the symbol which is displayed at the beginning when shell is waiting for user input.");
		commandDescription.add("MULTILINE is the symbol which can be placed at the end of the line if user wants to enter input which spans across multiple lines.");
		commandDescription.add("MORELINES is the symbol which is displayed at the beginning when shell is waiting for additional lines of the input.");
		commandDescription.add("First argument is the shell symbol whose value is to be displayed.");
		commandDescription.add("Second argument is optional. If it is present, given shell symbol is changed to the symbol denoted by the second argument.");
		
		return Collections.unmodifiableList(commandDescription);
	}
	
}
