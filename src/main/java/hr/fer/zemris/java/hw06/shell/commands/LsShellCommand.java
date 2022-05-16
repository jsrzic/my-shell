package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command which displays a non-recursive directory listing.
 * Takes a single argument - directory name (path).
 * @author Josip
 *
 */
public class LsShellCommand implements ShellCommand {
	
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
			env.writeln("Invalid number of arguments for command ls. Check out help command for more info.");
			return ShellStatus.CONTINUE;
		}
		
		Path p;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			p = Paths.get(args.get(0));
		} catch (InvalidPathException e) {
			env.writeln("Invalid path name.");
			return ShellStatus.CONTINUE;
		}
		
		try(Stream<Path> stream = Files.list(p)) {
			stream.forEach(f -> {
				BasicFileAttributeView faView = Files.getFileAttributeView(f, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes;
				try {
					attributes = faView.readAttributes();
				} catch (IOException e) {
					env.writeln("Could not read file attributes for file: " + f);
					return;
				}
				
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				char isDirectory = attributes.isDirectory() ? 'd' : '-';
				char isReadable= Files.isReadable(f) ? 'r' : '-';
				char isWritable = Files.isWritable(f) ? 'w' : '-';
				char isExecutable = Files.isExecutable(f) ? 'x' : '-';
				long fileSize = attributes.size();
				String fileName = f.getFileName().toString();
				
				env.writeln(String.format("%c%c%c%c %10d %s %s", isDirectory, isReadable, isWritable, isExecutable, fileSize, formattedDateTime, fileName));
			});
		} catch (NotDirectoryException e) {
			env.writeln("Given path is not a directory.");
		} catch (IOException|SecurityException e) {
			env.writeln("An error has occured while listing directory content.");
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "ls";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		commandDescription.add("Displays a non-recursive directory listing.");
		commandDescription.add("Takes a single argument - directory name (path).");
		
		return Collections.unmodifiableList(commandDescription);
	}

}
