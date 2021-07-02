package me.readln.etc;

// This class is independent of output devices

import lombok.Data;

@Data
public class Entity implements GameObject {

    private int y;     // coordinate on "rows"
    private int x;     // coordinate on "cols"
    private int edgeY; // maximum on "rows"
    private int edgeX; // maximum on "cols"
    private Orientation orientation = Orientation.UP;
    private boolean visible = true;
    private int weight = 0;

    //  (x, y) ------------|
    //  |                  |
    //  |                  |
    //  |__________________|

    private Shell shell;

    public Entity() {
    }

    public Entity(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public void makeStep() {

    }

    public int getLength () {
        return 0;
    }

    public void moveUp() {
       y -= 1;
    }

    public void moveDown() {
        y += 1;
    }

    public void moveLeft() {
        x -= 1;
    }

    public void moveRight() {
        x += 1;
    }

}
