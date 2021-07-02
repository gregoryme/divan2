package me.readln.etc.gameobjects.shells;

// This class is independent of output devices

import me.readln.etc.gameobjects.shells.ShellBase;

public class ShellBullet extends ShellBase {

    private final String SHELL_PICTURE_BASE_SYMBOL = "*";
    private String shellPicture = SHELL_PICTURE_BASE_SYMBOL; // default

    @Override
    public String getShellPicture() {
        return shellPicture;
    }

}
