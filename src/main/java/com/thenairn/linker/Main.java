/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenairn.linker;

import com.melloware.jintellitype.IntellitypeListener;
import com.melloware.jintellitype.JIntellitype;
import com.thenairn.linker.component.CustomTrayIcon;
import com.thenairn.linker.config.LinkerConfig;
import com.thenairn.linker.entity.Shortcut;
import com.thenairn.linker.entity.ShortcutListener;
import com.thenairn.linker.ui.overlay.TransparentOverlay;
import javax.swing.JFrame;

/**
 *
 * @author Tom
 */
public class Main {

    private static CustomTrayIcon tray;

    public static void main(String[] args) {
        LinkerConfig.init();
        tray = new CustomTrayIcon();
        JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL, '#');
        LinkerConfig.registerGlobalShortcut(LinkerConfig.LAUNCH_OVERLAY, new ShortcutListener() {

            @Override
            public void onShortcut(Shortcut shortcut) {
                System.out.println("CALLED");
                JFrame frame = new TransparentOverlay();
                frame.pack();
                frame.setVisible(true);
            }
        });
        //JFrame frame = new ConfigurationPanel();
    }

}
