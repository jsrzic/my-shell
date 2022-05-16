package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
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
 * This command opens given file and writes its content to console.
 * Takes one or two arguments.
 * The first argument is path to some file and is mandatory.
 * The second argument is charset name that will be used to interpret chars from bytes.
 * If not provided, a default platform charset will be used.
 * 
 * @author Josip
 *
 */
public class CatShellCommand implements ShellCommand {
	
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
			env.writeln("Invalid number of arguments for command cat. Check out help command for more info.");
			return ShellStatus.CONTINUE;
		}
		
		Charset charset;
		Path p;
		String line;
		
		try {
			charset = args.size() == 2 ? Charset.forName(args.get(1)) : Charset.defaultCharset();
		} catch (IllegalCharsetNameException|UnsupportedCharsetException e) {
			env.writeln("Given charset name is invalid or not given charset is not supported.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			p = Paths.get(args.get(0));
		} catch (InvalidPathException e) {
			env.writeln("Invalid path name.");
			return ShellStatus.CONTINUE;
		}
		
		try (BufferedReader br = Files.newBufferedReader(p, charset)) {
			while((line = br.readLine()) != null) {
				env.writeln(line);
			}
		} catch (IOException e) {
			env.writeln("An error has occurred while reading the given file.");
			return ShellStatus.CONTINUE;
		} 
		
		return ShellStatus.CONTINUE;	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cat";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("This command opens given file and writes its content to console.");
		commandDescription.add("Takes one or two arguments.");
		commandDescription.add("The first argument is path to some file and is mandatory.");
		commandDescription.add("The second argument is charset name that will be used to interpret chars from bytes.");
		commandDescription.add("If not provided, a default platform charset will be used.");
		
		return Collections.unmodifiableList(commandDescription);
	}

}
