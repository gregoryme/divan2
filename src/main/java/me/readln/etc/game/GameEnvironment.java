package me.readln.etc.game;

import com.googlecode.lanterna.input.KeyStroke;
import me.readln.etc.gameobjects.Sofa;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameEnvironment {

    private GameField gameField;
    private GameScreen gameScreen;
    private Sofa sofa; // is gameField[0] by default
    private CopyOnWriteArrayList<KeyStroke> keyStrokes;

    public GameEnvironment() {

        gameField = initGameField();
        gameScreen = initGameScreen();
        sofa = initSofa();
        keyStrokes = initKeyStrokesArray();

    }

    private GameField initGameField () {
        GameField gameField = new GameField();
        return gameField;
    }

    private GameScreen initGameScreen () {

        GameScreen gameScreen = new GameScreen();
        gameScreen.init((rows, cols) -> {gameField.setNewEdgesForAllObjects(cols, rows);});

        return gameScreen;
    }

    private Sofa initSofa () {

        Sofa sofa = new Sofa( - 10, -10, gameScreen.getGameScreenSizeCols(), gameScreen.getGameScreenSizeRows());
        sofa.setX(gameScreen.getGameScreenSizeCols() / 2 - sofa.getLength() / 2);
        sofa.setY(gameScreen.getGameScreenSizeRows() - 2);
        gameField.addGameObject(sofa);
        return sofa;

    }

    private CopyOnWriteArrayList<KeyStroke> initKeyStrokesArray () {
        CopyOnWriteArrayList<KeyStroke> keyStrokes = new CopyOnWriteArrayList<>();
        keyStrokes.add(null);
        return keyStrokes;
    }

    public GameField getGameField() {
        return gameField;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public Sofa getSofa() {
        return sofa;
    }

    public CopyOnWriteArrayList<KeyStroke> getKeyStrokes() {
        return keyStrokes;
    }
}
