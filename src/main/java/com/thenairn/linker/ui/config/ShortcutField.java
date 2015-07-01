/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenairn.linker.ui.config;

import com.thenairn.linker.entity.KeyData;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

/**
 *
 * @author Tom
 */
public class ShortcutField extends JTextField implements KeyListener {

    private KeyData data;

    public ShortcutField(KeyData data) {
        this();
        if (data != null) {
            this.data = data;
            this.setText(data.toString());
        }
    }

    public ShortcutField() {
        this.setEditable(false);
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.data = new KeyData(e);
        this.setText(data.toString());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_PRINTSCREEN) {
            this.data = new KeyData(e);
            this.setText(data.toString());
        }
    }

    public KeyData getData() {
        return this.data;
    }

}
