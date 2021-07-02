package me.readln.etc.func;

import com.googlecode.lanterna.input.KeyStroke;

@FunctionalInterface
public interface TranslateInteraction {
    public void transferInteraction (KeyStroke keyStroke);
}
