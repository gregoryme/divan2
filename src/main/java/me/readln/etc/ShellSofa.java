package me.readln.etc;

// This class is independent of output devices

public class ShellSofa extends ShellBase {

    private final String IMAGE_UP    = "_-^-_";
    private final String IMAGE_DOWN  = "--.--";
    private final String IMAGE_LEFT  = "<----";
    private final String IMAGE_RIGHT = "---->";

    private String shellPicture = IMAGE_UP; // default

    @Override
    public void setOrientation(Orientation orientation) {
        shellPicture = switch (orientation) {
            case DOWN  -> IMAGE_DOWN;
            case LEFT  -> IMAGE_LEFT;
            case RIGHT -> IMAGE_RIGHT;
            default -> {
                yield IMAGE_UP;
            }
        };
    }

    @Override
    public String getShellPicture() {
        return shellPicture;
    }

}
