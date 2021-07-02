package me.readln.etc.ui;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;
import me.readln.etc.func.TranslateInteraction;

import java.io.IOException;

// The purpose of this class is to read what
// the user is pressing

public class UiInteraction extends Thread  {

    Terminal terminal;
    TranslateInteraction translateInteraction;
    private boolean gameOver = false;

    @Override
    public void run() {
        try {
            doBusiness();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(Terminal terminal, TranslateInteraction translateInteraction) {
        this.terminal = terminal;
        this.translateInteraction = translateInteraction;
    }

    private void doBusiness () throws IOException {

        do {
            KeyStroke keyStroke = terminal.readInput();
            translateInteraction.transferInteraction(keyStroke);
            if (keyStroke.getKeyType() == KeyType.Escape) {
                gameOver = true; // the GAME OVER situation

            }
        } while(!gameOver);
    }
}
