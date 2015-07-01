/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenairn.linker.ui.overlay;

import com.sun.awt.AWTUtilities;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JFrame;

/**
 *
 * @author Tom
 */
public class TransparentOverlay extends JFrame {

    private Rectangle screen;

    public TransparentOverlay() {
        screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        final OverlayPanel panel = new OverlayPanel();
        Rectangler rectangler = new Rectangler() {

            @Override
            void newRectangle(Rectangle rect) {
                panel.setRectangle(rect);
                panel.repaint();
            }
        };
        panel.addMouseListener(rectangler);
        panel.addMouseMotionListener(rectangler);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        this.setUndecorated(true);
        AWTUtilities.setWindowOpacity(this, 0.10f);
    }



}
