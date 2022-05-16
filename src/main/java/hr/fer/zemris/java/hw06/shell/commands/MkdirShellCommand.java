package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command which creates the directory structure given by user input.
 * Takes a single argument: directory name (path to the directory).
 * @author Josip
 *
 */
public class MkdirShellCommand implements ShellCommand {
	
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
		
		if (args.size() != 1) {
			env.writeln("Invalid number of arguments for command mkdir. Check out help command for more info.");
			return ShellStatus.CONTINUE;
		}
		
		Path p;
		
		try {
			p = Paths.get(args.get(0));
		} catch (InvalidPathException e) {
			env.writeln("Invalid path name.");
			return ShellStatus.CONTINUE;
		}
				
		try {
			Files.createDirectories(p);
		} catch (Exception e) {
			env.writeln("An error has occurred creating the directory. " + e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		env.writeln("Directory created successfully.");
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "mkdir";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Creates the appropriate directory structure.");
		commandDescription.add("Takes a single argument: directory name (path to the directory).");
		
		return Collections.unmodifiableList(commandDescription);
	}

}
