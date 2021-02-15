package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Model of <code>ShellCommand</code>.
 * Quits program
 */
public class ExitShellCommand implements ShellCommand {

    /**
     * Quits program
     *
     * @param env <code>Environment</code> of current shell
     * @param arguments arguments for execution of command
     * @return Returns <code>ShellStatus.TERMINATE</code> status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        return ShellStatus.TERMINATE;
    }

    /**
     * @return Returns command name
     */
    @Override
    public String getCommandName() {
        return "exit";
    }

    /**
     * @return Returns command description
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new LinkedList<>();
        description.add("Quits the shell program");
        description.add("");
        description.add("exit");
        description.add("");

        return Collections.unmodifiableList(description);
    }
}
