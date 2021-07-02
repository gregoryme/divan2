package me.readln.etc.gameobjects.shells;

// This class is independent of output devices

import lombok.Data;
import me.readln.etc.enums.Orientation;
import me.readln.etc.func.DisplayShell;

@Data
public class ShellBase implements Shell {

    private String shellPicture;

    public ShellBase() {
    }

    public void setOrientation(Orientation orientation) {
    }

    public void displayShell(int x, int y, DisplayShell displayShell) {
        displayShell.display(x, y, this.getShellPicture());
    }

}
