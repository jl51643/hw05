package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.MyShell;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Model of <code>ShellCommand</code>
 * Displays or changes prompt, morelines or multiline symbol
 */
public class SymbolShellCommand implements ShellCommand {

    /**
     * Displays or changes prompt, morelines or multiline symbol.
     * If given only one argument then displays symbol of that argument
     * if given two arguments changes old symbol
     *
     * @param env <code>Environment</code> of current shell
     * @param arguments arguments for execution of command
     * @return returns <code>ShellStatus.CONTINUE</code> status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = MyShell.splitArguments(arguments, env);
        if (args.length == 1) {
            switch (args[0].toUpperCase()) {
                case "PROMPT" -> env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
                case "MORELINES" -> env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol()+ "'");
                case "MULTILINE" -> env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
            }
        } else if (args.length == 2) {
            switch (args[0].toUpperCase()) {
                case "PROMPT" -> {
                    env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + args[1] + "'");
                    env.setPromptSymbol(args[1].charAt(0));
                }
                case "MORELINES" -> {
                    env.writeln("Symbol for MORELINES changed from  '" + env.getMorelinesSymbol() + "' to " + args[1] + "'");
                    env.setMorelinesSymbol(args[1].charAt(0));
                }
                case "MULTILINE" -> {
                    env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '" + args[1] + "'");
                    env.setMultilineSymbol(args[1].charAt(0));
                }
            }
        } else {
            env.writeln("Unexpected number of arguments");
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * @return returns command name
     */
    @Override
    public String getCommandName() {
        return "symbol";
    }

    /**
     * @return returns command description
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new LinkedList<>();
        description.add("Displays or changes prompt, morelines or multiline symbol");
        description.add("");
        description.add("symbol [PROMPT][MORELINES][MULTILINE] [c]");
        description.add("");
        description.add("\tc - new symbol");
        description.add("");

        return Collections.unmodifiableList(description);
    }
}
