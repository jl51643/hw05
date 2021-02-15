package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.UnexpectedException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Model of <code>ShellCommand</code>
 * Displays contend of file
 */
public class CatShellCommand implements ShellCommand {

    /**
     * Displays content of file specified as <code>argument</code>
     *
     * @param env <code>Environment</code> of current shell
     * @param arguments arguments for execution of command
     * @return Returns <code>ShellStatus.CONTINUE</code> status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        Path path = null;
        Charset charset = null;

        String[] args = arguments.split("\\s");
        if (args.length > 0) {
            path = Paths.get(args[0]);
            path.normalize();
        }

        if (args.length == 2) {
            charset = Charset.forName(args[1]);
        }

        if (path == null || !Files.isRegularFile(path) || !Files.isReadable(path)) {
            env.writeln("Could not read file " + args[0]);
            return ShellStatus.CONTINUE;
        }

        List<String> lines = new LinkedList<>();

        if (charset == null) {
            try (Stream<String> is = Files.lines(path)) {
                lines = is.collect(Collectors.toList());
            } catch (IOException e) {
                env.writeln("Could not read file " + path.getFileName().toString());
            } catch (UncheckedIOException e) {
                env.writeln("Define valid charset to read file " + path.getFileName().toString());
            }
        } else {
            try (Stream<String> is = Files.lines(path, charset)) {
                lines = is.collect(Collectors.toList());
            } catch (IOException e) {
                env.writeln("Could not read file " + path.getFileName().toString());
            } catch (UncheckedIOException e) {
                env.writeln( "Invalid charset " + charset.displayName());
            }
        }

        lines.forEach(env::writeln);

        return ShellStatus.CONTINUE;
    }

    /**
     * @return Returns command name
     */
    @Override
    public String getCommandName() {
        return "cat";
    }

    /**
     * @return Returns command description
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new LinkedList<>();
        description.add("Displays content of file.");
        description.add("");
        description.add("cat path [charset]");
        description.add("");
        description.add("\tcharset - charset to read file.");
        description.add("");

        return Collections.unmodifiableList(description);
    }
}
