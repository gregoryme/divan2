package me.readln.etc;

// This class is independent of output devices

public class Sofa extends Entity {

    private Shell shell;

    public Sofa(int y, int x, int edgeX, int edgeY) {
        super(y, x);
        shell = new ShellSofa();
        setEdgeX(edgeX);
        setEdgeY(edgeY); // @ToDo, to one constr
    }

    @Override
    public Shell getShell() {
        return shell;
    }

    @Override
    public int getLength () {
        return shell.getShellPicture().length();
    }

    @Override
    public void setOrientation(Orientation orientation) {
        super.setOrientation(orientation);
        shell.setOrientation(orientation);
    }

    public int getGunpointX () {
        return switch (getOrientation()) {
            case UP, DOWN -> getX() + getLength() / 2;
            case LEFT -> getX() - 1;
            default -> {
                yield getX() + getLength();
            }
        };
    }

    public int getGunpointY () {
        return switch (getOrientation()) {
            case UP -> getY() - 1;
            case DOWN -> getY() + 1;
            default -> {
                yield getY();
            }
        };
    }

    // @ToDo, for refactoring
    private boolean isInsideScreen() {
        boolean isInside = true;
        if (getY() > getEdgeY()-2 || getY() < 0 || getX() > getEdgeX()-getLength() || getX() < 0) isInside = false;

        return isInside;
    }

    // controlling the situation when screen sizes are changed
    // ver 0.1
    @Override
    public void makeStep() {
        if (!isInsideScreen()) {
            setX(getEdgeX() / 2 - getLength() / 2);
            setY(getEdgeY() - 2);
        }
    }

}