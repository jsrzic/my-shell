package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command which takes no arguments and lists names of supported charsets for this Java platform.
 * @author Josip
 *
 */
public class CharsetsShellCommand implements ShellCommand {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.equals("")) {
			env.writeln("Invalid number of arguments for command charsets. Check out help command for more info.");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln("Supported charsets:");
		
		Charset.availableCharsets().keySet().stream().forEach(name -> env.writeln(name));
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "charsets";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Takes no arguments and lists names of supported charsets for this Java platform.");
		
		return Collections.unmodifiableList(commandDescription);
	}

}
