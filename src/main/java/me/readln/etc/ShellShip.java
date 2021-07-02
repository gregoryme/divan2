package me.readln.etc;

// This class is independent of output devices

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
