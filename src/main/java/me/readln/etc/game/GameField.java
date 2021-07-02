package me.readln.etc.game;

import me.readln.etc.func.DisplayShell;
import me.readln.etc.gameobjects.Bullet;
import me.readln.etc.gameobjects.GameObject;
import me.readln.etc.gameobjects.Ship;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// This class is independent of output devices

// @Todo need in refactoring

public class GameField {

    private final static int MAX_OBJECTS_BEFORE_CLEAN = 1000;

    private int score = 0;
    private int numberShotShips = 0;
    private int numberCollisionSofaAndShips = 0;

    private List<GameObject> gameObjects;

    public GameField() {
        init();
    }

    private List<GameObject> bakeGameObjectsList() {
        return new CopyOnWriteArrayList<>();
    }

    private void init () {
        gameObjects = bakeGameObjectsList();
    }

    private void clear () {
        List<GameObject> newGameObjectList = bakeGameObjectsList();
        for (GameObject gameObject : gameObjects) {
            if (gameObject.isVisible()) newGameObjectList.add(gameObject);
        }
        gameObjects = newGameObjectList;
    }

    public void addGameObject (GameObject gameObject) {
        if (gameObjects.size() >= MAX_OBJECTS_BEFORE_CLEAN) {
            clear();
        }
        gameObjects.add(gameObject);
    }

    public void makeStepAllObjects () {
        boolean makeStepForShip   = Ship.isTime();
        boolean makeStepForBullet = Bullet.isTime();

        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Ship) {
                if (makeStepForShip) gameObject.makeStep();
                continue;
            }
            if (gameObject instanceof Bullet) {
                if (makeStepForBullet) gameObject.makeStep();
                continue;
            }

            gameObject.makeStep();
        }
    }

    public void displayAllObjects (DisplayShell displayShell) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.isVisible()) {
                gameObject.getShell().displayShell(gameObject.getX(), gameObject.getY(), displayShell);
            }
        }
    }

    public void setNewEdgesForAllObjects (int edgeCols, int edgeRows) {
        for (GameObject gameObject : gameObjects) {
            gameObject.setEdgeX(edgeCols);
            gameObject.setEdgeY(edgeRows);
        }
    }

    public int getScore() {
        int scoreCalc =
                score - Ship.getNumberMissedShips() -
                        Bullet.getUsedBullets() -
                        numberCollisionSofaAndShips +
                        numberShotShips * 5;
        return scoreCalc;
    }

    // analyze collisions
    // ver 0.2
    public void analyzeCollisionsAllObjects () {

        for (GameObject gameObject : gameObjects) {

            if (!gameObject.isVisible()) continue;

            if (gameObject instanceof Ship) {
                collisionCheckBetweenShipAndBullets(gameObject);
                collisionCheckBetweenShipAndSofa(gameObject);
                continue;
            }

        }
    }

    // part of analyze collisions
    // contains part of SCORE FORMULA (function the from ship.weight)
    private void collisionCheckBetweenShipAndBullets (GameObject ship) {

        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Bullet) {
                if (!gameObject.isVisible()) continue;
                if (gameObject.getY() == ship.getY()) {
                    // bullet and ship is on one line (Y)
                    if (gameObject.getX() >= ship.getX() && gameObject.getX() < ship.getX() + ship.getLength()) {
                        // it's a collision
                        ship.setVisible(false);
                        gameObject.setVisible(false);
                        numberShotShips += ship.getWeight() / 2; // PART OF SCORE FORMULA @Todo should to be refactored
                    }
                }
            }
        }
    }

    // part of analyze collisions
    private void collisionCheckBetweenShipAndSofa(GameObject ship) {
        // @Todo currently, SOFA is a [0] on default
        int sofaY = gameObjects.get(0).getY();
        int shipY = ship.getY();
        int sofaX = gameObjects.get(0).getX();
        int shipX = ship.getX();
        int shipLength = ship.getLength();
        int sofaLength = gameObjects.get(0).getLength();

        if (sofaY == shipY) {
            // sofa and ship are on one line (Y)
            if ( (sofaX >= shipX && sofaX < shipX + shipLength) ||
                    (sofaX + sofaLength > shipX && sofaX + sofaLength <= shipX + shipLength)) {
                // it's a collision
                ship.setVisible(false);
                numberCollisionSofaAndShips++;
                return;
            }
        }
    }

    public int getNumberCollisionSofaAndShips() {
        return numberCollisionSofaAndShips;
    }

}
