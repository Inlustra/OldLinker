/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenairn.linker.entity;

import java.awt.event.KeyEvent;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class KeyData implements Serializable {

    private final int modifiers;
    private final int keyCode;

    public KeyData(KeyEvent e) {
        this(e.getModifiers(), isModifier(e.getKeyCode()) ? -1 : e.getKeyCode());

    }

    public KeyData(int modifiers, int keyCode) {
        this.modifiers = modifiers;
        this.keyCode = keyCode;
    }

    public int getModifiers() {
        return modifiers;
    }

    public int getKeyCode() {
        return keyCode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(KeyEvent.getKeyModifiersText(modifiers));
        if (modifiers != 0) {
            builder.append("+");
        }
        if (keyCode != -1) {
            builder.append(KeyEvent.getKeyText(keyCode));
        }
        return builder.toString();
    }

    public boolean isValidShortcut() {
        return modifiers != 0 && keyCode != -1;
    }

    private static boolean isModifier(int keyCode) {
        switch (keyCode) {
            case KeyEvent.ALT_DOWN_MASK:
            case KeyEvent.ALT_MASK:
            case KeyEvent.CTRL_DOWN_MASK:
            case KeyEvent.CTRL_MASK:
            case KeyEvent.SHIFT_MASK:
            case KeyEvent.SHIFT_DOWN_MASK:
            case KeyEvent.VK_WINDOWS:
            case KeyEvent.VK_CONTROL:
            case KeyEvent.VK_SHIFT:
            case KeyEvent.VK_ALT:
            case KeyEvent.VK_ALT_GRAPH:
                return true;
        }
        return false;
    }

}
