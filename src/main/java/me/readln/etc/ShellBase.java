package me.readln.etc;

// This class is independent of output devices

import lombok.Data;

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
