package hr.fer.zemris.java.hw06.shell;

public class ShellIOException extends RuntimeException {

	/**
	 * Auto-generated serial version ID of the exception.
	 */
	private static final long serialVersionUID = 3844731367581627444L;
	
	/**
	 * Creates a new ShellIOException.
	 * @param errorMessage information about what caused the exception to be thrown
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
}
