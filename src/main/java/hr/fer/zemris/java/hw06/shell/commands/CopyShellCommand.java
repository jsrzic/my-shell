package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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
 * Command which copies the source file into the destination file.
 * Expects two arguments: source file name and destination file name (i.e. paths and names).
 * If destination file exists, it can be overwritten.
 * If second argument is directory, copies source file into that directory.
 * @author Josip
 *
 */
public class CopyShellCommand implements ShellCommand {
	
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
		
		if (args.size() != 2) {
			env.writeln("Invalid number of arguments for command copy. Check out help command for more info.");
			return ShellStatus.CONTINUE;
		}
		
		Path src;
		Path dest;
		
		try {
			src = Paths.get(args.get(0));
		} catch (InvalidPathException e) {
			env.writeln("Invalid source path name.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			dest = Paths.get(args.get(1));
		} catch (InvalidPathException e) {
			env.writeln("Invalid destination path name.");
			return ShellStatus.CONTINUE;
		}
				
		if (Files.isDirectory(dest)) {
			try {
				dest = Paths.get(dest.toString(), src.getFileName().toString());
			} catch (InvalidPathException e) {
				env.writeln("Invalid destination path name.");
				return ShellStatus.CONTINUE;
			}	
		}
		
		if(Files.exists(dest)) {
			env.writeln(String.format("File %s already exists, do you want to overwrite it? [Y/N]", dest));
			String response = env.readLine().strip();
			
			if (response.equals("N")) {
				return ShellStatus.CONTINUE;
			}
			
			if (!response.equals("Y")) {
				env.writeln("Invalid response: " + response);
				return ShellStatus.CONTINUE;
			}
		}
		
		try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(src));
				BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(dest));) {
			
			byte[] b = new byte[4096];
			int r;
			while((r = bis.read(b)) > -1) {
				bos.write(b, 0, r);
			}
			
		} catch (IOException e) {
			env.writeln("An error has occurred while copying file.");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln(String.format("File %s copied to %s successfully.", src, dest));
		return ShellStatus.CONTINUE;

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "copy";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Copies the source file into the destination file.");
		commandDescription.add("Expects two arguments: source file name and destination file name (i.e. paths and names).");
		commandDescription.add("If destination file exists, it can be overwritten.");
		commandDescription.add("If second argument is directory, copies source file into that directory.");
		
		return Collections.unmodifiableList(commandDescription);
	}

}
