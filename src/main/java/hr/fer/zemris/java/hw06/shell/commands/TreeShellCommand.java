package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command which prints directory content recursively.
 * Each directory level shifts output two charatcers to the right.
 * Expects a single argument - directory name.
 * @author Josip
 *
 */
public class TreeShellCommand implements ShellCommand {
	
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
			env.writeln("Invalid number of arguments for command tree. Check out help command for more info.");
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
			Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
				int depth = 0;
				
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					env.writeln(" ".repeat(depth * 2) + dir.getFileName());
					depth++;
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					depth--;
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					env.writeln(" ".repeat(depth * 2) + file.getFileName());
					return FileVisitResult.CONTINUE;
				}
				
			});
		} catch (IOException e) {
			env.writeln("An error has occurred while traversing the directory tree.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "tree";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Prints directory content recursively.");
		commandDescription.add("Each directory level shifts output two charatcers to the right.");
		commandDescription.add("Expects a single argument - directory name.");
		
		return Collections.unmodifiableList(commandDescription);
	}

}
