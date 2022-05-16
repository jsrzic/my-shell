package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * Custom shell through which user can perform various commands to manipulate files and explore the file system.
 * @author Josip
 *
 */
public class MyShell {

	public static void main(String[] args) {
		SortedMap<String, ShellCommand> commands = new TreeMap<>();
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		
		commands = Collections.unmodifiableSortedMap(commands);
		
		Environment env = new EnvironmentImpl(commands);
		try {
			env.writeln("Welcome to MyShell v 1.0");
			
			ShellStatus status = ShellStatus.CONTINUE;
			String l;
			while(status != ShellStatus.TERMINATE) {
				StringBuilder sb = new StringBuilder();
				env.write(env.getPromptSymbol() + " ");
				l = env.readLine();
				while(l.charAt(l.length()-1) == env.getMorelinesSymbol()) {
					sb.append(l.substring(0, l.length()-1));
					env.write(env.getMultilineSymbol() + " ");
					l = env.readLine();
				}
				sb.append(l);
				String[] inputChunks = sb.toString().strip().split(" +", 2);
				String commandName = inputChunks[0];
				String arguments = inputChunks.length > 1 ? inputChunks[1] : "";
				
				ShellCommand command = commands.get(commandName);
				
				if (command == null) {
					env.writeln("Unknown command: " + commandName);
					continue;
				}
				
				status = command.executeCommand(env, arguments);
			}
			
		} catch (ShellIOException e) {
			System.err.println("Shell terminated abruptly. " + e.getMessage());
			return;
		}
			
	}

}
