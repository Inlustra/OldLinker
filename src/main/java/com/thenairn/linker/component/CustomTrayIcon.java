/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenairn.linker.component;

import com.thenairn.linker.entity.Images;
import static com.thenairn.linker.entity.Images.getImage;
import com.thenairn.linker.ui.config.ConfigurationPanel;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Tom
 */
public class CustomTrayIcon {

    private TrayIcon tray;

    public CustomTrayIcon() {
        try {
            tray = new TrayIcon(resizeImage(getImage(Images.DONE)));
            SystemTray.getSystemTray().add(tray);
            PopupMenu menu = new PopupMenu();
            MenuItem item = new MenuItem("Settings");
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = ConfigurationPanel.getInstance();
                    frame.setVisible(true);
                }
            });
            menu.add(item);
            tray.setPopupMenu(menu);
        } catch (IOException | AWTException ex) {
            Logger.getLogger(CustomTrayIcon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setImage(int image) {
        try {
            this.getTrayIcon().setImage(resizeImage(getImage(image)));
        } catch (IOException ex) {
            Logger.getLogger(CustomTrayIcon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TrayIcon getTrayIcon() {
        return tray;
    }

    public static Image resizeImage(BufferedImage image) {
        int trayIconWidth = new TrayIcon(image).getSize().width;
        return image.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH);
    }
    
    public void end() {
        SystemTray.getSystemTray().remove(tray);
    }
}
