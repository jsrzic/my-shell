package hr.fer.zemris.java.hw06.shell;

/**
 * Enumeration which offers two ways a command can respond to the shell after it is done: 
 * CONTINUE tells shell to prompt the user for the next input;
 * TERMINATE tells shell to terminate its main program
 * @author Josip
 *
 */
public enum ShellStatus {
	CONTINUE,
	TERMINATE
}
