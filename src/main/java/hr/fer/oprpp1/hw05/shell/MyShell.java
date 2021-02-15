package hr.fer.oprpp1.hw05.shell;

import hr.fer.oprpp1.hw05.shell.commands.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MyShell {

    private static class EnvironmentImpl implements Environment {

        private static char PROMPTSYMBOL = '>';
        private static char MORELINESSYMBOL = '\\';
        private static char MULTILINESYMBOL = '|';

        private SortedMap<String, ShellCommand> commands;

        public EnvironmentImpl() {

            commands = new TreeMap<>();
            commands.put("cat", new CatShellCommand());
            commands.put("charsets", new CharsetsShellCommand());
            commands.put("copy", new CopyShellCommand());
            commands.put("exit", new ExitShellCommand());
            commands.put("help", new HelpShellCommand());
            commands.put("hexdump", new HexDumpShellCommand());
            commands.put("ls", new LsShellCommand());
            commands.put("mkdir", new MkdirShellCommand());
            commands.put("symbol", new SymbolShellCommand());
            commands.put("tree", new TreeShellCommand());
        }

        Scanner shell = new Scanner(System.in);
        @Override
        public String readLine() throws ShellIOException {
            String line = "";
            try {
                line = shell.nextLine();
            } catch (NoSuchElementException e) {
                throw new ShellIOException("No input lines found");
            }
            return line;
        }

        @Override
        public void write(String text) throws ShellIOException {
            System.out.print(text);
        }

        @Override
        public void writeln(String text) throws ShellIOException {
            System.out.println(text);
        }

        @Override
        public SortedMap<String, ShellCommand> commands() {
            return Collections.unmodifiableSortedMap(this.commands);
        }

        @Override
        public Character getMultilineSymbol() {
            return this.MULTILINESYMBOL;
        }

        @Override
        public void setMultilineSymbol(Character symbol) {
            this.MULTILINESYMBOL = symbol;
        }

        @Override
        public Character getPromptSymbol() {
            return this.PROMPTSYMBOL;
        }

        @Override
        public void setPromptSymbol(Character symbol) {
            this.PROMPTSYMBOL = symbol;
        }

        @Override
        public Character getMorelinesSymbol() {
            return this.MORELINESSYMBOL;
        }

        @Override
        public void setMorelinesSymbol(Character symbol) {
            this.MORELINESSYMBOL = symbol;
        }
    }

    public static void main(String[] args) {

        Environment env = new EnvironmentImpl();
        ShellStatus status = ShellStatus.CONTINUE;
        while (!status.equals(ShellStatus.TERMINATE)) {
            env.write(env.getPromptSymbol() + " ");
            String line = env.readLine();
            while (line.endsWith(env.getMorelinesSymbol().toString())) {
                env.write(env.getMultilineSymbol() + " ");
                line = line.substring(0, line.length() - env.getMorelinesSymbol().toString().length()) + " ";
                line += env.readLine() + " ";
            }
            String commandName = extractCommand(line, env);
            String arguments = extractArguments(line, env);
            ShellCommand command = env.commands().get(commandName);
            status = command.executeCommand(env, arguments);
        }
    }

    private static String extractArguments(String line, Environment env) {
        String command = extractCommand(line, env);
        line = line.substring(command.length());
        line = line.trim();

        return line;

        /*String[] args;
        if (env.getMorelinesSymbol().toString().equals("\\")) {
            args = line.split("\\\\ ");
        } else  {
            args = line.split(env.getMorelinesSymbol().toString() + " ");
        }

        String argumnts = Arrays.stream(args).collect(Collectors.joining(" "));

        return argumnts;
*/
//        if (argumnts.contains("\"")) {
//            args = splitArgumentsWithQuotes(argumnts, env);
//        } else {
//            args = argumnts.split("\\s");
//        }
//
//        return argumnts = Arrays.stream(args).collect(Collectors.joining(" "));
    }

    public static String[] splitArguments(String argumnts, Environment env) {

        List<String> args = new LinkedList<>();
        char[] argsArray = argumnts.toCharArray();
        for (int i = 0; i < argsArray.length; i++) {
            String arg = "";
            if (argsArray[i] == '"') {
                //arg += argsArray[i];
                i++;
                while (true) {
                    if (i < argsArray.length && argsArray[i] != '\\' && argsArray[i] != '"') {
                        arg += argsArray[i];
                        i++;
                        continue;
                    }
                    if (i < argsArray.length && argumnts.length() > i+1 && argsArray[i] == '\\' && (argsArray[i+1] == '\\' || argsArray[i+1] == '"')) {
                        i++;
                        arg += argsArray[i];
                        i ++;
                        continue;
                    }
                    if (i < argsArray.length && argsArray[i] == '"') {
                        i++;
                        if (i <= argsArray.length || argsArray[i] == ' ' || argsArray[i] == env.getMorelinesSymbol()) {
                            break;
                        } else {
                            throw new IllegalArgumentException("Illegal arguments " + argumnts);
                        }
                    }
                    if (i < argsArray.length && argsArray[i] == '\\') {
                        arg += "\\";
                        i++;
                    }
                }
                args.add(arg);
                continue;
            } if (argsArray[i] == ' ') {
                continue;
            }
            while (i < argsArray.length && argsArray[i] != ' ') {
                arg += argsArray[i];
                i++;
            }
            args.add(arg);
        }

        String[] result = new String[args.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = args.get(i);
        }

        return result;
    }

    private static String extractCommand(String line, Environment env) {
        String[] command = line.split("\\s");
        if (env.commands().keySet().contains(command[0])) {
            return command[0];
        }
        throw new ShellIOException(command[0]  + " is not recognised as command");
    }
}
