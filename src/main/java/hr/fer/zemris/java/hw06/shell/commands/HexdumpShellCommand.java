package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.crypto.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command which displays file content in hexadecimal and ascii notation.
 * Expects a single argument: file name.
 * @author Josip
 *
 */
public class HexdumpShellCommand implements ShellCommand {
	
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
			env.writeln("Invalid number of arguments for command hexdump. Check out help command for more info.");
			return ShellStatus.CONTINUE;
		}
		
		Path p;
		
		try {
			p = Paths.get(args.get(0));
		} catch (InvalidPathException e) {
			env.writeln("Invalid path name.");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(p)) {
			env.writeln("Argument must be a file, not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		StringBuilder sb = new StringBuilder();
		
		try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(p))) {
			byte[] b = new byte[16];
			int r;
			int count = 0;
			char c;
			
			while((r = bis.read(b)) > -1) {
				StringBuilder hexSequence1 = new StringBuilder();
				StringBuilder hexSequence2 = new StringBuilder();
				StringBuilder asciiSequence = new StringBuilder();
				for (int i = 0; i < r; i++) {
					if (i < 8) {
						hexSequence1.append(" " + Util.bytetohex(new byte[] {b[i]}));
					} else {
						hexSequence2.append(Util.bytetohex(new byte[] {b[i]}) + " ");
					}
					
					c = (char)b[i];
					if (c < 32 || c > 127) {
						c = '.';
					}
					asciiSequence.append(c);
				}
				
				sb.append(String.format("%08X:%-24s|%-24s| %s\n", count, hexSequence1.toString(), hexSequence2.toString(), asciiSequence.toString()));
				count += r;
			}
			
		} catch (IOException e) {
			env.writeln("An error has occurred while reading file content.");
			return ShellStatus.CONTINUE;
		}
		
		env.write(sb.toString());
		
		return ShellStatus.CONTINUE;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "hexdump";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Displays file content in hexadecimal and ascii notation.");
		commandDescription.add("Expects a single argument: file name.");
		
		return Collections.unmodifiableList(commandDescription);
	}
	

}
