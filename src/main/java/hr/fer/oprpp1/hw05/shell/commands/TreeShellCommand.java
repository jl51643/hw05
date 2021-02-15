package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.MyShell;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Model of <code>ShellCommand</code>
 * Displays tree structure of directory
 */
public class TreeShellCommand implements ShellCommand {

    /**
     * Implementation of <code>FileVisitor</code>
     */
    private class FileVisitImpl implements FileVisitor<Path> {

        private Environment env;
        private String arguments;
        private int level;

        /**
         * Constructing new <code>FileVisitor</code>
         *
         * @param env environment of this shell
         */
        public FileVisitImpl(Environment env) {
            this.env = env;
            this.level = 0;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) {
            this.env.writeln("  ".repeat(this.level) + path.getFileName().toString());
            this.level = level + 1;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
            this.env.writeln("  ".repeat(this.level) + path.getFileName().toString());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            env.writeln("Could not open file " + file.getFileName());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            this.level = level - 1;
            return FileVisitResult.CONTINUE;
        }
    }

    /**
     * Displays tree structure of directory given in arguments
     *
     * @param env <code>Environment</code> of current shell
     * @param arguments arguments for execution of command
     * @return returns <code>ShellStatus.CONTINUE</code> status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = MyShell.splitArguments(arguments, env);
        if (args.length != 1) {
            env.writeln("Unexpected number of arguments.");
            return ShellStatus.CONTINUE;
        }


        Path path = Paths.get(args[0]);
        path.normalize();
        try {
            Files.walkFileTree(path, new FileVisitImpl(env));
        } catch (IOException e) {
            //e.printStackTrace();
            env.writeln("Access to file " + path.getFileName() + " is denied");
        } catch (InvalidPathException e) {
            env.writeln("Invalid path : " + path.toString());
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * @return returns command name
     */
    @Override
    public String getCommandName() {
        return "tree";
    }

    /**
     * @return returns command description
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new LinkedList<>();
        description.add("Displays tree structure of directory");
        description.add("");
        description.add("tree path");
        description.add("");

        return Collections.unmodifiableList(description);
    }
}
