package me.readln.etc.gameobjects.shells;

import me.readln.etc.enums.Orientation;
import me.readln.etc.func.DisplayShell;

public interface Shell {

    public void displayShell(int x, int y, DisplayShell displayShell);
    public String getShellPicture();
    public void setOrientation(Orientation orientation);

}
