package me.readln.etc;

import java.io.IOException;

// The purpose of this class is constantly refreshing the gameScreen
// using information from the gameField

public class UiDisplay extends Thread {

    private final static int REFRESH_PAUSE_TIME = 10; // ms

    private GameScreen gameScreen;
    private GameField gameField;
    volatile private boolean gameOver = false;

    @Override
    public void run() {
        try {
            doBusiness();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(GameScreen gameScreen, GameField gameField) {
        this.gameScreen = gameScreen;
        this.gameField = gameField;
    }

    private void doBusiness () throws IOException {

        do {
            try {
                Thread.sleep(REFRESH_PAUSE_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            gameScreen.clear();
            gameField.displayAllObjects((x, y , image) -> {
                        gameScreen.displayString(x, y, image);
                    } );
            gameScreen.displayStatusBar(bakeStatusString());
            gameScreen.refresh();
        } while (!gameOver);

    }

    private String bakeStatusString () {

        String statusString =
                "ver 2.|ESC=quit| SCORE: " + gameField.getScore() +
                        " | Missed: " + Ship.getNumberMissedShips() +
                        " | Bullets: " + Bullet.getUsedBullets() +
                        " | Crushes " + gameField.getNumberCollisionSofaAndShips();

        int delta = gameScreen.getGameScreenSizeCols() - statusString.length();

        if (delta > 0) {
            String spacer = new String(new char[delta-1]).replace('\0', ' ');
            statusString = statusString.concat(spacer);
        }

        return statusString;

    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

}
