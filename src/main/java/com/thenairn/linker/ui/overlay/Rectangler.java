/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenairn.linker.ui.overlay;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Tom
 */
abstract class Rectangler implements MouseListener, MouseMotionListener {

    int x1;
    int x2;
    int y1;
    int y2;
    int x;
    int y;
    int w;
    int h;

    @Override
    public void mouseClicked(final MouseEvent event) {
        createRectangle();
    }

    @Override
    public void mousePressed(final MouseEvent event) {
        this.x2 = this.x1 = event.getX();
        this.y2 = this.y1 = event.getY();
        createRectangle();
    }

    @Override
    public void mouseReleased(final MouseEvent event) {
        this.x2 = event.getX();
        this.y2 = event.getY();
        createRectangle();
    }

    @Override
    public void mouseEntered(final MouseEvent event) {
    }

    @Override
    public void mouseExited(final MouseEvent event) {
    }

    @Override
    public void mouseDragged(final MouseEvent event) {
        this.x2 = event.getX();
        this.y2 = event.getY(); // call repaint which calls paint repaint();
        createRectangle();
    }

    @Override
    public void mouseMoved(final MouseEvent event) {
    }

    private void createRectangle() {
        int width = this.x1 - this.x2;
        int height = this.y1 - this.y2;
        this.w = Math.abs(width);
        this.h = Math.abs(height);
        this.x = width < 0 ? this.x1 : this.x2;
        this.y = height < 0 ? this.y1 : this.y2;
        newRectangle(new Rectangle(x, y, w, h));
    }

    abstract void newRectangle(Rectangle rect);

}
