package me.readln.etc.game;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import me.readln.etc.func.inCaseOfChangeSizes;

import java.io.IOException;

public class GameScreen {

    private final static TextColor DEFAULT_TEXT_COLOR = TextColor.ANSI.WHITE;
    private final static TextColor DEFAULT_BACKGROUND_COLOR = TextColor.ANSI.BLACK;

    private int gameScreenSizeRows;
    private int gameScreenSizeCols;
    private Terminal terminal;
    private TerminalSize terminalSize;
    private TextGraphics textGraphics;
    private TextColor foregroundColor = DEFAULT_TEXT_COLOR;
    private TextColor backgroundColor = DEFAULT_BACKGROUND_COLOR;

    private inCaseOfChangeSizes sizesMatter;

    public GameScreen() {

    }

    public GameScreen(TextColor backgroundColor, TextColor textColor) {

        this.backgroundColor = backgroundColor;
        this.foregroundColor = textColor;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void init(inCaseOfChangeSizes sizesMatter) {

        this.sizesMatter = sizesMatter;

        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initResizeListener();
    }

    private void initialize() throws IOException {

        terminal = new DefaultTerminalFactory().createTerminal();
        terminal.setCursorVisible(false);
        terminalSize = terminal.getTerminalSize();
        textGraphics = terminal.newTextGraphics();

        textGraphics.setBackgroundColor(backgroundColor);
        textGraphics.setForegroundColor(foregroundColor);

        gameScreenSizeRows = terminalSize.getRows();
        gameScreenSizeCols = terminalSize.getColumns();
        sizesMatter.doItInCaseOfChangeSizes(gameScreenSizeRows, gameScreenSizeCols);

    }

    private void initResizeListener() {
        terminal.addResizeListener((terminal1, newSize) -> {
            gameScreenSizeRows = newSize.getRows();
            gameScreenSizeCols = newSize.getColumns();
            sizesMatter.doItInCaseOfChangeSizes(gameScreenSizeRows, gameScreenSizeCols);
            try {
                terminal1.flush();
            }
            catch(IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void displayStatusBar (String statusBarContent) {
        textGraphics.putString(1,gameScreenSizeRows - 1, statusBarContent, SGR.BOLD, SGR.REVERSE);
    }

    public void displayString(int x, int y, String string) {
        textGraphics.putString(x, y, string);
    }

    public void clear() {
        try {
            terminal.clearScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        try {
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {

        try {
            terminal.setCursorVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            terminal.resetColorAndSGR();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getGameScreenSizeRows() {
        return gameScreenSizeRows;
    }

    public int getGameScreenSizeCols() {
        return gameScreenSizeCols;
    }

}
