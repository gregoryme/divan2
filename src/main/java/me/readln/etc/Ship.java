package me.readln.etc;

// This class is independent of output devices

import lombok.Data;

@Data
public class Ship extends Entity {

    private Shell shell;
    private static int numberMissedShips = 0;

    private static final long PAUSE_MAKE_STEP = 500; // ms.
    private static long previsionTime = System.currentTimeMillis();
    private static long currentTime = previsionTime;
    public static boolean isTime () {
        boolean time = false;
        currentTime = System.currentTimeMillis();
        if (currentTime >= previsionTime + PAUSE_MAKE_STEP) {
            previsionTime = currentTime;
            time = true;
        }

        return time;
    }

    public static int getNumberMissedShips() {
        return numberMissedShips;
    }

    public Ship(int edgeX, int edgeY) {
        super();
        setEdgeX(edgeX); // cols
        setEdgeY(edgeY); // rows
        shipInit();
        shell = new ShellShip();
        setWeight(edgeY / shell.getShellPicture().length());
    }

    private void shipInit() {
        setY(1);
        this.setX ( (int) (Math.random() * getEdgeX()) );
    }

    @Override
    public int getLength() {
        return shell.getShellPicture().length();
    }

    @Override
    public void makeStep() {

        if (isVisible()) {
            if (getY() >= getEdgeY()) {
                setVisible(false);
                Ship.numberMissedShips++;
            } else {
                moveDown();
            }
        }

    }

    @Override
    public Shell getShell() {
        return shell;
    }

}
