package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Setups environment to communication with shell program and models of commands
 */
public interface Environment {

    /**
     * Reads line from specified input
     *
     * @return returns line read from input
     * @throws ShellIOException if there is problem with reading next input line
     */
    String readLine() throws ShellIOException;

    /**
     * Writes text to specified output
     *
     * @param text content which is written to output
     * @throws ShellIOException if there is problem with writing text to output
     */
    void write(String text) throws ShellIOException;

    /**
     * Writes next line of given text to specified output
     *
     * @param text content which is written to output
     * @throws ShellIOException if there is problem with writing text to output
     */
    void writeln(String text) throws ShellIOException;

    /**
     * @return Returns map of all implemented commands
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * @return Returns multiline symbol
     */
    Character getMultilineSymbol();

    /**
     * Sets multiline symbol
     *
     * @param symbol new multiline symbol
     */
    void setMultilineSymbol(Character symbol);

    /**
     * @return Returns prompt symbol
     */
    Character getPromptSymbol();

    /**
     * Sets prompt symbol
     *
     * @param symbol new prompt symbol
     */
    void setPromptSymbol(Character symbol);

    /**
     * @return Returns more lines symbol
     */
    Character getMorelinesSymbol();

    /**
     * Sets more lines symbol
     *
     * @param symbol new more lines symbol
     */
    void setMorelinesSymbol(Character symbol);
}
