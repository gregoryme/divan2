package me.readln.etc;

public interface GameObject {

    public int getY();
    public void setY(int y);
    public int getX();
    public void setX(int x);
    public Shell getShell();
    public Orientation getOrientation();
    public void setOrientation(Orientation orientation);
    public void makeStep();
    public boolean isVisible();
    public int getLength ();
    public void setEdgeY(int edgeY);
    public void setEdgeX(int edgeX);
    public void setVisible(boolean visible);
    public int getWeight();

}
