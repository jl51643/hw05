package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.MyShell;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class MkdirShellCommand implements ShellCommand {
    /**
     * Creates directory on path specified by argumnts
     *
     * @param env <code>Environment</code> of current shell
     * @param arguments arguments for execution of command
     * @return returns <code>ShellStatus.CONTINUE</code> status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = MyShell.splitArguments(arguments, env);
        if (args.length != 1) {
            env.write("Unexpected number of arguments.");
            return ShellStatus.CONTINUE;
        }

        Path path = Paths.get(args[0]);
        path.normalize();
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            env.write("Could not create directory " + args[0]);
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * @return returns command name
     */
    @Override
    public String getCommandName() {
        return "mkdir";
    }

    /**
     * @return returns command description
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new LinkedList<>();
        description.add("Creates a directory");
        description.add("");
        description.add("mkdir path");
        description.add("");

        return Collections.unmodifiableList(description);
    }
}
