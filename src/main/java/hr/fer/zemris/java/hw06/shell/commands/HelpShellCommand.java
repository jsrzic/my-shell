package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command used to get more information about other commands.
 * If started with no arguments, lists names of all supported commands.
 * If started with single argument, prints name and the description of selected command.
 * @author Josip
 *
 */
public class HelpShellCommand implements ShellCommand {
	
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
		
		if (args.size() > 1) {
			env.writeln("Invalid number of arguments for command help. Try help without arguments to see list of all available commands, "
					+ "or help [command] to find description of a specific command.");
			return ShellStatus.CONTINUE;
		}
		
		if (args.size() == 0) {
			env.writeln("Here is a list of all available commands, try help [command] to find out more about a specific command:");
			env.commands().keySet().forEach(c -> env.writeln(c));
		}
		
		else {
			if (!env.commands().containsKey(args.get(0))) {
				env.writeln("Command " + args.get(0) + " is not available.");
				return ShellStatus.CONTINUE;
			}
			
			env.commands().get(args.get(0)).getCommandDescription().forEach(l -> env.writeln(l));
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "help";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Command used to get more information about other commands.");
		commandDescription.add("If started with no arguments, lists names of all supported commands.");
		commandDescription.add("If started with single argument, prints name and the description of selected command.");
		
		return Collections.unmodifiableList(commandDescription);
	}

}
