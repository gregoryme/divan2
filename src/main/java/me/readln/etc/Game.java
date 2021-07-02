package me.readln.etc;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {

    private CopyOnWriteArrayList<KeyStroke> keyStrokes;
    private GameScreen gameScreen;

    private GameField gameField;
    private Sofa sofa;
    private UiInteraction uiInteraction;
    private UiDisplay uiDisplay;

    long previsionTime = System.currentTimeMillis();
    long currentTime = previsionTime;
    final long PAUSE_IN_SHIP_GENERATION = 1000 * 2; // sec.

    public Game() {
    }

    public void start() {
        init();
        gameEngine();
        finalMatters();
    }

    private void init() {

        gameScreen = new GameScreen();
        gameField = new GameField();
        gameScreen.init((rows, cols) -> {gameField.setNewEdgesForAllObjects(cols, rows);});

        sofa = new Sofa( - 10, -10, gameScreen.getGameScreenSizeCols(), gameScreen.getGameScreenSizeRows());
        sofa.setX(gameScreen.getGameScreenSizeCols() / 2 - sofa.getLength() / 2);
        sofa.setY(gameScreen.getGameScreenSizeRows() - 2);
        gameField.addGameObject(sofa);

        keyStrokes = new CopyOnWriteArrayList<>();
        keyStrokes.add(null);

        uiInteraction = new UiInteraction();
        uiInteraction.init(gameScreen.getTerminal(), (KeyStroke keyStroke) -> {
            keyStrokes.set(0, keyStroke);
        } );

        uiInteraction.start();

        uiDisplay = new UiDisplay();
        uiDisplay.init(gameScreen, gameField);
        uiDisplay.start();

    }

    private void gameEngine() {
        boolean gameOver = false;
        do {

            gameField.makeStepAllObjects();
            gameField.analyzeCollisionsAllObjects();
            if (isTimeForShipGenerate()) {
                gameField.addGameObject(shipGenerator());
            }

            KeyStroke keyStroke = keyStrokes.get(0);
            if (keyStroke != null) {

                KeyType keyType = keyStroke.getKeyType();
                keyStrokes.set(0, null);
                if (keyType == KeyType.Character) {
                    charactersMatter(keyStroke.getCharacter());
                }

                movementsMatter(keyType);
                if (keyType == KeyType.Escape) {
                    gameOver = true;
                    uiDisplay.setGameOver(true);
                }
            }
        } while(!gameOver);

        try {
            uiDisplay.join();
            uiInteraction.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void charactersMatter(char ch) {
        switch (Character.toLowerCase(ch)) {
            case ' ' -> {
                Bullet bullet = new Bullet(sofa.getGunpointY(),
                        sofa.getGunpointX(),
                        gameScreen.getGameScreenSizeCols(),
                        gameScreen.getGameScreenSizeRows());
                bullet.setOrientation(sofa.getOrientation());
                gameField.addGameObject(bullet);
            }
            case 'w' -> sofa.setOrientation(Orientation.UP);
            case 'd' -> sofa.setOrientation(Orientation.RIGHT);
            case 's' -> sofa.setOrientation(Orientation.DOWN);
            case 'a' -> sofa.setOrientation(Orientation.LEFT);
        }
    }

    private void movementsMatter (KeyType keyType) {
        if (keyType == KeyType.ArrowUp    && sofa.getY() > 0) sofa.moveUp();
        if (keyType == KeyType.ArrowLeft  && sofa.getX() > 0) sofa.moveLeft();
        if (keyType == KeyType.ArrowDown  && sofa.getY() < sofa.getEdgeY() - 2) sofa.moveDown();
        if (keyType == KeyType.ArrowRight && sofa.getX() < sofa.getEdgeX() - sofa.getLength()) sofa.moveRight();

    }

    private void finalMatters () {
        String finalString = "Thank you! Your score: " + gameField.getScore();
        int finalStringX = gameScreen.getGameScreenSizeCols() / 2 - finalString.length() / 2;
        int finalStringY = gameScreen.getGameScreenSizeRows() / 2;

        gameScreen.clear();
        gameScreen.displayString(finalStringX, finalStringY, finalString);
        gameScreen.refresh();

        try {
            KeyStroke keyStroke = gameScreen.getTerminal().readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameScreen.clear();
        gameScreen.refresh();
        gameScreen.close();
    }

    private boolean isTimeForShipGenerate() {
        boolean isTime = false;
        currentTime = System.currentTimeMillis();
        if (currentTime >= previsionTime + PAUSE_IN_SHIP_GENERATION) {
            previsionTime = currentTime;
            isTime = true;
        }

        return isTime;
    }

    private Ship shipGenerator () {
        return new Ship(gameScreen.getGameScreenSizeCols(), gameScreen.getGameScreenSizeRows());
    }

}
