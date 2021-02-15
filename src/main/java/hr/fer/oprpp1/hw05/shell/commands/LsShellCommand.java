package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.MyShell;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Model of <code>ShellCommand</code>
 * Displays all files from specified directory
 */
public class LsShellCommand implements ShellCommand {

    /**
     * Displays all files from directory specified by arguments
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
        path.normalize();
        if (!Files.isDirectory(path)) {
            env.writeln(args[0] + "Is not directory.");
            return ShellStatus.CONTINUE;
        }
        for (File f : Objects.requireNonNull(path.toFile().listFiles())) {
            String properties = getFileProperties(f);
            long size = f.length();
            String creationDateAndTime = getCreationDateAndTime(f);


            env.writeln(String.format("%s %10d %s %s", properties, size, creationDateAndTime, f.getName()));
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Returns date and time of creation of file
     *
     * @param f file
     * @return returns date and time of creation of file
     */
    private String getCreationDateAndTime(File f) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Path path = f.toPath();
        path.normalize();
        BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
        );
        BasicFileAttributes attributes = null;
        try {
            attributes = faView.readAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileTime fileTime = attributes.creationTime();
        String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));


        return formattedDateTime;
    }


    /**
     * Returns attributes of file.
     * Checks if file is directory, readable, writable and executable
     *
     * @param f file
     * @return returns attributes of file
     */
    private String getFileProperties(File f) {
        String dir = Files.isDirectory(f.toPath()) ? "d" : "-";
        String read = Files.isReadable(f.toPath()) ? "r" : "-";
        String write = Files.isWritable(f.toPath()) ? "w" : "-";
        String exe = Files.isExecutable(f.toPath()) ? "x" : "-";

        return dir + read + write + exe;
    }

    /**
     * @return returns command name
     */
    @Override
    public String getCommandName() {
        return "ls";
    }

    /**
     * @return returns command description
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new LinkedList<>();
        description.add("Displays a list of files and subdirectories in a directory");
        description.add("");
        description.add("ls path");
        description.add("");

        return Collections.unmodifiableList(description);
    }
}
