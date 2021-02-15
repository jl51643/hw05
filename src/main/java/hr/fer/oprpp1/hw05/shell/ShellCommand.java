package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Models commands in shell
 */
public interface ShellCommand {

    /**
     * Executes command and returns <code>ShellStatus</code> after execution
     *
     * @param env <code>Environment</code> of current shell
     * @param arguments arguments for execution of command
     * @return Returns <code>ShellStatus</code> after execution
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     * @return Returns command name
     */
    String getCommandName();

    /**
     * @return Returns description of command
     */
    List<String> getCommandDescription();
}
