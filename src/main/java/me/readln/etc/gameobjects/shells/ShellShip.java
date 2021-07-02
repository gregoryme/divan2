package me.readln.etc.gameobjects.shells;

// This class is independent of output devices

import me.readln.etc.gameobjects.shells.ShellBase;

public class ShellShip extends ShellBase {

    private final String SHELL_PICTURE_BASE_SYMBOL = "#";
    private final int    SHELL_PICTURE_MAX_SYMBOL  =  10;
    private String shellPicture = SHELL_PICTURE_BASE_SYMBOL; // default

    public ShellShip() {
        super();
        int randomPictureLength = (int) (Math.random() * SHELL_PICTURE_MAX_SYMBOL);
        for (int i = 0; i < randomPictureLength; i++) {
            shellPicture += SHELL_PICTURE_BASE_SYMBOL;
        }
    }

    @Override
    public String getShellPicture() {
        return shellPicture;
    }

}
