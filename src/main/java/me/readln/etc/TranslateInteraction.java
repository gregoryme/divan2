package me.readln.etc;

import com.googlecode.lanterna.input.KeyStroke;

@FunctionalInterface
public interface TranslateInteraction {
    public void transferInteraction (KeyStroke keyStroke);
}
