package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.crypto.Util;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.MyShell;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Model of <code>ShellCommand</code>
 * Displays content of file as hexadecimal
 */
public class HexDumpShellCommand implements ShellCommand {

    /**
     * Displays content of file as hexadecimal
     *
     * @param env <code>Environment</code> of current shell
     * @param arguments arguments for execution of command
     * @return returns <code>ShellStatus.CONTINUE</code> status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = MyShell.splitArguments(arguments, env);
        if (args.length != 1) {
            env.writeln("Unexpected number of arguments");
            return ShellStatus.CONTINUE;
        }

        Path path = Paths.get(args[0]);

        if (!Files.isRegularFile(path)) {
            env.writeln("Given path is not regular file");
            return ShellStatus.CONTINUE;
        }

        try (InputStream is = Files.newInputStream(path)) {
            byte[] inputBuffer = new byte[4096];
            int counter = 0;
            boolean hasMoreBytes = true;
            while (hasMoreBytes) {
                env.write(String.format("%08d", counter) + ":");
                char[] chars = new char[16];
                for (int i = 0; i < 16; i++) {
                    int nextByte = is.read();
                    byte[] b = new byte[1];
                    b[0] = (byte) nextByte;
                    if (nextByte == -1) {
                        hasMoreBytes = false;
                        env.write(printWhitespaces(i));
                        break;
                    }
                    if (nextByte < 32 || nextByte > 127) {

                        nextByte = 46;
                    }
                    if (i == 8) {
                        env.write("|" + Util.byteToHex(b)/*Integer.toHexString(nextByte)*/);
                    }
                    env.write(" " + Util.byteToHex(b)/*Integer.toHexString(nextByte)*/);
                    chars[i] = (char) nextByte;
                }
                env.write(" | ");
                for (int i = 0; i < chars.length; i++) {
                    if (chars[i] == 0)
                        env.write(" ");
                    env.write(Character.toString(chars[i]));
                }
                env.writeln("");
                counter += 10;
            }
        } catch (IOException e) {
            //e.printStackTrace();
            env.writeln("Could not open file " + path.getFileName());
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Formats output by adding whitespaces
     *
     * @param i number of processed bytes
     * @return String of whitespaces to complete output
     */
    private String printWhitespaces(int i) {
        if (i >= 8)
            return "   ".repeat(16 - i);
        else
            return "   ".repeat(8 - i) + "|" + " ".repeat(23);
    }

    /**
     * @return Returns command name
     */
    @Override
    public String getCommandName() {
        return "hexdump";
    }

    /**
     * @return Returns command description
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new LinkedList<>();
        description.add("Displays content of file in hexadecimal");
        description.add("");
        description.add("hexdump file");
        description.add("");

        return Collections.unmodifiableList(description);
    }
}
