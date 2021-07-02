package me.readln.etc;

// This class is independent of output devices

public class ShellBullet extends ShellBase {

    private final String SHELL_PICTURE_BASE_SYMBOL = "*";
    private String shellPicture = SHELL_PICTURE_BASE_SYMBOL; // default

    @Override
    public String getShellPicture() {
        return shellPicture;
    }

}
