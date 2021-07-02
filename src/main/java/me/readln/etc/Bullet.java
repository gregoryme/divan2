package me.readln.etc;

// This class is independent of output devices

public class Bullet extends Entity {

    private Shell shell;
    private static int usedBullets = 0;

    private static final long PAUSE_MAKE_STEP = 50; // ms.
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

    public Bullet(int y, int x, int edgeX, int edgeY) {
        super(y, x);
        setEdgeX(edgeX); // cols
        setEdgeY(edgeY); // rows
        shell = new ShellBullet();
        usedBullets++;
    }

    // @ Todo -- в общий
    private boolean isInsideScreen() {
        boolean isInside = true;
        if (getY() > getEdgeY() || getY() < 0 || getX() > getEdgeX() || getX() < 0) isInside = false;

        return isInside;
    }

    @Override
    public void makeStep() {
        if (isVisible()) {
                if (!isInsideScreen()) {
                    setVisible(false);
                } else {
                    switch (getOrientation()) {
                        case DOWN -> moveDown();
                        case LEFT -> moveLeft();
                        case RIGHT -> moveRight();
                        default -> moveUp();
                }
            }
        }
    }

    public static int getUsedBullets() {
        return usedBullets;
    }

    @Override
    public Shell getShell() {
        return shell;
    }

}
