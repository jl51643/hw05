package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Model od <code>ShellCommand</code>
 * Lists all available charsets for this machine
 */
public class CharsetsShellCommand implements ShellCommand {

    /**
     * Lists all available charsets for this machine
     *
     * @param env <code>Environment</code> of current shell
     * @param arguments arguments for execution of command
     * @return Returns <code>ShellStatus.CONTINUE</code> status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (!arguments.equals("")) {
            env.writeln("Unexpected number of arguments");
            return ShellStatus.CONTINUE;
        }
        for (var charset : Charset.availableCharsets().entrySet())
            env.writeln(charset.getValue().displayName());
        return ShellStatus.CONTINUE;
    }

    /**
     * @return Returns command name
     */
    @Override
    public String getCommandName() {
        return "charsets";
    }

    /**
     * @return Returns command description
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new LinkedList<>();
        description.add("Lists all available charsets for this machine");
        description.add("");
        description.add("charsets");
        description.add("");

        return Collections.unmodifiableList(description);
    }
}
