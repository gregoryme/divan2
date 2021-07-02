package me.readln.etc.game;

import com.googlecode.lanterna.input.KeyStroke;
import me.readln.etc.ui.UiDisplay;
import me.readln.etc.ui.UiInteraction;

import java.io.IOException;

public class Game {

    private final String MSG_THANK_YOU_SCORE = "Thank you! Your score: ";

    private UiInteraction uiInteraction; // process for interaction with user
    private UiDisplay uiDisplay;         // process for visualization on terminal

    private GameEnvironment gameEnvironment;
    private GameEngine gameEngine;

    public Game() {
    }

    public void start() {

        // game environment init
        gameEnvironment = new GameEnvironment();

        // processes init
        uiInteraction = new UiInteraction();
        uiInteraction.init(gameEnvironment.getGameScreen().getTerminal(), (KeyStroke keyStroke) -> {
            gameEnvironment.getKeyStrokes().set(0, keyStroke);
        } );
        uiInteraction.start();
        uiDisplay = new UiDisplay();
        uiDisplay.init(gameEnvironment.getGameScreen(), gameEnvironment.getGameField());
        uiDisplay.start();

        // start game
        gameEngine = initGameEngine();
        gameEngine.gameEngine();

        // preparation for final steps

        uiDisplay.setGameOver(true);

        try {
            uiDisplay.join();
            uiInteraction.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // final steps
        finalMatters();
    }

    private GameEngine initGameEngine () {
        GameEngine gameEngine = new GameEngine(gameEnvironment);
        return gameEngine;
    }

    private void finalMatters () {
        String finalString = MSG_THANK_YOU_SCORE + gameEnvironment.getGameField().getScore();
        int finalStringX = gameEnvironment.getGameScreen().getGameScreenSizeCols() / 2 - finalString.length() / 2;
        int finalStringY = gameEnvironment.getGameScreen().getGameScreenSizeRows() / 2;
        gameEnvironment.getGameScreen().clear();
        gameEnvironment.getGameScreen().displayString(finalStringX, finalStringY, finalString);
        gameEnvironment.getGameScreen().refresh();

        // press any key
        try {
            KeyStroke keyStroke = gameEnvironment.getGameScreen().getTerminal().readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameEnvironment.getGameScreen().clear();
        gameEnvironment.getGameScreen().refresh();
        gameEnvironment.getGameScreen().close();
    }

}
