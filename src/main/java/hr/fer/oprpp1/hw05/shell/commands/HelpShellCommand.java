package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.MyShell;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Model of <code>ShellCommand</code>.
 * If called without arguments return list
 * of all implemented commands.
 * If called with one argument returns more information
 * about that command
 */
public class HelpShellCommand implements ShellCommand {

    /**
     * Lists information about implemented commands.
     * If arguments are not specified returns list of all implemented commands.
     * If argument is given returns description of given command
     *
     * @param env <code>Environment</code> of current shell
     * @param arguments arguments for execution of command
     * @return returns <code>ShellStatus.CONTINUE</code> status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = MyShell.splitArguments(arguments, env);
        if (args.length == 0) {
            for (String command : env.commands().keySet()) {
                env.writeln(env.commands().get(command).getCommandName());
                //writeCommandNameAndDescription(env, command);
            }
        }else if (args.length == 1) {
            if (env.commands().containsKey(args[0])) {
                env.write(env.commands().get(args[0]).getCommandName() + "\t");
                for (String line : env.commands().get(args[0]).getCommandDescription())
                    env.writeln(line);
            } else {
                env.writeln(arguments + "Is not recognized as command.");
                env.writeln("Try typing \"help\" to see list of all implemented commands");
            }
        } else {
            env.writeln("Unexpected number of arguments");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * @return Returns command name
     */
    @Override
    public String getCommandName() {
        return "help";
    }

    /**
     * @return Returns command description
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new LinkedList<>();
        description.add("Provides Help information for shell commands.");
        description.add("");
        description.add("help [command]");
        description.add("");
        description.add("\tcommand - displays help information on that command.");
        description.add("");

        return Collections.unmodifiableList(description);
    }
}
