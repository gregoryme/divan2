package me.readln.etc.game;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import me.readln.etc.enums.Orientation;
import me.readln.etc.gameobjects.Bullet;
import me.readln.etc.gameobjects.Ship;

public class GameEngine {

    GameEnvironment gameEnvironment;

    public GameEngine(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    public void gameEngine() {

        boolean gameOver = false;
        do {

            gameEnvironment.getGameField().makeStepAllObjects();

            gameEnvironment.getGameField().analyzeCollisionsAllObjects();

            if (Ship.isTimeForShipGenerate()) {
                gameEnvironment.getGameField().addGameObject(shipGenerator());
            }

            KeyStroke keyStroke = gameEnvironment.getKeyStrokes().get(0);
            if (keyStroke != null) {

                KeyType keyType = keyStroke.getKeyType();
                gameEnvironment.getKeyStrokes().set(0, null);

                if (keyType == KeyType.Character) {
                    actionWhenCharacterPressed(keyStroke.getCharacter());
                }

                gameOver = actionWhenControlKeyPressed(keyType);

            }
        } while(!gameOver);

    }

    private void actionWhenCharacterPressed (char ch) {
        switch (Character.toLowerCase(ch)) {
            case ' ' -> {
                Bullet bullet = new Bullet(gameEnvironment.getSofa().getGunpointY(),
                        gameEnvironment.getSofa().getGunpointX(),
                        gameEnvironment.getGameScreen().getGameScreenSizeCols(),
                        gameEnvironment.getGameScreen().getGameScreenSizeRows());
                bullet.setOrientation(gameEnvironment.getSofa().getOrientation());
                gameEnvironment.getGameField().addGameObject(bullet);
            }
            case 'w' -> gameEnvironment.getSofa().setOrientation(Orientation.UP);
            case 'd' -> gameEnvironment.getSofa().setOrientation(Orientation.RIGHT);
            case 's' -> gameEnvironment.getSofa().setOrientation(Orientation.DOWN);
            case 'a' -> gameEnvironment.getSofa().setOrientation(Orientation.LEFT);
        }
    }

    private boolean actionWhenControlKeyPressed (KeyType keyType) {

        if (keyType == KeyType.Escape) {
            return true; // game over situation
        }

        int sofaX = gameEnvironment.getSofa().getX();
        int sofaY = gameEnvironment.getSofa().getY();
        int edgeX = gameEnvironment.getSofa().getEdgeX();
        int edgeY = gameEnvironment.getSofa().getEdgeY();
        int sofaLength = gameEnvironment.getSofa().getLength();

        if (keyType == KeyType.ArrowUp    && sofaY > 0) gameEnvironment.getSofa().moveUp();
        if (keyType == KeyType.ArrowLeft  && sofaX > 0) gameEnvironment.getSofa().moveLeft();
        if (keyType == KeyType.ArrowDown  && sofaY < edgeY - 2) gameEnvironment.getSofa().moveDown();
        if (keyType == KeyType.ArrowRight && sofaX < edgeX - sofaLength) gameEnvironment.getSofa().moveRight();

        return false;

    }

    private Ship shipGenerator () {
        return new Ship(gameEnvironment.getGameScreen().getGameScreenSizeCols(),
                gameEnvironment.getGameScreen().getGameScreenSizeRows());
    }

}
