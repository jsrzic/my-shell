package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface which defines functionality of a shell command and how it communicates with the given environment.
 * @author Josip
 *
 */
public interface ShellCommand {
	/**
	 * Performs the main logic behind the command.
	 * @param env environment used to communicate with the shell
	 * @param arguments everything that the user entered after the command name
	 * @return CONTINUE if shell should continue with normal work (prompt the user for input) or TERMINATE if shell should terminate
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Name of the command (as a string).
	 * @return name of the command (as a string).
	 */
	String getCommandName();
	
	/**
	 * Returns list of sentences which represent description of the command (how it is used and what it does).
	 * @return list of sentences which represent description of the command (how it is used and what it does)
	 */
	List<String> getCommandDescription();
}
