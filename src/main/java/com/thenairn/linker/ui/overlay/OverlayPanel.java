/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenairn.linker.ui.overlay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 *
 * @author Tom
 */
public class OverlayPanel extends JPanel {

    private Rectangle rect;
    private Rectangle screen;

    public OverlayPanel() {
        int width = 0;
        int height = 0;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        for (GraphicsDevice curGs : gs) {
            DisplayMode dm = curGs.getDisplayMode();
            width += dm.getWidth();
            height += dm.getHeight();
        }
        screen = new Rectangle(0, 0, width, height);
        rect = new Rectangle(0, 0, 0, 0);
        this.setPreferredSize(new Dimension((int) screen.getWidth(), (int) screen.getHeight()));
    }

    public void setRectangle(Rectangle rect) {
        this.rect = rect;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(screen.x, screen.y, screen.width, screen.height);
        g.clearRect(rect.x, rect.y, rect.width, rect.height);
    }

}
