package me.readln.etc.gameobjects;

// This class is independent of output devices

import lombok.Data;
import me.readln.etc.gameobjects.shells.Shell;
import me.readln.etc.gameobjects.shells.ShellShip;

@Data
public class Ship extends Entity {

    private Shell shell;
    private static int numberMissedShips = 0;

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

    // pause generation matter

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

    private static long previsionTimeGen = System.currentTimeMillis();
    private static long currentTimeGen = previsionTimeGen;
    private static long PAUSE_IN_SHIP_GENERATION = 1000 * 2; // sec.

    public static boolean isTimeForShipGenerate() {
        boolean isTime = false;
        currentTimeGen = System.currentTimeMillis();
        if (currentTimeGen >= previsionTimeGen + PAUSE_IN_SHIP_GENERATION) {
            previsionTimeGen = currentTimeGen;
            isTime = true;
        }

        return isTime;
    }

}
