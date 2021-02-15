package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.MyShell;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Model of <code>ShellCommand</code>
 * Copies one file to another location
 */
public class CopyShellCommand implements ShellCommand {

    /**
     * Copies one file to another location
     *
     * @param env <code>Environment</code> of current shell
     * @param arguments arguments for execution of command
     * @return returns <code>ShellStatus.CONTINUE</code> status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = MyShell.splitArguments(arguments, env);
        if (args.length != 2) {
            env.writeln("Unexpected number of arguments");
            return ShellStatus.CONTINUE;
        }
        Path src = Paths.get(args[0]);
        src.normalize();
        Path destination = Paths.get(args[1]);
        destination.normalize();
        if (Files.isDirectory(destination)) {
            destination = destination.resolve(src.getFileName());
        }
        if (Files.exists(destination)) {
            env.writeln("Given destination file already exists");
            env.writeln("Do you want to rewrite existing file? [yes/no]");
            String response = env.readLine();
            if (!response.equalsIgnoreCase("yes")) {
                env.writeln("File is not copied");
                return ShellStatus.CONTINUE;
            }
        }
        if (!Files.isRegularFile(src)) {
            env.writeln("Given source file is not file");
            return ShellStatus.CONTINUE;
        }


        try (InputStream is = Files.newInputStream(src); OutputStream os = Files.newOutputStream(destination)) {
            byte[] inputBuffer = new byte[4096];
            while (true) {
                int len = is.read(inputBuffer);
                if (len == -1)
                    break;
                os.write(inputBuffer, 0, len);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            env.writeln("Could not read file " + src.getFileName());
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * @return Returns command name
     */
    @Override
    public String getCommandName() {
        return "copy";
    }

    /**
     * @return Returns command description
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new LinkedList<>();
        description.add("Copies one file to another location.");
        description.add("");
        description.add("copy source destination");
        description.add("");
        description.add("\tsource - Specifies the file to be copied.");
        description.add("\tdestination - Specifies the directory and/or filename for the new file");
        description.add("");

        return Collections.unmodifiableList(description);
    }
}
